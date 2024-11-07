package com.ncsu.ebooks.database;

import com.ncsu.ebooks.database.booktables.*;
import com.ncsu.ebooks.database.coursetables.ActiveCourse;
import com.ncsu.ebooks.database.coursetables.Course;
import com.ncsu.ebooks.database.coursetables.EvaluationCourse;
import com.ncsu.ebooks.database.dataseeder.BookDataSeeder;
import com.ncsu.ebooks.database.dataseeder.CourseDataSeeder;
import com.ncsu.ebooks.database.dataseeder.MiscDataSeeder;
import com.ncsu.ebooks.database.dataseeder.UserDataSeeder;
import com.ncsu.ebooks.database.listtables.EnrolledList;
import com.ncsu.ebooks.database.listtables.WaitList;
import com.ncsu.ebooks.database.misctables.Notification;
import com.ncsu.ebooks.database.misctables.StudentScore;
import com.ncsu.ebooks.database.misctables.StudentScoreSummary;
import com.ncsu.ebooks.database.usertables.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

@Slf4j
@Component
public class DBConnector {
    @Value("${spring.datasource.url}")
    private String url;

    @Value("${spring.datasource.username}")
    private String user;

    @Value("${spring.datasource.password}")
    private String password;

    @Value("${spring.datasource.database}")
    private String database;

    public void connectToDB() {
        try (Connection conn = DriverManager.getConnection(url, user, password)) {
            System.out.println("Successfully connected to MariaDB");
            createTables(conn);
            System.out.println("All tables created successfully");
            seedTables(conn);
            System.out.println("All tables seeded successfully");
        } catch (SQLException e) {
            log.error("An error occurred in connectToDB", e);
        }
    }

    private void createTables(Connection conn) throws SQLException {
        try (Statement statement = conn.createStatement()) {
            String useDB = "USE " + database + ";";
            statement.executeUpdate(useDB);
            System.out.println("Using " + database + " database");

            ETextBook.createTable(conn);
            Chapter.createTable(conn);
            Section.createTable(conn);
            ContentBlock.createTable(conn);
            Activity.createTable(conn);
            AnswerSet.createTable(conn);

            User.createTable(conn);
            Admin.createTable(conn);
            Faculty.createTable(conn);
            Student.createTable(conn);

            Course.createTable(conn);
            EvaluationCourse.createTable(conn);
            ActiveCourse.createTable(conn);

            TeachingAssistant.createTable(conn);

            EnrolledList.createTable(conn);
            WaitList.createTable(conn);

            Notification.createTable(conn);
            StudentScore.createTable(conn);
            StudentScoreSummary.createTable(conn);
        } catch (SQLException e) {
            log.error("An error occurred in createTables", e);
        }
    }


    private void seedTables(Connection conn) throws SQLException {
        try (Statement statement = conn.createStatement()) {
            String useDB = "USE " + database + ";";
            statement.executeUpdate(useDB);
            System.out.println("Using " + database + " database");

            BookDataSeeder.seedTables(conn);
            UserDataSeeder.seedTables(conn);
            CourseDataSeeder.seedTables(conn);
            UserDataSeeder.seedTables2(conn);
            MiscDataSeeder.seedTables(conn);
        } catch (SQLException e) {
            log.error("An error occurred in seedTables", e);
        }
    }
}