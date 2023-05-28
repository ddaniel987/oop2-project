package com.example.oop2proekt2;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserLentBooksController {
    @FXML
    ComboBox<String> userBox;

    @FXML
    TextField userId;

    @FXML
    Button searchBtn;

    @FXML
    TableView<ObservableList<String>> tableView = new TableView<>();

    Integer id = -1;

    @FXML
    public void initialize() throws SQLException {
        tableView.getColumns().clear();
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        ResultSet users = DB.getAllUsers();
        while (users.next()) {
            int id = users.getInt("ID");
            String username = users.getString("Потр. Име");

            userBox.getItems().add(id + " : " + username);
        }

        userBox.valueProperty().addListener((observable, oldValue, newValue) -> {
            searchBtn.setDisable(false);
            id =  Integer.parseInt(newValue.split(" :")[0]);
        });
    }

    @FXML
    public void onUserId() {
        searchBtn.setDisable(userId.getText().length() == 0);
        try {
            id = Integer.parseInt(userId.getText());
        } catch(NumberFormatException ex) {
            SceneManager.Alert(Alert.AlertType.ERROR, "Грешка", "Моля, въведете валидно ID на потребителя или изберете от менюто горе.");
        }
    }

    @FXML
    public void onSearchBtn() {
       if(id == -1) {
           SceneManager.Alert(Alert.AlertType.ERROR, "Грешка", "Моля, въведете валидно ID на потребителя или изберете от менюто горе.");
           return;
       }

       try {
           ResultSet books = DB.getUserLentBooks(id);
           Helper.parseResultSet(tableView, books);
       } catch(Exception ex) {
           SceneManager.Alert(Alert.AlertType.ERROR, "Грешка", "Няма резултати по зададените от вас критерии.");
       }

    }
}
