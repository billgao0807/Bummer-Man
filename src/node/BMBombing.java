package node;

import Utilities.BMNodeType;

public class BMBombing extends BMNode{

	private int afterBombingType;
	private int timeToSleep;

	public BMBombing(int x, int y, BMNode[][] board, int afterBombingType) {
		super(x, y, BMNodeType.bombing, true, board);
		this.afterBombingType = afterBombingType;
		timeToSleep = 30;
		start();
	}

	@Override
	public boolean vanish() {
		timeToSleep = 30;
		return true;
	}

	@Override
	public void run() {
		while (timeToSleep > 0) {
			try {
				java.lang.Thread.sleep(10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			--timeToSleep;
		}
		end();

	}

	private void end() {
		if (afterBombingType == BMNodeType.road) {
			board[x][y] = new BMRoad(x, y, board);
		} else {
			board[x][y] = new BMNodeItem(x, y, board, afterBombingType);
		}
	}

}
