package com.revature.views;

import java.util.ArrayList;
import java.util.Arrays;

import com.revature.util.ScannerUtil;

public class FirstView implements View {

	public View printOptions() {
		String choice = ScannerUtil.getValidChoice(
				"Enter 'u' to access your user account,'c' to create a user account or 'x' to exit",
				new ArrayList<String>(Arrays.asList("u", "c", "x")));
		switch (choice) {
		case "u":
			return new LoginView();
		case "c":
			return new CreateWebAccountView();
		case "x":
			return null;
		default:
			return null;
		}
	}

}
