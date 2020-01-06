package ie.gmit.sw;

import java.util.Scanner;

/**
 * Class <i>Helper</i> methods helps with appending .txt to file input if the user
 * forgets to add it.
 * 
 * @author Mark Reilly
 */
public class Helper {
	Menu m = new Menu();
	private static Scanner console = new Scanner(System.in);

	/**
	 * Outputs the String  
	 * @param output
	 * @return the String <code>input</code>
	 */
	public static String getInputString(String output) {
		String input = null;
		System.out.print(output);

		input = textAppender(console.next());
		return input;
	}
	/**
	 * Returns the int entered.
	 * @param output
	 * @return
	 */
	public static int getInputInt(String output) {
		int input;
		System.out.println(output);

		input = console.nextInt();
		return input;
	}
	/**
	 * Checks if the String input ends in .txt.
	 * @param fileName
	 * @return true if String ends in .txt, else add .txt to
	 * the end of the String.
	 */
	public static String textAppender(String fileName) {
		String fileExtension;
		fileExtension = ".txt";
		if (!fileName.contains(fileExtension)) {
			fileName = fileName + fileExtension;
			return fileName;
		}
		return fileName;
	}

	

}
