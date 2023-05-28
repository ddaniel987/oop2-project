package com.example.oop2proekt2;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class ActionsBooksController {
    @FXML
    Button createBookBtn;


    @FXML
    public void onCreateBookBtn() throws IOException {
        Stage currentStage = (Stage)createBookBtn.getScene().getWindow();
        SceneManager.modal(currentStage, ActionsUsersController.class, "create_book", "Добавяне на нова книга");
    }

    @FXML
    public void onDeleteBookBtn() throws IOException {
        Stage currentStage = (Stage)createBookBtn.getScene().getWindow();
        SceneManager.modal(currentStage, ActionsUsersController.class, "delete_book", "Изтриване на книга");
    }

    @FXML
    public void onUpdateBookBtn() throws IOException {
        Stage currentStage = (Stage)createBookBtn.getScene().getWindow();
        SceneManager.modal(currentStage, ActionsUsersController.class, "edit_book", "Промяна на книга");
    }

    @FXML
    public void onDamageBookBtn() throws IOException {
        Stage currentStage = (Stage)createBookBtn.getScene().getWindow();
        SceneManager.modal(currentStage, ActionsUsersController.class, "damage_book", "Бракуване на книга");
    }

    @FXML
    public void onArchiveBookBtn() throws IOException {
        Stage currentStage = (Stage)createBookBtn.getScene().getWindow();
        SceneManager.modal(currentStage, ActionsUsersController.class, "archive_book", "Архивиране на книга");
    }


}
