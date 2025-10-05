package db;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 * DBManager — singleton that manages one shared JDBC {@link Connection}.
 * Connects to local MySQL 'webshop' database using credentials defined here.
 * Called via {@link #getConnection()} from other classes.
 */
public class DBManager {
    private static DBManager instance = null;
    private Connection con = null;

    private static final String URL =
            "jdbc:mysql://localhost:3306/webshop"
                    + "?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC"
                    + "&useUnicode=true&characterEncoding=UTF-8";

    private static final String USER = "webshop_user";
    private static final String PASS = "webshop_pw";

    private static DBManager getInstance() {
        if(instance == null)
            instance = new DBManager();
        return instance;
    }

    private DBManager() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
            con = DriverManager.getConnection(URL, USER, PASS);
        } catch (Exception e) {
            System.err.println("Database connection failed: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static Connection getConnection() {
        return getInstance().con;
    }
}
