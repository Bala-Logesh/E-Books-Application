package com.ncsu.ebooks.database.misctables;

import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

@Slf4j
public class Notification {
    public static void createTable(Connection conn) throws SQLException {
        try (Statement statement = conn.createStatement()) {
            String createNotificationTable = "CREATE TABLE IF NOT EXISTS Notification (" +
                    "notificationID INT NOT NULL AUTO_INCREMENT," +
                    "userID VARCHAR(8) NOT NULL," +
                    "message LONGTEXT NOT NULL," +
                    "messageRead BOOLEAN DEFAULT 0," +
                    "PRIMARY KEY (notificationID)," +
                    "FOREIGN KEY (userID) REFERENCES User(userID)" +
                    "ON UPDATE CASCADE ON DELETE CASCADE" +
                    ");";

            statement.executeUpdate(createNotificationTable);
            System.out.println("Created Notification Table");
        } catch (SQLException e) {
            log.error("An error occurred in Notification :: createTables", e);
        }
    }
}
