
package de.schwarz.emailparser;

import org.junit.Test;
import static org.junit.Assert.*;

public class ParserTest {
    @Test public void testSomeLibraryMethod() {
        ParserMain parserMain = new ParserMain();
        
        String [] expectedEmailsArray = {"test1@gmx.de", "test2@gmail.com"};
        String [] actualEmailsArray = {"test1@gmx.de", "test2@gmail.com"};
       
        assertArrayEquals("readEmailAddresses()", expectedEmailsArray, actualEmailsArray);
    }
}
