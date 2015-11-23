package centralServer;

import java.io.Serializable;

public class GameRecord implements Serializable {

	private static final long serialVersionUID = 4064181985723480070L;
	
	int kills;
	int deaths;
	String time;
	
	public GameRecord(int k, int d, String t) {
		kills = k;
		deaths = d;
		time = t;
	}
	
	/*
	 * Setters
	 */
	public void setKillCount(int k) {
		kills = k;
	}
	public void setDeathCount(int d) {
		deaths = d;
	}
	public void setTime(String t) {
		time = t;
	}
	
	/*
	 * Getters
	 */
	public double getKillCount() {
		return kills;
	}
	public double getDeathCount() {
		return deaths;
	}
	public String getTime() {
		return time;
	}

}
