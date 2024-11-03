package com.ncsu.ebooks.database.dataseeder;

import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

@Slf4j
public class UserDataSeeder {
    public static void seedTables(Connection conn) throws SQLException {
        try (Statement statement = conn.createStatement()) {
            String seedUserTable = "INSERT INTO User (userID, firstName, lastName, email, password, role)" +
                    "VALUES (\"ErPe1024\", \"Eric\", \"Perrig\", \"ez356@example.mail\", \"qwdmq\", \"STUDENT\")," +
                    "(\"AlAr1024\", \"Alice\", \"Artho\", \"aa23@edu.mail\", \"omdsws\", \"STUDENT\")," +
                    "(\"BoTe1024\", \"Bob\", \"Temple\", \"bt163@template.mail\", \"sak+=\", \"STUDENT\")," +
                    "(\"LiGa1024\", \"Lily\", \"Gaddy\", \"li123@example.edu\", \"cnaos\", \"STUDENT\")," +
                    "(\"ArMo1024\", \"Aria\", \"Marrow\", \"am213@example.edu\", \"jwocals\", \"STUDENT\")," +
                    "(\"KeRh1014\", \"Kellan\", \"Rhodes\", \"kr21@example.edu\", \"camome\", \"STUDENT\")," +
                    "(\"SiHa1024\", \"Sienna\", \"Hayes\", \"sh13@example.edu\", \"asdqm\", \"STUDENT\")," +
                    "(\"FiWi1024\", \"Finn\", \"Wilder\", \"fw23@example.edu\", \"f13mas\", \"STUDENT\")," +
                    "(\"LeMe1024\", \"Leona\", \"Mercer\", \"lm56@example.edu\", \"ca32\", \"STUDENT\")," +
                    "(\"JaWi1024\", \"James\", \"Williams\", \"jwilliams@ncsu.edu\", \"jwilliams@1234\", \"TEACHING_ASSISTANT\")," +
                    "(\"LiAl0924\", \"Lisa\", \"Alberti\", \"lalberti@ncsu.edu\", \"lalberti&5678@\", \"TEACHING_ASSISTANT\")," +
                    "(\"DaJo1024\", \"David\", \"Johnson\", \"djohnson@ncsu.edu\", \"djohnson%@1122\", \"TEACHING_ASSISTANT\")," +
                    "(\"ElCl1024\", \"Ellie\", \"Clark\", \"eclark@ncsu.edu\", \"eclark^#3654\", \"TEACHING_ASSISTANT\")," +
                    "(\"JeGi0924\", \"Jeff\", \"Gibson\", \"jgibson@ncsu.edu\", \"jgibson$#9877\", \"TEACHING_ASSISTANT\")," +
                    "(\"KeOg1024\", \"Kemafor\", \"Ogan\", \"kogan@ncsu.edu\", \"Ko2024!rpc\", \"FACULTY\")," +
                    "(\"JoDo1024\", \"John\", \"Doe\", \"john.doe@example.com\", \"Jd2024!abc\", \"FACULTY\")," +
                    "(\"SaMi1024\", \"Sarah\", \"Miller\", \"sarah.miller@domain.com\", \"Sm#Secure2024\", \"FACULTY\")," +
                    "(\"DaBr1024\", \"David\", \"Brown\", \"david.b@webmail.com\", \"DbPass123!\", \"FACULTY\")," +
                    "(\"EmDa1024\", \"Emily\", \"Davis\", \"emily.davis@email.com\", \"Emily#2024!\", \"FACULTY\")," +
                    "(\"MiWi1024\", \"Michael\", \"Wilson\", \"michael.w@service.com\", \"Mw987secure\", \"FACULTY\");";

            statement.executeUpdate(seedUserTable);

            String seedFacultyTable = "INSERT INTO Faculty (userID)" +
                    "VALUES (\"KeOg1024\")," +
                    "(\"JoDo1024\")," +
                    "(\"SaMi1024\")," +
                    "(\"DaBr1024\")," +
                    "(\"EmDa1024\")," +
                    "(\"MiWi1024\");";

            statement.executeUpdate(seedFacultyTable);

            System.out.println("Seeded Users Tables Set 1");
        } catch (SQLException e) {
            log.error("An error occurred in Users data seeder Set 1 :: seedTables", e);
        }
    }

    public static void seedTables2(Connection conn) throws SQLException {
        try (Statement statement = conn.createStatement()) {
            String seedTATable = "INSERT INTO TeachingAssistant (userID, activeCourseID)" +
                    "VALUES (\"JaWi1024\", 1)," +
                    "(\"LiAl0924\", 2)," +
                    "(\"DaJo1024\", 3)," +
                    "(\"ElCl1024\", 1)," +
                    "(\"JeGi0924\", 1);";

            statement.executeUpdate(seedTATable);

            String seedStudentTable = "INSERT INTO Student (userID)" +
                    "VALUES (\"ErPe1024\")," +
                    "(\"AlAr1024\")," +
                    "(\"BoTe1024\")," +
                    "(\"LiGa1024\")," +
                    "(\"ArMo1024\")," +
                    "(\"KeRh1014\")," +
                    "(\"SiHa1024\")," +
                    "(\"FiWi1024\")," +
                    "(\"LeMe1024\");";

            statement.executeUpdate(seedStudentTable);

            System.out.println("Seeded Users Tables Set 2");
        } catch (SQLException e) {
            log.error("An error occurred in Users data seeder Set 2 :: seedTables", e);
        }
    }
}
