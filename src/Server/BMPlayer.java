package Server;

import java.awt.Point;
import java.util.Vector;

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
	protected Point location;
	protected int speed;
	protected int power;
	protected int coolingTime;
	protected int detonatedTime;
	//protected Vector<BMItem> items;
	protected int deaths;
	protected int initialLives;
	protected int HP;
	protected int ID;
	protected boolean lost;
	protected BMSimulation simulation;
	
	
//	Functions:
//		+ BMPlayer(int ID, int initialLives)
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
		this.initialLives = initialLives;
		location = new Point();
		speed = 1;
		power = 3;
		coolingTime = 10;
		
		
	}
	
	
}
