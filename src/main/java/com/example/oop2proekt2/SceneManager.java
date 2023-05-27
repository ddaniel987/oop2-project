package com.example.oop2proekt2;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class SceneManager {
    private static Stage stage = null;

    public static void setStage(Stage st) {
        stage = st;
    }

    public static void changeScene(Class controller, String resource, String title) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(controller.getResource(resource + ".fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle(title);
        stage.setScene(scene);
        stage.show();
    }

    // @returns Stage of new modal
    public static Stage modal(Stage stageOwner, Class owner, String resource, String title) throws IOException {
        FXMLLoader loader = new FXMLLoader(owner.getResource(resource + ".fxml"));
        Parent root = loader.load();

        Stage modalStage = new Stage();
        modalStage.setTitle(title);
        Scene scene = new Scene(root);
        modalStage.setScene(scene);
        modalStage.initModality(Modality.APPLICATION_MODAL);
        modalStage.initOwner(stageOwner);
        modalStage.centerOnScreen();
        modalStage.show();

        return modalStage;
    }

    public static void Alert(Alert.AlertType type, String title, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);

        alert.showAndWait();
    }

}
