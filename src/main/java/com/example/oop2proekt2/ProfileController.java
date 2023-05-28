package com.example.oop2proekt2;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class ProfileController {
    @FXML
    Label name;

    @FXML
    Label role;

    @FXML
    public void initialize() {
        User cUser = Session.getCurrentUser();
        name.setText(cUser.getUsername());
        role.setText(cUser.getRole());
    }
}
