package com.example.oop2proekt2;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ChangeUserRatingController {
    @FXML
    Slider ratingSlider;

    @FXML
    Label ratingLabel;

    @FXML
    ComboBox<String> userBox;

    @FXML
    Button setRatingBtn;

    int selectedUserID = -1;

    @FXML
    public void initialize() throws SQLException {
        ResultSet users = DB.getAllUsers();
        while (users.next()) {
            int id = users.getInt("ID");
            String username = users.getString("Потр. Име");

            userBox.getItems().add(id + " : " + username);
        }

        ratingSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
            ratingLabel.setText(String.format("%.0f", newValue));
        });

        userBox.valueProperty().addListener((observable, oldValue, newValue) -> {
            String parsedId = newValue.split(" :")[0];

            try {
                ResultSet user = DB.getUserByID(Integer.parseInt(parsedId));
                user.next(); //отиваме на 1вия ред
                Integer rating = user.getInt("Рейтинг");
                ratingSlider.setValue(rating);
                ratingSlider.setDisable(false);

                selectedUserID = Integer.parseInt(parsedId);
                setRatingBtn.setDisable(false);
            } catch(Exception ex) {
                //user not found, ignore
                System.out.println(ex);
            }
        });
    }

    @FXML
    public void setRatingBtnClick() {
        try {
            DB.setUserRating(selectedUserID, Integer.parseInt(String.valueOf(Math.round(ratingSlider.getValue()))));
            SceneManager.Alert(Alert.AlertType.INFORMATION, "Success", "Рейтингът на потребителя беше променен успешно.");
            Stage current = (Stage)(ratingSlider.getScene().getWindow());
            current.close();

        } catch(Exception ex) {
            SceneManager.Alert(Alert.AlertType.ERROR, "Грешка", "Рейтингът на потребителя не можеше да бъде променен.");
        }
    }
}
