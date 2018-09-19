package de.schwarz.emailparser;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
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
            parser.readLines(input);
		}
		// file name given in args
		else {

            String inputFile = args[0];
            input = parser.getInputFromFileInArgs(inputFile);

            // only emails list file passed
		    if(args.length == 1) {
                parser.readLines(input);
            }
            // emails file AND DATABASE name passed in parameters
            else if(args.length == 2) {
            	String databaseName = args[1];

            	List<String> emailAddressesToDelete = new ArrayList<String>();
            	emailAddressesToDelete = parser.readLines(input);

            	deleteEmailAddressesFromDB(databaseName, emailAddressesToDelete);
            }
            // DATABASE name AND port passed in parameters
            else if(args.length == 3) {
                String databaseName = args[1];
                String port = args[2];

                List<String> emailAddressesToDelete = new ArrayList<String>();
                emailAddressesToDelete = parser.readLines(input);

                deleteEmailAddressesFromDB(databaseName, emailAddressesToDelete, port);

            }
        }
		
	}


    private static void deleteEmailAddressesFromDB(String databaseName, List<String> emailAddressesToDelete) {
	    // use standard port 3306 (standard port on MacOS: 3307)
	    deleteEmailAddressesFromDB(databaseName, emailAddressesToDelete, "3306");
    }


	private static void deleteEmailAddressesFromDB(String databaseName, List<String> emailAddressesToDelete, String port) {
		
        Connection connection = DBConnection.getConnection(databaseName, port);
        if(connection == null) {
            System.out.println("connection is null");
        }

        //Statement stmt = null;
        PreparedStatement stmt = null;
        ResultSet resultSet = null;
        try {
            //stmt = connection.createStatement();
            stmt = connection.prepareStatement("DELETE FROM lid_subscribers WHERE nl_email IN (?)");
            Object[] emailAddressesToDeleteArray = emailAddressesToDelete.toArray();
            Array array = connection.createArrayOf("VARCHAR", emailAddressesToDeleteArray);
            stmt.setArray(1, array);
            int updateCount = stmt.executeUpdate();

            /*
            resultSet = stmt.executeQuery("SELECT nl_email FROM lid_subscribers LIMIT 10");
            while (resultSet.next()) {
                String nl_email = resultSet.getString(1);
                System.out.println("DB e-mail address: " + nl_email);
            }
            */
        }
        catch (SQLException e) {
            System.out.println("SQL query not executed: " + e.getErrorCode());
        }
	}

	
	
}
