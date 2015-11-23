package Server;

import java.awt.Point;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;
import java.util.Set;
import java.util.Vector;

import node.BMBomb;
import node.BMBombing;
import node.BMNode;
import node.BMNodeItem;
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
	public BMAIPlayer(int id, int initialLives){
		super(id, initialLives);
		this.start();
	}
	
	public void run(){
		int [] moveRatings = new int[5];
		Random rand = new Random();
		System.out.println("Start running");
		while(!hasLost()){
			for(int i=0; i<5; i++) {
				moveRatings[i] = 0;
				if(canMove(i)) moveRatings[i] = calculateMoveRatings(i);
				else moveRatings[i] = Integer.MIN_VALUE;
			}
			int maxindex = 0;
			int maxvalue = Integer.MIN_VALUE;
			for(int i=0; i<5; i++){
				if(maxvalue < moveRatings[i]){
					maxvalue = moveRatings[i];
					maxindex = i;
				}
			}
			System.out.println("Player " + ID + " moves " + maxindex);
			//Stay
			if (maxindex == 0){
				/*try{
					//Thread.sleep(10);
					
				} catch(InterruptedException ie){
					ie.printStackTrace();
				}*/
				int randomMove = rand.nextInt(5);
				
				if (canMove(randomMove)) startMove(randomMove);
			}
			//Move
			else {
				for(int i=0; i<coordinatesRatio/speed; i++){
					startMove(maxindex);
					try{
						Thread.sleep(10);
					} catch(InterruptedException ie){
						ie.printStackTrace();
					}
				}
			}
			//Drop bomb
			if(!cooling){
				startMove(5);
			}
			
			for(int i=0; i<5; i++)
				moveRatings[i] = Integer.MIN_VALUE;
			
		}
	}

	private int calculateMoveRatings(int i) {
		int rating = 0;
		int initX = location.x/coordinatesRatio;
		int initY = location.y/coordinatesRatio;
		switch(i){
			case 1: initY--; break;
			case 2: initY++; break;
			case 3: initX--; break;
			case 4: initX++; break;
		}
		BMNode nextNode = simulation.getNode(initX, initY);
		if (nextNode instanceof BMBombing) rating -= 100;
		else rating -= potentialBombing(new Point(initX, initY));
		rating += closestItem(new Point(initX, initY));
		rating += closestPlayer(new Point(initX, initY));
		
		return rating;
	}

	private int closestItem(Point point) {
		int distance = -1;
		Set<Point> visitedPoints = new HashSet<Point>();
		Queue<Point> pointQueue = new LinkedList<Point>();
		Vector<Integer> stepsArray = new Vector<Integer>();
		int steps = 0;
		boolean itemFound = false;
		pointQueue.add(point);
		stepsArray.add(steps);
		int count = 0;
		while(!itemFound && !pointQueue.isEmpty()){
			Point p = pointQueue.poll();
			BMNode node = simulation.getNode(p.x, p.y);
			
			if (node instanceof BMNodeItem){
				itemFound = true;
				distance = stepsArray.get(count);
			}
			else{
				//Up
				if(gridInBounds(new Point(p.x, p.y-1))){
					BMNode upNode = simulation.getNode(p.x, p.y-1);
					if(upNode instanceof BMWall || upNode instanceof BMTile);
					else {
						Point newpoint = new Point(p.x, p.y-1);
						if (!visitedPoints.contains(newpoint)){
							pointQueue.add(new Point(p.x, p.y-1));
							stepsArray.add(stepsArray.get(count)+1);
							visitedPoints.add(newpoint);
						}
						
					}
				}
				//Down
				if(gridInBounds(new Point(p.x, p.y+1))){
					BMNode downNode = simulation.getNode(p.x, p.y+1);
					if(downNode instanceof BMWall || downNode instanceof BMTile);
					else {
						Point newpoint = new Point(p.x, p.y+1);
						if (!visitedPoints.contains(newpoint)){
							pointQueue.add(new Point(p.x, p.y+1));
							stepsArray.add(stepsArray.get(count)+1);
							visitedPoints.add(newpoint);
						}
					}
				}
				//Left
				if(gridInBounds(new Point(p.x-1, p.y))){
					BMNode leftNode = simulation.getNode(p.x-1, p.y);
					if(leftNode instanceof BMWall || leftNode instanceof BMTile);
					else {
						Point newpoint = new Point(p.x-1, p.y);
						if (!visitedPoints.contains(newpoint)){
							pointQueue.add(new Point(p.x-1, p.y));
							stepsArray.add(stepsArray.get(count)+1);
							visitedPoints.add(newpoint);
						}
					}
				}
				//Right
				if(gridInBounds(new Point(p.x+1, p.y))){
					BMNode rightNode = simulation.getNode(p.x++, p.y);
					if(rightNode instanceof BMWall || rightNode instanceof BMTile);
					else {
						
						Point newpoint = new Point(p.x+1, p.y);
						if (!visitedPoints.contains(newpoint)){
							pointQueue.add(new Point(p.x++, p.y));
							stepsArray.add(stepsArray.get(count)+1);
							visitedPoints.add(newpoint);
						}
					}
				}
				
			}
			count++;
		}
		if (itemFound){
			int result = (int)Math.pow((16+16-distance), 2);
			return result;
		}
		else return 0;
	}
	
	private int closestPlayer(Point point){
		Vector<BMPlayer> players = simulation.getAllPlayers();
		Vector<Point> playerPoints = new Vector<Point>();
		for (BMPlayer player : players){
			Point tmp = player.getLocation();
			playerPoints.add(new Point(tmp.x/16, tmp.y/16));
		}
		
		int distance = -1;
		Set<Point> visitedPoints = new HashSet<Point>();
		Queue<Point> pointQueue = new LinkedList<Point>();
		Vector<Integer> stepsArray = new Vector<Integer>();
		int steps = 0;
		boolean playerFound = false;
		pointQueue.add(point);
		stepsArray.add(steps);
		int count = 0;
		while(!playerFound && !pointQueue.isEmpty()){
			Point p = pointQueue.poll();
			BMNode node = simulation.getNode(p.x, p.y);
			
			if (hasPlayer(playerPoints, p)){
				playerFound = true;
				distance = stepsArray.get(count);
			}
			else{
				//Up
				if(gridInBounds(new Point(p.x, p.y-1))){
					BMNode upNode = simulation.getNode(p.x, p.y-1);
					if(upNode instanceof BMWall || upNode instanceof BMTile);
					else {
						Point newpoint = new Point(p.x, p.y-1);
						if (!visitedPoints.contains(newpoint)){
							pointQueue.add(new Point(p.x, p.y-1));
							stepsArray.add(stepsArray.get(count)+1);
							visitedPoints.add(newpoint);
						}
					}
				}
				//Down
				if(gridInBounds(new Point(p.x, p.y+1))){
					BMNode downNode = simulation.getNode(p.x, p.y+1);
					if(downNode instanceof BMWall || downNode instanceof BMTile);
					else {
						Point newpoint = new Point(p.x, p.y+1);
						if (!visitedPoints.contains(newpoint)){
							pointQueue.add(new Point(p.x, p.y+1));
							stepsArray.add(stepsArray.get(count)+1);
							visitedPoints.add(newpoint);
						}
					}
				}
				//Left
				if(gridInBounds(new Point(p.x-1, p.y))){
					BMNode leftNode = simulation.getNode(p.x-1, p.y);
					if(leftNode instanceof BMWall || leftNode instanceof BMTile);
					else {
						Point newpoint = new Point(p.x-1, p.y);
						if (!visitedPoints.contains(newpoint)){
							pointQueue.add(new Point(p.x-1, p.y));
							stepsArray.add(stepsArray.get(count)+1);
							visitedPoints.add(newpoint);
						}
					}
				}
				//Right
				if(gridInBounds(new Point(p.x+1, p.y))){
					BMNode rightNode = simulation.getNode(p.x++, p.y);
					if(rightNode instanceof BMWall || rightNode instanceof BMTile);
					else {
						Point newpoint = new Point(p.x+1, p.y);
						if (!visitedPoints.contains(newpoint)){
							pointQueue.add(new Point(p.x++, p.y));
							stepsArray.add(stepsArray.get(count)+1);
							visitedPoints.add(newpoint);
						}
					}
				}
				
			}
			count++;
		}
		int result = (int)Math.pow((16+16-distance), 2);
		return result;
	}
	
	private boolean hasPlayer(Vector<Point> v, Point p){
		for (Point pt : v){
			if (pt.x == p.x && pt.y == p.y) return true;
		}
		return false;
	}

	private int potentialBombing(Point point) {
		int sum = 0;
		BMNode node = simulation.getNode(point.x, point.y);
		if (node instanceof BMBomb) sum += ((BMBomb)node).getPower()*potentialBombingMultiplier;
		
		int upX = point.x;
		int upY = point.y;
		for (int i=1; i<6; i++){
			upY--;
			if (upY >= 0){
				BMNode upNode = simulation.getNode(upX, upY);
				if (upNode instanceof BMTile ||  upNode instanceof BMWall) break;
				else if (upNode instanceof BMBomb){
					int power = ((BMBomb)upNode).getPower();
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
			if (downY <= 15){
				BMNode downNode = simulation.getNode(downX, downY);
				if (downNode instanceof BMTile || downNode instanceof BMWall) break;
				else if (downNode instanceof BMBomb){
					int power = ((BMBomb)downNode).getPower();
					if (i <= power){
						sum += (power-i)*potentialBombingMultiplier;
					}
				}
			} else break;
		}
		
		int leftX = point.x;
		int leftY = point.y;
		for (int i=1; i<6; i++){
			leftX--;
			if (leftX >= 0){
				BMNode leftNode = simulation.getNode(leftX, leftY);
				if (leftNode instanceof BMTile || leftNode instanceof BMWall) break;
				else if (leftNode instanceof BMBomb){
					int power = ((BMBomb)leftNode).getPower();
					if (i <= power){
						sum += (power-i)*potentialBombingMultiplier;
					}
				}
			} else break;
		}
		
		int rightX = point.x;
		int rightY = point.y;
		for (int i=1; i<6; i++){
			leftX++;
			if (leftX <= 15){
				BMNode rightNode = simulation.getNode(rightX, rightY);
				if (rightNode instanceof BMTile || rightNode instanceof BMWall) break;
				else if (rightNode instanceof BMBomb){
					int power = ((BMBomb)rightNode).getPower();
					if (i <= power){
						sum += (power-i)*potentialBombingMultiplier;
					}
				}
			} else break;
		}
		return sum;
	}
	private boolean gridInBounds(Point p){
		return p.x >= 0 && p.x <= 15 && p.y >= 0 && p.y <= 15;
	}
	
}
