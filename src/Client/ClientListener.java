package Client;

import java.awt.Color;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Dictionary;
import java.util.Scanner;
import java.util.TreeMap;
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
	

	
	 public void sendMove(int key ) {
		 TreeMap<String, Object> tempMap = new TreeMap<String , Object>();
		 tempMap.put("type", "move");
		 tempMap.put("move", key);		 
	        try {
	        	oos.writeObject(tempMap);
				oos.flush();
	        } catch (Exception e) {
	            System.out.println(e.toString());
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
		 TreeMap<String, Object> tempMap = new TreeMap<String , Object>();
		 tempMap.put("type", "msg");
		 tempMap.put("content", msg);		 
	        try {
	        	oos.writeObject(tempMap);
				oos.flush();
	        } catch (Exception e) {
	            System.out.println(e.toString());
	        }
	    }

	 public void sendJoin(String username) {
		 TreeMap<String, Object> tempMap = new TreeMap<String , Object>();
		 tempMap.put("type", "join");
		 tempMap.put("username", username);		 
	        try {
	        	oos.writeObject(tempMap);
				oos.flush();
	        } catch (Exception e) {
	            System.out.println(e.toString());
	        }
	    }
	 
	 public void sendLogin(String username, String password) {
		 TreeMap<String, Object> tempMap = new TreeMap<String , Object>();
		 tempMap.put("type", "join");
		 tempMap.put("username", username);		
		 tempMap.put("password", password);
	        try {
	        	oos.writeObject(tempMap);
				oos.flush();
	        } catch (Exception e) {
	            System.out.println(e.toString());
	        }
	    }
	 
	 public void sendSignUp(String username, String password) {
		 TreeMap<String, Object> tempMap = new TreeMap<String , Object>();
		 tempMap.put("type", "signup");
		 tempMap.put("username", username);		
		 tempMap.put("password", password);	 
	        try {
	        	oos.writeObject(tempMap);
				oos.flush();
	        } catch (Exception e) {
	            System.out.println(e.toString());
	        }
	    }
	 
	 public void sendmy_rank(String username, String password) {
		 TreeMap<String, Object> tempMap = new TreeMap<String , Object>();
		 tempMap.put("type", "my_rank");
		 tempMap.put("username", username);		
	        try {
	        	oos.writeObject(tempMap);
				oos.flush();
	        } catch (Exception e) {
	            System.out.println(e.toString());
	        }
	    }
	 
//	 public void sendworld_rank(String ){
//		 TreeMap<String, Object> tempMap = new TreeMap<String , Object>();
//		 tempMap.put("type", "world_rank");
//		 tempMap.put("username", username);		
//		 tempMap.put("password", password);	 
//	        try {
//	        	oos.writeObject(tempMap);
//				oos.flush();
//	        } catch (Exception e) {
//	            System.out.println(e.toString());
//	        }
//	 }
	 
	 
	 
	 public void sendgame_result(Vector<Dictionary<String, Object>> game_result) {
		 TreeMap<String, Object> tempMap = new TreeMap<String , Object>();
		 tempMap.put("type", "game_result");		 
		 tempMap.put("result" , game_result);
		 
	        try {
	        	oos.writeObject(tempMap);
				oos.flush();
	        } catch (Exception e) {
	            System.out.println(e.toString());
	        }
	    }
	 
	 
	
	    
	public void run() {
		try {
			
			while(true) {
				// in case the server sends another factory to us
				TreeMap<String,Object>map = (TreeMap<String,Object>)ois.readObject();

				//if()
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