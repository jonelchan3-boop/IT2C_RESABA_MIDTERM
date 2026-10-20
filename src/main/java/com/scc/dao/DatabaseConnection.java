package com.scc.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static final String DB_URL = "jdbc:sqlite:scenario.db";

    public static Connection getConnection() throws SQLException {
        // Force load SQLite driver
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            throw new SQLException("SQLite Driver not found!", e);
        }
        Connection conn = DriverManager.getConnection(DB_URL);
        try (var stmt = conn.createStatement()) {
            stmt.execute("PRAGMA foreign_keys = ON;");
        }
        return conn;
    }

    public static void initializeDatabase() {
        String createTableSQL = """
            CREATE TABLE IF NOT EXISTS appointments (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                patient_name TEXT NOT NULL,
                doctor TEXT NOT NULL,
                consult_fee REAL NOT NULL,
                status TEXT NOT NULL CHECK(status IN ('SCHEDULED', 'COMPLETED'))
            );
            """;

        try (Connection conn = getConnection();
             var stmt = conn.createStatement()) {
            stmt.execute(createTableSQL);
            System.out.println("✅ Database & table ready!");
        } catch (SQLException e) {
            System.err.println("Database init error: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        initializeDatabase();
    }
}
