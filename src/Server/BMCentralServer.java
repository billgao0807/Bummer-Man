package Server;

import java.net.ServerSocket;
import java.util.Vector;


//Maintains user database
public class BMCentralServer extends Thread {
	
	private ServerSocket ss;
	private Vector<BMCentralServerClientCommunicator> csccVector;
	
	public BMCentralServer() {
		PortGUI pg = new PortGUI();
		ss = pg.getServerSocket();
	}
}
