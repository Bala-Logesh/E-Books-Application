package com.ncsu.ebooks.database.listtables;

import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

@Slf4j
public class WaitList {
    public static void createTable(Connection conn) throws SQLException {
        try (Statement statement = conn.createStatement()) {
            String createWaitListTable = "CREATE TABLE IF NOT EXISTS Wait (" +
                    "waitListId INT NOT NULL AUTO_INCREMENT," +
                    "studentID INT NOT NULL," +
                    "activeCourseID VARCHAR(7) NOT NULL," +
                    "PRIMARY KEY (waitListId)," +
                    "FOREIGN KEY (studentID) REFERENCES Student(studentID)" +
                    "ON DELETE CASCADE ON UPDATE CASCADE," +
                    "FOREIGN KEY (activeCourseID) REFERENCES ActiveCourse(activeCourseID)" +
                    "ON DELETE CASCADE ON UPDATE CASCADE" +
                    ");";

            statement.executeUpdate(createWaitListTable);
            System.out.println("Created Wait List Table");
        } catch (SQLException e) {
            log.error("An error occurred in Wait List :: createTables", e);
        }
    }
}
