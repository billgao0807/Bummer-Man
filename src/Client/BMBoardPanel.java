package Client;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;
import java.awt.Point;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Dictionary;
import java.util.Map.Entry;
import java.util.Stack;
import java.util.TreeMap;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.SwingUtilities;
import javax.swing.text.BadLocationException;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

import Server.BMPlayer;
import Server.BMRealPlayer;
import node.BMTile;
import Utilities.BMLibrary;
import Utilities.BMMove;
import Utilities.BMNodeType;
import customUI.PaintedButton;
import customUI.PaintedLabel;
import customUI.PaintedPanel;

public class BMBoardPanel extends JPanel{
	private final static int boardSize = 16;
	//private final TilePanel[][] tileGrid;

	private PaintedPanel chatPanel, boardPanel, playerPanel;
	private static JTextPane chatPane;
	private JTextField chatTF;
  	private final NodePanel[][] nodeGrid;

	private PaintedButton chatButton;
	private JLabel TimeLabel, HPLabel,AbilityLabel;
	private PaintedButton SpeedButton, PowerButton, Item1Button, Item2Button , QuitButton;
	private Integer[][]map;
	private KeyAdapter keylistener;
	private HostClientListener clientListener;
	private String local_username;
	private int local_hp;
	private int total_hp;
	private Vector<int[]> location; 
	private int time;
	private Vector<TreeMap<String, Object>> players;

	volatile int keyPressed = 0;
	private Thread sending;
	public static long a;

	public void setupMap(Integer[][] map, int time, Vector<TreeMap<String, Object>> players2 , String username, HostClientListener clientListener){

		this.clientListener = clientListener;
		this.map=map;
		this.time =time;
		location = new Vector<int[]>();
		this.local_username = username;
//		System.out.println(players2);
		for (int i = 0; i<players2.size(); i++) {
			if (((String)players2.get(i).get("username")).equals(local_username))
			{
				local_hp = (int)(players2.get(i).get("hp"));
				total_hp=local_hp;
			}
		}
		//boardPanel initialize
				boardPanel.setLayout(new GridLayout(boardSize,boardSize));
				for(int y = 0; y < boardSize; ++y) {
					for(int x = 0; x < boardSize; ++x) {
						if(map[x][y]==1) {
							nodeGrid[x][y] = new NodePanel(1); }
						else if (map[x][y]==2) {
							nodeGrid[x][y] = new NodePanel(2);}
						else if (map[x][y]==0) {
							nodeGrid[x][y] = new NodePanel (0);
						}
						else {System.out.println("wrong read from input");}
						boardPanel.add(nodeGrid[x][y]);
					}
				}
		this.players = players2;
		repaint();
	}
	
	public BMBoardPanel(ActionListener playingGame){

		setSize(1000,600);
		this.setLayout(new BorderLayout());
		chatPanel= new PaintedPanel(null);
		boardPanel = new PaintedPanel(null);
		playerPanel = new PaintedPanel(null);
		chatPanel.setPreferredSize(new Dimension(150, BMBoardPanel.this.getHeight()));
		keylistener = null;
		nodeGrid = new NodePanel[boardSize][boardSize];


//chatPanel initialize
		
		chatPanel.setLayout(new BorderLayout());
		chatPane = new JTextPane();
		chatTF = new JTextField();
		chatButton = new PaintedButton("send" , null, null, 10);	
		chatPane.setPreferredSize(new Dimension(chatPanel.getWidth(), 450));
		chatPane.setEditable(false);
		
//PlayerPanel initialize
		playerPanel.setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		TimeLabel = new JLabel("Time: " + time);
		HPLabel = new JLabel("HP " + local_hp +"/" +total_hp);
		AbilityLabel = new JLabel("Ability:");
		SpeedButton = new PaintedButton("Speend", null, null, 10);
		PowerButton = new PaintedButton("Power", null, null, 10);
		Item1Button = new PaintedButton ("Item1", null, null, 10);
		Item2Button = new PaintedButton ("Item2", null, null, 10);
		QuitButton = new PaintedButton ("Quit", null, null, 10);
		Item1Button.setPreferredSize(new Dimension(60, 50));
		SpeedButton.setPreferredSize(new Dimension(60, 50));
		Item2Button.setPreferredSize(new Dimension(60, 50));
		PowerButton.setPreferredSize(new Dimension(60, 50));
		//QuitButton.setPreferredSize(new Dimension(60, 50));

		boardPanel.setLayout(new GridLayout(boardSize,boardSize));

//playerPanel add component
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = 2;
		gbc.insets = new Insets(15,0,15,0);
		playerPanel.add(TimeLabel,gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 1;
		playerPanel.add(HPLabel,gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 2;
		gbc.gridwidth = 1;
		playerPanel.add(AbilityLabel,gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 3;
		gbc.gridwidth = 1;
		gbc.insets = new Insets(0,5,0,5);

		playerPanel.add(SpeedButton,gbc);
		
		gbc.gridx = 1;
		gbc.gridy = 3;
		gbc.gridwidth = 1;
		playerPanel.add(PowerButton,gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 4;
		gbc.gridwidth = 1;
		playerPanel.add(Item1Button,gbc);
		
		gbc.gridx = 1;
		gbc.gridy = 4;
		gbc.gridwidth = 1;
		playerPanel.add(Item2Button,gbc);

		gbc.gridx = 0;
		gbc.gridy = 5;
		gbc.gridwidth = 2;
		gbc.insets = new Insets(15,0,15,0);
		playerPanel.add(QuitButton,gbc);

		
//add component into chatPanel
		
		chatPanel.add(chatPane, BorderLayout.NORTH);
		chatPanel.add(chatTF, BorderLayout.CENTER);
		chatPanel.add(chatButton, BorderLayout.SOUTH);
//add players
		
		
//add all panels		
		this.add(chatPanel, BorderLayout.WEST);
		this.add(boardPanel, BorderLayout.CENTER);
		this.add(playerPanel, BorderLayout.EAST);
		addAction();
		revalidate();
		repaint();
	}
	

//	@Override 
//	public void paintComponent(Graphics g){
//		super.paintComponent(g);
//		if (players == null) return;
////		System.out.println("Player size " + players.size());
//		for (int i=0; i<players.size(); i++) {
//			int x = (int)(players.get(i).get("posX"));
//			int y = (int)(players.get(i).get("posY"));
//			Image image= BMLibrary.readImages("player" + i + ".png");
//			g.drawImage(image, (x-64)*boardPanel.getWidth()/1024+boardPanel.getX(), (y-64)*boardPanel.getHeight()/1024+boardPanel.getY(),boardPanel.getWidth()/16, boardPanel.getHeight()/16, boardPanel);
//		}
//	}
	@Override 
	public void paint(Graphics g){

		  System.out.println("Paint " + (System.currentTimeMillis()-BMBoardPanel.a) + " ms");
  			BMBoardPanel.a=System.currentTimeMillis();
		super.paint(g);
		if (players == null) return;
//		System.out.println("Player size " + players.size());
		for (int i=0; i<players.size(); i++) {
			int x = (int)(players.get(i).get("posX"));
			int y = (int)(players.get(i).get("posY"));
			Image image= BMLibrary.readImages("player" + i + ".png");
			g.drawImage(image, (x-15)*boardPanel.getWidth()/1024+boardPanel.getX(), (y-15)*boardPanel.getHeight()/1024+boardPanel.getY(),boardPanel.getWidth()/16, boardPanel.getHeight()/16, boardPanel);
		}
	}
	
	public void set_move(int time, Vector<TreeMap<String, Object>>  players_){
		this.players = players_;
//		paintComponent(this.getGraphics());
//		System.out.println("Start Repaint " +(System.currentTimeMillis()-a) + " ms");
		a=System.currentTimeMillis();
		this.repaint();
		validate();	
//		System.out.println("After Repaint " + (System.currentTimeMillis()-a) + " ms");
		a=System.currentTimeMillis();
	}
	
	public void startSending(){
		sending = new Thread(new Runnable(){

			@Override
			public void run() {
				try {
					while (true){
			 			Thread.sleep(10);
						clientListener.sendMove(keyPressed);
					}
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					System.out.println("Exception " + e.getMessage());
				}
			}
			
		});
		sending.start();
	}
	
	//public void set_move( Vector<Dictionary> board,  )
	public void addAction(){
//		KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(new KeyEventDispatcher() {
//
//            @Override
//            public boolean dispatchKeyEvent(KeyEvent ke) {
//            		int c = ke.getID();
//            	synchronized (this) {
//                    if(c == KeyEvent.VK_LEFT)
//    				    {
//						clientListener.sendMove(BMMove.left);
//
//    				    }
//    				  else if(c == KeyEvent.VK_RIGHT)
//    				    {						
//    					  clientListener.sendMove(BMMove.right);
//
//    				    }
//    				  else if (c == KeyEvent.VK_UP){
//  						clientListener.sendMove(BMMove.up);
//
//    				  }
//    				  else if (c == KeyEvent.VK_DOWN){
//  						clientListener.sendMove(BMMove.down);
//    				  }
//    				  else if (c == KeyEvent.VK_SPACE){
//  						clientListener.sendMove(BMMove.bomb);
//    				  }	 
//                    return false;
//                }
//            }
//        });
//		
//		
//		
		
		this.addKeyListener(new KeyAdapter(){

	        @Override
	        public void keyPressed(KeyEvent e) {
	        	int c = e.getKeyCode();
//				System.out.println("Press key " + c);
				  if(c == KeyEvent.VK_LEFT)
				    {
					  if (keyPressed == 0){
						  keyPressed = BMMove.left;
						  startSending();
					  }
				    }
				  else if(c == KeyEvent.VK_RIGHT)
				    {
					  if (keyPressed == 0){
						  keyPressed = BMMove.right;
						  startSending();
					  }
				    }
				  else if (c == KeyEvent.VK_UP){
					  if (keyPressed == 0){
						  keyPressed = BMMove.up;
						  startSending();
					  }
				  }
				  else if (c == KeyEvent.VK_DOWN){
					  if (keyPressed == 0){
						  keyPressed = BMMove.down;
						  startSending();
					  }
				  }
				  else if (c == KeyEvent.VK_SPACE){
					  if (keyPressed == 0){
						  keyPressed = BMMove.bomb;
						  startSending();
					  }
				  }
//				  System.out.println("Key press " + (System.currentTimeMillis()-BMBoardPanel.a) + " ms");
//		    			BMBoardPanel.a=System.currentTimeMillis();
//		        		char ch = event.getKeyChar();
//		
//			      	System.out.println(event.getKeyChar());
	        	
	        }
	        @Override
	        public void keyReleased(KeyEvent e) {
//				  System.out.println("Key release " + (System.currentTimeMillis()-BMBoardPanel.a) + " ms");
//	    			BMBoardPanel.a=System.currentTimeMillis();
//	        		char ch = e.getKeyChar();
//	
//		      	System.out.println(e.getKeyChar());

				int c = e.getKeyCode();
//				System.out.println("Release key");
//				  System.out.println("Key release " + (System.currentTimeMillis()-BMBoardPanel.a) + " ms");
//	    			BMBoardPanel.a=System.currentTimeMillis();
	
				  if(c == KeyEvent.VK_LEFT)
				    {
					  if (keyPressed == BMMove.left){
						  sending.interrupt();
						  keyPressed = 0;
						  sending = null;
					  }
				    }
				  else if(c == KeyEvent.VK_RIGHT)
				    {
					  if (keyPressed == BMMove.right){
						  sending.interrupt();
						  keyPressed = 0;
						  sending = null;
					  }
				    }
				  else if (c == KeyEvent.VK_UP){
					  if (keyPressed == BMMove.up){
						  sending.interrupt();
						  keyPressed = 0;
						  sending = null;
					  }
				  }
				  else if (c == KeyEvent.VK_DOWN){
					  if (keyPressed == BMMove.down){
						  sending.interrupt();
						  keyPressed = 0;
						  sending = null;
					  }
				  }
				  else if (c == KeyEvent.VK_SPACE){
					  if (keyPressed == BMMove.bomb){
						  sending.interrupt();
						  keyPressed = 0;
						  sending = null;
					  }
				  }	    	
	        }
		});
//		
//		keylistener = new KeyAdapter(){
//			@Override
//			public void keyPressed(KeyEvent e) {
//				// TODO Auto-generated method stub	
//				int c = e.getKeyCode();
//				System.out.println("Press key");
//				  if(c == KeyEvent.VK_LEFT)
//				    {
//					  if (keyPressed == 0){
//						  keyPressed = BMMove.left;
//						  startSending();
//					  }
//				    }
//				  else if(c == KeyEvent.VK_RIGHT)
//				    {
//					  if (keyPressed == 0){
//						  keyPressed = BMMove.right;
//						  startSending();
//					  }
//				    }
//				  else if (c == KeyEvent.VK_UP){
//					  if (keyPressed == 0){
//						  keyPressed = BMMove.up;
//						  startSending();
//					  }
//				  }
//				  else if (c == KeyEvent.VK_DOWN){
//					  if (keyPressed == 0){
//						  keyPressed = BMMove.down;
//						  startSending();
//					  }
//				  }
//				  else if (c == KeyEvent.VK_SPACE){
//					  if (keyPressed == 0){
//						  keyPressed = BMMove.bomb;
//						  startSending();
//					  }
//				  }	    	
//				  else {
//
//					  sending.interrupt();
//					  keyPressed = 0;
//					  sending = null;
//				  }
//				  System.out.println("Key press " + (System.currentTimeMillis()-BMBoardPanel.a) + " ms");
//		    			BMBoardPanel.a=System.currentTimeMillis();
//			}
//
//			@Override
//			public void keyReleased(KeyEvent e) {
//				// TODO Auto-generated method stub	
//				int c = e.getKeyCode();
//				System.out.println("Release key");
//				  System.out.println("Key release " + (System.currentTimeMillis()-BMBoardPanel.a) + " ms");
//	    			BMBoardPanel.a=System.currentTimeMillis();
//	
//				  if(c == KeyEvent.VK_LEFT)
//				    {
//					  if (keyPressed == BMMove.left){
//						  sending.interrupt();
//						  keyPressed = 0;
//						  sending = null;
//					  }
//				    }
//				  else if(c == KeyEvent.VK_RIGHT)
//				    {
//					  if (keyPressed == BMMove.right){
//						  sending.interrupt();
//						  keyPressed = 0;
//						  sending = null;
//					  }
//				    }
//				  else if (c == KeyEvent.VK_UP){
//					  if (keyPressed == BMMove.up){
//						  sending.interrupt();
//						  keyPressed = 0;
//						  sending = null;
//					  }
//				  }
//				  else if (c == KeyEvent.VK_DOWN){
//					  if (keyPressed == BMMove.down){
//						  sending.interrupt();
//						  keyPressed = 0;
//						  sending = null;
//					  }
//				  }
//				  else if (c == KeyEvent.VK_SPACE){
//					  if (keyPressed == BMMove.bomb){
//						  sending.interrupt();
//						  keyPressed = 0;
//						  sending = null;
//					  }
//				  }	    	
//			}
//			
//		};
//		this.addKeyListener(keylistener);
//		boardPanel.addKeyListener(keylistener);
	}
	
	public static void set_chat_text(String name, String content){
		String orgin = chatPane.getText();
	
		chatPane.setText(orgin + '\n' +name + " : " + content);

		chatPane.setCaretPosition(chatPane.getDocument().getLength());
		
	}

	
	public void endGame(){
		
	}

	public void repaintBoard(int[][] Board){
//		nodeGrid
		System.out.println("repaint board");
		for (int i = 0; i < 16; i++){
			for (int j = 0; j < 16; j++){
				if (Board != null && nodeGrid[i][j].node_type != Board[i][j]) nodeGrid[i][j].update(Board[i][j]);
			}
		}
	}
	
	
	class NodePanel extends PaintedPanel {

		private int node_type=-1;
		
		NodePanel(int node_type) {		
			super(null);
			this.node_type=node_type;
			setLayout(new GridBagLayout());
			update(node_type);
		}

		
		public void update(int new_type){
			node_type =new_type;
			if(new_type == -1) return;
			else if(new_type == BMNodeType.road){
				//road
				setImage(BMLibrary.readImages("wall0.png"));
				
			}
			if(new_type == 1){
				//wall
				setImage(BMLibrary.readImages("wall1.png"));

			}
			if(new_type == 2){
				//tile
				setImage(BMLibrary.readImages("wall2.png"));

			}
			else if(new_type == 3){
				//bomb
				setImage(BMLibrary.readImages("bomb"));

			}
			else if(new_type == 4){
				//bombing
				setImage(BMLibrary.readImages("bombing"));

			}
			else if(new_type == 5){
				//niceShoes
				setImage(BMLibrary.readImages("niceShoes"));

			}
			else if(new_type == 6){
				//badShoes
				setImage(BMLibrary.readImages("badShoes"));

			}
			else if(new_type == 7){
				//improvePower
				setImage(BMLibrary.readImages("improvePower"));

			}
			else if(new_type == 8){
				//reducePower
				setImage(BMLibrary.readImages("reducePower"));

			}
			else if(new_type == 9){
				//reduceCoolingTime
				setImage(BMLibrary.readImages("reduceCoolingTime"));

			}
			else if(new_type == 10){
				//increaseCoolingTime
				setImage(BMLibrary.readImages("increaseCoolingTime"));

			}
			else if(new_type == 11){
				//increaseDetonatedTime
				setImage(BMLibrary.readImages("increaseDetonatedTime"));

			}
			else if(new_type ==12) {
				//reduceDenotatedTime
				setImage(BMLibrary.readImages("reduceDenotatedTime"));

			}
		
			revalidate();
			repaint();
		}
		
		
		
	}
}