package com.example.oop2proekt2;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ActionsUsersController {
    @FXML
    Button changeRatingBtn;


    @FXML
    public void onAcceptUserBtn() throws IOException {
        Stage currentStage = (Stage)changeRatingBtn.getScene().getWindow();
        SceneManager.modal(currentStage, ActionsUsersController.class, "accept_user", "Одобрение на потребител");
    }

    @FXML
    public void onChangeRatingBtn() throws IOException {
        Stage stage = (Stage)changeRatingBtn.getScene().getWindow();

        //отваряне на модал
        SceneManager.modal(stage, ActionsUsersController.class, "change_user_rating", "Промяна на рейтинга на потребител");
    }

    @FXML
    public void onUserSearchBtn() throws IOException {
        Stage stage = (Stage)changeRatingBtn.getScene().getWindow();

        //отваряне на модал
        SceneManager.modal(stage, ActionsUsersController.class, "search_user", "Търсене на потребител");
    }

    @FXML
    public void onUserLentBooksBtn() throws IOException {
        Stage stage = (Stage)changeRatingBtn.getScene().getWindow();

        //отваряне на модал
        SceneManager.modal(stage, ActionsUsersController.class, "lent_books_user", "Невърнати книги на потребител");
    }

    @FXML
    public void onUserLendings() throws IOException {
        Stage stage = (Stage)changeRatingBtn.getScene().getWindow();

        //отваряне на модал
        SceneManager.modal(stage, ActionsUsersController.class, "user_lendings", "Всички формуляри на потребител");
    }
}
