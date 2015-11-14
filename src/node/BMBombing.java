package node;

public class BMBombing extends BMNode implements Runnable{

	private int afterBombingType;
	
	private final int initialTime;
	
	private int timeRemaining;
	
	public BMBombing(int x, int y, BMNodePanel np, int afterBombingType) {
		super(x, y, BMNodeType.bombing, true, np);
		this.afterBombingType = afterBombingType;
		initialTime = (int) System.currentTimeMillis();
		timeRemaining = 300;
		new Thread(new BMBombing(x, y, nodePanel, afterBombingType)).start();
	}

	@Override
	public boolean vanish() {
		timeRemaining += 300;
		return true;
	}

	@Override
	public void run() {
		int currTime = (int) System.currentTimeMillis();
		while(currTime-initialTime < timeRemaining) {
			// do something
		}
		
		end();
		
	}

	private void end() {
		nodePanel.node = new BMBombing(x, y, nodePanel, afterBombingType);
		
	}

}
