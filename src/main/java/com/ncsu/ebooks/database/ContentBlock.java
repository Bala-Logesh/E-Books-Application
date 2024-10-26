package com.ncsu.ebooks.database;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class ContentBlock {
    public static void createTable(Connection conn) throws SQLException {
        try (Statement statement = conn.createStatement()) {
            String createContentBlkTable = "CREATE TABLE IF NOT EXISTS ContentBlock (" +
                    "contentBlockID INT AUTO_INCREMENT UNIQUE NOT NULL," +
                    "sectionID INT NOT NULL," +
                    "image BLOB," +
                    "textBlock LONGTEXT," +
                    "hidden BOOLEAN DEFAULT 0," +
                    "PRIMARY KEY (contentBlockID)," +
                    "UNIQUE (contentBlockID, sectionID)," +
                    "FOREIGN KEY (sectionID) REFERENCES Section(sectionID)" +
                    "ON UPDATE CASCADE ON DELETE CASCADE," +
                    "CHECK (image IS NOT NULL OR textBlock IS NOT NULL)" +
                    ");";

            statement.executeUpdate(createContentBlkTable);
            System.out.println("Created Content Blocks Table");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
