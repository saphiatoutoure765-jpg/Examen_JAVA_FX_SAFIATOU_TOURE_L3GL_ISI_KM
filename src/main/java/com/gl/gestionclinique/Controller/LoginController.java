package com.gl.gestionclinique.Controller;

import com.gl.gestionclinique.Repository.Implementation.UserDAOImple;
import com.gl.gestionclinique.Service.AuthService;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class LoginController {

    @FXML private TextField emailField;
    @FXML private PasswordField passwordField;
    @FXML private Label errorLabel;

    private final AuthService authService = new AuthService(new UserDAOImple());

    @FXML
    protected void seConnecter() {
        String email = emailField.getText().trim();
        String motDePasse = passwordField.getText();

        if (email.isEmpty() || motDePasse.isEmpty()) {
            errorLabel.setText("Veuillez remplir tous les champs.");
            return;
        }

        boolean succes = authService.connecter(email, motDePasse);

        if (succes) {
            ouvrirTableauDeBord();
        } else {
            errorLabel.setText("Email ou mot de passe incorrect.");
        }
    }

    private void ouvrirTableauDeBord() {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/com/gl/gestionclinique/views/layout.fxml")
            );
            Scene scene = new Scene(loader.load(), 1100, 700);
            Stage stage = (Stage) emailField.getScene().getWindow();
            stage.setTitle("Clinique Médicale - Tableau de bord");
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            errorLabel.setText("Erreur lors de l'ouverture du tableau de bord.");
            e.printStackTrace();
        }
    }
}
