package Server;

public class BMRealPlayer extends BMPlayer {
//	The human player of the game. Implement all the data variables and functions of BMPlayer class.
//	Function:
//		+ BMRealPlayer() 
//		+ getMessage(string msg) : void
//		- run() : void
//	Variables:
//		- BurfferedReader : br
//		- Socket : s
//		- PrintWriter : pw
	public String getUserName(){
		return username;
	}
	public BMRealPlayer(int ID, int initialLives, String name){
		super(ID, initialLives);
		username = name;
	}
}
