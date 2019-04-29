package com.revature.views;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;

import com.revature.beans.Customer;
import com.revature.util.ScannerUtil;

public class MainMenu implements View {
	
	public MainMenu() {
		super();
		// TODO Auto-generated constructor stub
	}

	public MainMenu(Customer customer) {
		super();
		this.customer = customer;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	@Override
	protected Object clone() throws CloneNotSupportedException {
		// TODO Auto-generated method stub
		return super.clone();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		MainMenu other = (MainMenu) obj;
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
		return "MainMenu [customer=" + customer + "]";
	}

	private Customer customer;
	
	@Override
	public View printOptions() throws SQLException {
		System.out.println("Here are your accounts and balances:");
		customer.printAccounts();
		System.out.println();
		
		String choice = ScannerUtil.getValidChoice("Press 'a' to access a bank account, 'c' to create a new bank account, "
												 + "'j' to join another customer's account,\n 't' transfer money, 'l' to log "
												 + "off or 'd' to delete this web account: ",
				new ArrayList<String>(Arrays.asList("a", "c", "d", "j", "l", "t")));

		if (choice.equals("a")) {
			return new SelectAccountView(customer);
		}
		if (choice.equals("c")) {
			return new CreateBankAccount(customer);
		}
		if (choice.equals("d")) {
			return new DeleteWebAccountView(customer);
		}
		if (choice.equals("l")) {
			return new FirstView();
		}
		if (choice.equals("j")) {
			return new JoinBankAccountView(customer);
		}		
		if (choice.equals("t")) {
			return new TransferMoneyView(customer);
		}
		
		return null;
	}

}



















