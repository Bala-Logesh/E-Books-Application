package com.ncsu.ebooks.database.usertables;

import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

@Slf4j
public class Student {
    public static void createTable(Connection conn) throws SQLException {
        try (Statement statement = conn.createStatement()) {
            String createStudentTable = "CREATE TABLE IF NOT EXISTS Student (" +
                    "studentID INT NOT NULL AUTO_INCREMENT," +
                    "userID VARCHAR(8) NOT NULL, " +
                    "PRIMARY KEY (studentID)," +
                    "FOREIGN KEY (UserID) REFERENCES User(userID)" +
                    "ON DELETE CASCADE ON UPDATE CASCADE" +
                    ");";

            statement.executeUpdate(createStudentTable);
            System.out.println("Created Student Table");
        } catch (SQLException e) {
            log.error("An error occurred in Student :: createTables", e);
        }
    }
}
