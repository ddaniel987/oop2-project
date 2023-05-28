package com.example.oop2proekt2;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AcceptUserController {
    @FXML
    TableView<ObservableList<String>> tableView = new TableView<>();
    @FXML
    TextField userId;

    @FXML
    Button go;

    Integer id = -1;

    @FXML
    public void initialize() throws SQLException {
        tableView.getColumns().clear();
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        ResultSet users = DB.getUsersAwaitingAcception();
        Helper.parseResultSet(tableView, users);
    }

    @FXML
    public void onBookId() {
        go.setDisable(userId.getText().length() == 0);
        try {
            id = Integer.parseInt(userId.getText());
        } catch(NumberFormatException ex) {
            SceneManager.Alert(Alert.AlertType.ERROR, "Грешка", "Моля, въведете валидно ID на потребител или изберете от менюто горе.");
        }
    }

    @FXML
    public void onGoBtn() {
        if (id == -1) {
            SceneManager.Alert(Alert.AlertType.ERROR, "Грешка", "Моля, въведете валидно ID на потребителя или изберете от менюто горе.");
            return;
        }

        try {
            DB.acceptUser(id);
            SceneManager.Alert(Alert.AlertType.INFORMATION, "Успешно", "Потребителят беше одобрен успешно.");

            ResultSet users = DB.getUsersAwaitingAcception();
            Helper.parseResultSet(tableView, users);
        } catch (Exception ex) {
            SceneManager.Alert(Alert.AlertType.ERROR, "Грешка", "Проблем със заявката");
        }
    }
}
