package com.example.oop2proekt2;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

public class Helper {
    public static String md5(String input) throws NoSuchAlgorithmException {
        return String.format("%032x", new BigInteger(1, MessageDigest.getInstance("MD5").digest(input.getBytes())));
    }

    public static void parseResultSet(TableView<ObservableList<String>> tableView, ResultSet resultSet) throws SQLException {
        tableView.getColumns().clear(); //трием всичко от tableView преди да запишен новите данни за да не се получи наслагване
        tableView.getItems().clear();

        ObservableList<String> columnNames = FXCollections.observableArrayList();
        ResultSetMetaData metaData = resultSet.getMetaData();

        for (int i = 1; i <= metaData.getColumnCount(); i++) {
            columnNames.add(metaData.getColumnLabel(i));
        }

        for (String columnName : columnNames) {
            TableColumn<ObservableList<String>, String> column = new TableColumn<>(columnName);
            column.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().get(columnNames.indexOf(columnName))));
            tableView.getColumns().add(column);
        }

        ObservableList<ObservableList<String>> data = FXCollections.observableArrayList();
        while (resultSet.next()) {
            ObservableList<String> row = FXCollections.observableArrayList();
            for (int i = 1; i <= metaData.getColumnCount(); i++) {
                row.add(resultSet.getString(i));
            }
            data.add(row);
        }

        tableView.setItems(data);
    }


}
