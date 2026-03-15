package com.gl.gestionclinique;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(
                HelloApplication.class.getResource("views/login.fxml")
        );
        Scene scene = new Scene(fxmlLoader.load(), 1550, 800);
        stage.setTitle("Clinique Médicale - Connexion");
        stage.setScene(scene);
        stage.setResizable(true);
        stage.show();
    }
}
