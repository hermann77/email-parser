package de.schwarz.emailparser;


public class ParserMain {
    
	
	/**
	 * Main method
	 * @param args
	 */
	public static void main(String[] args) {

		if(args.length == 0) {
			Parser parser = new Parser();
			parser.readLines();
		}
		else {
            String inputFile = args[0];

        }
		
	}	
	
	
}
