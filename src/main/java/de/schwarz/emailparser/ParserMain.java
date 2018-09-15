package de.schwarz.emailparser;


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
            String inputFile = args[0];
            input =  parser.getInputFromFileInArgs(inputFile);
        }

        parser.readLines(input);
		
	}	
	
	
}
