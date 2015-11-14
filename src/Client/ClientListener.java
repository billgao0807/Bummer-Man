package Client;

import java.awt.Color;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;
import java.util.Vector;


public class ClientListener  extends Thread{
	private Socket mSocket;
	private ObjectOutputStream oos;

	private ObjectInputStream ois;
	private PrintWriter pw;
	//private GameManager mGManager;
	//private Card card;

	//private FactoryClientGUI mFClientGUI;
	
	public ClientListener(GameManager inGManager, Socket inSocket) {
		mSocket = inSocket;
		mGManager = inGManager;
		//mFClientGUI = inFClientGUI;
		boolean socketReady = initializeVariables();
		if (socketReady) {
			start();
		}
	}
	
	public void sendMove(int key){
		
		try {
			oos.writeObject(key);
			oos.flush();
		} catch (IOException ioe) {	
			System.out.print(ioe);
		}

	}
	

	
	private boolean initializeVariables() {
		try {
			ois = new ObjectInputStream(mSocket.getInputStream());
			pw = new PrintWriter(mSocket.getOutputStream());
			oos = new ObjectOutputStream(mSocket.getOutputStream());

		} catch (IOException ioe) {
			System.out.println(ioe);
			return false;
		}
		return true;
	}
	
	 public void sendMsg(String msg) {
	        try {
	        	oos.writeObject(msg);
				oos.flush();
	        } catch (Exception e) {
	            System.out.println(e.toString());
	        }
 
	    }

	    
	    
	public void run() {
		try {
			String msg;
			Vector<Color>players;
			while(true) {
				// in case the server sends another factory to us
				Object obj =ois.readObject();
				
<<<<<<< HEAD
				
=======
				/////
>>>>>>> Charlene
			}
		} catch (IOException ioe) {
			//mFClientGUI.addMessage(Constants.serverCommunicationFailed);
			System.out.println("serverCommunicationFailed");
		} catch (ClassNotFoundException cnfe) {
			System.out.println(cnfe);
		}finally{
			try{
				if(pw != null) {
					pw.close();
				}
				if (oos != null) {
					oos.close();
				}
				if (ois !=null) {
					ois.close();
				}
			} catch(IOException ioe) {
				System.out.println("lose connection");
			}
		}
	}
	
}