package com.example.oop2proekt2;

import java.sql.ResultSet;
import java.sql.SQLException;

public class User {
    private int id;
    private String username;
    private int rating;
    private String role;
    private String created_at;
    private int is_accepted;

    public User(int id, String username, int rating, String role, String created_at, int is_accepted) {
        this.id = id;
        this.username = username;
        this.rating = rating;
        this.role = role;
        this.created_at = created_at;
        this.is_accepted = is_accepted;
    }

    public int getId() {
        return this.id;
    }

    public String getUsername() {
        return this.username;
    }

    public int getAcception() {return this.is_accepted;}

    public int getRating() {
        return this.rating;
    }

    public String getRole() {
        return this.role;
    }

    public String getCreatedAt() {
        return this.created_at;
    }


    public ResultSet lentBooks() throws SQLException {
        return DB.getUserLentBooks(this.id);
    }

    public ResultSet allLendings() throws SQLException {
        return DB.getUserAllLendings(this.id);
    }


}
