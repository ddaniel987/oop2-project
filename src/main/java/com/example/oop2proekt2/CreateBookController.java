package com.example.oop2proekt2;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleButton;
import javafx.stage.Stage;

import java.sql.SQLException;

public class CreateBookController {
    @FXML
    TextField name;

    @FXML
    TextField author;

    @FXML
    TextField genre;

    @FXML
    ToggleButton damaged;

    @FXML
    ToggleButton archived;

    @FXML
    public void onCreateBtn() {
        if(name.getText().length() < 4) {
            SceneManager.Alert(Alert.AlertType.ERROR, "Грешка", "Името трябва да съдържа мин. 4 символа.");
            return;
        }

        if(author.getText().length() < 4) {
            SceneManager.Alert(Alert.AlertType.ERROR, "Грешка", "Авторът трябва да съдържа мин. 4 символа.");
            return;
        }

        if(genre.getText().length() < 4) {
            SceneManager.Alert(Alert.AlertType.ERROR, "Грешка", "Жанрът трябва да съдържа мин. 4 символа.");
            return;
        }


        try {
            DB.createBook(name.getText(), author.getText(), genre.getText(), damaged.isSelected(), archived.isSelected());
            Stage current = (Stage)(genre.getScene().getWindow());
            current.close();

            SceneManager.Alert(Alert.AlertType.INFORMATION, "Успешно", "Книгата беше успешно създадена и добавена.");
        } catch(SQLException ex) {
            SceneManager.Alert(Alert.AlertType.ERROR, "Грешка", "Грешка при изпълнението на заявката.");
        }
    }
}
