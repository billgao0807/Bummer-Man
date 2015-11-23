package centralServer;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import com.mysql.jdbc.Driver;

public class MySQLDriver {
	
	private Connection con;
	private final static String selectUser = "SELECT * FROM USERS WHERE USERNAME=?";
	private final static String deleteUser = "DELETE FROM USERS WHERE USERNAME=? AND PASS=?";
	private final static String addUser = "INSERT INTO USERS(USERNAME,PASS,VIP,MAXPOINTS) VALUES(?,?,?,?)";
	private final static String setMaxPoints = "UPDATE USERS WHERE USERNAME=? SET MAXPOINTS=?";
	private final static String sortByRank = "ALTER TABLE USERS ORDER BY RATING DESC";
	
	private final static String selectGameRecord = "SELECT * FROM GAMERECORDS WHERE USERNAME=?";
	private final static String addGameRecord = "INSERT INTO GAMERECORDS(USERNAME,POINTS,KILLS,DEATHS,TIME) VALUES(?,?,?,?,?)";
	//private final static String sortByTime = "ALTER TABLE USERS ORDER BY TIME DESC";

	private final static String connectionString = "jdbc:mysql://localhost:3306/bomberman?user=root&password=root";
	
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
			BMCentralServerGUI.addMessage(ServerConstants.connectionToMySQL + connectionString);
		} catch (SQLException e) {
			//e.printStackTrace();
			BMCentralServerGUI.addMessage(ServerConstants.failedConnectionToMySQL + connectionString);
		}
	}
	public void stop() {
		try {
			con.close();
			BMCentralServerGUI.addMessage(ServerConstants.disconnectedFromMySQL + connectionString);
		} catch (SQLException e) {
			//e.printStackTrace();
			BMCentralServerGUI.addMessage(ServerConstants.failedDisconnectFromMySQL + connectionString);
		}
	}
	
	/*
	 * Adds to database
	 */
	public void addUser(String userName, String password) {
		try {
			PreparedStatement ps = con.prepareStatement(addUser);
			ps.setString(1, userName);
			ps.setString(2, password);
			ps.setString(3, "false");
			ps.setDouble(4, 0);
			ps.executeUpdate();
			System.out.println("Adding User:" + userName);
			
			ps = con.prepareStatement(sortByRank);
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public void deleteUser(String userName, String password) {
		try {
			PreparedStatement ps = con.prepareStatement(deleteUser);
			ps.setString(1, userName);
			ps.setString(2, password);
			ps.executeUpdate();
			System.out.println("Deleting User:" + userName);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/*
	 * Checks if username and password match/exist
	 */
	public boolean doesMatch(String userName, String password) {
		try {
			ResultSet result = getUsernameResults(userName);
			while (result.next()) {
				System.out.println(result.getString(2)+ " exists");
				if (result.getString(3).equals(password))
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		System.out.println("Invalid username and password combination for: " + userName);
		return false;
	}
	public boolean doesExist(String userName) {
		try {
			ResultSet result = getUsernameResults(userName);
			while (result.next()) {
				System.out.println("The username, " + result.getString(2)+ ", already exists");
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return false;
	}
	public boolean isVIP(String userName) {
		try {
			ResultSet result = getUsernameResults(userName);
			while (result.next()) {
				if (result.getString(3).equals("true")) return true;
			}
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		}
		
		return false;
	}
	
	/*
	 * Game Record related methods
	 */
	public void update(String userName, Double points, Integer kills, Integer deaths) {
		try {
			//Update max points
			ResultSet result1 = getUsernameResults(userName);
			while (result1.next()) {
				if (result1.getDouble(4) < points) {
					PreparedStatement ps = con.prepareStatement(setMaxPoints);
					ps.setString(1, userName);
					ps.setDouble(2, points);
					ps.executeUpdate();
				}
			}
			
			//Update Game Records
			PreparedStatement ps = con.prepareStatement(addGameRecord);
			ps.setString(1, userName);
			ps.setDouble(2, points);
			ps.setInt(3, kills);
			ps.setInt(4, deaths);
			ps.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			BMCentralServerGUI.addMessage("Error updating MySQL");
		}
	}
	
	public Vector<GameRecord> getPersonalRecords(String userName) {
		
	}
	
	//Helper Function
	public ResultSet getUsernameResults(String userName) throws SQLException {
		PreparedStatement ps = con.prepareStatement(selectUser);
		ps.setString(1, userName);
		return ps.executeQuery();
	}
	
	/*
	public static void main(String[] args) {
		MySQLDriver msqld = new MySQLDriver();
		msqld.connect();
		
		msqld.addUser("TurdFerguson", "funny");
		System.out.println("Successfuly added user: " + msqld.doesMatch("TurdFerguson", "funny"));
		msqld.deleteUser("TurdFerguson", "funny");
		System.out.println("Successfuly delete user: " + msqld.doesMatch("TurdFerguson", "funny"));
		msqld.stop();
	}*/
	
	
}
