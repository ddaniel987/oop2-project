package com.example.oop2proekt2;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ChangeRoleController {
    @FXML
    ComboBox<String> userBox;

    @FXML
    ComboBox<String> roleBox;

    @FXML
    TextField userId;

    @FXML
    Button go;

    Integer id = -1;

    @FXML
    public void initialize() throws SQLException {
        ResultSet users = DB.getAllUsers();
        while (users.next()) {
            int id = users.getInt("ID");
            String name = users.getString("Потр. Име");

            userBox.getItems().add(id + " : " + name);
        }

        userBox.valueProperty().addListener((observable, oldValue, newValue) -> {
            go.setDisable(false);
            id = Integer.parseInt(newValue.split(" :")[0]);
        });

        roleBox.getItems().setAll(FXCollections.observableArrayList("user", "operator", "administrator"));
    }

    @FXML
    public void onUserId() {
        go.setDisable(userId.getText().length() == 0);
        try {
            id = Integer.parseInt(userId.getText());
        } catch (NumberFormatException ex) {
            SceneManager.Alert(Alert.AlertType.ERROR, "Грешка", "Моля, въведете валидно ID на потребителя или изберете от менюто горе.");
        }
    }

    @FXML
    public void onGoBtn() {
        if (id == -1) {
            SceneManager.Alert(Alert.AlertType.ERROR, "Грешка", "Моля, въведете валидно ID на потребителя или изберете от менюто горе.");
            return;
        }

        try {
            DB.changeRole(Session.getCurrentUser().getId(), roleBox.getValue());
            Stage current = (Stage) (go.getScene().getWindow());
            current.close();

            SceneManager.Alert(Alert.AlertType.INFORMATION, "Успешно", "Ролята на потребителя беше успешно променена.");
        } catch (Exception ex) {
            SceneManager.Alert(Alert.AlertType.ERROR, "Грешка", "Не съществува такъв потребител или друга грешка.");
        }
    }
}