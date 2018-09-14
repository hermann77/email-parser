package de.schwarz.emailparser;

import java.io.InputStream;
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
	
	
	public void readLines() {
		Scanner input = null;
		input = getInput();

		while(input.hasNextLine()) {
		    String nextLine = input.nextLine();
		    if(nextLine.contains("Final-Recipient")) { // line where to find E-Mail address
		    	String address = extractEmailAddress(nextLine);
		    	System.out.println("Address: " + address);
		    }
		}

		input.close();
	}
	
	
	public Scanner getInput() {

		// classLoader = getClass().getClassLoader();
		InputStream inputStream = classLoader.getResourceAsStream("emails_folder_file.txt");
		Scanner input = null;
		input = new Scanner(inputStream);
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
