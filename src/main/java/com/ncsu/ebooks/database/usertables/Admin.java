package com.ncsu.ebooks.database.usertables;

import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

@Slf4j
public class Admin {
    public static void createTable(Connection conn) throws SQLException {
        try (Statement statement = conn.createStatement()) {
            String createAdminTable = "CREATE TABLE IF NOT EXISTS Admin (" +
                    "adminID INT NOT NULL AUTO_INCREMENT," +
                    "userID VARCHAR(8) NOT NULL, " +
                    "PRIMARY KEY (adminID)," +
                    "FOREIGN KEY (UserID) REFERENCES User(userID)" +
                    "ON DELETE CASCADE ON UPDATE CASCADE" +
                    ");";

            statement.executeUpdate(createAdminTable);
            System.out.println("Created Admin Table");
        } catch (SQLException e) {
            log.error("An error occurred in Admin :: createTables", e);
        }
    }
}
