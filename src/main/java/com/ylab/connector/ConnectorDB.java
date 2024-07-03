package com.ylab.connector;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Singleton class for database connection.
 */
public class ConnectorDB {

    private static ConnectorDB instance;
    private Connection connection;

    private static String URL = "jdbc:postgresql://localhost:5432/coworkingDB";
    private static String USER = "root";
    private static String PASSWORD = "root";

    private ConnectorDB() {
        try {
            this.connection = DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Gets the single instance of ConnectorDB.
     *
     * @return single instance of ConnectorDB
     */
    public static ConnectorDB getInstance() {
        if (instance == null) {
            instance = new ConnectorDB();
        }
        return instance;
    }

    public static void setConnectionDetails(String jdbcUrl, String username, String password) {
        ConnectorDB.URL = jdbcUrl;
        ConnectorDB.USER = username;
        ConnectorDB.PASSWORD = password;
    }

    /**
     * Gets the database connection.
     *
     * @return the database connection
     */
    public Connection getConnection() {
        return connection;
    }
}
