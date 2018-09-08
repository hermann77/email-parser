package de.schwarz.emailparser;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.InetAddress;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ParserMain {
    
	/**
	 * Main method
	 * @param args
	 */
	public static void main(String[] args) {	
		ParserMain parserMain = new ParserMain();
		parserMain.readLines();
		
	}
	
	
	private void readLines() {
		
		ClassLoader classLoader = getClass().getClassLoader();
		File file = new File(classLoader.getResource("emails_folder_file.txt").getFile());
		Scanner input = null;
		try {
			input = new Scanner(file);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		while(input.hasNextLine()) {
		    String nextLine = input.nextLine();
		    if(nextLine.contains("Final-Recipient")) { // line where to find E-Mail address
		    	String address = extractEmailAddress(nextLine);
		    	System.out.println("Address: " + address);
		    }
		}

		input.close();
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
