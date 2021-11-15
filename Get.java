package challengeBackToBinary;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Get {

	/**
	 * Gets an integer from user, within a specified range.
	 * <hr>
	 * 
	 * @param minNum An int - the minimum number.
	 * @param maxNum An int - the maximum possible number.
	 * @return An int within specified range
	 */
	public static int get_int(String prompt, int minNum, int maxNum) {
		int number = 0;
		boolean inRange = false;
		while (!inRange) {
			number = get_int(prompt);
			if (number >= minNum && number <= maxNum) {
				inRange = true;
			} else {
				System.out.println("Integer was not within range");
			}
		}
		return number;
	}

	/**
	 * Gets an integer from user
	 * <hr>
	 * 
	 * @return An int of any value
	 */
	public static int get_int(String prompt) {
		int number = 0;
		boolean gotInt = false; // flag used to control the loop
		@SuppressWarnings("resource")
		Scanner scanner = new Scanner(System.in);
		System.out.println(prompt);
		while (!gotInt) {
			try {
				number = scanner.nextInt();
				gotInt = true;
			} catch (InputMismatchException e) {
				System.out.println("Naw mate. Thats not even an integer.");
			} finally {
				scanner.nextLine();
			}
		}
		return number;
	}

	/**
	 * Gets a String from user that is 8 characters long.
	 * @param prompt A String to prompt user with
	 * @return An 8 character String containing only 1s and 0s
	 * @throws InputMismatchException When the String contains any character other than '1' and '0'.
	 */
	public static String get_8bit_String(String prompt) throws InputMismatchException{
		@SuppressWarnings("resource")
		Scanner scanner = new Scanner(System.in);
		String errorMessage = "";
		String returnString = "";
		boolean hasError = false;
		System.out.println(prompt);
		for (int i = 0;returnString.length() != 8; i++) {
			if (i != 0 ) {
				System.out.println("The string needs to be 8 characters long");
			}
			returnString = scanner.nextLine();
		}
		for (int i = 0; i < returnString.length(); i++) {
			if (returnString.charAt(i) != '1' &&returnString.charAt(i) != '0') {
				hasError = true;
				errorMessage = "There were characters other than 1 and 0 in the provided string";
			}
		}
		if (hasError) {
			InputMismatchException e = new InputMismatchException(errorMessage);
			throw e;
		}
		return returnString;
	}
}
