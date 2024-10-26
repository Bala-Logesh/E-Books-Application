package com.ncsu.ebooks.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DBConnector {
    public static void connectToDB() {
        String url = "jdbc:mariadb://localhost:3306";
        String user = "root"; // replace with your username
        String password = "password"; // replace with your password
        try (Connection conn = DriverManager.getConnection(url, user, password)) {
            System.out.println("Successfully connected to MariaDB");
            createTables(conn);
            System.out.println("All tables created successfully");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void createTables(Connection conn) throws SQLException {
        try (Statement statement = conn.createStatement()) {
            String createDB = "CREATE DATABASE ebooks";
            statement.executeUpdate(createDB);
            String useDB = "USE ebooks";
            statement.executeUpdate(useDB);
            System.out.println("Using ebooks database...");

            ETextBook.createTable(conn);
            Chapter.createTable(conn);
            Section.createTable(conn);
            ContentBlock.createTable(conn);
            Activity.createTable(conn);
            AnswerSet.createTable(conn);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}