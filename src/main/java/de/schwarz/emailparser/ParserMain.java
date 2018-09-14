package de.schwarz.emailparser;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
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
		Parser parser = new Parser();
		parser.readLines();
		
	}	
	
	
}
