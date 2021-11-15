package challengeBackToBinary;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {

	public static ArrayList<boolean[]> binaryList;

	/**
	 * Takes a 64-length String of 1s and 0s, and performs various actions
	 * 
	 * @param args
	 */
	public static void main(String[] args) {

		// CHALLENGE STRING
		String binaryStr = "1000011110100111001111001011000101110001100001011011111011110000"; //8x 8-bit signed twos complements

		String[] binaryStrArr = new String[] {
				binaryStr.substring(0, 8), 	// -121
				binaryStr.substring(8, 16), // -89
				binaryStr.substring(16, 24),// +60
				binaryStr.substring(24, 32),// -79
				binaryStr.substring(32, 40),// +113
				binaryStr.substring(40, 48),// -123
				binaryStr.substring(48, 56),// -66
				binaryStr.substring(56, 64),// -16
		};

		//		String[] binaryStrArr = new String[] {
		//				"10000111",
		//				"10100111",
		//				"00111100",
		//				"10110001",
		//				"01110001",
		//				"10000101",
		//				"10111110",
		//				"11110000"
		//		};

		binaryList = populateArrayList(binaryStrArr);
		run();
	}

	///////////////////////////////////
	//			COMPONENTS			 //
	///////////////////////////////////

	/**
	 * 
	 * User menu switch. Takes a users menu selection and performs requested operation.
	 * 
	 * @param selection - A users menu selection
	 * @return An int - the users selection
	 */
	public static int userSwitch(int selection) {
		@SuppressWarnings("resource")
		Scanner scanner = new Scanner(System.in);
		System.out.println();
		switch (selection) {
		case 1: //1) Display all values 
			displayList();
			break;
		case 2: //2) Add 2 numbers from table 
			addFromTable();
			break;
		case 3: //3) Add 2 binary numbers (entered as binary string) 
			addFromUser();
			break;
		case 4: //4) Subtract one number from another (from table) 
			subtractFromTable();
			break;
		case 5: //5) Subtract one number from another (entered as binary string)
			subtractFromUser();
			break;
		case 6: //6) Enter binary to display as denary
			calculateDenaryFromBinary();
			break;
		case 7: //7) Enter denary to display as binary
			calculateBinaryFromDenary();
			break;
		}
		if (selection != 8) {
			System.out.printf("%nPRESS RETURN TO CONTINUE...%n");
			@SuppressWarnings("unused")
			String trash = scanner.nextLine(); // really shitty way of pausing the program until user presses enter. its no good
		}
		return selection;
	}

	/**
	 * Populate an ArrayList with string[] elements
	 * 
	 * @param binaryStrArr A String[] containing raw 8-bit binary numbers
	 * @return An ArrayList<boolean[]> representing the String[]
	 */
	public static ArrayList<boolean[]> populateArrayList(String[] binaryStrArr) {
		ArrayList<boolean[]> binaryList = new ArrayList<>();
		for (int i = 0; i < binaryStrArr.length; i++) {
			binaryList.add(Calc.convertStringToBoolArr(binaryStrArr[i]));
		}
		return binaryList;
	}

	/**
	 * Runs the program until user selects option 9 (Exit/ Quit)
	 */
	public static void run() {
		int selection = 0;
		while (selection != 8) {
			displayMenu();
			selection = userSwitch(Get.get_int("Please select an option:", 1, 8));
		}
		System.out.println("Exiting program");
	}

	///////////////////////////////////
	//	  	MENU OPERATIONS			 //
	///////////////////////////////////

	/**
	 * Gets two binary numbers from user, adds them together and prints the sum
	 */
	public static void addFromUser() {
		String binaryStrSource = Get.get_8bit_String("Provide a two's complement number (8-length, 1s and 0s only)");
		boolean[] binaryBoolSource = Calc.convertStringToBoolArr(binaryStrSource);
		String binaryStrAddor = Get.get_8bit_String("Provide a two's complement number to subtract from the first (8-length, 1s and 0s only)");
		boolean[] binaryBoolAddor = Calc.convertStringToBoolArr(binaryStrAddor);
		boolean[] subtracted = null;
		try {
			subtracted = Calc.add(binaryBoolSource, binaryBoolAddor, true);
			displayResult(binaryBoolSource, binaryBoolAddor, subtracted);
		} catch (ArithmeticException e) {
			System.err.println(e.getMessage());
		}

	}

	/**
	 * Gets two binary numbers from binaryList (selected by user), adds them and prints the sum
	 */
	public static void addFromTable() {
		displayList();
		int selection1 = Get.get_int("Please select the first number", 1, 8) - 1;
		int selection2 = Get.get_int("Please select the second number", 1, 8) - 1;
		try {
			boolean[] sum = Calc.add(binaryList.get(selection1), binaryList.get(selection2), true);
			displayResult(binaryList.get(selection1), binaryList.get(selection2), sum);
		} catch (ArithmeticException e) {
			System.err.println(e.getMessage());
		}
	}

	/**
	 * Gets two binary numbers from binaryList, subtracts the second number from the first, and prints the result
	 */
	public static void subtractFromTable() {
		displayList();
		boolean[] binarySource = binaryList.get(Get.get_int("Select the first binary number", 1, 8) - 1);
		boolean[] binaryToSubtract = binaryList.get(Get.get_int("Select the number to subrtract from first", 1, 8) - 1);
		binaryToSubtract = Calc.twosComplement(binaryToSubtract);
		boolean[] binaryAnswer;
		try {
			binaryAnswer = Calc.add(binarySource, binaryToSubtract, true);
			displayResult(binarySource, binaryToSubtract, binaryAnswer);
		} catch (ArithmeticException e) {
			System.err.println(e.getMessage());
		}
	}

	/**
	 * Gets two binary numbers from user, subtracts the second number from the first, and prints the result
	 */
	public static void subtractFromUser() {
		boolean[] binaryBoolSource = Calc.convertStringToBoolArr(Get.get_8bit_String("Provide a binary number (8-length, 1s and 0s only)"));
		boolean[] binaryBoolSubtractor = Calc.convertStringToBoolArr(Get.get_8bit_String("Provide a binary number (8-length, 1s and 0s only)"));
		binaryBoolSubtractor = Calc.twosComplement(binaryBoolSubtractor);
		boolean[] subtracted = null;
		try {
			subtracted = Calc.add(binaryBoolSource, binaryBoolSubtractor, true);
			displayResult(binaryBoolSource, binaryBoolSubtractor, subtracted);
		} catch (ArithmeticException e) {
			System.err.println(e.getMessage());
		}
	}

	/**
	 * Calculates and displays a denary representation of a two's complement number
	 */
	public static void calculateDenaryFromBinary() {
		String binaryStr;
		try {
			binaryStr = Get.get_8bit_String("Please enter a binary String:");
			boolean[] binaryArr = Calc.convertStringToBoolArr(binaryStr);
			System.out.println("The denary representation of " + binaryStr + " is " + Calc.convertBoolArrToDenary(binaryArr));
		} catch (InputMismatchException e) {
			System.err.println(e.getMessage());
		}
	}

	/**
	 * Calculates and displays a two's complement representation of a denary number
	 */
	public static void calculateBinaryFromDenary() {
		int number = Get.get_int("Provide a number between -128 and 127", -128, 127);
		System.out.println("Denary represenation of " + number + " is " + Calc.convertDenarytoString(number));
	}

	///////////////////////////////////
	//	  		DATA VALIDATION		 //
	///////////////////////////////////

	///////////////////////////////////
	//				PRINTS			 //
	///////////////////////////////////

	/**
	 * Print the menu of the program
	 */
	public static void displayMenu() {
		System.out.println();
		System.out.println("Menu");
		System.out.println("1) Display all values");
		System.out.println("2) Add 2 numbers from table");
		System.out.println("3) Add 2 binary numbers (entered as binary string)");
		System.out.println("4) Subtract one number from another (from table)");
		System.out.println("5) Subtract one number from another (entered as binary string)");
		System.out.println("6) Enter binary to display as denary");
		System.out.println("7) Enter denary to display as binary");
		System.out.println("8) Exit");
	}

	/**
	 * Print the global binaryList ArrayList in nice formatting
	 */
	public static void displayList() {
		System.out.printf("%6s %3s %3d %3d %3d %3d %3d %3d %3d %s%n", "Number # |", "+/-", 64, 32, 16, 8, 4, 2, 1, " | Denary");
		System.out.printf("-------------------------------------------------------%n");
		boolean[] row = null;
		for (int i = 0; i < 8; i++) {
			row = binaryList.get(i);
			System.out.printf("%8d |", i + 1);
			for (int j = 0; j < row.length; j++) {
				if (j == 0) {
					System.out.printf("%3d   ", (row[j]) ? 1 : 0);
				} else {
					System.out.printf("%2d  ", (row[j]) ? 1 : 0);
				}
			}
			System.out.printf("| %d%n", Calc.convertBoolArrToDenary(row));
		}
	}

	public static void displayResult(boolean[] binary1, boolean[] binary2, boolean[] answer) {
		System.out.printf("%6s %3s %3d %3d %3d %3d %3d %3d %3d %s%n", "Number # |", "+/-", 64, 32, 16, 8, 4, 2, 1, " | Denary");
		System.out.printf("-------------------------------------------------------%n");
		boolean[] row = null;
		for (int i = 0; i < 3; i++) {
			if (i == 0) {
				row = binary1;
				System.out.printf("%8s |", "1");
			} else if (i == 1) {
				row = binary2;
				System.out.printf("%8s |", "2");
			} else {
				row = answer;
				System.out.printf("%8s |", "Result");
			}
			for (int j = 0; j < 8; j++) {
				if (j == 0) {
					System.out.printf("%3d   ", (row[j]) ? 1 : 0);
				} else {
					System.out.printf("%2d  ", (row[j]) ? 1 : 0);
				}
			}
			System.out.printf("| %d%n", Calc.convertBoolArrToDenary(row));
		}

	}
}
