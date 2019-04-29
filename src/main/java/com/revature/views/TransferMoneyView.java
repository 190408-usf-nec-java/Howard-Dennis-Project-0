package com.revature.views;

import java.math.BigDecimal;
import java.sql.SQLException;
import com.revature.beans.Account;
import com.revature.beans.Customer;
import com.revature.daos.CustomerDao;
import com.revature.util.ScannerUtil;

public class TransferMoneyView implements View {

	private Customer customer;

	public TransferMoneyView() {
		super();
		// TODO Auto-generated constructor stub
	}

	public TransferMoneyView(Customer customer) {
		super();
		this.customer = customer;
	}

	@Override
	public String toString() {
		return "TransferMoneyView [customer=" + customer + "]";
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
		TransferMoneyView other = (TransferMoneyView) obj;
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
	public View printOptions() throws SQLException {

		if (customer.getAccounts().size() < 2) {
			System.out.println("I'm afraid that you don't have enough accounts for transfer.");
			return new MainMenu(customer);
		}
		customer.printAccounts();
		String from = ScannerUtil.getValidBankAccount("Transfer from which account?",
				customer.getAccounts().keySet());
		customer.printAccounts();
		String to = ScannerUtil.getValidBankAccount("Transfer to which account?",
				customer.getAccounts().keySet());

		if (from.equals(to)) {
			System.out.println("Invalid selection.");
			return new MainMenu(customer);
		}

		Account fromAccount = customer.getAccounts().get(from);
		Account toAccount = customer.getAccounts().get(to);
		
		if (fromAccount == null) {
			System.out.println(from + "was not able to return an account.");
		}
		
		if (toAccount == null) {
			System.out.println(to + "was not able to return an account.");
		}

		BigDecimal howMuch = ScannerUtil.transfer("How much? (enter in this format: ####.##)", fromAccount.getBalance(),
				toAccount.getBalance());

		int from_id = fromAccount.getAccount_id();
		int to_id = toAccount.getAccount_id();

		CustomerDao.transfer(from_id, to_id, howMuch, fromAccount.getBalance(), toAccount.getBalance());

		fromAccount.setBalance(fromAccount.getBalance().subtract(howMuch));
		toAccount.setBalance(toAccount.getBalance().add(howMuch));

		customer.getAccounts().remove(fromAccount.getAccount_name());
		customer.getAccounts().remove(toAccount.getAccount_name());
		customer.getAccounts().put(fromAccount.getAccount_name(), fromAccount);
		customer.getAccounts().put(toAccount.getAccount_name(), toAccount);

		return new MainMenu(customer);
	}

}