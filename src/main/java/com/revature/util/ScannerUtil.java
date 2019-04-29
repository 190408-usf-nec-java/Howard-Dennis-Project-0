package com.revature.util;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Set;

public class ScannerUtil {
	private static final Scanner sc = new Scanner(System.in);

	public static Scanner getScanner() {
		return sc;
	}

	public static String getValidChoice(String message, ArrayList<String> validChoices) {
		boolean done = false;
		String choice;
		do {
			System.out.println(message + "\n");

			choice = sc.nextLine();
			choice = choice.toLowerCase();
			if (choice == null || choice.isEmpty()) {

			} else if (!validChoices.contains(choice)) {
				System.out.println("I'm afraid that choice is invalid.");
			} else {
				done = true;
			}
		} while (!done);

		return choice;
	}

	public static String getValidBankAccount(String message, Set<String> validChoices) {
		boolean done = false;
		String choice;
		do {
			System.out.println(message + "\n");

			choice = sc.nextLine();
			if (choice == null || choice.isEmpty()) {

			} else if (!validChoices.contains(choice)) {
				System.out.println("I'm afraid that choice is invalid.");
			} else {
				done = true;
			}
		} while (!done);

		return choice;
	}

	public static String getUsername(String message) {
		boolean done = false;
		String line;
		do {
			System.out.println(message + "\n");

			line = sc.nextLine();
			if (line == null || line.isEmpty()) {

			} else if (!line.matches("[a-zA-Z1-9_-]+")) {
				System.out.println(
						"I'm afraid that your entry can only contain letters, numbers, dashes and underscores.");
			} else if (line.length() > 40) {
				System.out.println("I'm afraid that your entry is too long. It must be less than 40 characters.");
			} else {
				done = true;
			}
		} while (!done);

		return line;
	}

	public static String getPassword(String message) {
		boolean done = false;
		String line;
		do {
			System.out.println(message + "\n");

			line = sc.nextLine();
			if (line == null || line.isEmpty()) {

			} else if (line.contains(" ")) {

				System.out.println("I'm afraid that your entry cannot contain spaces.");
			} else if (!line.matches(
					"^(?=.*[A-Z].*[A-Z])(?=.*[!@#$%^&*(),.?\"':;{}|<>-_+=].*[!@#$%^&*(),.?\"':;{}|<>-_+=])(?=.*[0-9].*[0-9])(?=.*[a-z].*[a-z]).{8,40}$")) {
				System.out.println("Invalid password.");
			} else if (line.length() > 40) {

				System.out.println("I'm afraid that your entry is too long. It must be less than 40 characters.");
			} else {
				done = true;
			}
		} while (!done);

		return line;
	}

	public static String getBankAccountName(String message) {
		boolean done = false;
		String line;
		do {
			System.out.println(message + "\n");

			line = sc.nextLine();
			if (line == null || line.isEmpty()) {

			} else if (line.length() > 40) {
				System.out.println("I'm afraid that your entry is too long. It must be less than 40 characters.");
			} else if (line.charAt(0) == ' ') {
				System.out.println("I'm afraid that your entry cannot start with whitespace.");
			} else if (!line.matches("^((?!\\s{2}).)*$")) {
				System.out.println("I'm afraid that your entry cannot have two whitespaces in a row.");
			} else if (line.charAt(line.length() - 1) == ' ') {
				System.out.println("I'm afraid that your entry cannot end with whitespace.");
			} else if (!line.replaceAll(" ", "").matches("['a-zA-Z1-9_-]+")) {
				System.out.println(
						"I'm afraid that your entry can only contain letters, numbers, dashes, single spaces and underscores.");
			} else {
				done = true;
			}
		} while (!done);

		return line;
	}

	public static String getBankAccountName(String message, Set<String> takenNames) {
		boolean done = false;
		String line;
		do {
			System.out.println(message + "\n");

			line = sc.nextLine();
			if (line == null || line.isEmpty()) {

			} else if (takenNames.contains(line)) {
				System.out.println("I'm afraid that name has already in use among your accounts.");
			} else if (line.length() > 40) {
				System.out.println("I'm afraid that your entry is too long. It must be less than 40 characters.");
			} else if (line.charAt(0) == ' ') {
				System.out.println("I'm afraid that your entry cannot start with whitespace.");
			} else if (!line.matches("^((?!\\s{2}).)*$")) {
				System.out.println("I'm afraid that your entry cannot have two whitespaces in a row.");
			} else if (line.charAt(line.length() - 1) == ' ') {
				System.out.println("I'm afraid that your entry cannot end with whitespace.");
			} else if (!line.replaceAll(" ", "").matches("['a-zA-Z1-9_-]+")) {
				System.out.println(
						"I'm afraid that your entry can only contain letters, numbers, dashes, single spaces and underscores.");
			} else {
				done = true;
			}
		} while (!done);

		return line;
	}

	public static BigDecimal deposit(String message, BigDecimal minimum, BigDecimal maximum) {
		boolean done = false;
		String line;
		BigDecimal result = null;
		do {
			System.out.println(message + "\n");

			line = sc.nextLine();
			line = line.replaceAll(",", "");
			if (line.charAt(0) == '$') {
				line = line.substring(1);
			}
			if (line == null || line.isEmpty()) {

			} else if (!line.matches("[0-9]+\\.[0-9]{2}$")) {
				System.out.println("I'm afraid that your entry is invalid.");
			} else if (line.length() > 16) {
				System.out.println("Please enter a number less than 16 digits");
			} else {
				result = new BigDecimal(line);
				if (result.compareTo(minimum) <= 0) {
					System.out.println("I'm afraid that your entry does not meet the minimum, which is $"
							+ minimum.add(new BigDecimal("0.01")) + ".");
				} else if (result.compareTo(maximum) >= 0) {
					System.out.println("I'm afraid that this bank can't contain more than $10 trillion.");
				} else {
					done = true;
				}
			}
		} while (!done);

		return result;
	}

	public static String getName(String message) {
		boolean done = false;
		String line;
		do {
			System.out.println(message + "\n");
			line = sc.nextLine();
			if (line == null || line.isEmpty()) {

			} else if (line.length() > 40) {
				System.out.println("I'm afraid that your entry is too long. It must be less than 40 characters.");
			} else if (!line.matches("^[a-zA-Z]+(([', -][a-zA-Z ])?[a-zA-Z]*)*$")) {
				System.out.println("I'm afraid that your name entry is invalid");
			} else if (line.charAt(0) == ' ') {
				System.out.println("I'm afraid that your entry cannot start with whitespace.");
			} else if (line.charAt(line.length() - 1) == ' ') {
				System.out.println("I'm afraid that your entry cannot end with whitespace.");
			} else if (!line.matches("^((?!\\s{2}).)*$")) {
				System.out.println("I'm afraid that your entry cannot have two whitespaces in a row.");
			} else {
				done = true;
			}
		} while (!done);

		return line;
	}

	public static String getEmail(String message) {
		boolean done = false;
		String line;
		do {
			System.out.println(message + "\n");
			line = sc.nextLine();
			if (line == null || line.isEmpty()) {

			} else if (line.length() > 40) {
				System.out.println("I'm afraid that your entry is too long. It must be less than 40 characters.");
			} else if (!line.matches(
					"^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*" + "@" + "(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$")) {
				System.out.println("I'm afraid that your email entry is invalid");
			} else {
				done = true;
			}
		} while (!done);

		return line;
	}

	public static void enter(String message) {
		System.out.println(message);
		sc.nextLine();

	}

	public static String getLine(String message) {
		System.out.println(message);
		String line = sc.nextLine();
		return line;
	}

	public static BigDecimal getWithdraw(String message, BigDecimal balance) {
		boolean done = false;
		String line;
		BigDecimal result = null;
		do {
			System.out.println(message + "\n");

			line = sc.nextLine();
			line = line.replaceAll(",", "");
			if (line.charAt(0) == '$') {
				line = line.substring(1);
			}
			if (line == null || line.isEmpty()) {

			} else if (!line.matches("[0-9]+\\.[0-9]{2}$")) {
				System.out.println("I'm afraid that your entry is invalid.");
			} else if (line.length() > 16) {
				System.out.println("Please enter a number less than 16 digits");
			} else {
				result = new BigDecimal(line);
				if (result.compareTo(BigDecimal.ZERO) <= 0) {
					System.out.println("I'm afraid that your entry is invalid.");
				} else if (result.compareTo(balance) > 0) {
					System.out.println("I'm afraid that number is more money than is in the account.");
				} else {
					done = true;
				}
			}
		} while (!done);

		return result;
	}

	public static BigDecimal transfer(String message, BigDecimal fromBalance, BigDecimal toBalance) {
		boolean done = false;
		String line;
		BigDecimal result = null;
		do {
			System.out.println(message + "\n");

			line = sc.nextLine();
			line = line.replaceAll(",", "");
			if (line.charAt(0) == '$') {
				line = line.substring(1);
			}
			if (line == null || line.isEmpty()) {

			} else if (!line.matches("[0-9]+\\.[0-9]{2}$")) {
				System.out.println("I'm afraid that your entry is invalid.");
			} else if (line.length() > 16) {
				System.out.println("Please enter a number less than 16 digits");
			} else {
				result = new BigDecimal(line);
				if (result.compareTo(BigDecimal.ZERO) <= 0) {
					System.out.println("I'm afraid that your entry is invalid.");
				} else if (result.compareTo(fromBalance) > 0) {
					System.out.println("I'm afraid that number is more money than is in the account.");
				} else if (result.add(toBalance).compareTo(new BigDecimal("10000000000000")) > 0) {
					System.out.println("I'm afraid that number is more money than that account can hold.");
				} else {
					done = true;
				}
			}
		} while (!done);

		return result;
	}
}
