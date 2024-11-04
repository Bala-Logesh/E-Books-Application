package com.ncsu.ebooks.database.misctables;

import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

@Slf4j
public class StudentScore {
    public static void createTable(Connection conn) throws SQLException {
        try (Statement statement = conn.createStatement()) {
            String createNotificationTable = "CREATE TABLE IF NOT EXISTS StudentScore (" +
                    "scoreID INT NOT NULL AUTO_INCREMENT," +
                    "studentID INT NOT NULL," +
                    "activeCourseID INT NOT NULL," +
                    "activityID INT NOT NULL," +
                    "point INT DEFAULT 0," +
                    "PRIMARY KEY (scoreID)," +
                    "FOREIGN KEY (studentID) REFERENCES Student(studentID)" +
                    "ON UPDATE CASCADE ON DELETE CASCADE," +
                    "FOREIGN KEY (activityID) REFERENCES Activity(activityID)" +
                    "ON UPDATE CASCADE ON DELETE CASCADE," +
                    "FOREIGN KEY (activeCourseID) REFERENCES ActiveCourse(activeCourseID)" +
                    "ON UPDATE CASCADE ON DELETE CASCADE," +
                    "timeStamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP," +
                    "UNIQUE (studentID, activeCourseID, activityID)" +
                    ");";

            statement.executeUpdate(createNotificationTable);
            System.out.println("Created Student Score Table");
        } catch (SQLException e) {
            log.error("An error occurred in Student Score :: createTables", e);
        }
    }
}
