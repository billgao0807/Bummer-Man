package centralServer;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.TreeMap;
import java.util.Vector;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
//import java.util.concurrent.locks.Lock;
//import java.util.concurrent.locks.ReentrantLock;



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
	 * Methods for access the mySQLDB
	 */
	public synchronized boolean login(String username, String password) {
		return msqlDriver.doesMatch(username, password);
	}
	public synchronized boolean signup(String username, String password) {
		if (!msqlDriver.doesExist(username)) {
			msqlDriver.addUser(username, password);
			return true;
		} else 
			return false;
	}
	public synchronized boolean isVIP(String username) {
		return msqlDriver.isVIP(username);
	}
	public void updateRatings(Vector<TreeMap<String, Object> > tmVect) {
		for(TreeMap<String, Object> map : tmVect){
			Object uname = map.get("username");
			if(uname instanceof String){
				String username = (String) uname;
				if (username.equals("BOT")) continue;
				else{
					msqlDriver.update(
							(String) map.get(ServerConstants.usernameString), 
							(Double) map.get(ServerConstants.pointsString),
							(Integer) map.get(ServerConstants.killsString),
							(Integer) map.get(ServerConstants.deathsString));
				}
			}
		}
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
