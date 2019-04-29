package com.revature.views;

import java.util.ArrayList;
import java.util.Arrays;

import com.revature.beans.Account;
import com.revature.beans.Customer;
import com.revature.util.ScannerUtil;

public class BankAccountView implements View {

	private Customer customer;
	private Account account;

	public BankAccountView(Customer customer, Account account) {
		super();
		this.customer = customer;
		this.account = account;
	}

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	public BankAccountView() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public String toString() {
		return "BankAccountView [customer=" + customer + ", account=" + account + "]";
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
		BankAccountView other = (BankAccountView) obj;
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

	@Override
	public View printOptions() {

		account.printAccount();
		String choice = ScannerUtil.getValidChoice(
				"Press 'd' to deposit, 'w' to withdraw, 'x' to delete this bank account, 'a' to transfer admin\n priviledges to another customer or 'm' to return to the main menu: ",
				new ArrayList<String>(Arrays.asList("a", "w", "d", "m", "x")));

		if (choice.equals("a")) {
			return new TransferAdminView(customer, account);
		}
		if (choice.equals("w")) {
			return new WithdrawView(customer, account);
		}
		if (choice.equals("d")) {
			return new DepositView(customer, account);
		}
		if (choice.equals("m")) {
			return new MainMenu(customer);
		}
		if (choice.equals("x")) {
			return new DeleteBankAccount(customer, account);
		}

		return null;
	}
}
