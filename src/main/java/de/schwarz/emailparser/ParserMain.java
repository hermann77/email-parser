package de.schwarz.emailparser;

import org.apache.commons.cli.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class ParserMain {
    
	
	/**
	 * Main method
	 * @param args cli arguments
	 */
	public static void main(String[] args) {

        Parser parser = new Parser();
        Scanner input = null;
        List<String> emailAddressesToDelete = new ArrayList<String>();
        HashMap<String, List<String>> emailAddressesToDeletePerSubDir = new HashMap<>();

        Options options = new Options();
        Option inputOpt = new Option("i", "input", true, "input file path");
        inputOpt.setRequired(false);
        options.addOption(inputOpt);

        Option dbNameOpt = new Option("d", "dbname", true, "database name where the email addresses are saved");
        dbNameOpt.setRequired(false);
        options.addOption(dbNameOpt);

        Option tableOpt = new Option("t", "table", true, "database table name where the email addresses are saved");
        tableOpt.setRequired(false);
        options.addOption(tableOpt);

        Option attrOpt = new Option("a", "attribute", true, "attribute name where the email addresses are saved");
        attrOpt.setRequired(false);
        options.addOption(attrOpt);

        Option portOpt = new Option("p", "port", true, "database port");
        portOpt.setRequired(false);
        options.addOption(portOpt);

        Option testOpt = new Option("tp", "test-parser", true, "only for testing email parser");
        testOpt.setRequired(false);
        options.addOption(testOpt);

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
            emailAddressesToDelete = parser.readLines(input);
        }
        // file name given in args
        else {
            String inputPath = cmd.getOptionValue("input");
            String databaseName = cmd.getOptionValue("dbname");
            String tableName = cmd.getOptionValue("table");
            String attributeName = cmd.getOptionValue("attribute");
            String port = cmd.getOptionValue("port");

            emailAddressesToDeletePerSubDir = parser.scanPath(inputPath);

            if(port == null) {
                port = "3306";
            }

            emailAddressesToDelete = processIntersection(emailAddressesToDeletePerSubDir);

            //    deleteEmailAddressesFromDB(databaseName, tableName, attributeName, emailAddressesToDelete, port);
        }


        System.out.println("Amount of entire intersection: " + emailAddressesToDelete.size());

        for (String address : emailAddressesToDelete) {
            System.out.println("Address: " + address);
        }

	}


    /**
     * Top level intersection processing
     * where we take multiple lists List<String>
     * wrapped in a HashMap (HashMap<String, List<String>>):
     * for every directory
     */
    private static List<String> processIntersection(HashMap<String, List<String>> emailAddressesToDeletePerSubDir) {
        List<String> intersectList = new ArrayList<String>();

        AtomicInteger entryCounter = new AtomicInteger();

        intersectList = emailAddressesToDeletePerSubDir.entrySet().stream()
                .map(entry -> {
                    entryCounter.getAndIncrement();
                    System.out.println("Processing entry " + entryCounter.get()
                            + " :: Directory " + entry.getKey()
                            + " :: Amount of email addresses: " + entry.getValue().size());
                    // map only the email list
                    return entry.getValue();
                })
                .reduce((intersect, list) -> {
                    return buildIntersect(intersect, list);
                })
                .orElse(new ArrayList<String>());

        return intersectList;
    }


    /**
     * Intersection of two List<String>
     * @param list1
     * @param list2
     * @return List<String>
     */
    private static List<String> buildIntersect(List<String> list1, List<String> list2) {

        if (list2.size() == 0) {
            return new ArrayList<String>();
        }
        else { // on first call list1 is empty, so we build no intersection but just list2
            if (list1.size() == 0) {
                return list2;
            }
        }

        List<String> intersectList = new ArrayList<String>();

        intersectList = list1.stream()
                .distinct()
                .filter(list2::contains)
                .collect(Collectors.toList());

        return intersectList;
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
            System.out.println("SQL query not executed: " + e);
        }
	}


}
