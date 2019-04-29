package com.revature.views;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;

import com.revature.beans.Account;
import com.revature.beans.Customer;
import com.revature.daos.CustomerDao;
import com.revature.util.ScannerUtil;

public class DeleteWebAccountView implements View {

	private Customer customer;

	public DeleteWebAccountView() {
		super();
		// TODO Auto-generated constructor stub
	}

	public DeleteWebAccountView(Customer customer) {
		super();
		this.customer = customer;
	}

	public Customer getCustomer() {
		return customer;
	}

	@Override
	protected Object clone() throws CloneNotSupportedException {
		// TODO Auto-generated method stub
		return super.clone();
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DeleteWebAccountView other = (DeleteWebAccountView) obj;
		if (customer == null) {
			if (other.customer != null)
				return false;
		} else if (!customer.equals(other.customer))
			return false;
		return true;
	}

	@Override
	protected void finalize() throws Throwable {
		// TODO Auto-generated method stub
		super.finalize();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((customer == null) ? 0 : customer.hashCode());
		return result;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return super.toString();
	}

	@Override
	public View printOptions() throws SQLException {

		String choice = ScannerUtil.getValidChoice(
				"Are you sure that you want to delete this web " + "account and all your bank accounts? (y/n)",
				new ArrayList<String>(Arrays.asList("y", "n")));

		if (choice.equals("n")) {
			return new MainMenu(customer);
		}

		System.out.println("All accounts over which you are admin shall be completely deposited to you in cash.");
		BigDecimal sum = new BigDecimal(0);

		for (Account account : customer.getAccounts().values()) {
			if (account.isAdmin()) {
				sum = sum.add(account.getBalance());
			}
		}

		ScannerUtil.enter("$" + sum + " has been deposited to you in cash. Press enter to continue.");
		CustomerDao.deleteUser(customer);

		return new FirstView();
	}

}
