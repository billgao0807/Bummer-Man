package Server;

import java.awt.Point;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Vector;

import node.BMItem;

public abstract class BMPlayer extends Thread {
//	Store all the information of a player. The functions support all possible actions of the player.
//	Variables:
//		- Point : location
//		- int : speed
//		- int : power
//		- int : coolingTime
//		- int : detonatedTime
//		- Vector<BMItem> : items
//		- int : deaths
//		- int : initialLives
//		- int : HP
//		- int : kills
//		- int : ID
//		- bool : lost
//		- BMSimulation : simulation
	
	//Move 2 or 4 spaces per second 
	private static final int normalSpeed = 2;
	private static final int increasedSpeed = 4;
	//Bomb blasts 3 or 5 spaces at each direction
	private static final int normalPower = 3;
	private static final int increasedPower = 5;
	//Wait 10 or 5 seconds before player dropping another bomb 
	private static final int normalCoolingTime = 10;
	private static final int reducedCoolingTime = 5;
	//Wait 5 or 3 seconds between a bomb is dropped and it detonates
	private static final int normalDetonatedTime = 5;
	private static final int reducedDetonatedTime = 3;
	//Inclusive Coordinates limit:0, 255
	private static final int coordinateUpperLimit = 255;
	private static final int coordinateLowerLimit = 0;
	
	protected final Point initialLocation;
	protected Point location;
	protected int speed;
	protected int power;
	protected int coolingTime;
	protected int detonatedTime;
	protected Queue<BMItem> itemQueue;
	protected int deaths;
	protected int initialHP;
	protected int HP;
	protected int kills;
	protected int ID;
	protected boolean lost;
	protected BMSimulation simulation;
	
	
//	Functions:
//		+ BMPlayer(int ID, int initialLives, boolean isVIP)
//		+ setSimulation(BMSimulation simulation)
//		protected killed() : void     //should check at every moves
//		protected getLocation() : Point
//		protected getItemsPossessed() : Vector<BMItem>
//		protected getNumOfItemsAcquired() : int
//		protected getLivesLeft() : int
//		protected getInitialLives() : int
//		protected getNumOfPlayerKilled() : int
//		protected startMove(int move) : void
//		protected getCurrentNode(Point p) : Node
//		protected getSpeed() : int
//		protected getPower() : int
//		protected getCoolingTime() : int
//		protected getDetonatedTime() : int
//		- canMove() : Boolean
	public BMPlayer(int ID, int initialLives){
		this.ID = ID;
		this.initialHP = initialLives;
		location = new Point(-1, -1);
		speed = normalSpeed;
		power = normalPower;
		coolingTime = normalCoolingTime;
		detonatedTime = normalDetonatedTime;
		itemQueue = new LinkedList<BMItem>();
		HP = initialLives;
		kills = 0;
		lost = false;
	}
	
	public void setSimulation(BMSimulation simulation){
		this.simulation = simulation;
	}
	
	public void killed(){
		HP--;
		if (HP == 0) lost = true;
	}
	
	public Point getLocation(){
		return location;
	}
	
	public Vector<BMItem> getItemsProcessed(){
		Vector<BMItem> returnVector = new Vector<BMItem>();
		Object [] array = itemQueue.toArray();
		if (array.length > 2 || array.length < 0)
			System.out.println("Player has " + array.length + " items. Error in BMPlayer.");
		for(int i=0; i< array.length; i++){
			returnVector.addElement((BMItem) array[i]);
		}
		return returnVector;
	}
	
	public int getNumOfItemsAcquired(){
		return itemQueue.size();
	}
	
	public int getHP(){
		return HP;
	}
	public int getInitialLives(){
		return initialHP;
	}
	public int getNumOfPlayerKilled(){
		return kills;
	}
	public void startMove(int moveType){
		if (!canMove(moveType)) return;
		int initX = location.x;
		int initY = location.y;
		switch(moveType){
			//Stop
			case 0: location.setLocation(initX, initY);
					break;
			//Up
			case 1: location.setLocation(initX, initY-1);
					break;
			//Down
			case 2: location.setLocation(initX, initY+1);
					break;
			//Left
			case 3: location.setLocation(initX-1, initY);
					break;
			//Right
			case 4: location.setLocation(initX+1, initY);
					break;
			//Drop a bomb
			case 5: simulation.dropBomb(initX, initY);
					break;
		}
	}

	private boolean canMove(int moveType) {
		if (moveType < 0 || moveType > 5)
			return false;
		else if (moveType == 5 || moveType == 0) return true;
		
		else{
			switch(moveType){
			//Up
			case 1: return location.y > coordinateLowerLimit;
			//Down
			case 2: return location.y < coordinateUpperLimit;
			//Left
			case 3: return location.x > coordinateLowerLimit;
			//Right
			case 4: return location.x < coordinateUpperLimit;
			}
			System.out.println("Error in BMPlayer canMove");
			return true;
		}
		
	}
	
	public void setInitialLocation(int x){
		if (x == 0){
			x = 8;
			y = 8;
		}
		else if (x == 1){
			x = 248;
			y = 248;
		}
		else if (){
			
		}
	}
}
