package com.revature.views;

import java.math.BigDecimal;
import java.sql.SQLException;

import com.revature.beans.Account;
import com.revature.beans.Customer;
import com.revature.daos.CustomerDao;
import com.revature.util.ScannerUtil;

public class DepositView implements View {

	private Customer customer;
	private Account account;	
	
	public DepositView() {
		super();
		// TODO Auto-generated constructor stub
	}

	public DepositView(Customer customer, Account account) {
		super();
		this.customer = customer;
		this.account = account;
	}

	@Override
	public String toString() {
		return "DepositView [customer=" + customer + ", account=" + account + "]";
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
		DepositView other = (DepositView) obj;
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

		BigDecimal money = ScannerUtil.deposit(
				"Please enter your deposit amount (enter in the following format: ####.##): ",
				 new BigDecimal("0"), new BigDecimal("1000000000000").subtract(account.getBalance()));
		
		BigDecimal newBalance = CustomerDao.deposit(money, account);
		customer.getAccounts().remove(account.getAccount_name(), account);
		account.setBalance(newBalance);
		customer.getAccounts().put(account.getAccount_name(), account);
		
		
		return new MainMenu(customer);
	}

}
