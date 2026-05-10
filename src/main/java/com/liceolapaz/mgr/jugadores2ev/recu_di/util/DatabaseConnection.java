package com.liceolapaz.mgr.jugadores2ev.recu_di.util;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DatabaseConnection {

    private static Connection connection = null;
    private static final Logger LOGGER = Logger.getLogger(DatabaseConnection.class.getName());
    private static final Properties PROPERTIES = new Properties();

    static {
        try (InputStream input = DatabaseConnection.class.getClassLoader().getResourceAsStream("config.properties")) {
            if (input != null) {
                PROPERTIES.load(input);
            } else {
                LOGGER.log(Level.SEVERE, "No config.properties0 found");
            }
        } catch (IOException ex) {
            LOGGER.log(Level.SEVERE, "Error loading DB configuration", ex);
        }
    }

    private DatabaseConnection() { }

    public static synchronized Connection getConnection() {
        try {
            if (connection == null || connection.isClosed()) {
                connection = DriverManager.getConnection(
                        PROPERTIES.getProperty("db.url"),
                        PROPERTIES.getProperty("db.user"),
                        PROPERTIES.getProperty("db.password"));
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "DB connection error", e);
        }
        return connection;
    }
}