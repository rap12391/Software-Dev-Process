package edu.gatech.seclass.encode;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.apache.commons.cli.*;

public class Main {

    public static void main(String[] args) throws Exception {
		// Empty Skeleton Method
		if (args.length > 0) {
			String filename = args[args.length-1];
			String output = Main.out(args);
			File file_out = new File(filename);
			writeFile(output, file_out);
		} else {
			System.err.println("Usage: encode [-n [int]] [-r int | -l int] [-c string] <filename>");
		}
	}

	public static String out(String[] args){

		String filename = args[args.length-1];
		String file = getFileContent(filename);
//    	System.out.println(file);
		CommandLineParser parser = new DefaultParser();
		Options options = new Options();

		options.addOption("n", true, "Encode alphabetic numbers as numeric and shift by argument value, default is 13 if not argument is passed");
		options.addOption("r", true, "Rotate the entire string to the right by as many characters as indicated by the required <integer> parameter, which has to be greater than or equal to 1");
		options.addOption("l", true, "Rotate the entire string to the left by as many characters as indicated by the required <integer> parameter, which has to be greater than or equal to 1");
		options.addOption("c", true, "Reverses capitalization for all occurances in a string");

		try {
			CommandLine commandLine = parser.parse(options, args);

			if(commandLine.hasOption("r") && commandLine.hasOption("l")){
				System.err.println("Cannot use both -r and -l flags");
			}
				
			if(commandLine.hasOption("r")) {
				String inputRotateRight = commandLine.getOptionValue("r");
				int rotateRightVal = Integer.parseInt(inputRotateRight);
				if(rotateRightVal>=1) {
					int i = Math.abs((file.length()  - rotateRightVal)) % file.length();
					file = file.substring(i) + file.substring(0, i);
				}else {
					System.err.println("-r flag value must be an integer greater than or equal to 1");
				}
			}
				
			if(commandLine.hasOption("l")) {
				String inputRotateLeft = commandLine.getOptionValue("l");
				int rotateLeftVal = Integer.parseInt(inputRotateLeft);
				if(rotateLeftVal>=1) {
					int i = rotateLeftVal % file.length();
					file = file.substring(i) + file.substring(0, i);
					//				System.out.println(file);
				}else {
					System.err.println("-l flag value must be an integer greater than or equal to 1");
				}
			}

			if(commandLine.hasOption("c")) {
				String inputString = commandLine.getOptionValue("c");
//				inputString = inputString.replace("\"", "");
				char [] inputchars = inputString.toCharArray();
				char [] filechars = file.toCharArray();

				for (int i = 0; i < inputchars.length; i++){
					char c_inp = inputchars[i];
					char c_inp_up = Character.toUpperCase(c_inp);

					for (int j = 0; j < filechars.length; j++){
						char c_txt = filechars[j];

						if(c_inp == c_txt || c_inp_up == c_txt){
							if(Character.isUpperCase(c_txt)){
								filechars[j] = Character.toLowerCase(c_txt);
							} else {
								filechars[j] = Character.toUpperCase(c_txt);
							}
						}
					}
				}
				file = new String(filechars);
//				System.out.println(file);

			}

			if(!commandLine.hasOption("n") && !commandLine.hasOption("r") && !commandLine.hasOption("l") && !commandLine.hasOption("c")) {
				String inputShiftString = commandLine.getOptionValue("n", "13");
				int shiftVal = Integer.parseInt(inputShiftString);
				file = file.toLowerCase();
				StringBuilder sb = new StringBuilder();
				for (char shiftchar: file.toCharArray()){
					if(shiftchar<=122 & shiftchar>=97) {
						int temp = ((shiftchar - 'a' + shiftVal + 1) % 26);
						if(temp<=9){
							sb.append('0'+Integer.toString(temp));
						}else{
							sb.append(temp);
						}
					} else {
						sb.append(shiftchar);
					}
				}
				file = sb.toString();
//				System.out.println(file);
			}
			else if(commandLine.hasOption("n")) {
				String inputShiftString = commandLine.getOptionValue("n");
				int shiftVal = Integer.parseInt(inputShiftString);
				if(shiftVal>=0 && shiftVal<=25) {
					file = file.toLowerCase();
					StringBuilder sb = new StringBuilder();
					for (char shiftchar : file.toCharArray()) {
						if (shiftchar <= 122 & shiftchar >= 97) {
							int temp = ((shiftchar - 'a' + shiftVal + 1) % 26);
							if(temp<=9){
								sb.append('0'+Integer.toString(temp));
							}else{
								sb.append(temp);
							}
						} else {
							sb.append(shiftchar);
						}
					}
					file = sb.toString();
					//				System.out.println(file);
				}else{
					System.err.println("-n value must be greater than or equal to 0 or less than or equal to 25");
				}
			}
		} catch (ParseException e) {
			System.err.println("Usage: encode [-n [int]] [-r int | -l int] [-c string] <filename>");
		}
//		System.out.println(file);
		return file;
	}

	private static void usage() {
        System.err.println("Usage: encode [-n [int]] [-r int | -l int] [-c string] <filename>");
    }

	private static String getFileContent(String filename) {
		Charset charset = StandardCharsets.UTF_8;
		String content = null;
		try {
			content = new String(Files.readAllBytes(Paths.get(filename)), charset);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return content;
	}


	// Write File Utility
	private static File writeFile(String output, File file) throws Exception {

		FileOutputStream fileWriter = new FileOutputStream(file, false);
		byte [] myBytes = output.getBytes();

		fileWriter.write(myBytes);

		fileWriter.close();
		return file;
	}

}