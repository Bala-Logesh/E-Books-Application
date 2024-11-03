package com.ncsu.ebooks.database.booktables;

import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

@Slf4j
public class Section {
    public static void createTable(Connection conn) throws SQLException {
        try (Statement statement = conn.createStatement()) {
            String createSectionTable = "CREATE TABLE IF NOT EXISTS Section (" +
                    "sectionID INT AUTO_INCREMENT UNIQUE NOT NULL," +
                    "sectionNumber VARCHAR(6) NOT NULL," +
                    "chapterID INT NOT NULL," +
                    "title VARCHAR(255) NOT NULL," +
                    "hidden BOOLEAN DEFAULT 0," +
                    "PRIMARY KEY (sectionID)," +
                    "UNIQUE (title, sectionNumber, chapterID)," +
                    "FOREIGN KEY (chapterID) REFERENCES Chapter(chapterID)" +
                    "ON UPDATE CASCADE ON DELETE CASCADE" +
                    ");";

            statement.executeUpdate(createSectionTable);
            System.out.println("Created Sections Table");
        } catch (SQLException e) {
            log.error("An error occurred in Section :: createTables", e);
        }
    }
}
