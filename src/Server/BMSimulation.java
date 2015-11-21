package Server;

import java.awt.Point;
import java.util.Timer;
import java.util.TreeMap;
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
	private BMNode[][] board = new BMNode[16][16];
	private Vector<BMPlayer> players;
	private BMPlayer host;
	private int timeLeft;
	private BMHostServer hs;
	private int totalHP;
	private int numPlayer;
	
	public final static int start_game = 0;
	public final static int start_with_AI = 1;
	
	public BMSimulation(int port, int numPlayer){
		this.numPlayer = numPlayer; 
		players = new Vector<BMPlayer>();
		hs = new BMHostServer(port,numPlayer);
		hs.setSimulation(this);
		loadBoard(BMLibrary.getGameMap());
	}
	private void loadBoard(int[][] board){
		System.out.println("Board " +board[0][0] );
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
			String hp = player.getHP() + "/" + totalHP;
			HPs.add(hp);
		}
		return HPs;
	}
	public Vector<TreeMap<String,Object>> getResult(){
		Vector<TreeMap<String,Object>> results = new Vector<TreeMap<String,Object>>();
		for (BMPlayer player : players){
			TreeMap<String,Object> result = player.getResult();
			result.put("username", hs.getClients().get(player.getid()).getName());
			results.add(result);
		}
		return results;
	}
	public boolean canMove(int x, int y){
		return board[x][y].canPass();
	}
	public BMNode getNode(int x, int y){
		return board[x][y];
	}
	public void dropBomb(int x, int y, BMPlayer player){
		board[x][y] = new BMBomb(x,y,board,player);
	}
	public void startGame(int type){
		Vector<BMClient> clients = hs.getClients();
		for (int i = 0; i < clients.size(); i++){
			BMPlayer player = new BMRealPlayer(i,totalHP);
			clients.get(i).setPlayer(player);
			players.add(player);
		}
		if (type == start_with_AI){
			for (int i = 0; i < numPlayer-clients.size(); i++){
				players.add(new BMAIPlayer(totalHP));
			}
		}
		TreeMap<String,Object> map = new TreeMap<String,Object>();
		map.put("type", "start");
		map.put("time", timeLeft);
		map.put("board", getBoard());
		map.put("players", players);
		hs.sendMapToClients(map);
		startTimer();
	}
	public void endGame() {	
		TreeMap<String,Object> map = new TreeMap<String,Object>();
		map.put("type", "end");
		map.put("result", getResult());
		hs.sendMapToClients(map);
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
	public void joinGame(){
		TreeMap<String,Object> map = new TreeMap<String,Object>();
		map.put("type", "join");
		map.put("time", timeLeft);
		map.put("hp", totalHP);
		map.put("players", hs.getNames());
		hs.sendMapToClients(map);
	}
	public void getGameBoard(){
		TreeMap<String,Object> map = new TreeMap<String,Object>();
		map.put("type", "game");
		map.put("board", getBoard());
		map.put("time", timeLeft);
		map.put("players", players);
		hs.sendMapToClients(map);
	}
	public Vector<BMPlayer> getAllPlayers(){
		return players;
	}
	public void addKill(int id) {
		players.get(id).addKill();
	}
}
