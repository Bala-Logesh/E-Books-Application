package com.ncsu.ebooks.database.booktables;

import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

@Slf4j
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
                    "ON UPDATE CASCADE ON DELETE CASCADE" +
                    ");";

            statement.executeUpdate(createContentBlkTable);
            System.out.println("Created Content Blocks Table");
        } catch (SQLException e) {
            log.error("An error occurred in Content Block :: createTables", e);
        }
    }
}
