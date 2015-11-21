package Server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;


public class BMCentralServerClientCommunicator extends Thread {
	
	private Socket s;
	private BMCentralServer bmcs;
	
	private ObjectInputStream ois;
	private ObjectOutputStream oos;
	
	private boolean running;
	private UserPasswordInfo currentUPI;
	
	public BMCentralServerClientCommunicator(Socket s, BMCentralServer bmcs) throws IOException {
		super();
		this.s = s;
		this.bmcs = bmcs;
		ois = new ObjectInputStream(s.getInputStream());
		oos = new ObjectOutputStream(s.getOutputStream());
		running = true;
		
	}
	
	/*
	 * Login and Sign up
	 */
	private void login(UserPasswordInfo upi) {
		try {
			if (bmcs.login(upi.getUsername(), upi.getPassword())) {
				currentUPI = upi;
				sendObject(ServerConstants.SUCCESSFULLOGIN);
			} else {
				currentUPI = null;
				sendObject(ServerConstants.LOGINFAILED);
			}
		} catch (IOException ioe) {
			ioe.printStackTrace();
			System.out.println("Problem connecting with Server/Client:" + s.getInetAddress() + ":" + s.getPort());
		}
	}
	private void signup(UserPasswordInfo upi) {
		try {
			if (bmcs.signup(upi.getUsername(), upi.getPassword())) {
				sendObject(ServerConstants.SUCCESSFULSIGNUP);
			} else {
				sendObject(ServerConstants.SIGNUPFAILED);
			}
		} catch (IOException ioe) {
			ioe.printStackTrace();
			System.out.println("Problem connecting with Server/Client:" + s.getInetAddress() + ":" + s.getPort());
		}
	}
	
	/*
	 * Helpers and Accessors
	 */
	private synchronized boolean sendObject(Object obj) throws IOException {
		oos.writeObject(obj);
		oos.flush();
		
		return true;
	}
	public String getUsername() {
		if (currentUPI != null) {
			return currentUPI.getUsername();
		} else {
			System.out.println("No user currently logged in to this connection");
			return null;
		}
	}
	
	public void run() {
		try {
			while (running) {
				try {
					Object obj = ois.readObject();
					if (obj instanceof String) {
						String str = (String) obj;
						if (str.equals(ServerConstants.LOGOUT)) {
							currentUPI = null;
						}
					}
					else if (obj instanceof UserPasswordInfo) {
						UserPasswordInfo upi = (UserPasswordInfo) obj;
						if (upi.isLogin()) login(upi);
						else if (upi.isSignup()) signup(upi);
					}
				} catch (ClassNotFoundException cnfe) {
					cnfe.printStackTrace();
				}
				
			}
		} catch (IOException ioe) {
			bmcs.removeServerClientCommunicator(this);
			System.out.println(s.getInetAddress() + ":" + s.getPort() + " - " + ServerConstants.clientDisconnected);
			// this means that the socket is closed since no more lines are being received
			try {
				s.close();
			} catch (IOException ioe1) {
				ioe1.printStackTrace();;
			}
		}
	}
}
