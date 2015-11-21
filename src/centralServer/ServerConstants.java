package centralServer;

public class ServerConstants {
	
	public static final String LOGIN = "login";
	public static final String SIGNUP = "signup";
	public static final String LOGOUT = "logout";
	public static final String DISCONNECT = "disconnect";
	public static final String SUCCESSFULLOGIN = " SUCCESFULLY LOGGED IN |";
	public static final String LOGINFAILED = " LOGIN FAILED |";
	public static final String SUCCESSFULLOGOUT = " SUCCESFULLY LOGGED OUT |";
	public static final String LOGOUTFAILED = " LOGOUT FAILED |";
	public static final String SUCCESSFULSIGNUP = " SUCCESFULLY SIGNED UP |";
	public static final String SIGNUPFAILED = " SIGNUP FAILED |";
	
	public static final String REQUESTPERSONALRECORD = "Request for Personal Record";
	public static final String REQUESTWORLDRANKING = "Request for World Rankings";
	public static final String SENDINGPERSONALRECORD = "Sending Personal Record |";
	public static final String SENDINGWORLDRANKING = "Sending World Ranking |s";
	
	public static final String WorldRankingFetchFailure = "Error retrieving World Ranking";
	public static final String PersonalRankingFetchFailure = "Error retrieving Personal Record";
	public static final String CannotCompleteRequest = "Cannot complete request: ";
	public static final String NotLoggedIn = "Not Logged In to Server";
	public static final String NotConnected = "Not Connected to Server";
	
	
	public static final String VIPSTATUSREQUEST = "VIP Status Request";
	public static final String VIPSTATUSTRUE = "User is a VIP";
	public static final String VIPSTATUSFALSE = "User is not a VIP";
	
	
	/*
	 * PortGUI Constants (copied from Factory code)
	 */
	public static final int lowPort = 0;
	public static final int highPort = 65535;
	public static final int defaultPort = 6789;
	public static final String defaultHostname = "localhost";
	
	public static final String portDescriptionString = "<html>Enter the port number on which<br />you would like the server to listen.</html>";
	public static final String portLabelString = "Port";
	public static final String submitPortString = "Start Listening";
	public static final String portGUITitleString = "Factory Server - Port";
	public static final int portGUIwidth = 300;
	public static final int portGUIheight = 170;
	public static final String portErrorString = "<html><font color=\"red\">Please enter a valid port<br />between " + lowPort + " and " + highPort + "</font></html>";
	public static final String portAlreadyInUseString = "<html><font color=\"red\">Port already in use.  Select another port<br />between " + lowPort + " and " + highPort + "</font></html>";
	
	public static final String startClientConnectedString = "Client with IP address ";
	public static final String endClientConnectedString = " connected.";
	public static final String clientDisconnected = "BMClient disconnected.";
	
}
