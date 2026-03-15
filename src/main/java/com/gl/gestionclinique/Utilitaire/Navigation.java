package com.gl.gestionclinique.Utilitaire;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.StackPane;

import java.io.IOException;

public class Navigation {

    public static void loadView(String fxml, StackPane stackPane) {
        try {
            Parent view = FXMLLoader.load(
                    Navigation.class.getResource("/com/gl/gestionclinique/views/" + fxml)
            );
            stackPane.getChildren().setAll(view);

            StackPane.setAlignment(view, javafx.geometry.Pos.TOP_LEFT);
            if (view instanceof javafx.scene.layout.Region) {
                javafx.scene.layout.Region region = (javafx.scene.layout.Region) view;
                region.prefWidthProperty().bind(stackPane.widthProperty());
                region.prefHeightProperty().bind(stackPane.heightProperty());
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}