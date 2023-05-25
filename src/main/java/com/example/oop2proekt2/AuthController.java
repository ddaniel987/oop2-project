package com.example.oop2proekt2;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.IOException;

public class AuthController {
    @FXML
    private TextField username;

    @FXML
    private PasswordField password;

    @FXML
    private Label response;

    @FXML
    protected void onbtnVlezClick() {
        String username = this.username.getText();
        String password = this.password.getText();

        try {
            User res = DB.auth(username, password);
            if(res == null) throw new Exception();

            Session.setCurrentUser(res);


        } catch(Exception err) {
            System.out.println(err);
            response.setText("Невалидно име или парола.");
        }
    }

    @FXML
    protected  void onbtnRegClick() throws IOException {
        SceneManager.changeScene(RegisterController.class, "reg", "Регистрация");
    }
}