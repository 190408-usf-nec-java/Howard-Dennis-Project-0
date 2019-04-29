package com.revature.views;

import java.math.BigDecimal;
import java.sql.SQLException;

import com.revature.beans.Customer;
import com.revature.daos.CustomerDao;
import com.revature.util.ScannerUtil;

public class CreateBankAccount implements View {

	private Customer customer;

	public CreateBankAccount() {
		super();
		// TODO Auto-generated constructor stub
	}

	public CreateBankAccount(Customer customer) {
		super();
		this.customer = customer;
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
		CreateBankAccount other = (CreateBankAccount) obj;
		if (customer == null) {
			if (other.customer != null)
				return false;
		} else if (!customer.equals(other.customer))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "CreateBankAccount [customer=" + customer + "]";
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	@Override
	public View printOptions() throws SQLException {

		String account_name = ScannerUtil.getBankAccountName(
				"Please enter an account name(only letters, numbers, dashes, single spaces and underscores): ",
				customer.getAccounts().keySet());

		BigDecimal money = ScannerUtil.deposit(
				"The minimum to open a bank account is $10. Please enter your deposit amount (enter in the following format: ##.##): ",
				new BigDecimal("9.99"), new BigDecimal("1000000000000"));

		customer.getAccounts().put(account_name, CustomerDao.createAccount(account_name, money, customer.getUsername()));

		return new MainMenu(customer);
	}

}
