package node;

public class BMBomb extends BMNode implements Runnable{

	private final int power; 
	
	public BMBomb(int x, int y, BMNodePanel np, int power) {
		super(x, y, BMNodeType.bomb, false, np);
		this.power = power;
	}
	
	public boolean vanish() {
		return true;
	}

	@Override
	public void run() {
		//What does this do?
		
	}

}
