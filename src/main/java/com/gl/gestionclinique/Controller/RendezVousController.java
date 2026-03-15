package com.gl.gestionclinique.Controller;

import com.gl.gestionclinique.Model.*;
import com.gl.gestionclinique.Repository.Implementation.*;
import com.gl.gestionclinique.Service.*;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.time.LocalDateTime;
import java.util.List;
import java.util.ResourceBundle;

public class RendezVousController implements Initializable {

    @FXML private ComboBox<Patient> patientCombo;
    @FXML private ComboBox<Medecin> medecinCombo;
    @FXML private DatePicker datePicker;
    @FXML private TextField heureField;
    @FXML private Label messageLabel;

    @FXML private TableView<RendezVous> tableRendezVous;
    @FXML private TableColumn<RendezVous, Integer> colId;
    @FXML private TableColumn<RendezVous, String> colPatient;
    @FXML private TableColumn<RendezVous, String> colMedecin;
    @FXML private TableColumn<RendezVous, LocalDateTime> colDateHeure;
    @FXML private TableColumn<RendezVous, StatutRendezVous> colStatut;

    private final RendezVousService rvService = new RendezVousService(new RendezVousDAOImple());
    private final PatientService patientService = new PatientService(new PatientDAOImple());
    private final MedecinService medecinService = new MedecinService(new MedecinDAOImple());

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        patientCombo.setItems(FXCollections.observableArrayList(patientService.tousLesPatients()));
        medecinCombo.setItems(FXCollections.observableArrayList(medecinService.tous()));

        if (tableRendezVous != null) {
            colId.setCellValueFactory(new PropertyValueFactory<>("id"));
            colDateHeure.setCellValueFactory(new PropertyValueFactory<>("dateHeure"));
            colStatut.setCellValueFactory(new PropertyValueFactory<>("statut"));
            charger();
        }
    }

    @FXML
    protected void planifier() {
        if (patientCombo.getValue() == null || medecinCombo.getValue() == null
                || datePicker.getValue() == null || heureField.getText().isEmpty()) {
            afficherMessage("Tous les champs sont obligatoires.", false);
            return;
        }
        try {
            String[] parts = heureField.getText().split(":");
            LocalDateTime dateHeure = datePicker.getValue()
                    .atTime(Integer.parseInt(parts[0]), Integer.parseInt(parts[1]));

            RendezVous rv = new RendezVous();
            rv.setPatient(patientCombo.getValue());
            rv.setMedecin(medecinCombo.getValue());
            rv.setDateHeure(dateHeure);

            rvService.planifier(rv);
            afficherMessage("Rendez-vous planifié avec succès !", true);
            charger();
        } catch (RuntimeException e) {
            afficherMessage(e.getMessage(), false);
        }
    }

    @FXML
    protected void annuler() {
        RendezVous selected = tableRendezVous.getSelectionModel().getSelectedItem();
        if (selected == null) { afficherMessage("Sélectionnez un rendez-vous.", false); return; }
        rvService.annuler(selected);
        afficherMessage("Rendez-vous annulé.", true);
        charger();
    }

    @FXML
    protected void afficherDuJour() {
        List<RendezVous> liste = rvService.rendezVousDuJour();
        tableRendezVous.setItems(FXCollections.observableArrayList(liste));
    }

    private void charger() {
        tableRendezVous.setItems(FXCollections.observableArrayList(rvService.tousLesRendezVous()));
    }

    private void afficherMessage(String msg, boolean succes) {
        if (messageLabel != null) {
            messageLabel.setText(msg);
            messageLabel.setStyle(succes ? "-fx-text-fill: green;" : "-fx-text-fill: red;");
        }
    }
}
