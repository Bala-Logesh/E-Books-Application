package com.ncsu.ebooks.database.listtables;

import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

@Slf4j
public class EnrolledList {
    public static void createTable(Connection conn) throws SQLException {
        try (Statement statement = conn.createStatement()) {
            String createEnrolledListTable = "CREATE TABLE IF NOT EXISTS Enrolled (" +
                    "enrolledID INT NOT NULL AUTO_INCREMENT," +
                    "studentID INT NOT NULL," +
                    "activeCourseID INT NOT NULL," +
                    "courseScore INT NOT NULL DEFAULT 0," +
                    "PRIMARY KEY (enrolledID)," +
                    "FOREIGN KEY (studentID) REFERENCES Student(studentID)" +
                    "ON DELETE CASCADE ON UPDATE CASCADE," +
                    "FOREIGN KEY (activeCourseID) REFERENCES ActiveCourse(activeCourseID)" +
                    "ON DELETE CASCADE ON UPDATE CASCADE," +
                    "UNIQUE (studentID, activeCourseID)" +
                    ");";

            statement.executeUpdate(createEnrolledListTable);
            System.out.println("Created Enrolled Table");
        } catch (SQLException e) {
            log.error("An error occurred in Enrolled :: createTables", e);
        }
    }
}
