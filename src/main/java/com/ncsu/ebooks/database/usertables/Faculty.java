package com.ncsu.ebooks.database.usertables;

import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

@Slf4j
public class Faculty {
    public static void createTable(Connection conn) throws SQLException {
        try (Statement statement = conn.createStatement()) {
            String createFacultyTable = "CREATE TABLE IF NOT EXISTS Faculty (" +
                    "facultyId INT NOT NULL AUTO_INCREMENT," +
                    "userID VARCHAR(8) NOT NULL, " +
                    "PRIMARY KEY (facultyId)," +
                    "FOREIGN KEY (UserID) REFERENCES User(userID)" +
                    "ON DELETE CASCADE ON UPDATE CASCADE" +
                    ");";

            statement.executeUpdate(createFacultyTable);
            System.out.println("Created Faculty Table");
        } catch (SQLException e) {
            log.error("An error occurred in Faculty :: createTables", e);
        }
    }
}
