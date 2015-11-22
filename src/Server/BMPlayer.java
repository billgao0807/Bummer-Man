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
	private static final int smallCoordinateUpperLimit = 988;
	private static final int smallCoordinateLowerLimit = 28;
	//Inclusive big coordinates limit:0, 15
	private static final int bigCoordinateUpperLimit = 15;
	private static final int bigCoordinateLowerLimit = 0;
	
	private static final int coordinatesRatio = 64;
	
	protected Point initialLocation;
	protected Point location;
	protected int speed;
	protected int power;
	protected int coolingTime;
	protected int detonatedTime;
	//protected Queue<BMItem> itemQueue;
	protected Vector<Integer> items;
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
	
	private int itemCount = 0;
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
	private volatile boolean enableMove = true;
	public BMPlayer(int ID, int initialLives){
		this.ID = ID;
		this.initialHP = initialLives;
		location = new Point(-1, -1);
		setInitialLocation(ID);
		speed = normalSpeed;
		power = normalPower;
		coolingTime = normalCoolingTime;
		detonatedTime = normalDetonatedTime;
		//itemQueue = new LinkedList<BMItem>();
		items = new Vector<Integer>();
		HP = initialLives;
		kills = 0;
		lost = false;
		mLock = new ReentrantLock();
		respawning = false;
		items.add(-1);
		items.add(-1);
	}
	
	public void setSimulation(BMSimulation simulation){
		this.simulation = simulation;
	}
	
	public void killed(int id){
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
		simulation.addKill(id);
	}
	
	public Point getLocation(){
		return location;
	}
	
	public Vector<Integer> getItemsProcessed(){
		itemCount++;
		Vector<Integer> returnVector = new Vector<Integer>();
		//Object [] array = itemQueue.toArray();
		
		if (items.size() > 2 || items.size() < 0)
			System.out.println("Player has " + items.size() + " items. Error in BMPlayer getItemsProcessed.");
		for(int i=0; i< items.size(); i++){
			returnVector.add(items.get(i));
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
				Integer toBeRemovedItem = items.remove(0);
				switch(toBeRemovedItem){
					case 0: speed = normalSpeed;
					case 2: power = normalPower;
					case 4: coolingTime = normalCoolingTime;
					case 6: detonatedTime = normalDetonatedTime;
				}
			}
				items.add(item.getValue());
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
				if(item.getValue() - items.get(i) == 0){
					items.remove(i);
				}
			}
		}
	
	}
	public void startMove(int moveType){
			if (canMove(moveType)) moveHelper(moveType);
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
			case 5: simulation.dropBomb(initX/coordinatesRatio, initY/coordinatesRatio, this);
					cooling = true;
					new Thread(new Runnable(){
						@Override
						public void run() {
							try {
								Thread.sleep(1000*coolingTime);
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							finally {
								cooling = false;
							}
						}
					}).start();
		}
		BMNode nextNode = simulation.getNode(location.x/64, location.y/64);
		if (nextNode instanceof BMBombing){
			killed(((BMBombing)nextNode).getID());
		}
		else if (nextNode instanceof BMNodeItem){
			BMNodeItem itemNode = (BMNodeItem)nextNode;
			int value = itemNode.getValue();
			addItem(new BMItem(value));
			
		}
	}
	
	public int getid(){return ID;}
	
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
				BMNode nextNode = simulation.getNode(finalSmallX/64, finalSmallY/64);
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
		resultMap.put("points", new Integer(calculatePoints()));
		resultMap.put("Kill", kills);
		resultMap.put("death", new Integer(initialHP-HP));
		resultMap.put("item", itemCount);
		return resultMap;
	}

	protected String username = "";
	public TreeMap<String,Object> getInfo(){
		TreeMap<String,Object> info = new TreeMap<String,Object>();
		info.put("username",username);
		info.put("posX", location.x);
		info.put("posY", location.y);
		info.put("hp", HP);
		info.put("speed", speed);
		info.put("power", power);
		info.put("item1", items.get(0));
		info.put("item2", items.get(1));
//		System.out.println("Info " +info);
		return info;
	}
	public void addKill() {
		kills++;
	}
}
