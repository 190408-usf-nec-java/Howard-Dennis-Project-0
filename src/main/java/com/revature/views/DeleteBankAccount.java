package com.revature.views;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;

import com.revature.beans.Account;
import com.revature.beans.Customer;
import com.revature.daos.CustomerDao;
import com.revature.util.ScannerUtil;

public class DeleteBankAccount implements View {

	private Customer customer;
	private Account account;

	public DeleteBankAccount() {
		super();
		// TODO Auto-generated constructor stub
	}

	public DeleteBankAccount(Customer customer, Account account) {
		super();
		this.customer = customer;
		this.account = account;
	}

	@Override
	public String toString() {
		return "DeleteBankAccount [customer=" + customer + ", account=" + account + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((account == null) ? 0 : account.hashCode());
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
		DeleteBankAccount other = (DeleteBankAccount) obj;
		if (account == null) {
			if (other.account != null)
				return false;
		} else if (!account.equals(other.account))
			return false;
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

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	@Override
	public View printOptions() throws SQLException {

		if (customer.getAccounts().size() == 1) {
			ScannerUtil.enter("This being your only bank account, you would need to delete your entire "
					+ "web account in the main menu. (Press enter to continue)");
			return new MainMenu(customer);
		}
		if (!account.isAdmin()) {
			System.out.println("I'm afraid that only the admin of the account can delete it.");
			return new BankAccountView(customer, account);
		}
		int c = 0;
		for(Account account : customer.getAccounts().values())
		{
			if (account.isAdmin()) {
				++c;
				if (c == 2) {
					break;
				}
			}
		}
		if (c == 1) {
			System.out.println("This is your only admin, and can only be deleted with the deletion of your entire web account.\n");
			return new MainMenu(customer);
		}
		String choice = ScannerUtil.getValidChoice("Are you sure that you want to delete this bank account? (y/n)",
				new ArrayList<String>(Arrays.asList("y", "n")));

		if (choice.equals("n")) {
			return new BankAccountView(customer, account);
		}

		CustomerDao.deleteAccount(account);
		ScannerUtil.enter(account.balanceToString() + " has been deposited to you in cash. Press enter to continue.");
		customer.getAccounts().remove(account.getAccount_name(), account);

		return new MainMenu(customer);
	}

}
