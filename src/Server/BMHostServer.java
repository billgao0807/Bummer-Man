package Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.TreeMap;
import java.util.Vector;

public class BMHostServer extends Thread {
	private Vector<BMClient> ctVector = new Vector<BMClient>();
	private int port;
	private int numPlayer;
	private BMSimulation simulation;
	public Vector<BMClient> getClients(){
		return ctVector;
	}
	public BMHostServer(int port, int numPlayer) {
		this.port = port;
		this.numPlayer = numPlayer;
	}
	public void setSimulation(BMSimulation simulation){
		this.simulation = simulation;
	}
	public void removeChatThread(BMClient ct) {
		ctVector.remove(ct);
	}
	public void sendMapToClients(BMClient ct, TreeMap<String,Object> output) {
		for (BMClient ct1 : ctVector) {
				ct1.sendMap(output);
		}
	}
	public void run(){
		ServerSocket ss = null;
		try {
			System.out.println("Starting Chat Server");
			ss = new ServerSocket(6789);
			this.start();
			while (true) {
				System.out.println("Waiting for client to connect...");
				Socket s = ss.accept();
				System.out.println("Client " + s.getInetAddress() + ":" + s.getPort() + " connected");
				BMClient ct = new BMClient(s, this);
				ctVector.add(ct);
				ct.start();
			}
		} catch (IOException ioe) {
			System.out.println("IOE: " + ioe.getMessage());
		} finally {
			if (ss != null) {
				try {
					ss.close();
				} catch (IOException ioe) {
					System.out.println("IOE closing ServerSocket: " + ioe.getMessage());
				}
			}
		}
	}
}
