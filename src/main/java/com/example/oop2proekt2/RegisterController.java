package com.example.oop2proekt2;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.IOException;

public class RegisterController {
    @FXML
    private TextField username;

    @FXML
    private PasswordField password;

    @FXML
    private Label response;

    @FXML
    protected void onBtnReg() {
        String username = this.username.getText();
        String password = this.password.getText();

        if(password.length() < 6) {
            response.setText("Паролата трябва да е мин. 6 символа.");
            return;
        }

        try {
            User res = DB.reg(username, password);
            if(res == null) throw new Exception();

            Session.setCurrentUser(res);

        } catch(Exception err) {
            System.out.println(err);
            response.setText("Вече съществува потребител със това име.");
        }
    }


    @FXML
    protected  void onBtnBack() throws IOException {
        SceneManager.changeScene(Auth.class, "auth", "Вход");
    }
}