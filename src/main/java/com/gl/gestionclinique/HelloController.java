package com.gl.gestionclinique;

import com.gl.gestionclinique.Utilitaire.Navigation;
import com.gl.gestionclinique.Utilitaire.Session.SessionUtilisateur;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;

import java.net.URL;
import java.util.ResourceBundle;

public class HelloController implements Initializable {

    @FXML private StackPane stackPane;
    @FXML private Label userLabel;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        if (userLabel != null) {
            userLabel.setText("Connecté : " + SessionUtilisateur.getNomComplet()
                    + " (" + SessionUtilisateur.getRole() + ")");
        }

        Navigation.loadView("listPatient.fxml", stackPane);
    }

    @FXML protected void afficherPatients() {

        Navigation.loadView("listPatient.fxml", stackPane);
    }

    @FXML protected void afficherAjoutPatient() {

        Navigation.loadView("ajoutPatient.fxml", stackPane);
    }

    @FXML protected void afficherRendezVous() {

        Navigation.loadView("rendezVous.fxml", stackPane);
    }

    @FXML protected void afficherConsultations()
    {
        Navigation.loadView("consultation.fxml", stackPane);
    }

    @FXML protected void afficherFactures() {

        Navigation.loadView("facture.fxml", stackPane);
    }
}
