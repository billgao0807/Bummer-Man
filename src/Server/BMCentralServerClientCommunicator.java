package Server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import server.Constants;
import server.FactoryServerGUI;
import utilities.Util;

public class BMCentralServerClientCommunicator extends Thread {
	
	private Socket s;
	private BMCentralServer bmcs;
	
	private ObjectInputStream ois;
	private ObjectOutputStream oos;
	
	public BMCentralServerClientCommunicator(Socket s, BMCentralServer bmcs) throws IOException {
		super();
		this.s = s;
		this.bmcs = bmcs;
		ois = new ObjectInputStream(s.getInputStream());
		oos = new ObjectOutputStream(s.getOutputStream());
	}
	
	public void run() {
		try {
			while (running) {
				Object obj = ois.readObject();
			}
		} catch (IOException ioe) {
			bmcs.removeServerClientCommunicator(this);
			System.out.println(s.getInetAddress() + ":" + s.getPort() + " - " + ServerConstants.clientDisconnected);
			// this means that the socket is closed since no more lines are being received
			try {
				socket.close();
			} catch (IOException ioe1) {
				Util.printExceptionToCommand(ioe1);
			}
		}
	}
}
