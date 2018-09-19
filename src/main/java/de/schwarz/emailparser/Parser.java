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

		while(input.hasNextLine()) {
		    String nextLine = input.nextLine();
		    if(nextLine.contains("Final-Recipient")) { // line where to find E-Mail address
		    	String address = extractEmailAddress(nextLine);

				emailAddresses.add(address);
		    	System.out.println(address);
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
