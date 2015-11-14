package Server;

import java.awt.Point;
import java.util.Timer;
import java.util.Vector;

import Utilities.BMLibrary;
import Utilities.BMNodeType;
import Utilities.BMResult;
import node.BMNode;

public class BMSimulation extends Thread {
	private BMNode[][] board;
	private Vector<BMPlayer> players;
	private BMPlayer host;
	private int timeLeft;
	private BMHostServer hs;
	private int totalHP;
	public BMSimulation(BMPlayer host, int port){
		BMHostServer hs = new BMHostServer(port);
		loadBoard(BMLibrary.loadBoard());
	}
	private void loadBoard(int[][] board){
		for (int i = 0; i < 16; i++){
			for (int j = 0; j < 16; j++){
				this.board[i][j] = initNode(i,j,board[i][j]);
			}
		}
	}
	private BMNode initNode(int i, int j, int id){
		if (id > 4){
			return new BMNodeItem(i,j,board,id);
		}
		else if (id == BMNodeType.bombing){
			return new BMBombing(i,j,board);
		}
		else if (id == BMNodeType.bomb){
			return new BMBomb(i,j,board);
		}
		else if (id == BMNodeType.tile){
			return new BMTile(i,j,board);
		}
		else if (id == BMNodeType.wall){
			return new BMWall(i,j,board);
		}
		else return new BMRoad(i,j,board);
	}
	
	
	public void startTimer(){
		new Thread(new Runnable(){
			@Override
			public void run() {
				while (timeLeft >= 0){
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						System.out.println("Start Timer Exception: " + e.getMessage());
					}
					timeLeft--;
				}
				BMSimulation.this.endGame();
			}			
		}).start();
	}
	protected void endGame() {
		
	}
	public void setVariables(int time, int HP){
		timeLeft = time;
		totalHP = HP;
	}
	public Vector<String> getHPs(){
		Vector<String> HPs = new Vector<String>();
		for (BMPlayer player : players){
			String hp = player.getHP();
			HPs.add(hp);
		}
		return HPs;
	}
	public Vector<BMResult> getResult(){
		Vector<BMResult> results = new Vector<BMResult>();
		for (BMPlayer player : players){
			BMResult result = player.getResult();
			results.add(result);
		}
		return results;
	}
	public boolean canMove(int x, int y){
		return board[x][y].canMove();
	}
	public BMNode getNode(int x, int y){
		return board[x][y];
	}
	public void dropBomb(int x, int y, BMPlayer player){
		board[x][y] = new BMBomb(x,y,board,player.power,player.detonatedTime);
	}
	public void setNode()
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
