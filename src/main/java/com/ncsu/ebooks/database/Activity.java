package com.ncsu.ebooks.database;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class Activity {
    public static void createTable(Connection conn) throws SQLException {
        try (Statement statement = conn.createStatement()) {
            String createActivityTable = "CREATE TABLE Activity (" +
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
                    "ON UPDATE CASCADE ON DELETE RESTRICT" +
                    ");";

            statement.executeUpdate(createActivityTable);
            System.out.println("Created Activity Table");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
