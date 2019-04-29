package com.revature.views;

import com.revature.util.ScannerUtil;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import com.revature.beans.Account;
import com.revature.beans.Customer;
import com.revature.daos.CustomerDao;

public class JoinBankAccountView implements View {

	private Customer customer;

	public JoinBankAccountView() {
		super();
		// TODO Auto-generated constructor stub
	}

	public JoinBankAccountView(Customer customer) {
		super();
		this.customer = customer;
	}

	@Override
	public String toString() {
		return "JoinBankAccountView [customer=" + customer + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((customer == null) ? 0 : customer.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		JoinBankAccountView other = (JoinBankAccountView) obj;
		if (customer == null) {
			if (other.customer != null)
				return false;
		} else if (!customer.equals(other.customer))
			return false;
		return true;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public View printOptions() throws SQLException {

		String c = ScannerUtil.getValidChoice("Beware, if the admin of the account deletes it, all the money of\n "
				+ "the account will be deposited to that person. Continue? (y/n)", new ArrayList<String>(Arrays.asList("y", "n"))); 
		if (c.equals("n")) {
			return new MainMenu(customer);
		}
		System.out
				.println("The admin will need to enter thier credentials.\n");
		String username = ScannerUtil.getLine("Please enter admin username: ");
		String password = ScannerUtil.getLine("Please enter admin password: ");

		Customer admin = CustomerDao.login(username, password);
		if (admin != null && !admin.getUsername().equals(customer.getUsername())) {
			System.out.println("Login Successful!");
			String choice;
			boolean isAdmin;
			Account account;
			do {
				System.out.println("Here are your accounts");
				admin.printAccounts();
				choice = ScannerUtil.getValidBankAccount("Which account will you link?", admin.getAccounts().keySet());
				account = admin.getAccounts().get(choice);
				if (customer.getAccounts().values().contains(admin.getAccounts().get(choice))) {
					System.out.println(customer.getUsername() + "already has that account");
				}
				isAdmin = account.isAdmin();
				if (!isAdmin) {
					System.out.println("I'm afraid that you are not the admin of that account.");
				}
			} while (!isAdmin);
			if (customer.getAccounts().containsKey(choice)) {
				System.out.println(
						"You two will have to change the account name, since the joining customer has an account by the same name.");

				Set<String> taken = new HashSet<>();
				taken.addAll(customer.getAccounts().keySet());
				taken.addAll(admin.getAccounts().keySet());

				String name = ScannerUtil.getBankAccountName("Enter a bank account name", taken);
				account.setAccount_name(name);
				CustomerDao.changeAccountName(account);
			}

			CustomerDao.joinAccount(customer, account);
			
			account.setAdmin(false);

			customer.getAccounts().put(account.getAccount_name(), account);

			return new MainMenu(customer);

		} else {

			System.out.println("Invalid username or password");
			return new MainMenu(customer);
		}
	}

}
