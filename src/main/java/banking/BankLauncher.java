package banking;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;

import org.apache.log4j.Logger;

import com.revature.util.ConnectionUtil;
import com.revature.util.ScannerUtil;
import com.revature.views.FirstView;
import com.revature.views.View;

public class BankLauncher {

	public static void main(String[] args) {
		try (Connection conn = ConnectionUtil.getConnection(); Scanner sc = ScannerUtil.getScanner()) {
			conn.setAutoCommit(false);
			System.out.println("Hello, and welcome to H D Morgan!");
			View view = new FirstView();
			while (view != null) {
				try {
					view = view.printOptions();
				} catch (SQLException se) {
					se.printStackTrace();
					ConnectionUtil.connection.rollback();
					System.out.println("Uncommitted sql material has been rolled back");
					view = new FirstView();
				}
			}

		} catch (Exception se) {
			se.printStackTrace();
			try {
				if (!ConnectionUtil.connection.isClosed()) {
					ConnectionUtil.connection.rollback();
				}
				System.out.println("Uncommitted sql material has been rolled back");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println("Goodbye!");
		}
	}
}
