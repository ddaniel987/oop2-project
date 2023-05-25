package com.example.oop2proekt2;

import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;

public class Auth extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        DB.openConnection();
        SceneManager.setStage(stage);
        SceneManager.changeScene(Auth.class, "auth", "Вход");
    }

    public static void main(String[] args) {
        launch();
    }
}