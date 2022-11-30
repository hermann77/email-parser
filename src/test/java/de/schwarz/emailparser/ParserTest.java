package de.schwarz.emailparser;

import org.junit.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

import static org.junit.Assert.*;


public class ParserTest {

    /**
     * tests if Scanner object from input file
     * in resource folder (src/main/resources/emails_folder_file.txt) is NOT empty
     */
    @Test public void testInputFromResourceIsNotNull() {
        Parser parser = new Parser();
        Scanner input = parser.getInputFromResource();

        assertNotNull(input);
    }

    /**
     * tests if Scanner object from input file
     * given in args is NOT empty
     *
     * emails_folder_file.txt must be placed in project ROOT folder
     */
    @Test public void testInputIsNotNull() {
        Parser parser = new Parser();
        HashMap<String, List<String>> emailAddressesToDeletePerSubDir = new HashMap<>();
        emailAddressesToDeletePerSubDir = parser.scanPath("E-Mails_Export");

        assertNotNull(emailAddressesToDeletePerSubDir);
    }
}
