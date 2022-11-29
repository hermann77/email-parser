package de.schwarz.emailparser;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Parser {

	private final ClassLoader classLoader;

	
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

	//	input.useDelimiter("\\n|\\s+");

		while(input.hasNextLine()) {
	//	while(input.hasNext("$")) {
		    String nextLine = input.nextLine();

	//		System.out.println("Nextline: " + nextLine);

		    if(nextLine.contains("Final-Recipient")) { // line where to find E-Mail address
		    	address = extractEmailAddress(nextLine);

		    //	System.out.println("address to delete: " + address);
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
                   // System.out.println("STATUS CODE: " + nextLine);
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
	 * Top-level management of the scanning entire given directory for e-mails with auto-responses.
	 * The scanning/parsing the mbox files is put through to the readLines() method.
	 *
	 * @param pathString directory name where the e-mail responses are saved
	 * @return HashMap<String, List<String>> from file given in agrs
	 */
    public HashMap<String, List<String>> scanPath(String pathString) {

		HashMap<String, List<String>> emailAdrToDelete = new HashMap<>();
        File path = new File(pathString);
		/**
		 * 	Subdirectories with e-mail responses.
		 * 	Each subdirectory/mbox-directory must include a file 'mbox' with all the e-mails
 		 */
		File[] mboxDirs = path.listFiles();

		assert mboxDirs != null;
		for (File mboxDir : mboxDirs) {
			List<String> emailAddrToDeleteMbox = new ArrayList<>();

			String mboxDirName = mboxDir.getName();
			System.out.println("Dir: " + mboxDirName);

			Scanner input = null;
			try {
				File mboxFileObj = new File(mboxDir.getAbsolutePath() + "/mbox");
				input = new Scanner(mboxFileObj, "ISO-8859-1");
			}
			catch (FileNotFoundException e) {
				e.printStackTrace();
			}

			emailAddrToDeleteMbox = this.readLines(input);
			emailAdrToDelete.put(mboxDirName, emailAddrToDeleteMbox);
		}

		return emailAdrToDelete;
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
