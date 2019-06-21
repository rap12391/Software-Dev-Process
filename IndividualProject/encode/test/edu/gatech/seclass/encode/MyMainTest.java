package edu.gatech.seclass.encode;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.Assert.*;

public class MyMainTest {

    private ByteArrayOutputStream outStream;
    private ByteArrayOutputStream errStream;
    private PrintStream outOrig;
    private PrintStream errOrig;
    private Charset charset = StandardCharsets.UTF_8;

    @Rule
    public TemporaryFolder temporaryFolder = new TemporaryFolder();

    @Before
    public void setUp() throws Exception {
        outStream = new ByteArrayOutputStream();
        PrintStream out = new PrintStream(outStream);
        errStream = new ByteArrayOutputStream();
        PrintStream err = new PrintStream(errStream);
        outOrig = System.out;
        errOrig = System.err;
        System.setOut(out);
        System.setErr(err);
    }

    @After
    public void tearDown() throws Exception {
        System.setOut(outOrig);
        System.setErr(errOrig);
    }

    /*
    *  TEST UTILITIES
    */

    // Create File Utility
    private File createTmpFile() throws Exception {
        File tmpfile = temporaryFolder.newFile();
        tmpfile.deleteOnExit();
        return tmpfile;
    }

    // Write File Utility
    private File createInputFile(String input) throws Exception {
        File file =  createTmpFile();

        OutputStreamWriter fileWriter =
                new OutputStreamWriter(new FileOutputStream(file), StandardCharsets.UTF_8);

        fileWriter.write(input);

        fileWriter.close();
        return file;
    }


    //Read File Utility
    private String getFileContent(String filename) {
        String content = null;
        try {
            content = new String(Files.readAllBytes(Paths.get(filename)), charset);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return content;
    }

    /*
    * TEST FILE CONTENT
    */
    private static final String FILE1 = "aabcxyzz123";
    private static final String FILE2 = "abc123\n" +
            "xyz987\n" +
            "def456";
    private static final String FILE3 = "abc123  !@# \n" +
        "xyz987 *()\n" +
        "def456";



    // test cases

    /*
    *   TEST CASES
    */

    // Purpose: To test alphanumeric characters for a file size of one line and no arguments passed, progam should encode with default -n of 13
    // Frame #: Test Case 22
    @Test
    public void encodeTest1() throws Exception {
        File inputFile1 = createInputFile(FILE1);

        String args[] = {inputFile1.getPath()};
        Main.main(args);

        String expected1 = "1414151611121313123";

        String actual1 = getFileContent(inputFile1.getPath());

        assertEquals("The outputs differ!", expected1, actual1);
    }

    // Purpose: To test alphanumeric characters for a file size of many lines and no arguments passed, program should encode with default -n of 13
    // Frame #: Test Case 25
    @Test
    public void encodeTest2() throws Exception {
        File inputFile2 = createInputFile(FILE2);
        String args[] = {inputFile2.getPath()};
        Main.main(args);

        String expected2 = "141516123\n" +
                "111213987\n" +
                "171819456";

        String actual2 = getFileContent(inputFile2.getPath());

        assertEquals("The outputs differ!", expected2, actual2);
    }

    // Purpose: To test -c flag for an input length of larger than 1, file size of many lines and alphanumeric characters
    // Frame #: Test Case 26
    @Test
    public void encodeTest3() throws Exception {
        File inputFile3 = createInputFile(FILE2);

        String args[] = {"-c", "az", inputFile3.getPath()};
        Main.main(args);

        String expected3 = "Abc123\n" +
                "xyZ987\n" +
                "def456";

        String actual3 = getFileContent(inputFile3.getPath());

        assertEquals("The outputs differ!", expected3, actual3);
    }

    // Purpose: Testing if special characters for -c flag crashes program
    // Frame #: Test Case 27
    @Test
    public void encodeTest4() throws Exception {
        File inputFile4 = createInputFile(FILE2);

        String args[] = {"-c", "!@#$%^&*()", inputFile4.getPath()};
        Main.main(args);

        String expected4 = "abc123\n" +
                "xyz987\n" +
                "def456";

        String actual4 = getFileContent(inputFile4.getPath());

        assertEquals("The outputs differ!", expected4, actual4);
    }

    // Purpose: Testing if repeat characters for -c flag. Duplicates of characters should negate each other
    // Frame #: Test Case 28
    @Test
    public void encodeTest5() throws Exception {
        File inputFile5 = createInputFile(FILE2);

        String args[] = {"-c", "aazzz", inputFile5.getPath()};
        Main.main(args);

        String expected5 = "abc123\n" +
                "xyZ987\n" +
                "def456";

        String actual5 = getFileContent(inputFile5.getPath());

        assertEquals("The outputs differ!", expected5, actual5);
    }

    // Purpose: Testing -n flag with valid input.
    // Frame #: Test Case 29
    @Test
    public void encodeTest6() throws Exception {
        File inputFile6 = createInputFile(FILE2);

        String args[] = {"-n", "0", inputFile6.getPath()};
        Main.main(args);

        String expected6 = "010203123\n" +
                "242526987\n" +
                "040506456";

        String actual6 = getFileContent(inputFile6.getPath());

        assertEquals("The outputs differ!", expected6, actual6);
    }

    // Purpose: Testing -r flag with an input of length of text file, there should be no shift
    // Frame #: Test Case 8
    @Test
    public void encodeTest7() throws Exception {
        File inputFile7 = createInputFile(FILE2);

        String args[] = {"-r", "20", inputFile7.getPath()};
        Main.main(args);

        String expected7 = "23abc1\n" +
                "87xyz9\n" +
                "56def4";

        String actual7 = getFileContent(inputFile7.getPath());

        assertEquals("The outputs differ!", expected7, actual7);
    }

    // Purpose: Testing -l flag with an input of length of text file, there should be no shift
    // Frame #: Test Case 26
    @Test
    public void encodeTest8() throws Exception {
        File inputFile8 = createInputFile(FILE2);

        String args[] = {"-l", "20", inputFile8.getPath()};
        Main.main(args);

        String expected8 = "c123ab\n" +
                "z987xy\n" +
                "f456de";

        String actual8 = getFileContent(inputFile8.getPath());

        assertEquals("The outputs differ!", expected8, actual8);
    }

    // Purpose: Testing -l flag with valid input, file contains many lines and special characters and spaces
    // Frame #: Test Case 37
    @Test
    public void encodeTest9() throws Exception {
        File inputFile9 = createInputFile(FILE3);

        String args[] = {"-l", "1", inputFile9.getPath()};
        Main.main(args);

        String expected9 = "bc123  !@# a\n" +
                "yz987 *()x\n" +
                "ef456d";

        String actual9 = getFileContent(inputFile9.getPath());

        assertEquals("The outputs differ!", expected9, actual9);
    }

    // Purpose: Testing -l flag, -n flag, -c flag (with special chars and spaces), when the file has many lines and includes special characters and spaces
    // Frame #: Test Case 51
    @Test
    public void encodeTest10() throws Exception {
        File inputFile10 = createInputFile(FILE3);

        String args[] = {"-n","2","-l","2","-c","abz5!@#", inputFile10.getPath()};
        Main.main(args);

        String expected10 = "05123  !@# 2930\n" +
                "28987 *()2601\n" +
                "084560607";


        String actual10 = getFileContent(inputFile10.getPath());

        assertEquals("The outputs differ!", expected10, actual10);
    }

    // Purpose: Testing -c flag with 1 character input
    // Frame #: Test Case 14
    @Test
    public void encodeTest11() throws Exception {
        File inputFile11 = createInputFile(FILE2);

        String args[] = {"-c","a", inputFile11.getPath()};
        Main.main(args);

        String expected11 = "Abc123\n" +
                "xyz987\n" +
                "def456";

        String actual11 = getFileContent(inputFile11.getPath());

        assertEquals("The outputs differ!", expected11, actual11);
    }

    // Purpose: Testing -r flag with an input less than 1
    // Frame #: Test Case 7
    @Test
    public void encodeTest12() throws Exception {
        File inputFile12 = createInputFile(FILE2);

        String args[] = {"-r","-5", inputFile12.getPath()};
        Main.main(args);

        assertEquals("Usage: encode [-n [int]] [-r int | -l int] [-c string] [-d int] <filename>", errStream.toString().trim());
    }

    // Purpose: Testing -r flag with a non-integer input
    // Frame #: Test Case 6
    @Test
    public void encodeTest13() throws Exception {
        File inputFile13 = createInputFile(FILE2);

        String args[] = {"-r","3.5", inputFile13.getPath()};
        Main.main(args);

        assertEquals("Usage: encode [-n [int]] [-r int | -l int] [-c string] [-d int] <filename>", errStream.toString().trim());

    }

    // Purpose: Testing -c flag if the pattern is not in the file, program should not crash and file content should remain unchanged
    // Frame #: Test Case 16
    @Test
    public void encodeTest14() throws Exception {
        File inputFile14 = createInputFile(FILE2);

        String args[] = {"-c","jkl^&% ", inputFile14.getPath()};
        Main.main(args);

        String expected14 = "abc123\n" +
                "xyz987\n" +
                "def456";

        String actual14 = getFileContent(inputFile14.getPath());

        assertEquals("The outputs differ!", expected14, actual14);
    }

    // Purpose: Testing -c flag if the pattern has quotes included in it, input should read as expected and program should not crash
    // Frame #: Test Case 21
    @Test
    public void encodeTest15() throws Exception {
        File inputFile15 = createInputFile(FILE2);

        String args[] = {"-c","abc\"\"\"\"", inputFile15.getPath()};
        Main.main(args);

        String expected15 = "ABC123\n" +
                "xyz987\n" +
                "def456";

        String actual15 = getFileContent(inputFile15.getPath());

        assertEquals("The outputs differ!", expected15, actual15);
    }

    // Purpose: Testing -n flag with negative input
    // Frame #: Test Case 4
    @Test
    public void encodeTest16() throws Exception {
        File inputFile16 = createInputFile(FILE2);

        String args[] = {"-n","-3", inputFile16.getPath()};
        Main.main(args);

        assertEquals("Usage: encode [-n [int]] [-r int | -l int] [-c string] [-d int] <filename>", errStream.toString().trim());
    }

    // Purpose: Testing -l flag with input less than 1
    // Frame #: Test Case 11
    @Test
    public void encodeTest17() throws Exception {
        File inputFile17 = createInputFile(FILE2);

        String args[] = {"-l","-3", inputFile17.getPath()};
        Main.main(args);

        assertEquals("Usage: encode [-n [int]] [-r int | -l int] [-c string] [-d int] <filename>", errStream.toString().trim());
    }

    //Purpose: Testing -l flag with non-integer input
    //Frame #: Test Case 10
    @Test
    public void encodeTest18() throws Exception {
        File inputFile18 = createInputFile(FILE2);

        String args[] = {"-l","abc", inputFile18.getPath()};
        Main.main(args);
        assertEquals("the output differs!", "Usage: encode [-n [int]] [-r int | -l int] [-c string] [-d int] <filename>", errStream.toString().trim());
    }

    // Purpose: Testing -c flag with input longer than file
    // Frame #: Test Case 15
    @Test
    public void encodeTest19() throws Exception {
        File inputFile19 = createInputFile(FILE1);

        String args[] = {"-c","oeiuwoyrweekjhsaflkbcclkvlknsadklvnjsakufhwieouasndfk", inputFile19.getPath()};
        Main.main(args);
        String expected19 = "aaBcxYzz123";

        String actual19 = getFileContent(inputFile19.getPath());

        assertEquals("the output differs!", expected19, actual19);
    }

    // Purpose: Testing unpresent file
    // Frame #: Test Case 1
    @Test
    public void encodeTest20() throws Exception {

        String args[] = {"-c","test"};
        Main.main(args);

        assertEquals("the output differs!", "File Not Found", errStream.toString().trim());
    }

    // Purpose: Testing -n flag with input greater than 25
    // Frame #: Test Case 5
    @Test
    public void encodeTest21() throws Exception {
        File inputFile21 = createInputFile(FILE2);

        String args[] = {"-n","30", inputFile21.getPath()};
        Main.main(args);

        assertEquals("Usage: encode [-n [int]] [-r int | -l int] [-c string] [-d int] <filename>", errStream.toString().trim());
    }

    // Purpose: Testing no flags with special characters in file
    // Frame #: Test Case 23
    @Test
    public void encodeTest22() throws Exception {
        File inputFile22 = createInputFile(FILE3);

        String args[] = {inputFile22.getPath()};
        Main.main(args);
        String expected22 = "141516123  !@# \n" +
                "111213987 *()\n" +
                "171819456";

        String actual22 = getFileContent(inputFile22.getPath());

        assertEquals("the output differs!", expected22, actual22);
    }

    // Purpose: Testing file size of many lines and alphanumeric content. -n flag value of greater than 0 and -c flag value length of greater than one with alphanumeric input
    // Frame #: Test Case 30
    @Test
    public void encodeTest23() throws Exception {
        File inputFile23 = createInputFile(FILE2);

        String args[] = {"-n", "2", "-c", "abc", inputFile23.getPath()};

        Main.main(args);
        String expected23 = "293031123\n" +
                "260102987\n" +
                "060708456";

        String actual23 = getFileContent(inputFile23.getPath());

        assertEquals("the output differs!", expected23, actual23);
    }

    // Purpose: Testing file size of many lines and alphanumeric content. -n flag value of greater than 0 and -c flag value length of greater than one with alphanumeric input which includes special characters
    // Frame #: Test Case 31
    @Test
    public void encodeTest24() throws Exception {
        File inputFile24 = createInputFile(FILE2);

        String args[] = {"-n", "2", "-c", "abc#$%^", inputFile24.getPath()};

        Main.main(args);
        String expected24 = "293031123\n" +
                "260102987\n" +
                "060708456";

        String actual24 = getFileContent(inputFile24.getPath());

        assertEquals("the output differs!", expected24, actual24);
    }


    // Purpose: Testing file size of many lines and alphanumeric content. -n flag value of greater than 0 and -c flag value length of greater than one with alphanumeric input which includes repeat characters
    // Frame #: Test Case 32
    @Test
    public void encodeTest25() throws Exception {
        File inputFile25 = createInputFile(FILE2);

        String args[] = {"-n", "2", "-c", "abc#$%^a#$%", inputFile25.getPath()};

        Main.main(args);
        String expected25 = "033031123\n" +
                "260102987\n" +
                "060708456";

        String actual25 = getFileContent(inputFile25.getPath());

        assertEquals("the output differs!", expected25, actual25);
    }

    // Purpose: Testing file size of many lines with special characters in the content. No flags are passed here. Special chars should remain unaffected
    // Frame #: Test Case 33
    @Test
    public void encodeTest26() throws Exception {
        File inputFile26 = createInputFile(FILE3);

        String args[] = {inputFile26.getPath()};

        Main.main(args);
        String expected26 = "141516123  !@# \n" +
                "111213987 *()\n" +
                "171819456";

        String actual26 = getFileContent(inputFile26.getPath());

        assertEquals("the output differs!", expected26, actual26);
    }

    // Purpose: Testing file size of many lines with special characters in the content. -c flag value length will be larger than 1 and will contain alphanumeric characters enclosed in quotes.
    //          This will test how the -c flag handles multiple alphanumeric characters in a larger file
    // Frame #: Test Case 34
    @Test
    public void encodeTest27() throws Exception {
        File inputFile27 = createInputFile(FILE3);

        String args[] = {"-c", "ab123", inputFile27.getPath()};

        Main.main(args);
        String expected27 = "ABc123  !@# \n" +
                "xyz987 *()\n" +
                "def456";

        String actual27 = getFileContent(inputFile27.getPath());

        assertEquals("the output differs!", expected27, actual27);
    }

    // Purpose: Testing file size of many lines with special characters in the content. -c flag value length will be larger than 1 and will contain special characters enclosed in quotes.
    //          This will test how the -c flag handles multiple special characters in a larger file
    // Frame #: Test Case 35
    @Test
    public void encodeTest28() throws Exception {
        File inputFile28 = createInputFile(FILE3);

        String args[] = {"-c", "ab123#$%^", inputFile28.getPath()};

        Main.main(args);
        String expected28 = "ABc123  !@# \n" +
                "xyz987 *()\n" +
                "def456";

        String actual28= getFileContent(inputFile28.getPath());

        assertEquals("the output differs!", expected28, actual28);
    }

    // Purpose: Testing file size of many lines with special characters in the content. -c flag value length will be larger than 1 and will contain special and repeat characters enclosed in quotes.
    //          This will test how the -c flag handles multiple special and repeat characters in a larger file
    // Frame #: Test Case 36
    @Test
    public void encodeTest29() throws Exception {
        File inputFile29 = createInputFile(FILE3);

        String args[] = {"-c", "aaabb123#$%^", inputFile29.getPath()};

        Main.main(args);
        String expected29 = "Abc123  !@# \n" +
                "xyz987 *()\n" +
                "def456";

        String actual29 = getFileContent(inputFile29.getPath());

        assertEquals("the output differs!", expected29, actual29);
    }

    // Purpose: Testing -l and -c flag with valid input, file contains many lines and special characters and spaces. -c flag will include alphanumeric characters and will be a length of greater than 1
    // Frame #: Test Case 38
    @Test
    public void encodeTest30() throws Exception {
        File inputFile30 = createInputFile(FILE3);

        String args[] = {"-l", "8","-c", "abc123", inputFile30.getPath()};
        Main.main(args);

        String expected30 = "!@# ABC123  \n" +
                "()xyz987 *\n" +
                "f456de";

        String actual30 = getFileContent(inputFile30.getPath());

        assertEquals("The outputs differ!", expected30, actual30);
    }



}
