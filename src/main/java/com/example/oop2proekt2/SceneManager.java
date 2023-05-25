package com.example.oop2proekt2;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
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

}
