package Server;

import java.awt.Point;
import java.util.Vector;

import node.BMBomb;
import node.BMBombing;
import node.BMNode;
import node.BMTile;
import node.BMWall;

public class BMAIPlayer extends BMPlayer {
//	The AI players of the game. Besides all the functions of BMPlayer, AIPlayer has its own algorithm to make the best move.
//	Function:
//		- run() : void
//		- aiMove() : void
//		- aiGetBestTry : int
//		- aiTry(int move) : int
	private static final int potentialBombingMultiplier = 10;
	public BMAIPlayer(int initialLives){
		super(-1, initialLives);
	}
	
	public void run(){
		int [] moveRatings = new int[5];
		while(hasLost()){
			try{
				Thread.sleep(63);
			} catch(InterruptedException ie){
				ie.printStackTrace();
			}
			
			for(int i=0; i<5; i++) {
				moveRatings[i] = 0;
				if(canMove(i)) moveRatings[i] = calculateMoveRatings(i);
				else moveRatings[i] = Integer.MIN_VALUE;
			}
			
			
		}
	}

	private int calculateMoveRatings(int i) {
		int rating = 0;
		int initX = location.x/16;
		int initY = location.y/16;
		switch(i){
			case 1: initY--; break;
			case 2: initY++; break;
			case 3: initX--; break;
			case 4: initX++; break;
		}
		BMNode nextNode = simulation.getNode(new Point(initX, initY));
		if (nextNode instanceof BMBombing) rating -= 100;
		else rating -= potentialBombing(new Point(initX, initY));
		rating += closestItem(new Point(initX, initY));
		rating += closestPlayer(new Point(initX, initY));
		
		return 0;
	}

	private int closestItem(Point point) {
		
		//Queue<BMNode> nodeQueue = new LinkedQueue();
		
		return 0;
	}
	
	private int closestPlayer(Point point){
		Vector<BMPlayer> players = simulation.getAllPlayers();
		return 0;
	}

	private int potentialBombing(Point point) {
		int sum = 0;
		BMNode node = simulation.getNode(new Point(point.x, point.y));
		if (node instanceof BMBomb) sum += node.getPower()*potentialBombingMultiplier;
		
		int upX = point.x;
		int upY = point.y;
		for (int i=1; i<6; i++){
			upY--;
			if (upY >= 0){
				BMNode node = simulation.getNode(upX, upY);
				if (node instanceof BMTile || node instanceof BMWall) break;
				else if (node instanceof BMBomb){
					int power = node.getPower();
					if (i <= power){
						sum += (power-i)*potentialBombingMultiplier;
					}
				}
			} else break;
		}
		
		int downX = point.x;
		int downY = point.y;
		for (int i=1; i<6; i++){
			downY++;
			if (upY >= 0){
				BMNode node = simulation.getNode(downX, downY);
				if (node instanceof BMTile || node instanceof BMWall) break;
				else if (node instanceof BMBomb){
					int power = node.getPower();
					if (i <= power){
						sum += (power-i)*potentialBombingMultiplier;
					}
				}
			} else break;
		}
		
		int leftX = point.x;
		int leftY = point.y;
		for (int i=1; i<6; i++){
			leftY--;
			if (upY >= 0){
				BMNode node = simulation.getNode(leftX, leftY);
				if (node instanceof BMTile || node instanceof BMWall) break;
				else if (node instanceof BMBomb){
					int power = node.getPower();
					if (i <= power){
						sum += (power-i)*potentialBombingMultiplier;
					}
				}
			} else break;
		}
		
		int rightX = point.x;
		int rightY = point.y;
		for (int i=1; i<6; i++){
			upY--;
			if (upY >= 0){
				BMNode node = simulation.getNode(rightX, rightY);
				if (node instanceof BMTile || node instanceof BMWall) break;
				else if (node instanceof BMBomb){
					int power = node.getPower();
					if (i <= power){
						sum += (power-i)*potentialBombingMultiplier;
					}
				}
			} else break;
		}
		
		return sum;
	}
}
