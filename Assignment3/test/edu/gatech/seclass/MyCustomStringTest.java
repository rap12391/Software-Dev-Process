package edu.gatech.seclass;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;

/**
 * Junit test class created for use in Georgia Tech CS6300.
 *
 * This is an test class for a simple class that represents a string, defined
 * as a sequence of characters.
 *
 * You should implement your tests in this class.  Do not change the test names.
 */

public class MyCustomStringTest {

    private MyCustomStringInterface mycustomstring;

    @Before
    public void setUp() {
        mycustomstring = new MyCustomString();
    }

    @After
    public void tearDown() {
        mycustomstring = null;
    }

    //Test Purpose: This is the first instructor example test.
    @Test
    public void testCountDuplicates1() {
        mycustomstring.setString("People are sleeping... Zzz.");
        assertEquals(4, mycustomstring.countDuplicates());
    }

    //Test Purpose: Test how method handles a string length of 1
    @Test
    public void testCountDuplicates2() {
        mycustomstring.setString("1");
        assertEquals(0,mycustomstring.countDuplicates());

    }

    //Test Purpose: Testing how method handles empty string
    @Test
    public void testCountDuplicates3() {
        mycustomstring.setString("");
        assertEquals(0,mycustomstring.countDuplicates());

    }

    //Test Purpose: Testing how method handles no duplicates
    @Test
    public void testCountDuplicates4() {
        mycustomstring.setString("abcdefg hijk");
        assertEquals(0, mycustomstring.countDuplicates());

    }

    // Test Purpose: Testing the count of multiple duplicates and the use of special characters
    @Test
    public void testCountDuplicates5() {
        mycustomstring.setString("3333 4444");
        assertEquals(6, mycustomstring.countDuplicates());
    }

    //Test Purpose: Case sensitivity and count of special characters
    @Test
    public void testCountDuplicates6() {
        mycustomstring.setString("     AaBbCcc");
        assertEquals(5, mycustomstring.countDuplicates());

    }

    //Test Purpose: This is the second instructor example test.
    @Test
    public void testAddDigits1() {
        mycustomstring.setString("1234!!! H3y, l3t'5 put 50me d161ts in this 5tr1n6!11!1");
        assertEquals("5678!!! H7y, l7t'9 put 94me d505ts in this 9tr5n0!55!5", mycustomstring.addDigits(4, true));
    }

    //Test Purpose: This the third instructor example test.
    @Test
    public void testAddDigits2() {
        mycustomstring.setString("1234!!! H3y, l3t'5 put 50me d161ts in this 5tr1n6!11!1");
        assertEquals("7890!!! H9y, l9t'1 put 16me d727ts in this 1tr7n2!77!7", mycustomstring.addDigits(4, false));
    }

    //Test Purpose: NullPointException is thrown with null string
    @Test(expected = NullPointerException.class)
    public void testAddDigits3() {
        mycustomstring.setString(null);
        mycustomstring.addDigits(4,true);

    }

    //Test Purpose: IllegalArgumentException thrown if n is above 9
    @Test(expected = IllegalArgumentException.class)
    public void testAddDigits4() {
        mycustomstring.setString("abc123");
        mycustomstring.addDigits(10,true);

    }

    //Test Purpose: IllegalArgumentException thrown if n is below 0
    @Test(expected = IllegalArgumentException.class)
    public void testAddDigits5() {
        mycustomstring.setString("abc123");
        mycustomstring.addDigits(-1, true);

    }

    //Test Purpose: Testing that nothing is added when n=0 and positive = true
    @Test
    public void testAddDigits6() {
        mycustomstring.setString("abc123xyz");
        assertEquals("abc123xyz", mycustomstring.addDigits(0,true));

    }

    //Test Purpose: Method works for all digits when postive = True
    @Test
    public void testAddDigits7() {
        mycustomstring.setString("0123456789 abcd");
        assertEquals("3456789012 abcd", mycustomstring.addDigits(3, true));

    }

    //Test Purpose: Method works for all digits when positive = False
    @Test
    public void testAddDigits8() {
        mycustomstring.setString("0123456789 abcd");
        assertEquals("7890123456 abcd", mycustomstring.addDigits(3, false));

    }

    //Test Purpose: Testing that nothing is added when n=0 and positive = false
    @Test
    public void testAddDigits9() {
        mycustomstring.setString("abc123xyz");
        assertEquals("abc123xyz", mycustomstring.addDigits(0, false));

    }

    //Test Purpose: Testing edge case n=9 and positive= true
    @Test
    public void testAddDigits10() {
        mycustomstring.setString("zxy 98765432 abc");
        assertEquals("zxy 87654321 abc", mycustomstring.addDigits(9,true));

    }

    //Test Purpose: Testing edge case n=9 and positive = false
    @Test
    public void testAddDigits11() {
        mycustomstring.setString("zxy 98765432 abc");
        assertEquals("zxy 09876543 abc", mycustomstring.addDigits(9,false));
        

    }

    //Test Purpose: Test empty string output
    @Test
    public void testAddDigits12() {
        mycustomstring.setString("");
        assertEquals("", mycustomstring.addDigits(5, true));

    }


    //Test Purpose: This is the fourth instructor example test.
    @Test
    public void testFlipLetttersInSubstring1() {
        mycustomstring.setString("H3y, l3t'5 put 50me D161ts in this 5tr1n6!11!!");
        mycustomstring.flipLetttersInSubstring(18, 30);
        assertEquals("H3y, l3t'5 put 50ni s161tD em this 5tr1n6!11!!", mycustomstring.getString());
    }

    //Test Purpose: This is an instructor example test to demonstrate testing for an exception.
    @Test(expected = NullPointerException.class)
    public void testFlipLetttersInSubstring2() {
        mycustomstring.flipLetttersInSubstring(200, 100);
    }

    //Test Purpose: Checking edge conditions for startIndex and endIndex
    @Test
    public void testFlipLetttersInSubstring3() {
        mycustomstring.setString("Hello 1234 GoodBye");
        mycustomstring.flipLetttersInSubstring(1,18);
        assertEquals("eyBdo 1234 oGolleH", mycustomstring.getString());

    }

    //Test Purpose: Checking bounds of inputs
    @Test
    public void testFlipLetttersInSubstring4() {
        mycustomstring.setString("Hello 1234 GoodBye");
        mycustomstring.flipLetttersInSubstring(1,1);
        assertEquals("Hello 1234 GoodBye", mycustomstring.getString());

    }

    //Test Purpose: Testing null input throws NullPointerException
    @Test(expected = NullPointerException.class)
    public void testFlipLetttersInSubstring5() {
        mycustomstring.setString(null);
        mycustomstring.flipLetttersInSubstring(1,4);

    }

    //Test Purpose: Testing endIndex out of bounds, should throw MyIndexOutofBoundsException
    @Test(expected = MyIndexOutOfBoundsException.class)
    public void testFlipLetttersInSubstring6() {
        mycustomstring.setString("Hello");
        mycustomstring.flipLetttersInSubstring(1, 6);

    }

    //Test Purpose: Testing startIndex bounds, should throw MyIndexOutofBoundsException
    @Test(expected = MyIndexOutOfBoundsException.class)
    public void testFlipLetttersInSubstring7() {
        mycustomstring.setString("Hello 1234 Goodbye");
        mycustomstring.flipLetttersInSubstring(0, 5);

    }

    //Test Purpose: Checking if the number of letters are odd, then the middle letter within the string remains unchanged
    @Test
    public void testFlipLetttersInSubstring8() {
        mycustomstring.setString("abc123wxyz");
        mycustomstring.flipLetttersInSubstring(1,10);
        assertEquals("zyx123wcba",mycustomstring.getString());

    }

    //Test Purpose: Checking if empty string throws an index error
    @Test(expected = MyIndexOutOfBoundsException.class)
    public void testFlipLetttersInSubstring9() {
        mycustomstring.setString("");
        mycustomstring.flipLetttersInSubstring(1,1);

    }

    //Test Purpose: Checking if method works on a 1 letter string, should reamin unchanged
    @Test
    public void testFlipLetttersInSubstring10() {
        mycustomstring.setString("a");
        mycustomstring.flipLetttersInSubstring(1,1);
        assertEquals("a", mycustomstring.getString());

    }

}
