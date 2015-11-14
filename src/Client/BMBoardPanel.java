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
import java.util.Stack;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.SwingUtilities;

import BMNode.BMTile;
import customUI.PaintedButton;
import customUI.PaintedLabel;
import customUI.PaintedPanel;







public class BMBoardPanel extends JPanel{
	private final static int boardSize = 16;
	//private final TilePanel[][] tileGrid;

	private PaintedPanel chatPanel, boardPanel, playerPanel;
	private JTextPane chatPane;
	private JTextField chatTF;
  	private final NodePanel[][] nodeGrid;

	private PaintedButton chatButton;
	private JLabel TimeLabel, HPLabel,AbilityLabel;
	private PaintedButton SpeedButton, PowerButton, Item1Button, Item2Button , QuitButton;
	private int[][]map;
	private KeyListener keylistener;
	private ClientListener clientListener;
	
	public BMBoardPanel(ActionListener playingGame, Image inImage, ClientListener clientListener, int[][] map ){

//Panel initialize
		this.clientListener = clientListener;
		this.map=map;
		setSize(1000,600);
		this.setLayout(new BorderLayout());
		chatPanel= new PaintedPanel(null);
		boardPanel = new PaintedPanel(null);
		playerPanel = new PaintedPanel(null);
		chatPanel.setPreferredSize(new Dimension(150, BMBoardPanel.this.getHeight()));
		keylistener = null;
		
//boardPanel initialize
		boardPanel.setLayout(new GridLayout(boardSize,boardSize));
		for(int y = 0; y < boardSize; ++y) {
			for(int x = 0; x < boardSize; ++x) {
				if(map[x][y]==1) {
					nodeGrid[x][y] = new WallPanel(1); }
				else if (map[x][y]==2) {
					nodeGrid[x][y] = new TilePanel(2);}
				else if (map[x][y]==0) {
					nodeGrid[x][y] = new NodePanel (0);
				}
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
		TimeLabel = new JLabel("Time: ");
		HPLabel = new JLabel("HP 1/3");
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
		
		
//add all panels		
		this.add(chatPanel, BorderLayout.WEST);
		this.add(boardPanel, BorderLayout.CENTER);
		this.add(playerPanel, BorderLayout.EAST);
		addAction();
		redraw();

	}
	
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
	public void redraw() {
		for(NodePanel row[] : nodeGrid) {
			for(NodePanel tp : row) {
				tp.update();
			}
		}
		revalidate();
		repaint();
	}
	
	public void endGame(){
		
	}
	
	
	
	class NodePanel extends PaintedPanel {
		//private final Stack<Component> components;
		
		private JPanel pawn = new JPanel();
		private boolean pawnDisplayed = false;	
		private int node_type=-1;
	
		NodePanel(int node_type) {
		
			super(null);
			this.node_type=node_type;
			setLayout(new GridBagLayout());
			//components = new Stack<Component>();
	//check what the tile is
//			if(mTile != null) {
//				
//				if(mTile.isWall()) {
//					JLabel wall = new JLabel("Wall");
//					components.push(wall);
//			
//				}
//				else if(mTile.isTile()) {
//					JLabel tile_label = new JLabel("Tile");
//					components.push(tile_label);
//					if(mTile.hasItem()){
//						//tile contains a item
//						
//					}
//				} 
//				
//			}
		}

		
		
//		protected void update() {
//			if(node_type == null) return;
//			if(node_type.isOccupied() && !pawnDisplayed) {
//				pawnDisplayed = true;
//				//pawn.setImage(ImageLibrary.getImage("images/pawns/"+GameHelpers.getNameFromColor(mTile.getPawnColor())+"_pawn.png"));
//				components.push(pawn);
//			}
//			if(node_type.isOccupied() && pawnDisplayed) {
//				//pawn.setImage(ImageLibrary.getImage("images/pawns/"+GameHelpers.getNameFromColor(mTile.getPawnColor())+"_pawn.png"));
//			}
//			if(!mTile.isOccupied() && pawnDisplayed) {
//				pawnDisplayed = false;
//				components.pop();
//			}
//			removeAll();
//			if(!components.isEmpty())add(components.peek());
//		}
		
		public void update(){
			if(node_type == -1) return;
			if(node_type == 0){
				//road
				
			}
			if(node_type == 1){
				//wall
			}
			if(node_type == 2){
				
			}
			if(node_type == 3)
			if(node_type == 4)
			if(node_type == 5)
			if(node_type == 5)

		}
		
		
		class  WallPanel extends NodePanel{

			JLabel mLabel;
			{
				mLabel = new PaintedLabel(null);
				add(mLabel);
			}
			WallPanel() {
				super(null);
			}
			@Override
			protected void update() {
//change the image
			}
		}
		
		//Used for the home counter display
		class  TilePanel extends NodePanel{


			JLabel mLabel;
			{
				mLabel = new PaintedLabel(null);
				add(mLabel);
			}
			TilePanel( ) {
				super(null);
			}
			@Override
			protected void update() {
//change the image
	
			}
		}
		
	}

	//class PlayerPanel ext
}