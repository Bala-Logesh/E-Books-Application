package com.ncsu.ebooks.database.misctables;

import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

@Slf4j
public class StudentScoreSummary {
    public static void createTable(Connection conn) throws SQLException {
        try (Statement statement = conn.createStatement()) {
            String createNotificationTable = "CREATE TABLE IF NOT EXISTS StudentScoreSummary (" +
                    "ssummaryID INT NOT NULL AUTO_INCREMENT," +
                    "studentID INT NOT NULL," +
                    "activeCourseID INT NOT NULL," +
                    "totalPoints INT NOT NULL DEFAULT 0," +
                    "totalActivities INT DEFAULT 0," +
                    "PRIMARY KEY (ssummaryID)," +
                    "FOREIGN KEY (studentID) REFERENCES Student(studentID)" +
                    "ON UPDATE CASCADE ON DELETE CASCADE," +
                    "FOREIGN KEY (activeCourseID) REFERENCES ActiveCourse(activeCourseID)" +
                    "ON UPDATE CASCADE ON DELETE CASCADE," +
                    "timeStamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP," +
                    "UNIQUE (studentID, activeCourseID)" +
                    ");";

            statement.executeUpdate(createNotificationTable);
            System.out.println("Created Student Score Summary Table");
        } catch (SQLException e) {
            log.error("An error occurred in Student Score Summary :: createTables", e);
        }
    }
}
