package com.ncsu.ebooks.database;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class Chapter {
    public static void createTable(Connection conn) throws SQLException {
        try (Statement statement = conn.createStatement()) {
            String createChapterTable = "CREATE TABLE IF NOT EXISTS Chapter (" +
                    "chapterID INT AUTO_INCREMENT UNIQUE NOT NULL," +
                    "chapterNumber VARCHAR(6) NOT NULL," +
                    "title VARCHAR(255) NOT NULL," +
                    "eTextBookID INT NOT NULL," +
                    "hidden BOOLEAN DEFAULT 0," +
                    "PRIMARY KEY (chapterID)," +
                    "UNIQUE (title, chapterNumber, eTextBookID)," +
                    "FOREIGN KEY (eTextBookID) REFERENCES ETextBook(eTextBookID)" +
                    "ON UPDATE CASCADE ON DELETE CASCADE," +
                    "CHECK (chapterNumber REGEXP '^Chap[0-9]{2,}$'));";

            statement.executeUpdate(createChapterTable);
            System.out.println("Created Chapters Table");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
