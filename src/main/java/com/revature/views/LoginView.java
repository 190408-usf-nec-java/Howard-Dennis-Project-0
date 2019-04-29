package com.revature.views;

import com.revature.util.ScannerUtil;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;

import com.revature.beans.Customer;
import com.revature.daos.CustomerDao;

public class LoginView implements View {

	public View printOptions() throws SQLException {
		String username = ScannerUtil.getLine("Please enter your username: ");
		String password = ScannerUtil.getLine("Please enter your password: ");

		Customer customer = CustomerDao.login(username, password);
		if (customer != null) {
			System.out.println("Login Successful!");
			return new MainMenu(customer);
		}
		System.out.println("Invalid username or password");

		String choice = ScannerUtil.getValidChoice("Do you want to try again? (Type y or n)",
				new ArrayList<String>(Arrays.asList("y", "n")));

		if (choice.equals("n")) {
			return new FirstView();
		}
		return new LoginView();
	}

}
