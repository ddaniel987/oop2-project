package com.example.oop2proekt2;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ArchiveBookController {
    @FXML
    ComboBox<String> bookBox;

    @FXML
    TextField bookId;

    @FXML
    Button go;

    Integer id = -1;

    @FXML
    public void initialize() throws SQLException {
        ResultSet books = DB.getAllBooks();
        while (books.next()) {
            int id = books.getInt("ID");
            String name = books.getString("Име на книгата");

            bookBox.getItems().add(id + " : " + name);
        }

        bookBox.valueProperty().addListener((observable, oldValue, newValue) -> {
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
            SceneManager.Alert(Alert.AlertType.ERROR, "Грешка", "Моля, въведете валидно ID на книга или изберете от менюто горе.");
        }
    }

    @FXML
    public void onGoBtn() {
        if(id == -1) {
            SceneManager.Alert(Alert.AlertType.ERROR, "Грешка", "Моля, въведете валидно ID на потребителя или изберете от менюто горе.");
            return;
        }

        try {
            DB.archiveBook(id);
            Stage current = (Stage)(go.getScene().getWindow());
            current.close();
            SceneManager.Alert(Alert.AlertType.INFORMATION, "Успешно", "Книгата беше успешно архивирана.");
        } catch(Exception ex) {
            SceneManager.Alert(Alert.AlertType.ERROR, "Грешка", "Не съществува такава книга или друга грешка.");
        }

    }
}
