package Client;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Dictionary;
import java.util.TreeMap;
import java.util.Vector;

import javax.swing.JPanel;

import Utilities.BMLibrary;


public class BMClientPanel extends JPanel{
	private static final long serialVersionUID = 6415716059554739910L;
	private BMLoginPanel loginPanel;
	private BMMenuPanel menuPanel;
	private BMRoomPanel roomPanel;
	private BMRankPanel rankPanel;
	public BMBoardPanel boardPanel;
	private int [][] board = null;
	private int time = 0;	
	HostClientListener hostClient;
	private Vector<TreeMap<String, Object>> players;
	String username;
	
	{
		
		loginPanel = new BMLoginPanel(new ActionListener() {
			@Override
			
			public void actionPerformed(ActionEvent ae) {
				BMClientPanel.this.removeAll();
				/*Signup
				BMClientPanel.this.add(roomPanel);
				*/
				BMClientPanel.this.revalidate();
				BMClientPanel.this.repaint();
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
			}
		},
				new ActionListener(){
				@Override				
			public void actionPerformed(ActionEvent ae) {
					/*add check the correctness of the username and password*/
					username = loginPanel.nameInput.getText().trim();
				BMClientPanel.this.removeAll();				
				BMClientPanel.this.add(menuPanel);
				BMClientPanel.this.revalidate();
				BMClientPanel.this.repaint();
<<<<<<< HEAD
				}
			}, BMLibrary.getImage("images/background1.jpg"));
=======
			}}, BMLibrary.readImages("bin/Utilities/images/menu.png"));
>>>>>>> Ellen
		
		
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
				String temp = menuPanel.port.getText();
				String ip = menuPanel.IP.getText();
				int portNumber = Integer.parseInt(temp);
				hostClient = new HostClientListener(menuPanel, ip, portNumber);
			}
		},
		new ActionListener()
		{
		@Override
			public void actionPerformed(ActionEvent e)
			{
			//joinGame
			hostClient.sendJoin(username);
			BMClientPanel.this.removeAll();
			BMClientPanel.this.add(roomPanel);		
			BMClientPanel.this.revalidate();
				
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
		}, BMLibrary.getImage("images/menu.png"));
		
	roomPanel = new BMRoomPanel(
			new ActionListener(){
				@Override
				public void actionPerformed(ActionEvent e){
					//enter the game
					BMClientPanel.this.removeAll();
					BMClientPanel.this.add(boardPanel);
					BMClientPanel.this.revalidate();				
				}
			},
			new ActionListener()
			{
			@Override
				public void actionPerformed(ActionEvent e)
				{
				//enter Game
				BMClientPanel.this.removeAll();
				BMClientPanel.this.add(boardPanel);		
				BMClientPanel.this.revalidate();
					
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
			}
			);
	
	boardPanel = new BMBoardPanel(new ActionListener(){
		@Override
		public void actionPerformed(ActionEvent e)
		{
			
		}
	}, hostClient, time, board, players, username);
		
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
	
	void set_start(int [][] board, int time, Vector<TreeMap<String, Object>> players)
	{
		this.board = board;
		this.time = time;
		this.players = players;
	}
	
	void set_join(Vector<TreeMap<String, Object>> players)
	{
		this.players = players;
	}
	
	
}
