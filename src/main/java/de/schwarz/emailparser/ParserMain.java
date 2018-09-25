package de.schwarz.emailparser;

import org.apache.commons.cli.*;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ParserMain {
    
	
	/**
	 * Main method
	 * @param args cli arguments
	 */
	public static void main(String[] args) {

        Parser parser = new Parser();
        Scanner input = null;


        Options options = new Options();

        Option inputOpt = new Option("i", "input", true, "input file path");
        inputOpt.setRequired(true);
        options.addOption(inputOpt);

        Option dbNameOpt = new Option("d", "dbname", true, "database name where the email addresses are saved");
        dbNameOpt.setRequired(true);
        options.addOption(dbNameOpt);

        Option tableOpt = new Option("t", "table", true, "database table name where the email addresses are saved");
        tableOpt.setRequired(true);
        options.addOption(tableOpt);

        Option attrOpt = new Option("a", "attribute", true, "attribute name where the email addresses are saved");
        attrOpt.setRequired(true);
        options.addOption(attrOpt);

        Option portOpt = new Option("p", "port", true, "database port");
        portOpt.setRequired(false);
        options.addOption(portOpt);

        CommandLineParser cliParser = new DefaultParser();
        HelpFormatter formatter = new HelpFormatter();
        CommandLine cmd = null;

        try{
            cmd = cliParser.parse(options, args);
        }
        catch (ParseException e) {
            System.out.println(e.getMessage());
            formatter.printHelp("java -jar build/libs/email-parser-all-<VERSION>.jar", options);

            System.exit(1);
        }


        if(args.length == 0) {
            input = parser.getInputFromResource();
            parser.readLines(input);
        }
        // file name given in args
        else {
            String inputFile = cmd.getOptionValue("input");
            String databaseName = cmd.getOptionValue("dbname");
            String tableName = cmd.getOptionValue("table");
            String attributeName = cmd.getOptionValue("attribute");
            String port = cmd.getOptionValue("port");

            input = parser.getInputFromFileInArgs(inputFile);

            List<String> emailAddressesToDelete = new ArrayList<String>();
            emailAddressesToDelete = parser.readLines(input);

            if(port == null) {
                port = "3306";
            }
            deleteEmailAddressesFromDB(databaseName, tableName, attributeName, emailAddressesToDelete, port);
        }

	}




	private static void deleteEmailAddressesFromDB(String databaseName, String tableName, String attributeName, List<String> emailAddressesToDelete, String port) {

        Connection connection = DBConnection.getConnection(databaseName, port);
        if(connection == null) {
            return;
        }

        PreparedStatement stmt = null;
        try {
            for(String address : emailAddressesToDelete) {
                stmt = connection.prepareStatement("DELETE FROM " + tableName +" WHERE " + attributeName + " IN (?)");

                stmt.setString(1, address);
                int updateCount = stmt.executeUpdate();
            }

        }
        catch (NullPointerException e) {
            System.out.println("NullPointerException");
        }
        catch (SQLException e) {
            System.out.println("SQL query not executed: " + e.toString());
        }
	}

	
	
}
