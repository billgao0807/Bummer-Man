package node;

public class BMRoad extends BMNode {

	public BMRoad(int x, int y, BMNodePanel np) {
		super(x, y, BMNodeType.road, true, np);
	}

	@Override
	public boolean vanish() {
		return false;
	}
}
