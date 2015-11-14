package Server;

import java.util.Timer;
import java.util.Vector;

public class BMSimulation extends Thread {
	private BMNode[][] nodes;
	private Vector<BMPlayer> players;
	private BMPlayer host;
	private Timer timeLeft;
	private BMHostServer hs;
	public BMSimulation(BMPlayer host, int port){
		BMHostServer hs = new BMHostServer(port);
	}
	public Vector<String> getHPs(){
		Vector<String> HPs = new Vector<String>();
		for (BMPlayer player : players){
			String hp = player.getHP();
		}
	}
//	 In charge of all the information of one round of the game. 
//		Function:
//			+ BMSimulation(BMPlayer host, String ip)
//			+ getHPs() : String[ ]
//			+ getResults() : String[ ]
//			+ canMove(int x, int y) : Boolean
//			+ getNode(Point p) : BMNode
//			+ dropBomb(int x, int y) : void
//			+ setNode(int x, int y, BMNode node) : void
//			+ sendMessage(int id, string msg) : void
//			+ getGameData() : string // get the data of the game in json_encode
//			- getHP(int id) : String
//			- getResult(int id) : String
//			- startGame() : void
//		Variable:
}
