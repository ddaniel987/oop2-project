package com.example.oop2proekt2;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ActionsUsersController {
    @FXML
    Button changeRatingBtn;

    @FXML
    public void onChangeRatingBtn() throws IOException {
        Stage stage = (Stage)changeRatingBtn.getScene().getWindow();

        //отваряне на модал
        SceneManager.modal(stage, ActionsUsersController.class, "change_user_rating", "Промяна на рейтинга на потребител");
    }
}
