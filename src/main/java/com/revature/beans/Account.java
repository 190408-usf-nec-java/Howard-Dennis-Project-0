package com.revature.beans;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.NumberFormat;

public class Account implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 2124071717490501844L;
	private int account_id;
	private BigDecimal balance;
	private String account_name;
	private boolean isAdmin;
	
	public String balanceToString()
	{
		return NumberFormat.getCurrencyInstance().format(balance);
	}
	public void printAccount()
	{
		System.out.printf("%-40s %s\n", account_name, balance);
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
		Account other = (Account) obj;
		if (account_id != other.account_id)
			return false;
		if (account_name == null) {
			if (other.account_name != null)
				return false;
		} else if (!account_name.equals(other.account_name))
			return false;
		if (balance == null) {
			if (other.balance != null)
				return false;
		} else if (!balance.equals(other.balance))
			return false;
		if (isAdmin != other.isAdmin)
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
		result = prime * result + account_id;
		result = prime * result + ((account_name == null) ? 0 : account_name.hashCode());
		result = prime * result + ((balance == null) ? 0 : balance.hashCode());
		result = prime * result + (isAdmin ? 1231 : 1237);
		return result;
	}
	@Override
	public String toString() {
		return "Account [account_id=" + account_id + ", balance=" + balance + ", account_name=" + account_name
				+ ", isAdmin=" + isAdmin + "]";
	}
	public int getAccount_id() {
		return account_id;
	}
	public void setAccount_id(int account_id) {
		this.account_id = account_id;
	}
	public BigDecimal getBalance() {
		return balance;
	}
	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}
	public String getAccount_name() {
		return account_name;
	}
	public void setAccount_name(String account_name) {
		this.account_name = account_name;
	}
	public boolean isAdmin() {
		return isAdmin;
	}
	public void setAdmin(boolean isAdmin) {
		this.isAdmin = isAdmin;
	}
	public Account(int account_id, BigDecimal balance, String account_name, boolean isAdmin) {
		super();
		this.account_id = account_id;
		this.balance = balance;
		this.account_name = account_name;
		this.isAdmin = isAdmin;
	}
	public Account() {
		super();
		// TODO Auto-generated constructor stub
	}
		
}
