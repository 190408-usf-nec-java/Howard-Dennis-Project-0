package com.revature.views;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;

import com.revature.beans.Customer;
import com.revature.daos.CustomerDao;
import com.revature.util.ScannerUtil;

public class CreateWebAccountView implements View {

	@Override
	public View printOptions() throws SQLException {

		boolean done = false;
		String username = null;
		while (!done) {
			username = ScannerUtil
					.getUsername("Please enter a username (only letters, numbers, dashes and underscores): ");
			done = !CustomerDao.findUsername(username);
			if (!done) {
				String choice = ScannerUtil.getValidChoice("Do you want to try again? (Type y or n)",
						new ArrayList<String>(Arrays.asList("y", "n")));
				if (choice.equals("n")) {
					return new FirstView();
				}
			}
		}

		String password = ScannerUtil.getPassword(
				"Please enter a password(must be at least 8 characters, at least two upper and\n lower case characters, at least two numbers and at least two special characters): ");

		String account_name = ScannerUtil.getBankAccountName(
				"Please enter an account name (letters, numbers, dashes, single spaces and underscores): ");

		BigDecimal money = ScannerUtil.deposit(
				"The minimum to open a bank account is $10. Please enter your deposit amount (enter in the following format: ##.##): ",
				new BigDecimal("9.99"), new BigDecimal("1000000000000"));

		String first_name = ScannerUtil.getName("Enter your first name: ");

		String last_name = ScannerUtil.getName("Enter your last name: ");

		String email = ScannerUtil.getEmail("Enter your email address: ");

		Customer customer = CustomerDao.createUser(first_name, last_name, email, username, password, account_name,
				money);

		return new MainMenu(customer);
	}

}
