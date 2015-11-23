package centralServer;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.Queue;
import java.util.TreeMap;
import java.util.Vector;



//Maintains user database
public class BMCentralServer extends Thread {
	
	private ServerSocket ss;
	private Vector<BMCentralServerClientCommunicator> csccVector;
	
	private MySQLDriver msqlDriver;
	private static BMCentralServerGUI csGUI;
	//private Lock sqlLock;
	
	{
		csccVector = new Vector<BMCentralServerClientCommunicator>();
		msqlDriver = new MySQLDriver();
		csGUI = new BMCentralServerGUI(msqlDriver);
		//sqlLock = new ReentrantLock();
	}
	
	/*
	 * Constructor
	 */
	public BMCentralServer() {
		super();
		PortGUI pg = new PortGUI();
		ss = pg.getServerSocket();
		csGUI.setVisible(true);
		start();
	}
	
	public void removeServerClientCommunicator(BMCentralServerClientCommunicator cscc) {
		csccVector.remove(cscc);
	}
	
	/*
	 * Methods for signup, login, and vip status
	 */
	public synchronized boolean login(String username, String password) {
		try {
			return msqlDriver.doesMatch(username, password);
		} catch (SQLException e) {
			//e.printStackTrace();
			BMCentralServerGUI.addMessage(ServerConstants.GenericSQLException);
		}
		return false;
	}
	public synchronized boolean signup(String username, String password) {
		try {
			if (!msqlDriver.doesExist(username)) {
				msqlDriver.addUser(username, password);
				return true;
			} else 
				return false;
		} catch (SQLException e) {
			BMCentralServerGUI.addMessage(ServerConstants.GenericSQLException);
		}
		
		return false;
	}
	public synchronized boolean isVIP(String username) {
		try {
			return msqlDriver.isVIP(username);
		} catch (SQLException e) {
			//e.printStackTrace();
			BMCentralServerGUI.addMessage(ServerConstants.GenericSQLException + "Occured while accessing VIP Status");
			
		}
		return false;
	}
	
	/*
	 * Methods for accessing/modifying rankings and records
	 */
	public void updateRatings(Vector<TreeMap<String, Object> > tmVect) {
		try {
			for(TreeMap<String, Object> map : tmVect){
				Object uname = map.get("username");
				if(uname instanceof String){
					String username = (String) uname;
					if (username.equals("BOT")) continue;
					else{
						msqlDriver.updateGameRecords(
								(String) map.get(ServerConstants.usernameString), 
								(Double) map.get(ServerConstants.pointsString),
								(Integer) map.get(ServerConstants.killString),
								(Integer) map.get(ServerConstants.deathString));
					}
				}
			}
		} catch (SQLException e) {
			BMCentralServerGUI.addMessage(ServerConstants.GenericSQLException + "Occured while updating rankings");
		}
	}
	
	public Vector<GameRecord> retrievePersonalRecords(String username) {
		try {
			return msqlDriver.getPersonalRecords(username);
		} catch (SQLException e) {
			//e.printStackTrace();
			BMCentralServerGUI.addMessage(ServerConstants.GenericSQLException 
					+ "Occured while retrieving personal records for: "
					+ username);
			
		}
		
		return new Vector<GameRecord>();
	}
	public Queue<RankContainer> retrieveWorldRankings() {
		try {
			return msqlDriver.getWorldRankings();
		} catch (SQLException e) {
			//e.printStackTrace();
			BMCentralServerGUI.addMessage(ServerConstants.GenericSQLException 
					+ "Occured while retrieving World Rankings");
			
		}
		
		return new LinkedList<RankContainer>();
	}
	
	/*
	 * (non-Javadoc)
	 * @see java.lang.Thread#run()
	 */
	public void run() {
		try {
			while(true) {
				Socket s = ss.accept();
				BMCentralServerGUI.addMessage(ServerConstants.startClientConnectedString + s.getInetAddress() + ServerConstants.endClientConnectedString);
				try {
					// this line can throw an IOException
					// if it does, we won't start the thread
					BMCentralServerClientCommunicator cscc = new BMCentralServerClientCommunicator(s, this);
					cscc.start();
					csccVector.add(cscc);
					} catch (IOException ioe) {
						ioe.printStackTrace();
						}
				} 
			} catch (IOException ioe) {
				ioe.printStackTrace();
			} finally {
					try {
						if (ss != null)
							ss.close();
						msqlDriver.stop();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
	}
	
	
	public static void main(String args[]) {
		new BMCentralServer();
	}
	
}
