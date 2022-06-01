package config;

import java.sql.Connection;
import java.sql.DriverManager;

public class DatabaseConfiguration {

    public static Connection connect() {
        try {
            Class.forName("org.sqlite.JDBC");
            Connection connection = DriverManager.getConnection("jdbc:sqlite:database/quizDb.db");
            return connection;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }
}