package com.ncsu.ebooks.database.coursetables;

import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

@Slf4j
public class EvaluationCourse {
    public static void createTable(Connection conn) throws SQLException {
        try (Statement statement = conn.createStatement()) {
            String createEvaluationCourseTable = "CREATE TABLE IF NOT EXISTS EvaluationCourse (" +
                    "evaluationID INT NOT NULL AUTO_INCREMENT," +
                    "courseID VARCHAR(30) NOT NULL," +
                    "PRIMARY KEY (evaluationID)," +
                    "FOREIGN KEY (courseID) REFERENCES Course(courseID)" +
                    "ON UPDATE CASCADE ON DELETE CASCADE" +
                    ");";

            statement.executeUpdate(createEvaluationCourseTable);
            System.out.println("Created Evaluation Courses Table");
        } catch (SQLException e) {
            log.error("An error occurred in Evaluation Course :: createTables", e);
        }
    }
}
