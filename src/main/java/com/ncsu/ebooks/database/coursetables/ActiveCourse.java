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
                    "activeCourseID INT AUTO_INCREMENT NOT NULL," +
                    "courseID VARCHAR(30) NOT NULL," +
                    "capacity INT NOT NULL," +
                    "token VARCHAR(7) NOT NULL," +
                    "openToEnroll BOOLEAN NOT NULL DEFAULT 0," +
                    "PRIMARY KEY (activeCourseID)," +
                    "FOREIGN KEY (courseID) REFERENCES Course(courseID)" +
                    "ON UPDATE CASCADE ON DELETE CASCADE" +
                    ");";

            statement.executeUpdate(createActiveCourseTable);
            System.out.println("Created Active Courses Table");
        } catch (SQLException e) {
            log.error("An error occurred in Active Course :: createTables", e);
        }
    }
}
