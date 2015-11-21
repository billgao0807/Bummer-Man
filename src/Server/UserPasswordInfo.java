package Server;

import java.io.Serializable;

public class UserPasswordInfo implements Serializable {

	private static final long serialVersionUID = -3947594960486424457L;
	
	private String username;
	private String password;
	
	private boolean isLogin = false;
	private boolean isSignUp = false;
	
	public UserPasswordInfo(String u, String p, String loginOrSignup) {
		username = u;
		password = p;
		
		if (loginOrSignup.equals(ServerConstants.LOGIN)){
			isLogin = true;
			isSignUp = false;
		} else if (loginOrSignup.equals(ServerConstants.SIGNUP)) {
			isLogin = false;
			isSignUp = true;
		}
		
	}
	
	/*
	 * Setters
	 */
	public void setUsername(String u) {
		username = u;
	}
	public void setPassword(String p) {
		password = p;
	}
	
	/*
	 * Getters
	 */
	public String getUsername() {
		return username;
	}
	public String getPassword() {
		return password;
	}
	
	/*
	 * For outside classes to determine whether the use is logging in or signing up
	 */
	public boolean isLogin() {
		return isLogin;
	}
	public boolean isSignup() {
		return isSignUp;
	}

}
