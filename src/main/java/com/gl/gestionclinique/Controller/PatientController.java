package com.gl.gestionclinique.Controller;

import com.gl.gestionclinique.Model.GroupeSanguin;
import com.gl.gestionclinique.Model.Patient;
import com.gl.gestionclinique.Repository.Implementation.PatientDAOImple;
import com.gl.gestionclinique.Service.PatientService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

public class PatientController implements Initializable {

    // Champs formulaire
    @FXML private TextField nomField;
    @FXML private TextField prenomField;
    @FXML private DatePicker dateNaissancePicker;
    @FXML private ComboBox<String> sexeCombo;
    @FXML private TextField telephoneField;
    @FXML private TextField adresseField;
    @FXML private ComboBox<GroupeSanguin> groupeSanguinCombo;
    @FXML private TextArea antecedentsArea;
    @FXML private TextField rechercheField;
    @FXML private Label messageLabel;

    // Tableau liste
    @FXML private TableView<Patient> tablePatients;
    @FXML private TableColumn<Patient, Integer> colId;
    @FXML private TableColumn<Patient, String> colNom;
    @FXML private TableColumn<Patient, String> colPrenom;
    @FXML private TableColumn<Patient, String> colTelephone;
    @FXML private TableColumn<Patient, String> colSexe;
    @FXML private TableColumn<Patient, GroupeSanguin> colGroupeSanguin;

    private final PatientService patientService = new PatientService(new PatientDAOImple());
    private Patient patientSelectionne = null;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        if (sexeCombo != null)
            sexeCombo.setItems(FXCollections.observableArrayList("Homme", "Femme"));
        if (groupeSanguinCombo != null)
            groupeSanguinCombo.setItems(FXCollections.observableArrayList(GroupeSanguin.values()));
        if (tablePatients != null)
            configurerTableau();
    }

    private void configurerTableau() {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colNom.setCellValueFactory(new PropertyValueFactory<>("nom"));
        colPrenom.setCellValueFactory(new PropertyValueFactory<>("prenom"));
        colTelephone.setCellValueFactory(new PropertyValueFactory<>("telephone"));
        colSexe.setCellValueFactory(new PropertyValueFactory<>("sexe"));
        colGroupeSanguin.setCellValueFactory(new PropertyValueFactory<>("groupeSanguin"));

        chargerPatients();

        tablePatients.getSelectionModel().selectedItemProperty().addListener((obs, old, selected) -> {
            if (selected != null) remplirFormulaire(selected);
        });
    }

    @FXML
    protected void ajouterPatient() {
        if (!validerFormulaire()) return;
        Patient p = construirePatient();
        patientService.ajouterPatient(p);
        afficherMessage("Patient ajouté avec succès !", true);
        viderFormulaire();
        chargerPatients();
    }

    @FXML
    protected void modifierPatient() {
        if (patientSelectionne == null) {
            afficherMessage("Sélectionnez un patient à modifier.", false);
            return;
        }
        if (!validerFormulaire()) return;
        mettreAJourPatient(patientSelectionne);
        patientService.modifierPatient(patientSelectionne);
        afficherMessage("Patient modifié avec succès !", true);
        viderFormulaire();
        chargerPatients();
    }

    @FXML
    protected void supprimerPatient() {
        Patient selected = tablePatients.getSelectionModel().getSelectedItem();
        if (selected == null) {
            afficherMessage("Sélectionnez un patient à supprimer.", false);
            return;
        }
        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION,
                "Supprimer " + selected.getNom() + " ?", ButtonType.YES, ButtonType.NO);
        confirm.showAndWait().ifPresent(btn -> {
            if (btn == ButtonType.YES) {
                patientService.supprimerPatient(selected);
                chargerPatients();
                afficherMessage("Patient supprimé.", true);
            }
        });
    }

    @FXML
    protected void rechercherPatient() {
        String terme = rechercheField.getText().trim();
        List<Patient> resultats = terme.isEmpty()
                ? patientService.tousLesPatients()
                : patientService.rechercherParNom(terme);
        tablePatients.setItems(FXCollections.observableArrayList(resultats));
    }

    @FXML
    protected void viderFormulaire() {
        if (nomField != null) nomField.clear();
        if (prenomField != null) prenomField.clear();
        if (telephoneField != null) telephoneField.clear();
        if (adresseField != null) adresseField.clear();
        if (antecedentsArea != null) antecedentsArea.clear();
        if (dateNaissancePicker != null) dateNaissancePicker.setValue(null);
        if (sexeCombo != null) sexeCombo.setValue(null);
        if (groupeSanguinCombo != null) groupeSanguinCombo.setValue(null);
        patientSelectionne = null;
    }

    private void chargerPatients() {
        List<Patient> patients = patientService.tousLesPatients();
        tablePatients.setItems(FXCollections.observableArrayList(patients));
    }

    private void remplirFormulaire(Patient p) {
        patientSelectionne = p;
        nomField.setText(p.getNom());
        prenomField.setText(p.getPrenom());
        telephoneField.setText(p.getTelephone());
        adresseField.setText(p.getAdresse());
        antecedentsArea.setText(p.getAntecedentsMedicaux());
        dateNaissancePicker.setValue(p.getDateNaissance());
        sexeCombo.setValue(p.getSexe());
        groupeSanguinCombo.setValue(p.getGroupeSanguin());
    }

    private Patient construirePatient() {
        Patient p = new Patient();
        mettreAJourPatient(p);
        return p;
    }

    private void mettreAJourPatient(Patient p) {
        p.setNom(nomField.getText().trim());
        p.setPrenom(prenomField.getText().trim());
        p.setTelephone(telephoneField.getText().trim());
        p.setAdresse(adresseField.getText().trim());
        p.setAntecedentsMedicaux(antecedentsArea.getText().trim());
        p.setDateNaissance(dateNaissancePicker.getValue());
        p.setSexe(sexeCombo.getValue());
        p.setGroupeSanguin(groupeSanguinCombo.getValue());
    }

    private boolean validerFormulaire() {
        if (nomField.getText().trim().isEmpty()) {
            afficherMessage("Le nom est obligatoire.", false);
            return false;
        }
        if (prenomField.getText().trim().isEmpty()) {
            afficherMessage("Le prénom est obligatoire.", false);
            return false;
        }
        if (telephoneField.getText().trim().isEmpty()) {
            afficherMessage("Le téléphone est obligatoire.", false);
            return false;
        }
        return true;
    }

    private void afficherMessage(String msg, boolean succes) {
        if (messageLabel != null) {
            messageLabel.setText(msg);
            messageLabel.setStyle(succes ? "-fx-text-fill: green;" : "-fx-text-fill: red;");
        }
    }
}
