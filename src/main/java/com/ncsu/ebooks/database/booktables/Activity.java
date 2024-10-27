package com.ncsu.ebooks.database.booktables;

import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

@Slf4j
public class Activity {
    public static void createTable(Connection conn) throws SQLException {
        try (Statement statement = conn.createStatement()) {
            String createActivityTable = "CREATE TABLE IF NOT EXISTS Activity (" +
                    "activityID INT AUTO_INCREMENT UNIQUE NOT NULL," +
                    "sectionID INT NOT NULL," +
                    "contentBlockID INT NOT NULL, " +
                    "question LONGTEXT NOT NULL, " +
                    "hidden BOOLEAN DEFAULT 0, " +
                    "PRIMARY KEY (activityID), " +
                    "UNIQUE (activityID, sectionID)," +
                    "FOREIGN KEY (contentBlockID) REFERENCES ContentBlock(contentBlockID)" +
                    "ON UPDATE CASCADE ON DELETE RESTRICT," +
                    "FOREIGN KEY (sectionID) REFERENCES Section(sectionID)" +
                    "ON UPDATE CASCADE ON DELETE CASCADE" +
                    ");";

            statement.executeUpdate(createActivityTable);
            System.out.println("Created Activity Table");
        } catch (SQLException e) {
            log.error("An error occurred in Activity :: createTables", e);
        }
    }
}
