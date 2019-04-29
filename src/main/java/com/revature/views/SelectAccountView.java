package com.revature.views;

import java.sql.SQLException;

import com.revature.beans.Customer;
import com.revature.util.ScannerUtil;

public class SelectAccountView implements View {
	private Customer customer;

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
		SelectAccountView other = (SelectAccountView) obj;
		if (customer == null) {
			if (other.customer != null)
				return false;
		} else if (!customer.equals(other.customer))
			return false;
		return true;
	}

	public SelectAccountView() {
		super();
		// TODO Auto-generated constructor stub
	}

	public SelectAccountView(Customer customer) {
		super();
		this.customer = customer;
	}

	@Override
	public String toString() {
		return "SelectAccountView [customer=" + customer + "]";
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	@Override
	public View printOptions() throws SQLException {
		if (customer.getAccounts().size() == 1) {
			return new BankAccountView(customer, customer.getAccounts().entrySet().iterator().next().getValue());
		}
		customer.printAccounts();

		String choice = ScannerUtil.getValidBankAccount("Enter the name of the account that you wish to access:",
														 customer.getAccounts().keySet());
		return new BankAccountView(customer, customer.getAccounts().get(choice));
	}
}

















