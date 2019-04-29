package com.revature.beans;

import java.io.Serializable;
import java.util.HashMap;

public class Customer implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -945674371751880456L;
	private String username;
	private HashMap<String, Account> accounts; // to hashmap
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public HashMap<String, Account> getAccounts() {
		return accounts;
	}
	public void setAccounts(HashMap<String, Account> accounts) {
		this.accounts = accounts;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((accounts == null) ? 0 : accounts.hashCode());
		result = prime * result + ((username == null) ? 0 : username.hashCode());
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
		Customer other = (Customer) obj;
		if (accounts == null) {
			if (other.accounts != null)
				return false;
		} else if (!accounts.equals(other.accounts))
			return false;
		if (username == null) {
			if (other.username != null)
				return false;
		} else if (!username.equals(other.username))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "Customer [username=" + username + ", accounts=" + accounts + "]";
	}
	public Customer(String username, HashMap<String, Account> accounts) {
		super();
		this.username = username;
		this.accounts = accounts;
	}
	public Customer() {
		super();
		// TODO Auto-generated constructor stub
	}
	public void printAccounts() {
		for(Account account : accounts.values())
		{
			account.printAccount();
		}
	}

}
