package com.example.oop2proekt2;

import java.io.IOException;

public class Session {
    private static User currentUser = null;

    public static User getCurrentUser() {
        return currentUser;
    }

    public static void setCurrentUser(User user) throws IOException {
        currentUser = user;
        SceneManager.changeScene(DashboardController.class, "dashboard", user.getId() + " | " + user.getUsername() + " | Начало");
    }

    public static void logout() throws IOException {
        currentUser = null;
        SceneManager.changeScene(Auth.class, "auth", "Вход");
    }
}
