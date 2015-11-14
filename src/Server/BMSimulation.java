package Server;

import java.util.Timer;
import java.util.Vector;

public class BMSimulation extends Thread {
	private BMNode[][] nodes;
	private Vector<BMPlayer> players;
	private BMPlayer host;
	private int timeLeft;
	private BMHostServer hs;
	private int totalHP;
	public BMSimulation(BMPlayer host, int port){
		BMHostServer hs = new BMHostServer(port);
	}
	public void startTimer(){
		new Thread(new Runnable(){
			@Override
			public void run() {
				while (timeLeft > 0){
					Thread.sleep(1000);
					timeLeft--;
				}
			}			
		}).start();
	}
	public void setVariables(int time, int HP){
		timeLeft = time;
		totalHP = HP;
	}
	public Vector<String> getHPs(){
		Vector<String> HPs = new Vector<String>();
		for (BMPlayer player : players){
			String hp = player.getHP() + "/" + totalHP;
			HPs.add(hp);
		}
		return HPs;
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
