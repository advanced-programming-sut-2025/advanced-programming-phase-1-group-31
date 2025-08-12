package common;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseManager {
    private static final String DB_URL = "jdbc:sqlite:userinfo/userinfo.db";

    public static Connection getConnection() throws SQLException {
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return DriverManager.getConnection(DB_URL);
    }


    public static void initDatabase() {
        String sql = """
                CREATE TABLE IF NOT EXISTS users (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    username TEXT NOT NULL UNIQUE,
                    password TEXT NOT NULL,
                    nickname TEXT,
                    lobby_json TEXT
                );
                """;

        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
            System.out.println("Database initialized.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
