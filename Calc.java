package challengeBackToBinary;

public class Calc {

	///////////////////////////////////
	//			ARITHMETIC			 //
	///////////////////////////////////

	/**
	 * Two's complement of a binary number
	 * 
	 * @param binary A boolean[][]
	 * @return A boolean[][] - twos complement of parameter binary number
	 */
	public static boolean[] twosComplement(boolean[] binary) {
		binary = flipBits(binary);
		binary = addOne(binary);
		return binary;
	}

	/**
	 * Takes a boolean[][] and flips the bits
	 * 
	 * @param binary A boolean[][]
	 * @return A boolean[][] with flipped bits
	 */
	public static boolean[] flipBits(boolean[] binary) {
		boolean[] flippedBinary = new boolean[8];
		for (int i = 0; i < binary.length; i++) {
			if (binary[i]) {
				flippedBinary[i] = false;
			} else {
				flippedBinary[i] = true;
			}
		}
		return flippedBinary;
	}

	/**
	 * Returns a long of a positive binary number
	 * 
	 * @param binary A boolean[][]
	 * @return A long
	 */
	public static long calcPosBinary(boolean[] binary) {
		long sum = 0;
		int magnitude = 0;
		for (int i = binary.length - 1; i >= 0; i--) {
			sum += binary[i] ? (long) Math.pow(2, magnitude) : 0;
			magnitude++;
		}
		return sum;
	}

	/**
	 * Add 1 to binary boolean[][]
	 */
	public static boolean[] addOne(boolean[] binarySource) {
		boolean[] binaryToAdd = new boolean[] { false, false, false, false, false, false, false, true }; // 1 in binary
		boolean[] binaryAnswer = add(binarySource, binaryToAdd, false);
		return binaryAnswer;
	}

	/**
	 * Adds two binary numbers together
	 * 
	 * @param binarySource     A boolean[] representing a binary number
	 * @param binaryToAdd      A boolean[] representing a binary number
	 * @param isTwosComplement - a boolean that controls the calculation of overflow
	 * @return A boolean[] containing the result of the addition
	 * @throws ArithmeticException If overflow is detected
	 */
	public static boolean[] add(boolean[] binarySource, boolean[] binaryToAdd, boolean isTwosComplement) throws ArithmeticException {
		boolean[] binaryAnswer = new boolean[8];
		boolean overflowed = false;
		boolean carry = false;
		for (int i = 7; i >= 0; i--) {

			int binaryAddor = 0;
			if (binarySource[i]) {
				binaryAddor++;
			}
			if (binaryToAdd[i]) {
				binaryAddor++;
			}
			if (carry) {
				binaryAddor++;
				carry = false;
			}

			// binary arithmetic block
			switch (binaryAddor) {
			case 0: //0+0
				binaryAnswer[i] = false;
				break;
			case 1: //1+0
				binaryAnswer[i] = true;
				break;
			case 2: //1+1
				binaryAnswer[i] = false;
				carry = true;
				break;
			case 3: //1+1+1
				binaryAnswer[i] = true;
				carry = true;
				break;
			}

			//check for overflows
			if (i == 0) {
				String overflowMessage = "OVERFLOW: ";
				if (!isTwosComplement) {
					if (carry) {
						overflowed = true;
						overflowMessage += "Two unsigned binary numbers were summed and result overflowed";
					}
				} else if (isTwosComplement) {
					if (!binarySource[0] && !binaryToAdd[0] && binaryAnswer[0]) {
						overflowed = true;
						overflowMessage += "The sum of two positive two's complement number returned a negative number";
					}
					if (binarySource[0] && binaryToAdd[0] && !binaryAnswer[0]) {
						overflowed = true;
						overflowMessage += "The sum of two negative two's complement number returned a positive number";
					}

				}
				if (overflowed) {
					long number1 = convertBoolArrToDenary(binarySource);
					long number2 = convertBoolArrToDenary(binaryToAdd);
					ArithmeticException e = new ArithmeticException(overflowMessage +
							"\nNumber 1: " + number1 +
							"\nNumber 2: " + number2 +
							"\nResult would be: " + (number1 + number2) +
							"\n[isTwosComplement = " + isTwosComplement + "]");
					throw e;
				}
			}

		}
		return binaryAnswer;
	}

	///////////////////////////////////
	//			CONVERSIONS			 //
	///////////////////////////////////

	/**
	 * Converts an 8-bit String of 1s and 0s to a boolean[]
	 * 
	 * @param binaryStr A String - 8-length and containing only 1s and 0s
	 * @return A String representing the binary number
	 * @throws ArithmeticException If the String is not 8-long or contains characters other than 1s and 0s
	 */
	public static boolean[] convertStringToBoolArr(String binaryStr) throws ArithmeticException {
		boolean hasError = false;
		String errorMessage = "";
		if (binaryStr.length() != 8) {
			hasError = true;
			errorMessage = "The String provided was not 8 in length";
		}
		boolean[] binaryArr = new boolean[binaryStr.length()];
		for (int i = 0; i < binaryStr.length(); i++) {
			if (binaryStr.charAt(i) == '1') {
				binaryArr[i] = true;
			} else if (binaryStr.charAt(i) != '0') {
				hasError = true;
				errorMessage = "The String contained characters that were not 1 or 0";
			}
		}
		if (hasError) {
			ArithmeticException e = new ArithmeticException(errorMessage);
			throw e;
		}
		return binaryArr;
	}

	/**
	 * Converts a boolean[] representing a binary number into a String of 1s and 0s
	 * 
	 * @param binaryArr A boolean[][]
	 * @return A String representation of a binary number
	 */
	public static String convertBoolArrToString(boolean[] binaryArr) {
		String returnString = "";
		for (int i = 0; i < binaryArr.length; i++) {
			returnString += (binaryArr[i]) ? "1" : "0";
		}
		return returnString;
	}

	/**
	 * Calculates a 8 bit two's complement's denary representation
	 * 
	 * @param binary A string
	 * @return A (long) denary representation of the boolean[][]
	 */
	public static long convertBoolArrToDenary(boolean[] binary) {
		long denary;
		if (!binary[0]) {
			denary = Calc.calcPosBinary(binary);
		} else {
			boolean[] tmp = Calc.twosComplement(binary);
			denary = 0 - Calc.calcPosBinary(tmp);
		}
		return denary;
	}

	/**
	 * Calculates denary numbers two's complement representation
	 * 
	 * @param number
	 * @return A String two's complement representation of the denary number
	 */
	public static String convertDenarytoString(int number) {
		String returnStr = "";
		boolean isNegative = (number < 0) ? true : false;
		number = (int) Math.sqrt(Math.pow(number, 2));
		for (int i = number; i > 0; i /= 2) {
			returnStr = i % 2 + returnStr;
		}
		if (returnStr.length() < 8) {
			returnStr = "0".repeat(8 - returnStr.length()) + returnStr;
		}
		if (isNegative) {
			boolean[] tmp = Calc.convertStringToBoolArr(returnStr);
			tmp = Calc.twosComplement(tmp);
			returnStr = Calc.convertBoolArrToString(tmp);
		}
		return returnStr;
	}

	///////////////////////////////////
	//			PRINTERs  			 //
	///////////////////////////////////

	/**
	 * Prints the String representation of boolean[] binary number
	 * 
	 * @param binary A boolean[] representing a binary number
	 */
	public static void displayBinary8Bit(boolean[] binary) {
		System.out.printf("%6s %3s %3d %3d %3d %3d %3d %3d %3d %s%n", "Number # |", "+/-", 64, 32, 16, 8, 4, 2, 1, " | Denary");
		System.out.printf("-------------------------------------------------------%n");
		System.out.printf("%8s |", "");
		for (int j = 0; j < binary.length; j++) {
			if (j == 0) {
				System.out.printf("%3d   ", (binary[j]) ? 1 : 0);
			} else {
				System.out.printf("%2d  ", (binary[j]) ? 1 : 0);
			}
		}
		System.out.printf("| %d%n", Calc.convertBoolArrToDenary(binary));

	}

	public static void displayResult(boolean[] binary1, boolean[] binary2, boolean[] answer) {
		
	}
}
