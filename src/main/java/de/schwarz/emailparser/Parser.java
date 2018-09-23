package de.schwarz.emailparser;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Parser {

	private ClassLoader classLoader;

	
	/**
	 * Constructor
	 */
	public Parser() {
		classLoader = this.getClass().getClassLoader();
		if(classLoader == null) {
			System.out.println("classloader is NULL");
		}
	}

    /**
     * Reads Scanner obj line by line and outputs email address in the line with 'Final-Recipient'
     * @param input
     */
	public List<String> readLines(Scanner input) {

        List<String> emailAddresses = new ArrayList<String>();

        String address = ""; // to save email address till we get smtp status code
        String statusCode = "";

		while(input.hasNextLine()) {
		    String nextLine = input.nextLine();
		    if(nextLine.contains("Final-Recipient")) { // line where to find E-Mail address
		    	address = extractEmailAddress(nextLine);

		    	System.out.println("address to delete: " + address);
		    }
			// only after we read diagnostic code
			// and only on status codes: 550,
			// we harves email address to later delete
		    else if(nextLine.contains("Diagnostic-Code")) {
		        // 550 - mailbox unavailable
                // 511 - user unknown
                // 520 - user unknown
                // 553 - no such user
                // 554 - no account with this address
                // 552 - exceeded storage ==> don't delete for now
		        if(nextLine.contains("550") ||
                        nextLine.contains("511") ||
                        nextLine.contains("520") ||
                        nextLine.contains("553") ||
                        nextLine.contains("554") ||
                        nextLine.contains("Host or domain name not found")) {
                    // System.out.println("STATUS CODE: 550 or domain name not found");
                    emailAddresses.add(address);
                }
                else {
                 //   System.out.println("STATUS CODE: " + nextLine);
                }
			}

		}



		input.close();

		return emailAddresses;
	}

	/**
	 *
	 * @return Scanner object from file placed in src/main/resources/emails_folder_file.txt
	 */
	public Scanner getInputFromResource() {

		// classLoader = getClass().getClassLoader();
		InputStream inputStream = classLoader.getResourceAsStream("emails_folder_file.txt");
		Scanner input = null;
		input = new Scanner(inputStream);
		return input;
	}

	/**
	 *
	 * @param fileString file name
	 * @return Scanner object from file given in agrs
	 */
    public Scanner getInputFromFileInArgs(String fileString) {

        File file = new File(fileString);

        Scanner input = null;
        try {
            input = new Scanner(file);
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return input;
    }

	/**
	 * 
	 * @param line
	 * @return String email address found in String line
	 */
	private static String extractEmailAddress(String line) {

	    String address = null;
		Pattern pattern = Pattern.compile("[\\w.-]+@[\\w.-]+");
	    Matcher matcher = pattern.matcher(line);
	    while(matcher.find()){
	    	address = matcher.group();
	    }

		return address;    
	}

}
