package com.gl.gestionclinique.Controller;

import com.gl.gestionclinique.Model.*;
import com.gl.gestionclinique.Repository.Implementation.*;
import com.gl.gestionclinique.Service.*;
import com.gl.gestionclinique.Utilitaire.PDF.OrdonnancePdfGenerator;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class ConsultationController implements Initializable {

    @FXML private ComboBox<Patient> patientCombo;
    @FXML private ComboBox<Medecin> medecinCombo;
    @FXML private ComboBox<RendezVous> rendezVousCombo;
    @FXML private TextArea diagnosticArea;
    @FXML private TextArea observationsArea;
    @FXML private TextArea prescriptionArea;
    @FXML private Label messageLabel;

    @FXML private TableView<Consultation> tableConsultations;
    @FXML private TableColumn<Consultation, Integer> colId;
    @FXML private TableColumn<Consultation, String> colDate;
    @FXML private TableColumn<Consultation, String> colDiagnostic;

    private final ConsultationService consultationService = new ConsultationService(new ConsultationDAOImple());
    private final PatientService patientService = new PatientService(new PatientDAOImple());
    private final MedecinService medecinService = new MedecinService(new MedecinDAOImple());
    private final RendezVousService rvService = new RendezVousService(new RendezVousDAOImple());

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        patientCombo.setItems(FXCollections.observableArrayList(patientService.tousLesPatients()));
        medecinCombo.setItems(FXCollections.observableArrayList(medecinService.tous()));
        rendezVousCombo.setItems(FXCollections.observableArrayList(rvService.tousLesRendezVous()));

        if (tableConsultations != null) {
            colId.setCellValueFactory(new PropertyValueFactory<>("id"));
            colDate.setCellValueFactory(new PropertyValueFactory<>("date"));
            colDiagnostic.setCellValueFactory(new PropertyValueFactory<>("diagnostic"));
            charger();
        }
    }

    @FXML
    protected void enregistrer() {
        if (patientCombo.getValue() == null || medecinCombo.getValue() == null) {
            afficherMessage("Patient et médecin sont obligatoires.", false);
            return;
        }
        Consultation c = new Consultation();
        c.setPatient(patientCombo.getValue());
        c.setMedecin(medecinCombo.getValue());
        c.setRendezVous(rendezVousCombo.getValue());
        c.setDiagnostic(diagnosticArea.getText());
        c.setObservations(observationsArea.getText());
        c.setPrescription(prescriptionArea.getText());

        consultationService.enregistrer(c);
        afficherMessage("Consultation enregistrée !", true);
        charger();
    }

    @FXML
    protected void genererOrdonnance() {
        Consultation selected = tableConsultations.getSelectionModel().getSelectedItem();
        if (selected == null) { afficherMessage("Sélectionnez une consultation.", false); return; }

        FileChooser fc = new FileChooser();
        fc.setTitle("Enregistrer l'ordonnance");
        fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("PDF", "*.pdf"));
        fc.setInitialFileName("ordonnance_" + selected.getId() + ".pdf");
        File file = fc.showSaveDialog(null);
        if (file == null) return;

        try {
            new OrdonnancePdfGenerator(selected).generer(file.getAbsolutePath());
            afficherMessage("Ordonnance générée : " + file.getName(), true);
        } catch (Exception e) {
            afficherMessage("Erreur génération PDF : " + e.getMessage(), false);
        }
    }

    private void charger() {
        tableConsultations.setItems(FXCollections.observableArrayList(consultationService.toutes()));
    }

    private void afficherMessage(String msg, boolean succes) {
        if (messageLabel != null) {
            messageLabel.setText(msg);
            messageLabel.setStyle(succes ? "-fx-text-fill: green;" : "-fx-text-fill: red;");
        }
    }
}
