package Server;

import java.awt.Point;
import java.util.Timer;
import java.util.Vector;

import Utilities.BMLibrary;
import Utilities.BMNodeType;
import Utilities.BMResult;
import node.BMBomb;
import node.BMBombing;
import node.BMNode;
import node.BMNodeItem;
import node.BMRoad;
import node.BMTile;
import node.BMWall;

public class BMSimulation extends Thread {
	private BMNode[][] board;
	private Vector<BMPlayer> players;
	private BMPlayer host;
	private int timeLeft;
	private BMHostServer hs;
	private int totalHP;
	private int numPlayer;
	
	public final static int start_game = 0;
	public final static int start_with_AI = 1;
	
	public BMSimulation(BMPlayer host, int port, int numPlayer){
		this.numPlayer = numPlayer; 
		players = new Vector<BMPlayer>();
		BMHostServer hs = new BMHostServer(port,numPlayer);
		hs.setSimulation(this);
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
		if (id == BMNodeType.tile){
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
	public void startGame(int type){
		Vector<BMClient> clients = hs.getClients();
		for (int i = 0; i < clients.size(); i++){
			BMPlayer player = new BMRealPlayer();
			clients.setPlayer(player);
			players.add(player);
		}
		if (type == start_with_AI){
			for (int i = 0; i < numPlayer-clients.size(); i++){
				players.add(new BMAIPlayer());
			}
		}
	}
	public void endGame() {
		
	}
	public Integer[][] getBoard(){
		Integer[][] myBoard = new Integer[16][16];
		for (int i = 0; i < 16; i++){
			for (int j = 0; j < 16; j++){
				myBoard[i][j] = board[i][j].getType();
			}
		}
		return myBoard;
	}
	public Vector<BMPlayer> getPlayers(){
		return players;
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
