package de.schwarz.emailparser;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    private static String dbName = "lesenindeutschland";
    private static String url = "jdbc:mysql://localhost:3307/" + dbName;
    private static String driverName = "com.mysql.jdbc.Driver";
    private static String username = "root";
    private static String password = "";
    private static Connection connection;


    public static Connection getConnection() {
        try {
            Class.forName(driverName);
            try {
                connection = DriverManager.getConnection(url, username, password);
            }
            catch (SQLException e) {
                System.out.println("DB connection error: " + e.getErrorCode());
            }
        }
        catch (ClassNotFoundException e) {
            System.out.println("DB driver not fount: " + e.getException().toString());
        }

        return connection;
    }

}
