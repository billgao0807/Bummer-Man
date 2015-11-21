package Server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

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
	
	private volatile boolean running;
	private boolean loggedIn;
	private boolean signupSuccess;
	
	private Queue<Ranking> ranks;
	
	{
		mLock = new ReentrantLock();
		//successLock = new ReentrantLock();
		mRanksArrived = mLock.newCondition();
		loginResult = mLock.newCondition();
		signupResult = mLock.newCondition();
		
		running = false;
		loggedIn = false;
	}
	
	public BMCentralServerClient(int port) throws UnknownHostException {
		try{
			s = new Socket("localhost", port);
			oos = new ObjectOutputStream(s.getOutputStream());
			ois = new ObjectInputStream(s.getInputStream());
			running = true;
			start();
		} catch(IOException ioe) {
			ioe.printStackTrace();
			running = false;
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
			ioe.printStackTrace();
			running = false;
		}
	}
	
	/*
	 * Login screen methods
	 */
	public boolean login(String username, String password) {
		mLock.lock();
		System.out.println("**LOGGING IN**");
		UserPasswordInfo upi = new UserPasswordInfo(username, password, ServerConstants.LOGIN);
		try {
			sendObject(upi);
			loginResult.await();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException ie) {
			System.out.println("LOGIN INTERRUPTED");
		}
		mLock.unlock();
		
		if (loggedIn) return true;
		else return false;
	}
	public boolean signup(String username, String password) {
		mLock.lock();
		System.out.println("**SIGNING UP**");
		UserPasswordInfo upi = new UserPasswordInfo(username, password, ServerConstants.SIGNUP);
		try {
			sendObject(upi);
			signupResult.await();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException ie) {
			System.out.println("SIGNUP INTERRUPTED");
		}
		mLock.unlock();
		
		if (signupSuccess) return true;
		else return false;
	}
	
	/*
	 * Logs out of the server
	 */
	public void logout() {
		System.out.println("**LOGGING OUT**");
		try {
			sendObject(ServerConstants.LOGOUT);
		} catch (IOException e) {
			//e.printStackTrace();
			System.out.println(ServerConstants.LOGOUTFAILED);
		}
	}
	
	/*
	 * Access Rankings
	 */
	public Queue<Ranking> requestWorldRankings() {
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
		Queue<Ranking> emptyRanks = new LinkedList<Ranking>();
		return emptyRanks;
	}
	
	/*
	 * Helper Methods
	 */
	private synchronized boolean sendObject(Object obj) throws IOException {
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
				Object obj = ois.readObject();
				if (obj instanceof Queue<?>){
					mLock.lock();
					ranks = (Queue<Ranking>) obj;
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
						running = false;
					}
					
					mLock.unlock();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
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
}
