package ie.gmit.sw;

import java.util.Scanner;

/**
 * Helper class methods help with appending .txt to file input if the user
 * forgets to add it.
 * 
 * @author Mark Reilly
 */
public class Helper {
	Menu m = new Menu();
	private static Scanner console = new Scanner(System.in);

	public static String getInputString(String output) {
		String input = null;
		System.out.print(output);

		input = textAppender(console.next());
		return input;
	}

	public static String getInputString1(String output) {
		String input = null;
		System.out.print(output);

		input = textAppender(console.next());
		return input;
	}

	public static int getInputInt(String output) {
		int input;
		System.out.println(output);

		input = console.nextInt();
		return input;
	}

	public static String textAppender(String fileName) {
		String fileExtension;
		fileExtension = ".txt";
		if (!fileName.contains(fileExtension)) {
			fileName = fileName + fileExtension;
			return fileName;
		}
		return fileName;
	}

	public static String textAppender1(String fileName1) {
		String fileExtension;
		fileExtension = ".txt";
		if (!fileName1.contains(fileExtension)) {
			fileName1 = fileName1 + fileExtension;
			return fileName1;
		}
		return fileName1;
	}

}
