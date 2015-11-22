package Client;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Dictionary;
import java.util.TreeMap;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import Server.BMHostServer;
import Server.BMPlayer;
import Server.BMSimulation;
import Utilities.BMLibrary;
import customUI.PaintedButton;
import customUI.PaintedPanel;
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
	private Vector<TreeMap<String, Object>> players;
	String username;
	private int hp;
	protected BMHostServer hs;
	protected BMSimulation simulation;
	//true = host the game
	private boolean identity = true;
	
	{
		players = new Vector<TreeMap<String,Object>>();
//		detailSignin = new BMSigninPage(new ActionListener() {
//			@Override
//			
//			public void actionPerformed(ActionEvent ae) {
//				BMClientPanel.this.removeAll();
//				detailSignin.setVisible(false);
//				/*Signup
//				BMClientPanel.this.add(roomPanel);
//				*/
//				BMClientPanel.this.revalidate();
//				BMClientPanel.this.repaint();
//			}
//		},
//				new ActionListener(){
//				@Override
//			public void actionPerformed(ActionEvent ae) {
//				BMClientPanel.this.removeAll();
//				detailSignin.setVisible(false);
//				/*QuickGame
//				BMClientPanel.this.add(roomPanel);
//				*/
//				BMClientPanel.this.revalidate();
//				BMClientPanel.this.repaint();
//			}
//		},
//				new ActionListener(){
//				@Override				
//			public void actionPerformed(ActionEvent ae) {
//					/*add check the correctness of the username and password*/
//					username = detailSignin.nameInput.getText().trim();
//					
//				BMClientPanel.this.removeAll();				
//				BMClientPanel.this.add(menuPanel);
//				BMClientPanel.this.revalidate();
//				BMClientPanel.this.repaint();
//				System.out.println("CLosing jframe");
//				detailSignin.closeMe();
//			}}
//				);
		loginPanel = new BMLoginPanel(new ActionListener() {
			@Override
			
			public void actionPerformed(ActionEvent ae) {
				BMClientPanel.this.removeAll();
				/*Signup
				BMClientPanel.this.add(roomPanel);
				*/
				BMClientPanel.this.revalidate();
				BMClientPanel.this.repaint();
				System.out.println("login 1");
//				detailSignin.closeMe();
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
//				detailSignin.closeMe();
			}
		},
				new ActionListener(){
				@Override				
			public void actionPerformed(ActionEvent ae) {
					/*add check the correctness of the username and password*/
					username = loginPanel.getSignin().nameInput.getText().trim();
				BMClientPanel.this.removeAll();				
				BMClientPanel.this.add(menuPanel);
				BMClientPanel.this.revalidate();
				BMClientPanel.this.repaint();
				System.out.println("login 3");
				loginPanel.closeSignup();
//				detailSignin.closeMe();
			}}, BMLibrary.readImages("menu.png"));
		
		
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
				String temp1 = menuPanel.ipField.getText();
				String temp2 = menuPanel.portField.getText();
				int port = 0;
				if (temp2.length() != 0)
				{
					try
					{
						port = Integer.parseInt(temp2);
					}catch(NumberFormatException nfe)
					{
						System.out.println("number format is wrong");
					}
				}
			else
			{
				String temp = menuPanel.portField.getText().trim();
				try
				{
					port = Integer.parseInt(temp2);
				}catch(NumberFormatException nfe)
				{
					popError("Port format is wrong!");
					System.out.println("number format is wrong");
				}
			}
			String ip = null;
			if (temp1.length() == 0)
			{
				System.out.println("ip is empty");
				popError("IP is empty!");
				
			}
			else
			{
				ip = temp1;
			}
				
				
				
				simulation = new BMSimulation(5555,5);
				
				hostClient = new HostClientListener(BMClientPanel.this, "localhost", 5555);
				hostClient.sendJoin(username);
				
			}
		},
		new ActionListener()
		{
		@Override
			public void actionPerformed(ActionEvent e)
			{
				hostClient = new HostClientListener(BMClientPanel.this, "localhost", 5555);
				hostClient.sendJoin(username);
				identity = false;
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
	roomPanel = new BMRoomPanel(identity,
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
	
	void popError(String error)
	{
		System.out.println("popError");
		ipChecking popup = new ipChecking(error);
	}
}

class ipChecking extends JFrame
{
	
	ipChecking(String error)
	{
		setSize(new Dimension(320,480));
		setLocationRelativeTo(null);
		
		System.out.println("FRAME");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		Notice notice = new Notice(this,error,null);
		add(notice);
		setVisible(true);
		
	}	
	public void close() {
		this.setVisible(false);
		
	}
}
class Notice extends PaintedPanel
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JLabel message; 
	private PaintedButton ok;
	ipChecking frame;
	Notice(ipChecking frame,String error, Image image)
	{
		super(image);
		this.frame = frame;
		System.out.println("notice");
		setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		message = new JLabel(error);
		JPanel mp = new JPanel();
		mp.setOpaque(false);
		mp.add(message);
		ok = new PaintedButton("OK",BMLibrary.readImages("button0.png") , BMLibrary.readImages("button0-0.png"), 30);
		ok.addActionListener(new ActionListener()
				{
				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					frame.close();
				}
				});
		
		JPanel okp = new JPanel();
		okp.setOpaque(false);
		okp.add(ok);
		gbc.gridy = 1;
		add(mp,gbc);
		gbc.gridy = 2;
		add(okp,gbc);
		
	}
}