package com.ncsu.ebooks.database.coursetables;

import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

@Slf4j
public class Course {
    public static void createTable(Connection conn) throws SQLException {
        try (Statement statement = conn.createStatement()) {
            String createCourseTable = "CREATE TABLE IF NOT EXISTS Course (" +
                    "courseID VARCHAR(30) NOT NULL," +
                    "title VARCHAR(255) NOT NULL," +
                    "facultyID INT NOT NULL," +
                    "startDate DATETIME NOT NULL," +
                    "endDate DATETIME NOT NULL," +
                    "eTextBookID INT NOT NULL," +
                    "PRIMARY KEY (courseID)," +
                    "FOREIGN KEY (facultyID) REFERENCES Faculty(facultyID)" +
                    "ON UPDATE CASCADE ON DELETE RESTRICT," +
                    "FOREIGN KEY (eTextBookID) REFERENCES ETextBook(eTextBookID)" +
                    "ON UPDATE CASCADE ON DELETE RESTRICT" +
                    ");";

            statement.executeUpdate(createCourseTable);
            System.out.println("Created Courses Table");
        } catch (SQLException e) {
            log.error("An error occurred in Course :: createTables", e);
        }
    }
}
