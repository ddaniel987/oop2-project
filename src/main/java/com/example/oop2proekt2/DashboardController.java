package com.example.oop2proekt2;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.ResultSet;

public class DashboardController {
    @FXML
    Pane userPane;

    @FXML
    Pane operatorPane;

    @FXML
    Pane adminPane;

    @FXML
    TableView<ObservableList<String>> tableView = new TableView<>();

    @FXML
    public void initialize() {
        tableView.getColumns().clear();
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        String role = Session.getCurrentUser().getRole();
        if(role.equals("user"))
            userPane.setVisible(true);
        if(role.equals("operator"))
            operatorPane.setVisible(true);
        if(role.equals("administrator"))
            adminPane.setVisible(true);
    }
    @FXML
    public void onLogout() throws IOException {
        Session.logout();
    }


    @FXML
    public void onChangeRoleBtn() throws IOException {
        Stage stage = (Stage)operatorPane.getScene().getWindow();
        SceneManager.modal(stage, DashboardController.class, "change_role", "Смени ролята на потребител");
    }

    @FXML
    public void onUserLendBook() throws IOException {
        Stage stage = (Stage)operatorPane.getScene().getWindow();
        SceneManager.modal(stage, DashboardController.class, "lend_book", "Заеми книга");
    }

    @FXML
    public void onUserReturnBook() throws IOException {
        Stage stage = (Stage)operatorPane.getScene().getWindow();
        SceneManager.modal(stage, DashboardController.class, "return_book", "Върни книга");
    }


    @FXML
    public void onUserActionsBtn() throws IOException {
        Stage stage = (Stage)operatorPane.getScene().getWindow();
        SceneManager.modal(stage, DashboardController.class, "actions_users", "Операции с потребители");
    }


    @FXML
    public void onAllBooksBtnClick() {
        try {
            ResultSet all = DB.getAllBooks();
            Helper.parseResultSet(tableView, all);

        } catch(Exception err) {
            System.out.println(err);
        }
    }

    @FXML
    public void onAllUsersBtnClick() {
        try {
            ResultSet all = DB.getAllUsers();
            Helper.parseResultSet(tableView, all);

        } catch(Exception err) {
            System.out.println(err);
        }
    }

    @FXML
    public void onAllLendingsBtnClick() {
        try {
            ResultSet all = DB.getAllLendings();
            Helper.parseResultSet(tableView, all);

        } catch(Exception err) {
            System.out.println(err);
        }
    }


    @FXML
    public void onAllLentBooksBtnClick() {
        try {
            ResultSet all = DB.getLentBooks();
            Helper.parseResultSet(tableView, all);

        } catch(Exception err) {
            System.out.println(err);
        }
    }

    @FXML
    public void onDecommissionedBtnClick() {
        try {
            ResultSet all = DB.getDecommissionedBooks();
            Helper.parseResultSet(tableView, all);

        } catch(Exception err) {
            System.out.println(err);
        }
    }

    @FXML
    public void onArchivedBtnClick() {
        try {
            ResultSet all = DB.getArchiveBooks();
            Helper.parseResultSet(tableView, all);

        } catch(Exception err) {
            System.out.println(err);
        }
    }

    @FXML
    public void onFreeBooksBtnClick() {
        try {
            ResultSet freeBooks = DB.getFreeBooks();
            Helper.parseResultSet(tableView, freeBooks);

        } catch(Exception err) {
            System.out.println(err);
        }
    }


    @FXML
    protected void onBtnSelfLent() {
        try {
            ResultSet freeBooks = Session.getCurrentUser().lentBooks();
            Helper.parseResultSet(tableView, freeBooks);

        } catch(Exception err) {
            System.out.println(err);
        }
    }

    @FXML
    public void onBtnAllSelfLents() {
        try {
            ResultSet alllents = Session.getCurrentUser().allLendings();
            Helper.parseResultSet(tableView, alllents);

        } catch(Exception err) {
            System.out.println(err);
        }
    }

    @FXML
    public void onBookActionsBtn() throws IOException {
        Stage stage = (Stage)operatorPane.getScene().getWindow();
        SceneManager.modal(stage, DashboardController.class, "actions_books", "Операции с книги");
    }

    @FXML
    public void onUserProfileBtn() throws IOException {
        Stage stage = (Stage)operatorPane.getScene().getWindow();
        SceneManager.modal(stage, DashboardController.class, "profile", "Моят Профил");
    }
}
