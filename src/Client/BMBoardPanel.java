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
import java.awt.event.ActionListener;
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

import node.BMTile;
import Utilities.BMLibrary;
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
	private int[][]map;
	private KeyListener keylistener;
	private HostClientListener clientListener;
	private String local_username;
	private int local_hp;
	private int total_hp;
	private Vector<int[]> location; 
	private int time;
	private Vector<TreeMap<String,Object>> players;

	
	public BMBoardPanel(ActionListener playingGame,HostClientListener clientListener, int time,int[][]map, Vector<TreeMap<String,Object>> players , String username){

//Panel initialize
		this.clientListener = clientListener;
		this.map=map;
		this.time =time;
		location = new Vector<int[]>();
		this.local_username = local_username;
		for (int i = 0; i<players.size(); i++) {
			if (players.get(i).get("username").equals(local_username))
			{
				local_hp = (int)players.get(i).get("hp");
				total_hp=local_hp;
			}
		}
//		for (int i = 0; i<players.size(); i++) {
//			
//			int x = (int)players.get(i).get("posX");
//			int y = (int)players.get(i).get("posY");
//			location.get(i)[0] =x;
//			location.get(i)[0] =y;
//
//		}
		
		setSize(1000,600);
		this.setLayout(new BorderLayout());
		chatPanel= new PaintedPanel(null);
		boardPanel = new PaintedPanel(null);
		playerPanel = new PaintedPanel(null);
		chatPanel.setPreferredSize(new Dimension(150, BMBoardPanel.this.getHeight()));
		keylistener = null;
		nodeGrid = new NodePanel[boardSize][boardSize];

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
		this.addKeyListener(keylistener);
		addAction();
		revalidate();
		repaint();
	}
	

	@Override 
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		for (int i=0; i<players.size(); i++) {
			int x =(Integer)players.get(i).get("posX");
			int y =(Integer)players.get(i).get("poxY");
			Image image= BMLibrary.readImage("bin/Utilities/images/player" + i + ".png");
			g.drawImage(image, x*this.boardPanel.getWidth()/256, y*this.boardPanel.getHeight()/256, this.boardPanel);

		}
//		for (TreeMap<String,Object> player : players){
//			int x = (Integer)(player.get("posX"));
//			int y = (Integer)(player.get("posY"));
//			Image image= BMLibrary.readImage("bin/Utilities/images/player" + i + ".png");
//			g.drawImage(image, x*this.boardPanel.getWidth()/256, y*this.boardPanel.getHeight()/256, this.boardPanel);
//		}
		
	}
	
	public void set_move(int[][]board, int time, Vector<TreeMap<String,Object>>  players_){
		this.players = players_;
		revalidate();
		repaint();
		
		repaintBoard(board);
	}						

	
	//public void set_move( Vector<Dictionary> board,  )
	public void addAction(){
		keylistener = new KeyListener(){

			@Override
			public void keyTyped(KeyEvent e) {
				// TODO Auto-generated method stub
			}

			@Override
			public void keyPressed(KeyEvent e) {
				// TODO Auto-generated method stub	
				int c = e.getKeyCode();

				  if(c == KeyEvent.VK_LEFT)
				    {
				    	clientListener.sendMove(3);

				    }
				  else if(c == KeyEvent.VK_RIGHT)
				    {
				    	clientListener.sendMove(4);

				    }
				  else if (c == KeyEvent.VK_UP){
				    	clientListener.sendMove(1);

				  }
				  else if (c == KeyEvent.VK_DOWN){
				    	clientListener.sendMove(2);

				  }
				  else if (c == KeyEvent.VK_SPACE){
				    	clientListener.sendMove(5);

				  }
			}

			@Override
			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub	
		    	clientListener.sendMove(0);
			}
			
		};
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
		for (int i = 0; i < 16; i++){
			for (int j = 0; j < 16; j++){
				if (nodeGrid[i][j].node_type != Board[i][j]) nodeGrid[i][j].update(Board[i][j]);
			}
		}
	}
	
	
	class NodePanel extends PaintedPanel {

		private int node_type=-1;
		
		NodePanel(int node_type) {		
			super(null);
			this.node_type=node_type;
			setLayout(new GridBagLayout());
			
			if(node_type == 0){
				//road
				setImage(BMLibrary.getImages("road"));
				
			}
			else if(node_type == 1){
				//wall
				setImage(BMLibrary.getImages("wall"));

			}
			else if(node_type == 2){
				//tile
				setImage(BMLibrary.getImages("tile"));

			}
			else if(node_type == 3){
				//bomb
				setImage(BMLibrary.getImages("bomb"));

			}
			else if(node_type == 4){
				//bombing
				setImage(BMLibrary.getImages("bombing"));

			}
			else if(node_type == 5){
				//niceShoes
				setImage(BMLibrary.getImages("niceShoes"));

			}
			else if(node_type == 6){
				//badShoes
				setImage(BMLibrary.getImages("badShoes"));

			}
			else if(node_type == 7){
				//improvePower
				setImage(BMLibrary.getImages("improvePower"));

			}
			else if(node_type == 8){
				//reducePower
				setImage(BMLibrary.getImages("reducePower"));

			}
			else if(node_type == 9){
				//reduceCoolingTime
				setImage(BMLibrary.getImages("reduceCoolingTime"));

			}
			else if(node_type == 10){
				//increaseCoolingTime
				setImage(BMLibrary.getImages("increaseCoolingTime"));

			}
			else if(node_type == 11){
				//increaseDetonatedTime
				setImage(BMLibrary.getImages("increaseDetonatedTime"));

			}
			else if(node_type ==12){
				//reduceDenotatedTime
				setImage(BMLibrary.getImages("reduceDenotatedTime"));
			}
			revalidate();
			repaint();

		}

		
		public void update(int new_type){
			node_type =new_type;
			if(new_type == -1) return;
			if(new_type == 0){
				//road
				setImage(BMLibrary.getImages("road"));
				
			}
			if(new_type == 1){
				//wall
				setImage(BMLibrary.getImages("wall"));

			}
			if(new_type == 2){
				//tile
				setImage(BMLibrary.getImages("tile"));

			}
			if(new_type == 3){
				//bomb
				setImage(BMLibrary.getImages("bomb"));

			}
			if(new_type == 4){
				//bombing
				setImage(BMLibrary.getImages("bombing"));

			}
			if(new_type == 5){
				//niceShoes
				setImage(BMLibrary.getImages("niceShoes"));

			}
			if(new_type == 6){
				//badShoes
				setImage(BMLibrary.getImages("badShoes"));

			}
			if(new_type == 7){
				//improvePower
				setImage(BMLibrary.getImages("improvePower"));

			}
			if(new_type == 8){
				//reducePower
				setImage(BMLibrary.getImages("reducePower"));

			}
			if(new_type == 9){
				//reduceCoolingTime
				setImage(BMLibrary.getImages("reduceCoolingTime"));

			}
			if(new_type == 10){
				//increaseCoolingTime
				setImage(BMLibrary.getImages("increaseCoolingTime"));

			}
			if(new_type == 11){
				//increaseDetonatedTime
				setImage(BMLibrary.getImages("increaseDetonatedTime"));

			}
			if(new_type ==12) {
				//reduceDenotatedTime
				setImage(BMLibrary.getImages("reduceDenotatedTime"));

			}
		
			revalidate();
			repaint();
		}
		
		
		
	}
}