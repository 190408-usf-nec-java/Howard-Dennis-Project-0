package com.revature.daos;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

import com.revature.beans.Account;
import com.revature.beans.Customer;
import com.revature.util.ConnectionUtil;

public class CustomerDao {

	public static Customer login(String username, String password) throws SQLException {
		Customer customer = null;

		String sql = "SELECT first_name FROM customers WHERE username = ? AND password = ?";
		PreparedStatement ps = ConnectionUtil.connection.prepareStatement(sql);

		ps.setString(1, username);
		ps.setString(2, password);

		ResultSet rs = ps.executeQuery();
		if (rs.next()) {

			customer = new Customer(username, null);
			customer.setAccounts(new HashMap<>());
			sql = "SELECT account_id, isAdmin FROM useraccountsjunction WHERE username = ?";
			ps = ConnectionUtil.connection.prepareStatement(sql);
			ps.setString(1, username);
			rs = ps.executeQuery();

			while (rs.next()) {
				int account_id = rs.getInt(1);
				boolean isAdmin = rs.getBoolean(2);
				sql = "SELECT balance, account_name FROM accounts WHERE account_id = ?";
				ps = ConnectionUtil.connection.prepareStatement(sql);
				ps.setInt(1, account_id);
				ResultSet rs2 = ps.executeQuery();

				if (rs2.next()) {
					customer.getAccounts().put(rs2.getString(2),
							new Account(account_id, new BigDecimal(rs2.getString(1).substring(1).replaceAll(",", "")),
									rs2.getString(2), isAdmin));
				}
				rs2.close();
				ps.close();
			}
			rs.close();
		}
		return customer;
	}

	public static Customer createUser(String first_name, String last_name, String email, String username,
			String password, String account_name, BigDecimal money) throws SQLException {

		String sql = "INSERT INTO customers (username, password, first_name, last_name, email) VALUES "
				+ "(?, ?, ?, ?, ?)";
		PreparedStatement ps = ConnectionUtil.connection.prepareStatement(sql);

		ps.setString(1, username);
		ps.setString(2, password);
		ps.setString(3, first_name);
		ps.setString(4, last_name);
		ps.setString(5, email);

		if (ps.executeUpdate() == 0) {
			throw new SQLException("user wasn't inserted");
		}

		ConnectionUtil.connection.commit();

		ps.close();

		Account account = createAccount(account_name, money, username);

		HashMap<String, Account> accounts = new HashMap<>();
		accounts.put(account_name, account);

		return new Customer(username, accounts);

	}

	public static Account createAccount(String account_name, BigDecimal money, String username) throws SQLException {

		String sql = "INSERT INTO accounts (account_name, balance) VALUES " + "(?, ?) RETURNING account_id";
		PreparedStatement ps = ConnectionUtil.connection.prepareStatement(sql);

		ps.setString(1, account_name);
		ps.setBigDecimal(2, money);

		ResultSet rs = ps.executeQuery();

		int account_id;

		if (rs.next()) {
			account_id = rs.getInt(1);
		} else {
			throw new SQLException("Account wasn't inserted");
		}

		Account account = new Account(account_id, money, account_name, true);

		rs.close();
		ps.close();

		sql = "INSERT INTO useraccountsjunction (account_id, username, isAdmin) VALUES " + "(?, ?, true)";
		ps = ConnectionUtil.connection.prepareStatement(sql);

		ps.setInt(1, account_id);
		ps.setString(2, username);

		if (ps.executeUpdate() == 0) {
			throw new SQLException("Account junction wasn't inserted");
		}

		ps.close();

		ConnectionUtil.connection.commit();

		return account;
	}

	public static boolean findUsername(String username) throws SQLException {

		String sql = "SELECT username FROM customers WHERE username = ?";

		PreparedStatement ps = ConnectionUtil.connection.prepareStatement(sql);

		ps.setString(1, username);

		ResultSet rs = ps.executeQuery();

		boolean taken = rs.next();

		rs.close();

		ps.close();

		if (taken) {
			System.out.println("I'm afraid that username is taken.");
		}

		return taken;
	}

	public static void deleteUser(Customer customer) throws SQLException {

		String sql = "DELETE FROM customers WHERE username = ?";

		PreparedStatement ps = ConnectionUtil.connection.prepareStatement(sql);

		ps.setString(1, customer.getUsername());

		if (ps.executeUpdate() == 0) {
			throw new SQLException();
		}

		ConnectionUtil.connection.commit();

		ps.close();

		for (Account account : customer.getAccounts().values()) {
			if (account.isAdmin()) {
				CustomerDao.deleteAccount(account);
			}
		}
	}

	/**
	 * Deletes account from sql, NOT from user
	 * 
	 * @param account
	 * @throws SQLException
	 */
	public static void deleteAccount(Account account) throws SQLException {

		String sql = "delete from accounts where account_id = ?";

		PreparedStatement ps = ConnectionUtil.connection.prepareStatement(sql);

		ps.setInt(1, account.getAccount_id());

		int a = ps.executeUpdate();

		ConnectionUtil.connection.commit();

		ps.close();

		if (a < 1) {
			throw new SQLException("Delete account failed");
		}
	}

	public static void joinAccount(Customer customer, Account account) throws SQLException {

		String sql = "INSERT INTO useraccountsjunction (account_id, username, isAdmin) VALUES " + "(?, ?, false)";

		PreparedStatement ps = ConnectionUtil.connection.prepareStatement(sql);

		ps.setInt(1, account.getAccount_id());
		ps.setString(2, customer.getUsername());

		if (ps.executeUpdate() == 0) {
			throw new SQLException("Account junction wasn't inserted");
		}

		ps.close();

		ConnectionUtil.connection.commit();
	}

	public static BigDecimal withdraw(BigDecimal money, Account account) throws SQLException {

		String sql = "update accounts set balance = ? where account_id = ?";

		PreparedStatement ps = ConnectionUtil.connection.prepareStatement(sql);

		BigDecimal difference = account.getBalance().subtract(money);

		ps.setBigDecimal(1, difference);
		ps.setInt(2, account.getAccount_id());

		int a = ps.executeUpdate();

		ConnectionUtil.connection.commit();

		ps.close();

		if (a < 1) {
			throw new SQLException("Withdraw failed");
		}

		return difference;
	}

	public static BigDecimal deposit(BigDecimal money, Account account) throws SQLException {
		String sql = "update accounts set balance = ? where account_id = ?";

		PreparedStatement ps = ConnectionUtil.connection.prepareStatement(sql);

		BigDecimal sum = account.getBalance().add(money);

		ps.setBigDecimal(1, sum);
		ps.setInt(2, account.getAccount_id());

		int a = ps.executeUpdate();

		ConnectionUtil.connection.commit();

		ps.close();

		if (a < 1) {
			throw new SQLException("Deposit failed");
		}

		return sum;
	}

	public static void tradeAdmin(Customer admin, Customer nonAdmin, int account_id) throws SQLException {

		String sql = "update useraccountsjunction set isAdmin = ? where account_id = ? and username = ?";

		PreparedStatement ps = ConnectionUtil.connection.prepareStatement(sql);

		ps.setBoolean(1, false);
		ps.setInt(2, account_id);
		ps.setString(3, admin.getUsername());

		int a = ps.executeUpdate();
		if (a == 0) {
			throw new SQLException("Admin change failed");
		}

		ps.setBoolean(1, true);
		ps.setInt(2, account_id);
		ps.setString(3, nonAdmin.getUsername());

		a = ps.executeUpdate();
		if (a == 0) {
			throw new SQLException("Admin change failed");
		}

		ConnectionUtil.connection.commit();

		ps.close();
	}

	public static void transfer(int from_id, int to_id, BigDecimal howMuch, BigDecimal fromAmount, BigDecimal toAmount)
			throws SQLException {

		String sql = "update accounts set balance = ? where account_id = ?";

		PreparedStatement ps = ConnectionUtil.connection.prepareStatement(sql);

		ps.setBigDecimal(1, fromAmount.subtract(howMuch));
		ps.setInt(2, from_id);

		int a = ps.executeUpdate();
		if (a == 0) {
			throw new SQLException("Transfer failed");
		}

		ps.setBigDecimal(1, toAmount.add(howMuch));
		ps.setInt(2, to_id);

		a = ps.executeUpdate();
		if (a == 0) {
			throw new SQLException("Transfer failed");
		}

		ConnectionUtil.connection.commit();

		ps.close();
	}

	public static void changeAccountName(Account account) throws SQLException {

		String sql = "update accounts set account_name = ? where account_id = ?";

		PreparedStatement ps = ConnectionUtil.connection.prepareStatement(sql);

		ps.setString(1, account.getAccount_name());
		ps.setInt(2, account.getAccount_id());

		int a = ps.executeUpdate();
		if (a == 0) {
			throw new SQLException("Transfer failed");
		}

		ConnectionUtil.connection.commit();

		ps.close();
	}
}
