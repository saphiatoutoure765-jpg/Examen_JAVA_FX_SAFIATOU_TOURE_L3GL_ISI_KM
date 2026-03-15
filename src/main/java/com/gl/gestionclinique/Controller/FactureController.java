package com.gl.gestionclinique.Controller;

import com.gl.gestionclinique.Model.*;
import com.gl.gestionclinique.Repository.Implementation.*;
import com.gl.gestionclinique.Service.*;
import com.gl.gestionclinique.Utilitaire.PDF.FacturePdfGenerator;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class FactureController implements Initializable {

    @FXML private ComboBox<Consultation> consultationCombo;
    @FXML private TextField montantField;
    @FXML private ComboBox<ModePaiement> modePaiementCombo;
    @FXML private Label messageLabel;

    @FXML private TableView<Facture> tableFactures;
    @FXML private TableColumn<Facture, Integer> colId;
    @FXML private TableColumn<Facture, String> colDate;
    @FXML private TableColumn<Facture, Double> colMontant;
    @FXML private TableColumn<Facture, StatutFacture> colStatut;
    @FXML private TableColumn<Facture, ModePaiement> colMode;

    private final FactureService factureService = new FactureService(new FactureDAOImple());
    private final ConsultationService consultationService = new ConsultationService(new ConsultationDAOImple());

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        consultationCombo.setItems(FXCollections.observableArrayList(consultationService.toutes()));
        modePaiementCombo.setItems(FXCollections.observableArrayList(ModePaiement.values()));

        if (tableFactures != null) {
            colId.setCellValueFactory(new PropertyValueFactory<>("id"));
            colDate.setCellValueFactory(new PropertyValueFactory<>("date"));
            colMontant.setCellValueFactory(new PropertyValueFactory<>("montantTotal"));
            colStatut.setCellValueFactory(new PropertyValueFactory<>("statut"));
            colMode.setCellValueFactory(new PropertyValueFactory<>("modePaiement"));
            charger();
        }
    }

    @FXML
    protected void genererFacture() {
        if (consultationCombo.getValue() == null || montantField.getText().isEmpty()) {
            afficherMessage("Consultation et montant sont obligatoires.", false);
            return;
        }
        try {
            Facture f = new Facture();
            f.setConsultation(consultationCombo.getValue());
            f.setMontantTotal(Double.parseDouble(montantField.getText()));
            f.setModePaiement(modePaiementCombo.getValue());
            factureService.generer(f);
            afficherMessage("Facture générée !", true);
            charger();
        } catch (NumberFormatException e) {
            afficherMessage("Montant invalide.", false);
        }
    }

    @FXML
    protected void marquerPayee() {
        Facture selected = tableFactures.getSelectionModel().getSelectedItem();
        if (selected == null) { afficherMessage("Sélectionnez une facture.", false); return; }
        factureService.marquerPayee(selected);
        afficherMessage("Facture marquée comme payée.", true);
        charger();
    }

    @FXML
    protected void exporterPDF() {
        Facture selected = tableFactures.getSelectionModel().getSelectedItem();
        if (selected == null) { afficherMessage("Sélectionnez une facture.", false); return; }

        FileChooser fc = new FileChooser();
        fc.setTitle("Enregistrer la facture PDF");
        fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("PDF", "*.pdf"));
        fc.setInitialFileName("facture_" + selected.getId() + ".pdf");
        File file = fc.showSaveDialog(null);
        if (file == null) return;

        try {
            new FacturePdfGenerator(selected).generer(file.getAbsolutePath());
            afficherMessage("PDF exporté : " + file.getName(), true);
        } catch (Exception e) {
            afficherMessage("Erreur : " + e.getMessage(), false);
        }
    }

    private void charger() {
        tableFactures.setItems(FXCollections.observableArrayList(factureService.historique()));
    }

    private void afficherMessage(String msg, boolean succes) {
        if (messageLabel != null) {
            messageLabel.setText(msg);
            messageLabel.setStyle(succes ? "-fx-text-fill: green;" : "-fx-text-fill: red;");
        }
    }
}
