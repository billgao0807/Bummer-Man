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
	
	public BMClient(Socket s, BMHostServer hs) {
		this.hs = hs;
		this.s = s;
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
}
