package com.ncsu.ebooks.database;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class ETextBook {
    public static void createTable(Connection conn) throws SQLException {
        try (Statement statement = conn.createStatement()) {
            String createETBTable = "CREATE TABLE IF NOT EXISTS ETextBook (" +
                    "eTextBookID INT PRIMARY KEY AUTO_INCREMENT NOT NULL," +
                    "title VARCHAR(255) NOT NULL" +
                    ");";

            statement.executeUpdate(createETBTable);
            System.out.println("Created ETextbook Table");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
