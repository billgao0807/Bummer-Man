package centralServer;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Vector;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import Utilities.BMRating;

//For connecting to MySQL Server, which contains all the scores, user accounts, etc. 
public class BMCentralServerClient extends Thread {
	
	private Socket s;
	private ObjectOutputStream oos;
	private ObjectInputStream ois;
	
	private Lock mLock;
	//private Lock successLock;
	private Condition mRanksArrived;
	private Condition loginResult;
	private Condition signupResult;
	private Condition vipResult;
	
	private volatile boolean running;
	private Boolean loggedIn;
	private Boolean vipStatus;
	private Boolean signupSuccess;
	
	private Queue<BMRating> ranks;
	private Vector<GameRecord> gameRecords;
	
	{
		mLock = new ReentrantLock();
		//successLock = new ReentrantLock();
		mRanksArrived = mLock.newCondition();
		loginResult = mLock.newCondition();
		signupResult = mLock.newCondition();
		vipResult = mLock.newCondition();
		
		running = false;
		loggedIn = false;
		vipStatus = false;
	}
	
	public BMCentralServerClient(int port) throws UnknownHostException {
		try{
			s = new Socket("localhost", port);
			oos = new ObjectOutputStream(s.getOutputStream());
			ois = new ObjectInputStream(s.getInputStream());
			running = true;
			start();
		} catch(IOException ioe) {
			//ioe.printStackTrace();
			running = false;
			System.out.println(ServerConstants.CannotCompleteRequest + "Can't a connect to localhost" + ":" + port);
		}
	}
	
	public BMCentralServerClient(String ip, int port) {
		try{
			s = new Socket(ip,port);
			oos = new ObjectOutputStream(s.getOutputStream());
			ois = new ObjectInputStream(s.getInputStream());
			running = true;
			start();
		} catch(IOException ioe) {
			//ioe.printStackTrace();
			running = false;
			System.out.println(ServerConstants.CannotCompleteRequest + "Can't a connect to localhost" + ":" + port);
		}
	}
	
	/*
	 * Login screen methods
	 */
	public Boolean login(String username, String password) {
		mLock.lock();
		System.out.println("**LOGGING IN**");
		UserPasswordInfo upi = new UserPasswordInfo(username, password, ServerConstants.LOGIN);
		try {
			sendObject(upi);
			if (running) loginResult.await();
		} catch (IOException | NullPointerException e) {
			//e.printStackTrace();
			System.out.println(ServerConstants.CannotCompleteRequest + ServerConstants.NotLoggedIn);
		} catch (InterruptedException ie) {
			System.out.println("LOGIN INTERRUPTED");
		}
		mLock.unlock();
		
		if (loggedIn) return true;
		else return false;
	}
	public Boolean signup(String username, String password) {
		mLock.lock();
		System.out.println("**SIGNING UP**");
		UserPasswordInfo upi = new UserPasswordInfo(username, password, ServerConstants.SIGNUP);
		try {
			sendObject(upi);
			signupResult.await();
		} catch (IOException | NullPointerException e) {
			//e.printStackTrace();
			signupSuccess = false;
			System.out.println(ServerConstants.CannotCompleteRequest + ServerConstants.NotLoggedIn);
		} catch (InterruptedException ie) {
			signupSuccess = false;
			System.out.println("SIGNUP INTERRUPTED");
		}
		mLock.unlock();
		
		if (signupSuccess) return true;
		else return false;
	}
	public Boolean getVIPStatus() {
		if (!loggedIn) {
			System.out.println(ServerConstants.CannotCompleteRequest + ServerConstants.NotLoggedIn);
			return null;
		}
		
		mLock.lock();
		try {
			sendObject(ServerConstants.VIPSTATUSREQUEST);
			vipResult.await();
		} catch (IOException ioe) {
			//ioe.printStackTrace();
			System.out.println(ServerConstants.CannotCompleteRequest + ServerConstants.NotConnected);
		} catch (InterruptedException ie) {
			ie.printStackTrace();
			System.out.println(ServerConstants.CannotCompleteRequest + "Method call interrupted");
		}
		
		mLock.unlock();
		return vipStatus;
	}
	
	/*
	 * Logs out of the server
	 */
	public void logout() {
		if (running) {
			System.out.println("**LOGGING OUT**");
			try {
				sendObject(ServerConstants.LOGOUT);
				ranks = null;
				gameRecords = null;
			} catch (IOException e) {
				//e.printStackTrace();
				System.out.println(ServerConstants.LOGOUTFAILED);
			}
		}
	}
	public void disconnect() {
		try {
			sendObject(ServerConstants.DISCONNECT);
		} catch (IOException e) {
			//e.printStackTrace();
			System.out.println(ServerConstants.CannotCompleteRequest + "Disconnect Failed");
		}
	}
	
	/*
	 * Access Rankings
	 */
	public Queue<BMRating> requestWorldRankings() {
		try {
			mLock.lock();
			sendObject(ServerConstants.REQUESTWORLDRANKING);
			mRanksArrived.await();
			mLock.unlock();
			return ranks;
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException ie) {
			ie.printStackTrace(); 
		}
		
		System.out.println(ServerConstants.WorldRankingFetchFailure);
		Queue<BMRating> emptyRanks = new LinkedList<BMRating>();
		return emptyRanks;
	}
	
	/*
	 * Helper Methods
	 */
	private synchronized boolean sendObject(Object obj) throws IOException, NullPointerException {
		oos.writeObject(obj);
		oos.flush();
		return true;
	}
	/*
	 * (non-Javadoc)
	 * @see java.lang.Thread#run()
	 */
	@SuppressWarnings("unchecked")
	public void run() {
		try {
			while(running) {
				try {
					Object obj = ois.readObject();
					if (obj instanceof Queue<?>){
						mLock.lock();
						ranks = (Queue<BMRating>) obj;
						mRanksArrived.signal();
						mLock.unlock();
					}
					
					if (obj instanceof String) {
						mLock.lock();
						String str = (String) obj;
						if (str.equals(ServerConstants.SUCCESSFULLOGIN)){
							loggedIn = true;
							loginResult.signal();
						} else if (str.equals(ServerConstants.LOGINFAILED)) {
							loggedIn = false;
							loginResult.signal();
						} else if (str.equals(ServerConstants.SUCCESSFULSIGNUP)) {
							signupSuccess = true;
							signupResult.signal();
						} else if (str.equals(ServerConstants.SIGNUPFAILED)) {
							signupSuccess = false;
							signupResult.signal();
						} else if (str.equals(ServerConstants.SUCCESSFULLOGOUT)) {
							loggedIn = false;
							vipStatus = null;
						} else if (str.equals(ServerConstants.VIPSTATUSTRUE)) {
							vipStatus = true;
							vipResult.signal();
						} else if (str.equals(ServerConstants.VIPSTATUSFALSE)) {
							vipStatus = false;
							vipResult.signal();
						} else if (str.equals(ServerConstants.DISCONNECT)) {
							running = false;
						}
						
						mLock.unlock();
					}
				} catch (ClassNotFoundException cnfe) {
					System.out.println("Error reading object sent from Server");
					cnfe.printStackTrace();
				}
			}
		} catch (IOException ioe) {
			//ioe.printStackTrace();
			System.out.println("Lost connection to Central Server at " + s.getInetAddress() + ":" + s.getPort());
		} finally {
			if (s != null) {
				try {
					s.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					System.out.println(ServerConstants.LOGOUTFAILED);
				}
			}
		}
	}
	
	
	/*
	 * For debugging
	 */
	/*
	public static void main(String args[]) {
		try {
			BMCentralServerClient csc = new BMCentralServerClient(6789);
			csc.login("TurdFerguson", "hello");
			csc.signup("TurdFerguson", "hello");
			csc.login("TurdFerguson", "hello");
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}*/
}
