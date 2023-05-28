package com.example.oop2proekt2;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import java.sql.ResultSet;

public class SearchUserController {
    @FXML
    ComboBox criteriaBox;

    @FXML
    TextField searchBox;

    @FXML
    TableView<ObservableList<String>> tableView = new TableView<>();

    @FXML
    public  void initialize() {
        tableView.getColumns().clear();
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        criteriaBox.getItems().setAll(FXCollections.observableArrayList("id", "username"));

        criteriaBox.valueProperty().addListener((observable, oldValue, newValue) -> {
            searchBox.setDisable(false);
        });
    }

    @FXML
    public void onSearch() {
        String criteria = criteriaBox.getValue().toString();
        String val = searchBox.getText();


        try {
            ResultSet found = DB.getUserByCriteria(criteria.equals("username"), val);
            Helper.parseResultSet(tableView, found);
        } catch(Exception ex) {
            System.out.println(ex);
        }

    }
}
