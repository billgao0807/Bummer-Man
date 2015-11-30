package Client;

/*
 * Zhongyang Gao zhongyag@usc.edu
 * Jiahui Wei jiahuiwe@usc.edu
 * Zhongzhen Yang zhongyanz@usc.edu
 * Yiqing Xu yiqingxu@usc.edu
 * Brandon Keiji Horton bkhorton@usc.edu
 * Cheuk Yin Matthew Lam cheukyin@usc.edu
 * 
 * 
 * 
 * 
 * 
 */


import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import Utilities.BMLibrary;
import Utilities.Help;
import Utilities.MusicLibrary;
public class BMGameFrame extends JFrame implements KeyListener{
private static final long serialVersionUID = 5147395078473323173L;
	
	private final static Dimension minSize = new Dimension(640,480);
	private final static Dimension maxSize = new Dimension(960,640);
	private static JMenuBar menuBar;
	private static Cursor cursor;
	private static Cursor cursor2;

	//new comment
	{
		setTitle("Bomberman");
		setSize(minSize);
//		setMinimumSize(minSize);
//		setMaximumSize(maxSize);
//		this.setFocusable(true);
//		this.requestFocusInWindow();
		add(new BMClientPanel());
		setLocationRelativeTo(null);
		menuBar = new JMenuBar();
		Toolkit toolkit = Toolkit.getDefaultToolkit();
    	Image cursorImg =  BMLibrary.readImages("mouse2.png");
    	cursor = toolkit.createCustomCursor(cursorImg,new Point(0,0),"hand");
    	
    	Image cursorImg2 =  BMLibrary.readImages("mouse1.png");
    	cursor2 = toolkit.createCustomCursor(cursorImg2,new Point(0,0),"hand2");
    	
    	JMenuItem help = new JMenuItem("Help");
    	//help.setMnemonic('H');
    	//help.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_H,ActionEvent.CTRL_MASK));
    	help.addActionListener(new ActionListener() {
    		@Override
    		public void actionPerformed(ActionEvent ae) {
    			Help.display();
    		}
    	});
    	menuBar.add(help);
//		this.addKeyListener(this);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
	
	public static void main(String[] args) {
		BMGameFrame bmgf = new BMGameFrame();
		//MusicLibrary.startGame();
		bmgf.setJMenuBar(menuBar);
		bmgf.setVisible(true);
		bmgf.setCursor(cursor);
		bmgf.addMouseListener(new MouseAdapter(){
			public void mousePressed(MouseEvent e){
				bmgf.setCursor(cursor2);				
				
			}
			public void mouseReleased(MouseEvent e){
				bmgf.setCursor(cursor);

				}

			
		});
		

	}
	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		System.out.println("type");
	}
	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		System.out.println("press");
	}
	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		System.out.println("Release");
	}
	
	
	
}