package node;

public abstract class BMNode {
		protected int x;
		protected int y;
		protected int type;
		protected Boolean canPass;
		protected BMNodePanel nodePanel;
		
		public BMNode(int x, int y, int type, boolean canPass, BMNodePanel np) {
			this.x = x;
			this.y = y;
			this.type = type;
			this.canPass = canPass;
			this.nodePanel = np;
		}
		
		public boolean vanish() {
			nodePanel.node = new BMBombing(x, y, nodePanel, BMNodeType.road);
			return false;
		};
}