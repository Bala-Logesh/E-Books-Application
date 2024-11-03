package com.ncsu.ebooks.database.dataseeder;

import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

@Slf4j
public class CourseDataSeeder {
    public static void seedTables(Connection conn) throws SQLException {
        try (Statement statement = conn.createStatement()) {
            String seedCourseTable = "INSERT INTO Course (courseID, title, facultyID, startDate, endDate, eTextBookID)" +
                    "VALUES (\"NCSUOganCSC440F24\", \"CSC440 Database Systems\", 1, '2024-08-15', '2024-12-15', 101)," +
                    "(\"NCSUOganCSC540F24\", \"CSC540 Database Systems\", 1, '2024-08-17', '2024-12-15', 101)," +
                    "(\"NCSUSaraCSC326F24\", \"CSC326 Software Engineering\", 3, '2024-08-23', '2024-10-23', 102)," +
                    "(\"NCSUDoeCSC522F24\", \"CSC522 Fundamentals of Machine Learning\", 2, '2024-08-25', '2025-12-18', 101)," +
                    "(\"NCSUSaraCSC326F25\", \"CSC326 Software Engineering\", 3, '2025-08-27', '2025-12-19', 102);";

            statement.executeUpdate(seedCourseTable);

            String seedActiveCourseTable = "INSERT INTO ActiveCourse (courseID, token, capacity)" +
                    "VALUES (\"NCSUOganCSC440F24\", \"XYJKLM\", 60)," +
                    "(\"NCSUOganCSC540F24\", \"STUKZT\", 50)," +
                    "(\"NCSUSaraCSC326F24\", \"LRUFND\", 100);";

            statement.executeUpdate(seedActiveCourseTable);

            String seedEvaluationCourseTable = "INSERT INTO EvaluationCourse (courseID)" +
            "VALUES (\"NCSUDoeCSC522F24\")," +
            "(\"NCSUSaraCSC326F25\");";

            statement.executeUpdate(seedEvaluationCourseTable);


            System.out.println("Seeded Courses Tables");
        } catch (SQLException e) {
            log.error("An error occurred in Courses data seeder :: seedTables", e);
        }
    }
}
