package node;

import Server.BMPlayer;
import Utilities.BMNodeType;

public class BMBomb extends BMNode{

	private final int power; 
	private final int timeToLive;
	private final BMPlayer mPlayer;

	public BMBomb(int x, int y, BMNode[][] board, BMPlayer player) {
		super(x, y, BMNodeType.bomb, false, board);
		this.power = player.getPower();
		this.timeToLive = player.getDetonatedTime();
		mPlayer = player;
		start();
	}

	public boolean vanish() {
		for (int i = 0; i < power; i++) {
			if (x + i < 16) {
				if (board[x+i][y].vanish(mPlayer.getid()))
					break;
			}
		}
		for (int i = 0; i < power; i++) {
			if (x - i < 16) {
				if (board[x-i][y].vanish(mPlayer.getid()))
					break;
			}
		}
		for (int i = 0; i < power; i++) {
			if (y + i < 16) {
				if (board[x][y+i].vanish(mPlayer.getid()))
					break;
			}
		}
		
		for (int i = 0; i < power; i++) {
			if (y - i < 16) {
				if (board[x][y-i].vanish(mPlayer.getid()))
					break;
			}
		}
		return true;
	}

	@Override
	public void run() {
		try {
			java.lang.Thread.sleep(timeToLive);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		vanish();
	}
	
	public int getPower() {
		return power;
	}
	
	public int getID() {
		if (mPlayer == null) return -1;
		return mPlayer.getid();
	}

}
