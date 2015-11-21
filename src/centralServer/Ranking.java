package centralServer;

import java.io.Serializable;

/*
 * Encapsulates the ranking data for a a single player
 */
public class Ranking implements Serializable {
	private static final long serialVersionUID = 6412772210281108230L;
	
	private double rating;
	private double relativeDeviation;
	private double volativity;
	private String username;
	
	/*
	 * Setters
	 */
	public void setRating(double r) {
		rating = r;
	}
	public void setRelativeDeviation(double rd) {
		relativeDeviation = rd;
	}
	public void setVolativity(double v) {
		volativity = v;
	}
	public void setUsername(String s) {
		username = s;
	}
	
	/*
	 * Getters
	 */
	public double getRating() {
		return rating;
	}
	public double getRelativeDeviation() {
		return relativeDeviation;
	}
	public double getVolativity() {
		return volativity;
	}
	public String getUsername() {
		return username;
	}
}
