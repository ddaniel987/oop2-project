package com.example.oop2proekt2;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.ZoneOffset;

public class LendBookController {
    @FXML
    ComboBox<String> freeBooks;

    @FXML
    TextField bookId;

    @FXML
    Button go;

    @FXML
    DatePicker until;

    Integer id = -1;

    @FXML
    public void initialize() throws SQLException {
        ResultSet fb = DB.getFreeBooks();
        while (fb.next()) {
            int id = fb.getInt("ID");
            String bn = fb.getString("Име на книгата");

            freeBooks.getItems().add(id + " : " + bn);
        }

        freeBooks.valueProperty().addListener((observable, oldValue, newValue) -> {
            go.setDisable(false);
            id =  Integer.parseInt(newValue.split(" :")[0]);
        });
    }

    @FXML
    public void onBookId() {
        go.setDisable(bookId.getText().length() == 0);
        try {
            id = Integer.parseInt(bookId.getText());
        } catch(NumberFormatException ex) {
            SceneManager.Alert(Alert.AlertType.ERROR, "Грешка", "Моля, въведете валидно ID на потребителя или изберете от менюто горе.");
        }
    }

    @FXML
    public void onGoBtn() {
        if(id == -1) {
            SceneManager.Alert(Alert.AlertType.ERROR, "Грешка", "Моля, въведете валидно ID на потребителя или изберете от менюто горе.");
            return;
        }

        try {
            LocalDate ts = until.getValue();
            if(ts == null) {
                SceneManager.Alert(Alert.AlertType.ERROR, "Грешка", "Моля, изберете дата на връщане.");
                return;
            }

            DB.lentBook(id, Session.getCurrentUser().getId(), Date.valueOf(ts));

            Stage current = (Stage)(go.getScene().getWindow());
            current.close();

            SceneManager.Alert(Alert.AlertType.INFORMATION, "Успешно", "Вие наехте книгата успешно.");

        } catch(Exception ex) {
            SceneManager.Alert(Alert.AlertType.ERROR, "Грешка", "Няма резултати по зададените от вас критерии.");
        }

    }
}
