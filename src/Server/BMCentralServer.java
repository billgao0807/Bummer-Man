package Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;
//import java.util.concurrent.locks.Lock;
//import java.util.concurrent.locks.ReentrantLock;



//Maintains user database
public class BMCentralServer extends Thread {
	
	private ServerSocket ss;
	private Vector<BMCentralServerClientCommunicator> csccVector;
	
	private MySQLDriver msqlDriver;
	//private Lock sqlLock;
	
	{
		csccVector = new Vector<BMCentralServerClientCommunicator>();
		msqlDriver = new MySQLDriver();
		//sqlLock = new ReentrantLock();
	}
	
	/*
	 * Constructor
	 */
	public BMCentralServer() {
		super();
		PortGUI pg = new PortGUI();
		msqlDriver.connect();
		
		ss = pg.getServerSocket();
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
	
	
	
	public void run() {
		try {
			while(true) {
				Socket s = ss.accept();
				System.out.println(ServerConstants.startClientConnectedString + s.getInetAddress() + ServerConstants.endClientConnectedString);
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
