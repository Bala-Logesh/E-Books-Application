package com.ncsu.ebooks.database.usertables;

import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

@Slf4j
public class TeachingAssistant {
    public static void createTable(Connection conn) throws SQLException {
        try (Statement statement = conn.createStatement()) {
            String createTeachingAssistantTable = "CREATE TABLE IF NOT EXISTS TeachingAssistant (" +
                    "teachingAsstID INT NOT NULL AUTO_INCREMENT," +
                    "userID VARCHAR(8) NOT NULL," +
                    "activeCourseID INT," +
                    "resetPassword BOOLEAN DEFAULT 1," +
                    "PRIMARY KEY (teachingAsstID)," +
                    "FOREIGN KEY (userID) REFERENCES User(userID)" +
                    "ON DELETE CASCADE ON UPDATE CASCADE," +
                    "FOREIGN KEY (activeCourseID) REFERENCES ActiveCourse(activeCourseID)" +
                    "ON DELETE CASCADE ON UPDATE CASCADE" +
                    ");";

            statement.executeUpdate(createTeachingAssistantTable);
            System.out.println("Created Teaching Assistant Table");
        } catch (SQLException e) {
            log.error("An error occurred in Teaching Assistant :: createTables", e);
        }
    }
}
