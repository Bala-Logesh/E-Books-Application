package com.ncsu.ebooks.database.usertables;

import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

@Slf4j
public class User {
    public static void createTable(Connection conn) throws SQLException {
        try (Statement statement = conn.createStatement()) {
            String createUserTable = "CREATE TABLE IF NOT EXISTS User (" +
                    "userID VARCHAR(8) NOT NULL UNIQUE," +
                    "firstName VARCHAR(50) NOT NULL," +
                    "lastName VARCHAR(50) NOT NULL," +
                    "email VARCHAR(50) NOT NULL," +
                    "password VARCHAR(50) NOT NULL," +
                    "accountCreation TIMESTAMP DEFAULT CURRENT_TIMESTAMP," +
                    "role ENUM('ADMIN', 'FACULTY', 'TEACHING_ASSISTANT', 'STUDENT') NOT NULL," +
                    "PRIMARY KEY (userID)," +
                    "UNIQUE (userID, email)" +
                    ");";

            statement.executeUpdate(createUserTable);
            System.out.println("Created Users Table");
        } catch (SQLException e) {
            log.error("An error occurred in User :: createTables", e);
        }
    }
}
