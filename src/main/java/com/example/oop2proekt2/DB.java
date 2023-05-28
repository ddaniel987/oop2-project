package com.example.oop2proekt2;

import javafx.scene.control.Alert;

import java.security.NoSuchAlgorithmException;
import java.sql.*;

public class DB {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/oop2";
    private static final String DB_USERNAME = "root";
    private static final String DB_PASSWORD = "";

    private static Connection connection;

    public static void openConnection() {
        try {
            connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
            System.out.println("Connected to the database");
        } catch (SQLException e) {
            System.err.println("Failed to connect to the database: " + e.getMessage());
        }
    }

    public static User auth(String username, String password) throws SQLException, NoSuchAlgorithmException {
        String hashedPw = Helper.md5(password);
        String query = "SELECT * from users where username = ? AND password = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, username);
        statement.setString(2, hashedPw);

        ResultSet res = statement.executeQuery();

        if (res.next()) {
            User user = new User(res.getInt("id"), res.getString("username"), res.getInt("rating"), res.getString("role"), res.getString("created_at"), res.getInt("is_accepted"));
            return user;
        }

        return (User) null;
    }

    public static User reg(String username, String password) throws SQLException, NoSuchAlgorithmException {
        String hashedPw = Helper.md5(password);
        String query = "INSERT INTO users (username, password) VALUES (?, ?)";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, username);
        statement.setString(2, hashedPw);

        int rowsAffected = statement.executeUpdate();
        if (rowsAffected > 0) {
            try {
                User u = DB.auth(username, password);
                if (u == null) {
                    throw new Exception();
                }
                return u;
            } catch (Exception err) {
                return null;
            }
        }
        return null;
    }


    //GETTERS
    public static ResultSet getAllBooks() throws SQLException {
        PreparedStatement statement = connection.prepareStatement("SELECT id as 'ID', book_name as 'Име на книгата', book_author as 'Автор', book_genre as 'Жанр', is_damaged as 'Повредена', is_archived as 'Архивирана' FROM books");
        ResultSet resultSet = statement.executeQuery();
        return resultSet;
    }

    public static ResultSet getAllUsers() throws SQLException {
        PreparedStatement statement = connection.prepareStatement("SELECT id AS 'ID', username AS 'Потр. Име', rating AS 'Рейтинг', role AS 'Позиция', created_at AS 'Добавен на' FROM users");
        ResultSet resultSet = statement.executeQuery();
        return resultSet;
    }

    public static ResultSet getUsersAwaitingAcception() throws SQLException {
        PreparedStatement statement = connection.prepareStatement("SELECT id AS 'ID', username AS 'Потр. Име', rating AS 'Рейтинг', role AS 'Позиция', created_at AS 'Добавен на' FROM users WHERE is_accepted = 0 AND role = 'user'");
        ResultSet resultSet = statement.executeQuery();
        return resultSet;
    }

    // търсене (search)
    public static ResultSet getUserByCriteria(Boolean isUsername, String criteria) throws SQLException {
        String c = isUsername ? "username" : "id";
        PreparedStatement statement = connection.prepareStatement("SELECT id AS 'ID', username AS 'Потр. Име', rating AS 'Рейтинг', role AS 'Позиция', created_at AS 'Добавен на' FROM users WHERE `" + c +"` LIKE ?");
        statement.setString(1, criteria);
        ResultSet resultSet = statement.executeQuery();
        return resultSet;
    }

    public static ResultSet getUserByID(int id) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("SELECT id AS 'ID', username AS 'Потр. Име', rating AS 'Рейтинг', role AS 'Позиция', created_at AS 'Добавен на' FROM users WHERE id = ?");
        statement.setInt(1, id);
        ResultSet resultSet = statement.executeQuery();
        return resultSet;
    }
    public static ResultSet getLentBooks() throws SQLException { //всички заети, не само на юзъра
        PreparedStatement statement = connection.prepareStatement("SELECT lendings.id AS 'ID', lendings.book_id AS 'Номер на книгата', books.book_name AS 'Име на книгата', lendings.to_id AS 'Взета от', lendings.until AS 'Взета до', lendings.by_id AS 'Дадена от' FROM lendings INNER JOIN books ON books.id = lendings.book_id WHERE returned = 0");
        ResultSet resultSet = statement.executeQuery();
        return resultSet;
    }

    public static ResultSet getDecommissionedBooks() throws SQLException { //бракувани
        PreparedStatement statement = connection.prepareStatement("SELECT id as 'ID', book_name as 'Име на книгата', book_author as 'Автор', book_genre as 'Жанр', is_damaged as 'Повредена', is_archived as 'Архивирана' FROM books WHERE is_damaged = 1");
        ResultSet resultSet = statement.executeQuery();
        return resultSet;
    }

    public static ResultSet getArchiveBooks() throws SQLException { //архивирани
        PreparedStatement statement = connection.prepareStatement("SELECT id as 'ID', book_name as 'Име на книгата', book_author as 'Автор', book_genre as 'Жанр', is_damaged as 'Повредена', is_archived as 'Архивирана' FROM books WHERE is_archived = 1");
        ResultSet resultSet = statement.executeQuery();
        return resultSet;
    }

    public static ResultSet getFreeBooks() throws SQLException {
        PreparedStatement statement = connection.prepareStatement("SELECT id as 'ID', book_name as 'Име на книгата', book_author as 'Автор', book_genre as 'Жанр', is_damaged as 'Повредена', is_archived as 'Архивирана' FROM books WHERE id NOT IN (SELECT book_id FROM lendings WHERE returned = 0) AND is_damaged = 0 AND is_archived = 0");
        ResultSet resultSet = statement.executeQuery();
        return resultSet;
    }

    public static ResultSet getUserLentBooks(int id) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("SELECT id AS 'ID', book_name AS 'Име на книгата', book_author AS 'Автор', book_genre AS 'Жанр', is_damaged AS 'Повредена', is_archived AS 'Архивирана' FROM books WHERE id IN (SELECT book_id FROM lendings WHERE returned = 0 AND to_id = ?)");
        statement.setInt(1, id);
        ResultSet resultSet = statement.executeQuery();
        return resultSet;
    }


    public static ResultSet getUserAllLendings(int id) throws SQLException { //всички "формуляри" на потребителя
        PreparedStatement statement = connection.prepareStatement("SELECT lendings.id AS 'ID', books.book_name AS 'Име на книгата', users.username AS 'Оператор', lendings.returned AS 'Върната', lendings.until AS 'Срок за връщане', lendings.created_at AS 'Взета на' FROM lendings INNER JOIN books ON books.id = lendings.book_id INNER JOIN users ON users.id = lendings.to_id WHERE lendings.to_id = ?");
        statement.setInt(1, id);
        ResultSet resultSet = statement.executeQuery();
        return resultSet;
    }

    public static ResultSet getAllLendings() throws SQLException { //всички "формуляри"
        PreparedStatement statement = connection.prepareStatement("SELECT lendings.id AS 'ID', books.book_name AS 'Име на книгата', users.username AS 'Оператор', lendings.returned AS 'Върната', lendings.until AS 'Срок за връщане', lendings.created_at AS 'Взета на' FROM lendings INNER JOIN books ON books.id = lendings.book_id INNER JOIN users ON users.id = lendings.to_id");
        ResultSet resultSet = statement.executeQuery();
        return resultSet;
    }



    //SETTERS

    public static void setUserRating(int id, int rating) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("UPDATE users SET rating = ? where id = ?");
        statement.setInt(1, rating);
        statement.setInt(2, id);
        statement.executeUpdate();
    }

    public static void createBook(String name, String author, String genre, Boolean isDamaged, Boolean isArchived) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("INSERT INTO books (book_name, book_author, book_genre, is_damaged, is_archived) VALUES (?, ?, ?, ? , ?)");
        statement.setString(1, name);
        statement.setString(2, author);
        statement.setString(3, genre);
        statement.setInt(4, isDamaged ? 1 : 0);
        statement.setInt(5, isArchived ? 1 : 0);
        statement.executeUpdate();
    }

    public static void deleteBook(int id) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("delete from books where id = ?");
        statement.setInt(1, id);
        statement.executeUpdate();
    }

    public static void editBook(int id, String field, String newVal) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("update books set `" + field  + "` = ? where id = ?");
        statement.setInt(2, id);
        if(field == "is_damaged" || field == "is_archived")
            statement.setInt(1, Integer.parseInt(newVal));
        else
            statement.setString(1, newVal);

        statement.executeUpdate();
    }

    public static void damageBook(int id) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("update books set is_damaged = 1 where id = ?");
        statement.setInt(1, id);
        statement.executeUpdate();
    }

    public static void archiveBook(int id) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("update books set is_archived = 1 where id = ?");
        statement.setInt(1, id);
        statement.executeUpdate();
    }

    public static void acceptUser(int id) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("update users set is_accepted = 1 where id = ?");
        statement.setInt(1, id);
        statement.executeUpdate();
    }

    public static void lentBook(int bookId, int userId, Date until) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("insert into lendings (book_id, to_id, returned, until) values (?, ?, 0, ?)");
        statement.setInt(1, bookId);
        statement.setInt(2, userId);
        statement.setDate(3, until);
        statement.executeUpdate();
    }

    public static void returnBook(int bookId, int userId) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("update lendings set returned = 1 where book_id = ? and to_id = ?");
        statement.setInt(1, bookId);
        statement.setInt(2, userId);
        statement.executeUpdate();
    }

    public static void changeRole(int userId, String role) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("update users set role = ? where id = ?");
        statement.setString(1, role);
        statement.setInt(2, userId);
        statement.executeUpdate();
    }



    public void closeConnection() {
        try {
            if (connection != null) {
                connection.close();
                System.out.println("Connection to the database closed");
            }
        } catch (SQLException e) {
            System.err.println("Failed to close the connection: " + e.getMessage());
        }
    }
}
