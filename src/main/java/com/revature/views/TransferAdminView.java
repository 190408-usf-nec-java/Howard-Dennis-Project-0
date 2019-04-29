package com.revature.views;

import java.sql.SQLException;

import com.revature.beans.Account;
import com.revature.beans.Customer;
import com.revature.daos.CustomerDao;
import com.revature.util.ScannerUtil;

public class TransferAdminView implements View {
	private Customer customer;
	private Account account;

	public TransferAdminView() {
		super();
		// TODO Auto-generated constructor stub
	}

	public TransferAdminView(Customer customer, Account account) {
		super();
		this.customer = customer;
		this.account = account;
	}

	@Override
	public String toString() {
		return "TransferAdminView [customer=" + customer + ", account=" + account + "]";
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
		TransferAdminView other = (TransferAdminView) obj;
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
		
		if (!account.isAdmin()) {
			System.out.println("You aren't the admin of this account.");
			return new BankAccountView(customer, account);
		}
		System.out.println("The non-admin will need to have a joint account and will need to enter thier credentials.\n");
		String username = ScannerUtil.getLine("Please enter non-admin username: ");
		String password = ScannerUtil.getLine("Please enter non-admin password: ");
		
		Customer nonAdmin = CustomerDao.login(username, password);
		if (nonAdmin != null && !nonAdmin.getUsername().equals(customer.getUsername())) {
			if(!nonAdmin.getAccounts().containsKey(account.getAccount_name()))
			{
				ScannerUtil.enter("This customer will have to be linked to the account through the main menu.");
				return new MainMenu(customer);
			}
			CustomerDao.tradeAdmin(customer, nonAdmin, account.getAccount_id());
			account.setAdmin(false);
			customer.getAccounts().put(account.getAccount_name(), account);
			System.out.println("Admin Change Successful!\n");
		}
		else {
			System.out.println("Invalid credentials");
		}
		return new BankAccountView(customer, account);
	}

}
