package node;

public class BMWall extends BMNode {

	public BMWall(int x, int y, BMNodePanel np) {
		super(x, y, BMNodeType.wall, false, np);
	}
	
	

	@Override
	public boolean vanish() {
		return false;
	}

}
