package node;

import Utilities.BMNodeType;

public abstract class BMNode extends Thread{
		protected int x;
		protected int y;
		protected int type;
		protected Boolean canPass;
		protected BMNode[][] board;
		
		public BMNode(int x, int y, int type, boolean canPass, BMNode[][] board) {
			this.x = x;
			this.y = y;
			this.type = type;
			this.canPass = canPass;
			this.board = board;
		}
		
		public boolean vanish() {
			board[x][y] = new BMBombing(x, y, board, BMNodeType.road);
			return false;
		}
		
		public int getType() {
			return type;
		}
		
		public boolean canPass() {
			return canPass;
		}
}