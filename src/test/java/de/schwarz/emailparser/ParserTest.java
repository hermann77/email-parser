
package de.schwarz.emailparser;

import org.junit.Test;
import static org.junit.Assert.*;

import java.util.Scanner;

public class ParserTest {
	
    @Test public void testInputIsNotNull() {
        Parser parser = new Parser();
        Scanner input = parser.getInputFromResource();
        
        assertFalse(input == null);
    }
}
