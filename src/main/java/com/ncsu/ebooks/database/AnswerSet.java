package com.ncsu.ebooks.database;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class AnswerSet {
    public static void createTable(Connection conn) throws SQLException {
        try (Statement statement = conn.createStatement()) {
            String createAnswerSetTable = "CREATE TABLE IF NOT EXISTS AnswerSet (" +
                    "answerSetID INT AUTO_INCREMENT UNIQUE NOT NULL," +
                    "activityID INT NOT NULL, " +
                    "answerOption LONGTEXT NOT NULL, " +
                    "correct BOOLEAN NOT NULL DEFAULT 0, " +
                    "explanation LONGTEXT NOT NULL, " +
                    "PRIMARY KEY (activityID, answerSetID)," +
                    "FOREIGN KEY (activityID) REFERENCES Activity(activityID)" +
                    "ON UPDATE CASCADE ON DELETE RESTRICT" +
                    ");";

            statement.executeUpdate(createAnswerSetTable);
            System.out.println("Created Answer Set Table");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
