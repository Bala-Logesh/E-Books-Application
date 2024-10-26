package com.ncsu.ebooks.database;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class Section {
    public static void createTable(Connection conn) throws SQLException {
        try (Statement statement = conn.createStatement()) {
            String createSectionTable = "CREATE TABLE Section (" +
                    "sectionID INT AUTO_INCREMENT UNIQUE NOT NULL," +
                    "sectionNumber INT NOT NULL," +
                    "chapterID INT NOT NULL," +
                    "title VARCHAR(255) NOT NULL," +
                    "hidden BOOLEAN DEFAULT 0," +
                    "PRIMARY KEY (sectionID)," +
                    "UNIQUE (title, sectionNumber, chapterID)," +
                    "FOREIGN KEY (chapterID) REFERENCES Chapter(chapterID)" +
                    "ON UPDATE CASCADE ON DELETE RESTRICT" +
                    ");";

            statement.executeUpdate(createSectionTable);
            System.out.println("Created Chapters Table");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
