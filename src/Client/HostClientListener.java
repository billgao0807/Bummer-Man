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

import javax.swing.table.TableModel;


public class HostClientListener  extends Thread{
	private Socket mSocket;
	private ObjectOutputStream oos;

	private ObjectInputStream ois;
	private PrintWriter pw;
	public String username;
	private BMClientPanel clientpanel;

	
	public HostClientListener(BMClientPanel clientpanel, String ip, int host) {		
		try
		{
			mSocket = new Socket(ip,host);
			
		}
		catch(IOException ioe)
		{
			System.out.println("IOE in SorryClient constructor: " + ioe.getMessage());
		}
	
		this.clientpanel = clientpanel;
		boolean socketReady = initializeVariables();
		if (socketReady) {
			start();
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
	 
	 public void run() {
			try {
				
				while(true) {
					// in case the server sends another factory to us
					TreeMap<String,Object>map = (TreeMap<String,Object>)ois.readObject();

					if (((String)map.get("type")).equals("join")){
						int time = (int )map.get("time");
						int hp = (int) map.get("hp");
						boolean status 	= (boolean) map.get("status");
						String errMsg = (String) map.get("errMsg");
						Vector<TreeMap<String,Object>> player = (Vector<TreeMap<String,Object>>) map.get("player");				
						
						if (status) clientpanel.set_join(player, hp, time);
						else System.out.println("join error" + errMsg);
						
						
					}
					else if (((String)map.get("type")).equals("error")){
						String errMsg= (String) map.get("errMsg");

					}
					else if (((String)map.get("type")).equals("start")){
						//receive { type = start, board = 2D array of integers represent the board, time = xxx, players =  (Vector<Dictionary>) [ { username = xxx, posX =  x, posY = y, hp = xxx, speed = xxx, power = xxx, item1 = xxx, item2 = xxx } ] }
						int [][] board = (int[][]) map.get("board");
						int time = (int) map.get("time");
						
						Vector<TreeMap<String,Object>> players =  (Vector<TreeMap<String,Object>>) map.get("players");
						clientpanel.set_start(board, time, players);
					}
					else if (((String)map.get("type")).equals("move")){
						
						int [][] board = (int[][]) map.get("board");
						int time = (int) map.get("time");
						Vector<TreeMap<String,Object>> players =  (Vector<TreeMap<String,Object>>) map.get("players");
						clientpanel.boardPanel.set_move(board, time, players);
					
					}
					else if (((String)map.get("type")).equals("msg")){
						String username = (String)map.get("username");
						String content = (String)map.get("content");
						BMBoardPanel.set_chat_text (username, content);
					}
					else if (((String)map.get("type")).equals("end")){
						
						Vector<Dictionary> result = (Vector<Dictionary>)map.get("result");
						BMBoardPanel.set_result(result);
					}
					
					
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
	