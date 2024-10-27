package com.ncsu.ebooks.database.coursetables;

import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

@Slf4j
public class ActiveCourse {
    public static void createTable(Connection conn) throws SQLException {
        try (Statement statement = conn.createStatement()) {
            String createActiveCourseTable = "CREATE TABLE IF NOT EXISTS ActiveCourse (" +
                    "ActiveCourseID VARCHAR(7) NOT NULL," +
                    "CourseID VARCHAR(30) NOT NULL," +
                    "Capacity INT NOT NULL," +
                    "OpenToEnroll BOOLEAN NOT NULL DEFAULT 0," +
                    "PRIMARY KEY (ActiveCourseID)," +
                    "FOREIGN KEY (CourseID) REFERENCES Course(CourseID)" +
                    "ON UPDATE CASCADE ON DELETE CASCADE" +
                    ");";

            statement.executeUpdate(createActiveCourseTable);
            System.out.println("Created Active Courses Table");
        } catch (SQLException e) {
            log.error("An error occurred in Active Course :: createTables", e);
        }
    }
}
