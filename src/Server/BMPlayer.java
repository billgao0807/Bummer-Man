package Server;

import java.awt.Point;
import java.io.Serializable;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;
import java.util.TreeMap;
import java.util.Vector;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import node.BMBomb;
import node.BMBombing;
import node.BMNode;
import node.BMNodeItem;
import node.BMTile;
import node.BMWall;

import Utilities.BMItem;
import Utilities.BMResult;

public abstract class BMPlayer extends Thread implements Serializable{
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
	private static final long serialVersionUID = 4139291657328559403L;
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
	//Inclusive small coordinates limit:7, 247
	private static final int smallCoordinateUpperLimit = 247;
	private static final int smallCoordinateLowerLimit = 7;
	//Inclusive big coordinates limit:0, 15
	private static final int bigCoordinateUpperLimit = 15;
	private static final int bigCoordinateLowerLimit = 0;
	
	protected Point initialLocation;
	protected Point location;
	protected int speed;
	protected int power;
	protected int coolingTime;
	protected int detonatedTime;
	//protected Queue<BMItem> itemQueue;
	protected Vector<BMItem> items;
	protected int deaths;
	protected int initialHP;
	protected int HP;
	protected int kills;
	protected int ID;
	protected boolean lost;
	protected int playerNumber;
	protected BMSimulation simulation;
	
	private Lock mLock;
	protected volatile boolean respawning;
	protected volatile boolean cooling;
	
	
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
	private boolean enableMove = true;
	public BMPlayer(int ID, int initialLives){
		this.ID = ID;
		this.initialHP = initialLives;
		location = new Point(-1, -1);
		speed = normalSpeed;
		power = normalPower;
		coolingTime = normalCoolingTime;
		detonatedTime = normalDetonatedTime;
		//itemQueue = new LinkedList<BMItem>();
		items = new Vector<BMItem>();
		HP = initialLives;
		kills = 0;
		lost = false;
		mLock = new ReentrantLock();
		respawning = false;
		new Thread(new Runnable(){

			@Override
			public void run() {
				while (true){
					try {
						Thread.sleep(100/speed);
					} catch (InterruptedException e) {
						System.out.println("Run " + e.getMessage());;
					}
					BMPlayer.this.enableMove = true;
				}
			}
			
		}).start();
	}
	
	public void setSimulation(BMSimulation simulation){
		this.simulation = simulation;
	}
	
	public void killed(){
		if (respawning) return;
		respawning = true;
		HP--;
		if (HP < 0) lost = true;
		location = initialLocation;
		try{
			Thread.sleep(3000);
		} catch (InterruptedException ie){
			ie.printStackTrace();
		}
		respawning = false;
	}
	
	public Point getLocation(){
		return location;
	}
	
	public Vector<BMItem> getItemsProcessed(){
		Vector<BMItem> returnVector = new Vector<BMItem>();
		//Object [] array = itemQueue.toArray();
		
		if (items.size() > 2 || items.size() < 0)
			System.out.println("Player has " + items.size() + " items. Error in BMPlayer getItemsProcessed.");
		for(int i=0; i< items.size(); i++){
			returnVector.addElement((BMItem) items.get(i));
		}
		return returnVector;
	}
	
	public int getNumOfItemsAcquired(){
		return items.size();
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
	public int getSpeed(){
		return speed;
	}
	public int getPower(){
		return power;
	}
	public int getCoolingTime(){
		return coolingTime;
	}
	public int getDetonatedTime(){
		return detonatedTime;
	}
	public boolean hasLost(){
		return lost;
	}
	public boolean isRealPlayer(){
		return ID >= 0;
	}
	private void addItem(BMItem item){
		if(item.getValue()%2 == 0){
			if(items.size() == 2){
				BMItem toBeRemovedItem = items.remove(0);
				switch(toBeRemovedItem.getValue()){
					case 0: speed = normalSpeed;
					case 2: power = normalPower;
					case 4: coolingTime = normalCoolingTime;
					case 6: detonatedTime = normalDetonatedTime;
				}
			}
				items.add(item);
			switch(item.getValue()){
				case 0: speed = increasedSpeed;
				case 2: power = increasedPower;
				case 4: coolingTime = reducedCoolingTime;
				case 6: detonatedTime = reducedDetonatedTime;
			
			}
		}
		else{
			switch(item.getValue()){
				case 1: speed = normalSpeed;
				case 3: power = normalPower;
				case 5: coolingTime = normalCoolingTime;
				case 7: detonatedTime = normalDetonatedTime;
			}
			for (int i=0; i< items.size(); i++){
				if(item.getValue() - items.get(i).getValue() == 0){
					items.remove(i);
				}
			}
		}
	
	}
	public synchronized void startMove(int moveType){
		if (enableMove){
			if (canMove(moveType)) moveHelper(moveType);
			enableMove = false;
		}
	}
	protected void moveHelper(int moveType){
		
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
			case 5: simulation.dropBomb(initX/16, initY/16, this);
					cooling = true;
					break;
		}
		BMNode nextNode = simulation.getNode(location.x/16, location.y/16);
		if (nextNode instanceof BMBombing){
			killed();
		}
		else if (nextNode instanceof BMNodeItem){
			BMNodeItem itemNode = (BMNodeItem)nextNode;
			int value = itemNode.getValue();
			addItem(new BMItem(value));
			
		}
	}
	
	protected boolean canMove(int moveType) {
		if (hasLost()) return false;
		if (moveType < 0 || moveType > 5){
			System.out.println("Error input of moveType. BMPlayer canMove.");
			return false;
		}
		else if (moveType == 0) return true;
		else if (moveType == 5){
			if (cooling) return false;
			BMNode node = simulation.getNode(location.x/16, location.y/16);
			if (node instanceof BMBombing || node instanceof BMBomb){
				return false;
			}
			else return true;
		}
		else{
			//int initBigX = location.x/16;
			//int initBigY = location.y/16;
			int initSmallX = location.x;
			int initSmallY = location.y;
			switch(moveType){
				case 0: initSmallY--; break;
				case 1: initSmallY++; break;
				case 2: initSmallX--; break;
				case 3: initSmallX++; break;
			}
			//int finalBigX = initBigX;
			//int finalBigY = initBigY;
			int finalSmallX = initSmallX;
			int finalSmallY = initSmallY;
			/*if (pointInBigBounds(finalBigX, finalBigY)){
				BMNode nextNode = simulation.getNode(finalBigX, finalBigY);
				if (nextNode instanceof BMWall || nextNode instanceof BMTile) return false;
				else return true;
			}
			else return false;*/
			if (pointInSmallBounds(new Point(finalSmallX, finalSmallY))){
				BMNode nextNode = simulation.getNode(finalSmallX, finalSmallY);
				if (nextNode instanceof BMWall || nextNode instanceof BMTile) return false;
				else return true;
			}
			else return false;
			//System.out.println("Error in BMPlayer canMove");			
		}
	}
	//Note that Point stores small coordinates. Use ints for big coordinates
	protected boolean pointInSmallBounds(Point p){
		return inSmallBounds(p.x) && inSmallBounds(p.y);
	}
	private boolean inSmallBounds(int x){
		return x >= smallCoordinateLowerLimit && x <= smallCoordinateUpperLimit; 
	}
	protected boolean pointInBigBounds(int bigX, int bigY){
		return inBigBounds(bigX) && inBigBounds(bigY); 
	}
	private boolean inBigBounds(int bigC){
		return bigC >= bigCoordinateLowerLimit && bigC <= bigCoordinateUpperLimit;
	}
	
	
	public void setInitialLocation(int playerNumber){
		if (playerNumber > 3 || playerNumber < 0) System.out.println("Input out of bounds, needs 0 to 3. In BMPlayer setInitialLocation.");
		switch(playerNumber){
		
			case 0:
				location.x = smallCoordinateLowerLimit;
				location.y = smallCoordinateLowerLimit;
				break;
			case 1:
				location.x = smallCoordinateUpperLimit;
				location.y = smallCoordinateUpperLimit;
				break;
			case 2:
				location.x = smallCoordinateUpperLimit;
				location.y = smallCoordinateLowerLimit;
				break;
			case 3:
				location.x = smallCoordinateLowerLimit;
				location.y = smallCoordinateUpperLimit;
				break;
		}
		initialLocation = new Point(location.x, location.y);
		this.playerNumber = playerNumber;
	}
	
	private int calculatePoints(){
		return 10*kills - 5*(initialHP-HP);
	}
	public TreeMap<String,Object> getResult() {
		TreeMap<String,Object> resultMap = new TreeMap<String,Object>();
		resultMap.put("ID", ID);
		resultMap.put("Points", new Integer(calculatePoints()));
		resultMap.put("Kill", kills);
		resultMap.put("Death", new Integer(initialHP-HP));
		for(int i=0; i<items.size(); i++){
			resultMap.put("Item " + (i+1), items.get(i));
		}
		return resultMap;
		
	}
}
