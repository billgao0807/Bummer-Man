package node;

import Utilities.BMNodeType;

public class BMBomb extends BMNode{

	private final int power; 
	private final int timeToLive;

	public BMBomb(int x, int y, BMNode[][] board, int power, int timeToLive) {
		super(x, y, BMNodeType.bomb, false, board);
		this.power = power;
		this.timeToLive = timeToLive;
		start();
	}

	public boolean vanish() {
		for (int i = 0; i < power; i++) {
			if (x + i < 16) {
				if (!board[x+i][y].vanish())
					break;
			}
		}
		for (int i = 0; i < power; i++) {
			if (x - i < 16) {
				if (!board[x-i][y].vanish())
					break;
			}
		}
		for (int i = 0; i < power; i++) {
			if (y + i < 16) {
				if (!board[x][y+i].vanish())
					break;
			}
		}
		
		for (int i = 0; i < power; i++) {
			if (y - i < 16) {
				if (!board[x][y-i].vanish())
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

	}

}
