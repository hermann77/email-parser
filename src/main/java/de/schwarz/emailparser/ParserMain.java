package de.schwarz.emailparser;


import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class ParserMain {
    
	
	/**
	 * Main method
	 * @param args
	 */
	public static void main(String[] args) {

        Parser parser = new Parser();
        Scanner input = null;

        // no file name given ==> use the standard place in src/main/resources/emails_folder_file.txt
		if(args.length == 0) {
            input = parser.getInputFromResource();
		}
		// file name given in args
		else {
		    if(args.length == 1) {
                String inputFile = args[0];
                input = parser.getInputFromFileInArgs(inputFile);
            }
            // DATABASE name passed in parameters 
            else if(args.length > 1) {
            	
            	String databaseName = args[1];
            	String port = args[2];
            	deleteEmailAddressesFromDB(databaseName, port)

            }
        }

        parser.readLines(input);
		
	}
	
	
	
	private void deleteEmailAddressesFromDB(String databaseName, String port) {
		
        Connection connection = DBConnection.getConnection();
        if(connection == null) {
            System.out.println("connection is null");
        }

        Statement stmt = null;
        ResultSet resultSet = null;
        try {
            stmt = connection.createStatement();
            resultSet = stmt.executeQuery("SELECT nl_email FROM lid_subscribers LIMIT 10");
            while (resultSet.next()) {
                String nl_email = resultSet.getString(1);
                System.out.println("DB e-mail address: " + nl_email);
            }
        }
        catch (SQLException e) {
            System.out.println("SQL query not executed: " + e.getErrorCode());
        }

	}
	
	
}
