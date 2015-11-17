package Server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.TreeMap;

public class BMClient extends Thread {
	private ObjectOutputStream oos;
	private ObjectInputStream ois;
	private BMHostServer hs;
	private Socket s;
	private BMSimulation mSimulation;
	
	public BMClient(Socket s, BMHostServer hs, BMSimulation mSimulation) {
		this.hs = hs;
		this.s = s;
		this.mSimulation = mSimulation;
		this.start();
		try {
			ois = new ObjectInputStream(s.getInputStream());
			oos = new ObjectOutputStream(s.getOutputStream());
		} catch (IOException ioe) {
			System.out.println("IOE in ChatThread constructor: " + ioe.getMessage());
		}
	}

	public void sendMap(TreeMap<String,Object> output) {
		try {
			oos.writeObject(output);
			oos.flush();
		} catch (IOException e) {
			System.out.println("BMClient sendMap IOE: " + e.getMessage());
		}
	}

	public void run() {
		try {
			while (true) {
				try {
					@SuppressWarnings("unchecked")
					TreeMap<String,Object> map = (TreeMap<String,Object>)ois.readObject();
					String type = (String)map.get("type");  
					if (type.startsWith("join")) {
						sendJoinMap(map);
					} else if (type.startsWith("move")) {
						sendMoveMap(map);
					} else if (type.startsWith("msg")) {
						sendMsgMap(map);
					} 
				} catch (ClassNotFoundException e) {
					System.out.println("BMClient Run CNFE: " + e.getMessage());
				}
			}
		} catch (IOException ioe) {
			hs.removeChatThread(this);
			System.out.println(s.getInetAddress() + ":" + s.getPort() + " disconnected.");
		} 
	}

	private void setup(TreeMap<String, Object> map) {
		// TODO Auto-generated method stub
		
	}

	private void sendMsgMap(TreeMap<String, Object> map) {
		TreeMap<String,Object> tempMap = new TreeMap<String, Object>();
		tempMap.put("type", "msg");
		tempMap.put("username", sender);
		tempMap.put("content", (String)map.get("content"));
		hs.sendMapToClients(tempMap);
		
		
	}

	private void sendMoveMap(TreeMap<String, Object> map) {
		TreeMap<String,Object> tempMap = new TreeMap<String, Object>();
		tempMap.put("type", "game");
		tempMap.put("board", mSimulation.getBoard());
		tempMap.put("time", mSimulation.getTime());
		tempMap.put("players", mSimulation.getPlayerDict());
		hs.sendMapToClients(tempMap);
	}

	private void sendJoinMap(TreeMap<String,Object> map) {
		TreeMap<String,Object> tempMap = new TreeMap<String, Object>();
		tempMap.put("type", "join");
		tempMap.put("time", mSimulation.getTime());
		tempMap.put("hp", mSimulation.getHPs());
		tempMap.put("player", mSimulation.getPlayers());
		hs.sendMapToClients(tempMap);
		
	}
	
}
