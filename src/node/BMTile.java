package node;

import java.util.Random;

public class BMTile extends BMNode {

	Random rn;

	public BMTile(int x, int y, BMNodePanel np){
		super(x, y, BMNodeType.tile, false, np);
		rn = new Random();
	}
	
	@Override
	public boolean vanish() {
		int temp = rn.nextInt();
		
		// 30% get item, 70% get nothing(just become road)
		if (temp % 10 <3) {
			// Generate random item type
			int tempItemID = rn.nextInt();
			tempItemID = tempItemID % 4;
			tempItemID += 5;
			// Let this tile be the randomly generated item type after bombing
			nodePanel.node = new BMBombing(x, y, nodePanel, tempItemID);
		} else {
			nodePanel.node = new BMBombing(x, y, nodePanel, BMNodeType.road);
		}
		return false;
	}

}
