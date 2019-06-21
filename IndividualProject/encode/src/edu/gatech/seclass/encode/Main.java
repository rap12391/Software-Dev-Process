package edu.gatech.seclass.encode;

import java.io.File;
import java.io.IOException;
import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;
import org.apache.commons.cli.*;

public class Main {

    public static void main(String[] args) {
    	try {
			// Empty Skeleton Method
			if (args.length > 0) {
				String filename = args[args.length - 1];
				String output = Main.out(args);
				File file_out = new File(filename);
				writeFile(output, file_out);
			} else{
				usage();
			}
		}catch(Exception e){
			System.err.println("File Not Found");
		}
	}

	public static String out(String[] args){
		String filename = args[args.length-1];
		String file = getFileContent(filename);
		List<String> list = new ArrayList<String>();

		for(int i =0; i<args.length; i++){
			if(list.contains(args[i])&&args[i].equals("-n")|args[i].equals("-c")|args[i].equals("-r")|args[i].equals("-l")|args[i].equals("-d")){
				int ind = list.indexOf(args[i]);
				list.remove(ind+1);
				list.remove(ind);
			}
			list.add(args[i]);
			if(args[i].equals("-n")&& !isInteger(args[i+1])){
				list.add("0");
			}
		}
		args = list.toArray(new String[0]);

		CommandLineParser parser = new DefaultParser();
		Options options = new Options();

		options.addOption("n", true, "Encode alphabetic numbers as numeric and shift by argument value, default is 13 if not argument is passed");
		options.addOption("r", true, "Rotate the entire string to the right by as many characters as indicated by the required <integer> parameter, which has to be greater than or equal to 1");
		options.addOption("l", true, "Rotate the entire string to the left by as many characters as indicated by the required <integer> parameter, which has to be greater than or equal to 1");
		options.addOption("c", true, "Reverses capitalization for all occurances in a string");
		options.addOption("d", true, "Removes number occurrences of argument value");


		try {
			CommandLine commandLine = parser.parse(options, args);

			if(commandLine.hasOption("r") && commandLine.hasOption("l")){
				usage();
			}

			if(commandLine.hasOption("d")){
				String rem_str_val = commandLine.getOptionValue("d");
				int rem_val = Integer.parseInt(rem_str_val);
				if(rem_val>=0) {
					char[] unique_chars = getUniqueChars(file);
					for (int i = 0; i < unique_chars.length; i++) {
//					List<Integer> indexVals = getIndexVals(unique_chars[i], file);
						file = updateString(file, unique_chars[i], rem_val);
					}
				}else {
					usage();
				}
			}
				
			if(commandLine.hasOption("r")) {
				String inputRotateRight = commandLine.getOptionValue("r");
				int rotateRightVal = Integer.parseInt(inputRotateRight);
				if(!file.isEmpty()) {
					String[] lines = file.split("(?<=\\r\\n|\\n|\\r)", -1);
					int numoflines = lines.length;
					if (rotateRightVal >= 1 && numoflines > 0) {
						StringBuilder rotated_sb = new StringBuilder();
						for (int i = 0; i < numoflines; i++) {
							String line = lines[i];
							String sub_str = line.substring(line.length() - 1);
							if (sub_str.equals("\n") | sub_str.equals("\r") | sub_str.equals("\r\n")) {
								String delim = sub_str;
								line = line.substring(0, line.length() - 1);
								int j = Math.abs((line.length() - rotateRightVal)) % line.length();
								if ((line.length() - rotateRightVal) < 0) {
									j = line.length() - j;
								}
								line = line.substring(j) + line.substring(0, j);
								rotated_sb.append(line + delim);
							} else {
								int j = Math.abs((line.length() - rotateRightVal)) % line.length();
								if ((line.length() - rotateRightVal) < 0) {
									j = line.length() - j;
								}
								line = line.substring(j) + line.substring(0, j);
								rotated_sb.append(line);
							}
						}
						file = new String(rotated_sb.toString());

					}else {
						usage();
					}
				} else {
					usage();
				}
			}
				
			if(commandLine.hasOption("l")) {
				String inputRotateLeft = commandLine.getOptionValue("l");
				int rotateLeftVal = Integer.parseInt(inputRotateLeft);
				if(!file.isEmpty()) {
					String[] lines = file.split("(?<=\\r\\n|\\n|\\r)", -1);
					int numoflines = lines.length;
					if (rotateLeftVal >= 1 && numoflines > 0) {
						StringBuilder rotated_sb = new StringBuilder();
						for (int i = 0; i < numoflines; i++) {
							String line = lines[i];
							String sub_str = line.substring(line.length() - 1);
							if (sub_str.equals("\n") | sub_str.equals("\r") | sub_str.equals("\r\n")) {
								String delim = sub_str;
								line = line.substring(0, line.length() - 1);
								int j = rotateLeftVal % line.length();
								line = line.substring(j) + line.substring(0, j);
								rotated_sb.append(line + delim);
							} else {
								int j = rotateLeftVal % line.length();
								line = line.substring(j) + line.substring(0, j);
								rotated_sb.append(line);
							}
						}
						file = new String(rotated_sb.toString());
						//				System.out.println(file);
					} else {
						usage();
					}
				} else{
					usage();
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

			if(!commandLine.hasOption("n") && !commandLine.hasOption("r") && !commandLine.hasOption("l") && !commandLine.hasOption("c") && !commandLine.hasOption("d")) {
				String inputShiftString = commandLine.getOptionValue("n", "13");
				int shiftVal = Integer.parseInt(inputShiftString);
//				file = file.toLowerCase();
				StringBuilder sb = new StringBuilder();
				for (char shiftchar: file.toCharArray()){
					if(shiftchar<=122 & shiftchar>=97) {
						int temp = ((shiftchar - 'a' + shiftVal + 1) % 26);
						if (temp==0){
							temp = 26;
						}
						if(temp<=9){
							sb.append('0'+Integer.toString(temp));
						}else{
							sb.append(temp);
						}
					} else if(shiftchar<=90 & shiftchar>=65) {
						int temp = ((shiftchar - 'A' + shiftVal + 1) % 26)+26;
						if(temp == 26){
							temp = 52;
						}
						if (temp <= 9) {
							sb.append('0' + Integer.toString(temp));
						}else{
							sb.append(temp);
						}
					}
					else {
						sb.append(shiftchar);
					}
				}
				file = sb.toString();
//				System.out.println(file);
			}
			if(commandLine.hasOption("n")) {
				String inputShiftString = commandLine.getOptionValue("n");
				int shiftVal = Integer.parseInt(inputShiftString);
				if(shiftVal>=0 && shiftVal<=25) {
//					file = file.toLowerCase();
					StringBuilder sb = new StringBuilder();
					for (char shiftchar : file.toCharArray()) {
						if (shiftchar <= 122 & shiftchar >= 97) {
							int temp = ((shiftchar - 'a'+1 +shiftVal) % 26);
							if (temp==0){
								temp = 26;
							}
							if(temp<=9){
								sb.append('0'+Integer.toString(temp));
							}else {
								sb.append(temp);
							}
						} else if(shiftchar<=90 & shiftchar>=65) {
							int temp = ((shiftchar - 'A'+1+shiftVal) % 26)+26;
							if(temp==26){
								temp = 52;
							}
							if (temp <= 9) {
								sb.append('0' + Integer.toString(temp));
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
					usage();
				}
			}
		} catch (ParseException e) {
			usage();
		} catch (NumberFormatException e){
			usage();
		}
		return file;
	}

	private static void usage() {
        System.err.println("Usage: encode [-n [int]] [-r int | -l int] [-c string] [-d int] <filename>");
    }

	private static String getFileContent(String filename) {
		Charset charset = StandardCharsets.UTF_8;
		String content = null;
		try {
			content = new String(Files.readAllBytes(Paths.get(filename)), charset);
		} catch (IOException e) {
//			e.printStackTrace();
		}
		return content;
	}


	// Write File Utility
	private static File writeFile(String output, File file) throws Exception {
		try {
			FileOutputStream fileWriter = new FileOutputStream(file, false);
			byte[] myBytes = output.getBytes();

			fileWriter.write(myBytes);

			fileWriter.close();
		}catch (Exception e){
			System.err.println("File Not Found");
		}
		return file;
	}

	private static char[] getUniqueChars(String input_string){
		Set<Character> chars = new TreeSet<>();

		for( char c : input_string.toCharArray() ) {
			chars.add(c);
		}
		String char_string = chars.toString();
		char [] char_arr = char_string.toCharArray();
		return char_arr;
	}

	private static List<Integer> getIndexVals(char input_char, String file) {
		int index = file.indexOf(input_char);
		List<Integer> positions = new ArrayList();
		while (index >= 0) {
//			System.out.println(index);
			positions.add(index);
			index = file.indexOf(input_char, index + 1);
		}
		return positions;
	}


	private static String updateString(String input_string, char c, int rem_val){
		StringBuilder sb = new StringBuilder(input_string);
		List<Integer> index_list = getIndexVals(c, input_string);
		Collections.reverse(index_list);
		for(int elem: index_list){
			int ii = index_list.size()- index_list.indexOf(elem);
			if(ii>rem_val) {
				sb.deleteCharAt(elem);
			}
		}
		String result_string = sb.toString();
		return result_string;
	}

	public static boolean isInteger(String s) {
		try {
			Integer.parseInt(s);
		} catch(NumberFormatException e) {
			return false;
		} catch(NullPointerException e) {
			return false;
		}
		// only got here if we didn't return false
		return true;
	}
}