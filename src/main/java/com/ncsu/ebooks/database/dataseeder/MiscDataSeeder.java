package com.ncsu.ebooks.database.dataseeder;

import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

@Slf4j
public class MiscDataSeeder {
    public static void seedTables(Connection conn) throws SQLException {
        try (Statement statement = conn.createStatement()) {
            String seedWaitListTable = "INSERT INTO Wait (studentID, activeCourseID)" +
                    "VALUES (8, 1)," +
                    "(9, 2)," +
                    "(2, 2)," +
                    "(7, 2)," +
                    "(8, 2);";

            statement.executeUpdate(seedWaitListTable);

            String seedEnrolledListTable = "INSERT INTO Enrolled (studentID, activeCourseID)" +
                    "VALUES (1, 1)," +
                    "(1, 2)," +
                    "(2, 1)," +
                    "(3, 1)," +
                    "(4, 1)," +
                    "(4, 2)," +
                    "(5, 2)," +
                    "(5, 1)," +
                    "(7, 1)," +
                    "(8, 3)," +
                    "(9, 1);";

            statement.executeUpdate(seedEnrolledListTable);

            String seedScoresTable = "INSERT INTO StudentScore (studentID, activeCourseID, activityID, point, timeStamp)" +
                    "VALUES (1, 1, 2, 3, '2024-08-01 11:10:00')," +
                    "(1, 1, 3, 1, '2024-08-01 14:18:00')," +
                    "(1, 2, 3, 1, '2024-08-02 19:06:00')," +
                    "(2, 1, 2, 3, '2024-08-01 13:24:00')," +
                    "(3, 1, 2, 0, '2024-08-01 09:24:00')," +
                    "(4, 1, 2, 3, '2024-08-01 07:45:00')," +
                    "(4, 1, 3, 3, '2024-08-01 12:30:00')," +
                    "(4, 2, 2, 3, '2024-08-03 16:52:00')," +
                    "(5, 1, 2, 1, '2024-08-01 21:18:00')," +
                    "(5, 1, 3, 3, '2024-08-01 05:03:00')," +
                    "(8, 3, 4, 1, '2024-08-29 22:41:00');";

            statement.executeUpdate(seedScoresTable);

            String seedScoreSummaryTable = "INSERT INTO StudentScoreSummary (studentID, activeCourseID, totalPoints, totalActivities)" +
                    "VALUES (1, 1, 4, 2)," +
                    "(1, 2, 1, 1)," +
                    "(2, 1, 3, 1)," +
                    "(3, 1, 0, 1)," +
                    "(4, 1, 9, 3)," +
                    "(4, 2, 0, 0)," +
                    "(5, 2, 0, 0)," +
                    "(5, 1, 4, 2)," +
                    "(7, 1, 0, 0)," +
                    "(8, 3, 1, 1);";


            statement.executeUpdate(seedScoreSummaryTable);

            System.out.println("Seeded Misc Tables");
        } catch (SQLException e) {
            log.error("An error occurred in Misc :: seedTables", e);
        }
    }
}
