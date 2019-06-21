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

        assertEquals("The files differ!", expected1, actual1);
    }

    // Purpose: To test alphanumeric characters for a file size of many lines and no arguments passed, program should encode with default -n of 13
    // Frame #: Test Case 25
    @Test
    public void encodeTest2() throws Exception {
        File inputFile2 = createInputFile(FILE2);

        Main.main(args);

        String expected2 = "141516123\n" +
                "111213987\n" +
                "171819456";

        String actual2 = getFileContent(inputFile2.getPath());

        assertEquals("The files differ!", expected2, actual2);
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

        assertEquals("The files differ!", expected3, actual3);
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

        assertEquals("The files differ!", expected4, actual4);
    }

    // Purpose: Testing if repeat characters for -c flag. Assuming that repeat characters do not negate each other
    // Frame #: Test Case 28
    @Test
    public void encodeTest5() throws Exception {
        File inputFile5 = createInputFile(FILE2);

        String args[] = {"-c", "aazzz", inputFile5.getPath()};
        Main.main(args);

        String expected5 = "Abc123\n" +
                "xyZ987\n" +
                "def456";

        String actual5 = getFileContent(inputFile5.getPath());

        assertEquals("The files differ!", expected5, actual5);
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

        assertEquals("The files differ!", expected6, actual6);
    }

    // Purpose: Testing -r flag with an input of 26, there should be no shift
    // Frame #: Test Case 8
    @Test
    public void encodeTest7() throws Exception {
        File inputFile7 = createInputFile(FILE2);

        String args[] = {"-r", "26", inputFile7.getPath()};
        Main.main(args);

        String expected7 = "abc123\n" +
                "xyz987\n" +
                "def456";

        String actual7 = getFileContent(inputFile7.getPath());

        assertEquals("The files differ!", expected7, actual7;
    }

    // Purpose: Testing -l flag with an input of 26, there should be no shift
    // Frame #: Test Case 26
    @Test
    public void encodeTest8() throws Exception {
        File inputFile8 = createInputFile(FILE2);

        String args[] = {"-l", "26", inputFile8.getPath()};
        Main.main(args);

        String expected8 = "abc123\n" +
                "xyz987\n" +
                "def456";

        String actual8 = getFileContent(inputFile8.getPath());

        assertEquals("The files differ!", expected8, actual8;
    }

    // Purpose: Testing -l flag with valid input, file contains many lines and special characters and spaces
    // Frame #: Test Case 37
    @Test
    public void encodeTest9() throws Exception {
        File inputFile9 = createInputFile(FILE3);

        String args[] = {"-l", "1", inputFile9.getPath()};
        Main.main(args);

        String expected9 = "bc123  !@# x\n" +
                "yz987 *()d\n" +
                "ef456a";

        String actual9 = getFileContent(inputFile9.getPath());

        assertEquals("The files differ!", expected9, actual9;
    }

    // Purpose: Testing -l flag, -n flag, -c flag (with special chars and spaces), when the file has many lines and includes special characters and spaces
    // Frame #: Test Case 51
    @Test
    public void encodeTest10() throws Exception {
        File inputFile10 = createInputFile(FILE3);

        String args[] = {"-n","2","-l","2","-c","abz5!@#" inputFile10.getPath()};
        Main.main(args);

        String expected10 = "05123  !@# 2601 \n" +
                "02987 *()0607 \n" +
                "084560304";

        String actual10 = getFileContent(inputFile10.getPath());

        assertEquals("The files differ!", expected10, actual10;
    }

    // Purpose: Testing -c flag with 1 character input
    // Frame #: Test Case 14
    @Test
    public void encodeTest11() throws Exception {
        File inputFile11 = createInputFile(FILE2);

        String args[] = {"-c","a" inputFile11.getPath()};
        Main.main(args);

        String expected11 = "Abc123\n" +
                "xyz987\n" +
                "def456";

        String actual11 = getFileContent(inputFile11.getPath());

        assertEquals("The files differ!", expected11, actual11;
    }

    // Purpose: Testing -r flag with an input less than 1
    // Frame #: Test Case 7
    @Test
    public void encodeTest12() throws Exception {
        File inputFile12 = createInputFile(FILE2);

        String args[] = {"-r","-5" inputFile12.getPath()};
        Main.main(args);

        assertEquals("-r flag value must be an integer greater than or equal to 1", errStream.toString().trim());
    }

    // Purpose: Testing -r flag with a non-integer input
    // Frame #: Test Case 6
    @Test
    public void encodeTest13() throws Exception {
        File inputFile13 = createInputFile(FILE2);

        String args[] = {"-r","3.5" inputFile13.getPath()};
        Main.main(args);

        assertEquals("-r flag value must be an integer greater than or equal to 1", errStream.toString().trim());
    }

    // Purpose: Testing -c flag if the pattern is not in the file, program should not crash and file content should remain unchanged
    // Frame #: Test Case 16
    @Test
    public void encodeTest14() throws Exception {
        File inputFile14 = createInputFile(FILE2);

        String args[] = {"-c","jkl^&% " inputFile14.getPath()};
        Main.main(args);

        String expected14 = "abc123\n" +
                "xyz987\n" +
                "def456";

        String actual14 = getFileContent(inputFile14.getPath());

        assertEquals("The files differ!", expected14, actual14;
    }

    // Purpose: Testing -c flag if the pattern has quotes included in it, input should read as expected and program should not crash
    // Frame #: Test Case 21
    @Test
    public void encodeTest15() throws Exception {
        File inputFile15 = createInputFile(FILE2);

        String args[] = {"-c","abc\"\"\"\"" inputFile15.getPath()};
        Main.main(args);

        String expected15 = "ABC123\n" +
                "xyz987\n" +
                "def456";

        String actual15 = getFileContent(inputFile15.getPath());

        assertEquals("The files differ!", expected15, actual15;
    }



}
