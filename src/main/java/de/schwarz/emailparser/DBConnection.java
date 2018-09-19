package de.schwarz.emailparser;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    private static String dbName = "lesenindeutschland";
    private static String username = "root";
    private static String password = "";
    private static String url = "jdbc:mysql://localhost:3306/" + dbName + "?user=" + username
                                                                + "&password=" + password ;
    private static String driverName = "com.mysql.jdbc.Driver";
    private static Connection connection;


    public static Connection getConnection() {
        try {
            Class.forName(driverName).newInstance();
            try {
                connection = DriverManager.getConnection(url);
                if(connection == null) {
                    System.out.println("connection is NULL");
                }
                else {
                    System.out.println("connection is NOT NULL");
                }
            }
            catch (SQLException e) {
                System.out.println("DB connection error: " + e.getErrorCode());
            }
        }
        catch (Exception e) {
            System.out.println("DB driver not found: " + e.getMessage());
            System.out.println("DB CLASS NOT FOUND: " + e.getClass());
        }

        return connection;
    }

}
