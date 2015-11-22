package Client;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Dictionary;
import java.util.TreeMap;
import java.util.Vector;
import javax.swing.JPanel;

import Server.BMHostServer;
import Server.BMPlayer;
import Server.BMSimulation;
import Utilities.BMLibrary;
import centralServer.BMCentralServer;
import centralServer.BMCentralServerClient;
public class BMClientPanel extends JPanel{
	private static final long serialVersionUID = 6415716059554739910L;
	private BMLoginPanel loginPanel;
	private BMMenuPanel menuPanel;
	private BMRoomPanel roomPanel;
	private BMRankPanel rankPanel;
	public BMBoardPanel boardPanel;
	public BMSigninPage detailSignin;
	private Integer[][] board = null;
	private int time = 0;	
	HostClientListener hostClient;
	BMCentralServerClient serverClient;
	private Vector<TreeMap<String, Object>> players;
	String username;
	String password;
	private int hp;
	protected BMHostServer hs;
	protected BMSimulation simulation;
	
	{
		players = new Vector<TreeMap<String,Object>>();


		serverClient = new BMCentralServerClient ( "localhost", 5555 );
		
		
		loginPanel = new BMLoginPanel(new ActionListener() {
			@Override
			
			public void actionPerformed(ActionEvent ae) {
				BMClientPanel.this.removeAll();
				if (serverClient.signup(username, password))
				{
					
					BMClientPanel.this.add(loginPanel);				
					BMClientPanel.this.revalidate();
					BMClientPanel.this.repaint();
				}
				else
				{
					System.out.println("sign up failed");
				}
				/*
				BMClientPanel.this.add(roomPanel);
			
				BMClientPanel.this.revalidate();
				BMClientPanel.this.repaint();
				*/
				System.out.println("login 1");
			}
		},
				new ActionListener(){
				@Override
			public void actionPerformed(ActionEvent ae) {
				BMClientPanel.this.removeAll();
				/*QuickGame
				BMClientPanel.this.add(roomPanel);
				*/
				BMClientPanel.this.revalidate();
				BMClientPanel.this.repaint();
				System.out.println("login 2");

			}
		},
				new ActionListener(){
				@Override				
			public void actionPerformed(ActionEvent ae) {
					/*add check the correctness of the username and password*/
					username = loginPanel.getSignin().nameInput.getText().trim();
					password = loginPanel.getSignin().passwordInput.getText().trim();
					if (serverClient.login(username, password))
					{
						BMClientPanel.this.removeAll();				
						BMClientPanel.this.add(menuPanel);
						BMClientPanel.this.revalidate();
						BMClientPanel.this.repaint();
					}
					else
					{
						System.out.println("login in failed");
					}
				
				System.out.println("login 3");
				loginPanel.closeSignup();
			}}, BMLibrary.readImages("menu.png"),serverClient );
		
		
		//Set up the panel to display
				setLayout(new BorderLayout());
				add(loginPanel);
				refreshComponents();
	}
	private void refreshComponents()
	{
		menuPanel = new BMMenuPanel(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				//host the game
				BMClientPanel.this.removeAll();
				BMClientPanel.this.add(roomPanel);
				BMClientPanel.this.revalidate();	
				String temp = menuPanel.portField.getText();
				int portNumber = Integer.parseInt(temp);
				simulation = new BMSimulation(5555,5);

				hostClient = new HostClientListener(BMClientPanel.this, "localhost", 5555);
			}
		},
		new ActionListener()
		{
		@Override
			public void actionPerformed(ActionEvent e)
			{
				hostClient = new HostClientListener(BMClientPanel.this, "localhost", 5555);
				hostClient.sendJoin(username);
				
			}
		},
		new ActionListener()
		{
		@Override
			public void actionPerformed(ActionEvent e)
			{
			//Login
				BMClientPanel.this.removeAll();
				BMClientPanel.this.add(rankPanel);
				BMClientPanel.this.revalidate();
				
			}
		}, BMLibrary.readImages("menu.png")
	);

		boardPanel = new BMBoardPanel(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e)
			{
				
			}
		});
	roomPanel = new BMRoomPanel(
			new ActionListener(){
				@Override
				public void actionPerformed(ActionEvent e){
					//enter the game
					simulation.startGame(0);				
				}
			},
			new ActionListener()
			{
			@Override
				public void actionPerformed(ActionEvent e)
				{
				
				
				//enter Game
					simulation.startGame(1);
				}
			},
			new ActionListener()
			{
			@Override
				public void actionPerformed(ActionEvent e)
				{
				//Login
					BMClientPanel.this.removeAll();
					BMClientPanel.this.add(menuPanel);
					BMClientPanel.this.revalidate();
					
				}
			},BMLibrary.readImages("background3.png")
			);
		
	rankPanel = new BMRankPanel(new ActionListener(){
		@Override
		public void actionPerformed(ActionEvent e)
		{
			BMClientPanel.this.removeAll();
			BMClientPanel.this.add(menuPanel);
			BMClientPanel.this.revalidate();
		}
	});
	}
	
	void set_start(Integer [][] board, int time, Vector<TreeMap<String, Object>> players)
	{
		this.board = board;
		this.time = time;
		this.players = players;
		boardPanel.setupMap(board, time, players , username, hostClient);

		BMClientPanel.this.removeAll();
		BMClientPanel.this.add(boardPanel);		
		BMClientPanel.this.revalidate();
		boardPanel.setFocusable(true);
		boardPanel.requestFocusInWindow();
		boardPanel.requestFocus();
	}
	
	void set_join(Vector<TreeMap<String,Object>> players, int hp, int time)
	{
		this.players = players;
		this.time = time;
		this.hp = hp;
		BMClientPanel.this.removeAll();
		BMClientPanel.this.add(roomPanel);		
		BMClientPanel.this.revalidate();
	}
}