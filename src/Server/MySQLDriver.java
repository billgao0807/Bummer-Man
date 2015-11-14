package Server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.mysql.jdbc.Driver;

import server.FactoryServerGUI;

public class MySQLDriver {
	
	private Connection con;
	private final static String selectUser = "SELECT * FROM USERS WHERE NAME=?";
	private final static String addUser = "INSERT INTO USERS(USERNAME,PASSWORD) VALUES(?,?)";
	
	private final static String connectionString = "jdbc:mysql://localhost:3306/factory?user=root&password=";
	
	public MySQLDriver() {
		try {
			new Driver();
		} catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	/*
	 * To connecting and disconnecting to SQL DB
	 * - change hard-coded connectionString to change for now. We can adjust implementation later
	 */
	public void connect() {
		try {
			con = DriverManager.getConnection(connectionString);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public void stop() {
		try {
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/*
	 * Checks if username and password match
	 */
	public boolean doesExist(String productName) {
		try {
			PreparedStatement ps = con.prepareStatement(selectUser);
			ps.setString(1, productName);
			ResultSet result = ps.executeQuery();
			while (result.next()) {
				System.out.println(result.getString(1)+ " exists with count: " + result.getInt(2));
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		System.out.println("Unable to find product with name: " + productName);
		return false;
	}
	
	
}
