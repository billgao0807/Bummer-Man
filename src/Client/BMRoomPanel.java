package Client;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import Utilities.BMLibrary;
import customUI.PaintedButton;
import customUI.PaintedLabel;
import customUI.PaintedPanel;
import java.awt.Insets;
import javax.swing.SwingConstants;
import java.awt.FlowLayout;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;

public class BMRoomPanel extends PaintedPanel{
	private JLabel time;
	private JLabel HP;
	private JComboBox<String> timing;
	private JComboBox<Integer> HPCounting;
	private JButton start;
	private JButton startAI;
	private JButton quitG;
	private JPanel timeP = new JPanel();
	private JPanel HPP = new JPanel();
	private JPanel startPanel = new JPanel();
	private JPanel startAIP = new JPanel();
	private JPanel quitP = new JPanel();
	private boolean identity;
	private PaintedPanel player0;
	private PaintedPanel player1;
	private PaintedPanel player2;
	private PaintedPanel player3;
	
	BMRoomPanel(boolean identity,ActionListener startP, ActionListener startB, ActionListener quit,Image image)
	{
		super(image, true);
		time = new JLabel("Time:");
		HP = new JLabel("HP:");
		this.identity = identity;
		String[] t = new String[]{"1 min", "2 min", "30 secs"};
		//timing = new JComboBox<>(t);
		Integer[] hp = new Integer[]{1,2,3};
		//HPCounting = new JComboBox<>(hp);
//		
//		timeP.add(time);
//		timeP.add(timing);	
//		timeP.setOpaque(false);
//		HPP.add(HP);
//		HPP.add(HPCounting);
//		HPP.setOpaque(false);
		
		start =  new PaintedButton("Start", null ,null, 20);		
		startPanel.add(start);
		start.addActionListener(startP);
		startPanel.setOpaque(false);
		
		startAI = new PaintedButton("Start(AI)", null, null, 20);
		startAIP.add(startAI);
		startAI.addActionListener(startB);
		startAIP.setOpaque(false);
		
		quitG = new PaintedButton("Quit", null, null, 20);
		quitP.add(quitG);
		quitG.addActionListener(quit);
		quitP.setOpaque(false);
		setLayout(new GridLayout(0, 3, 0, 0));
		
		
		JPanel left = new JPanel();
		left.setOpaque(false);
		add(left);
		left.setLayout(new BoxLayout(left, BoxLayout.Y_AXIS));
		left.setBounds(0, 0, this.getWidth()/3, this.getHeight()/2);
		
		player0 = new PaintedPanel(BMLibrary.readImages("player0.png"));
		left.add(player0);
		player0.setLayout(new GridLayout(1, 0, 0, 0));
		
		player1 = new PaintedPanel(BMLibrary.readImages("player1.png"));
		left.add(player1);
		
		JPanel mid = new JPanel();
		mid.setOpaque(false);
		add(mid);
		
		JComboBox comboBox = new JComboBox();
		
		JComboBox comboBox_1 = new JComboBox();
		
		JButton btnPlay = new JButton("Play");
		
		JButton btnPlayai = new JButton("Play(AI)");
		GroupLayout gl_mid = new GroupLayout(mid);
		gl_mid.setHorizontalGroup(
			gl_mid.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_mid.createSequentialGroup()
					.addGap(30)
					.addComponent(comboBox, 0, 90, Short.MAX_VALUE)
					.addGap(30))
				.addGroup(gl_mid.createSequentialGroup()
					.addGap(30)
					.addComponent(comboBox_1, 0, 90, Short.MAX_VALUE)
					.addGap(30))
				.addGroup(gl_mid.createSequentialGroup()
					.addGap(37)
					.addComponent(btnPlay, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
					.addGap(38))
				.addGroup(gl_mid.createSequentialGroup()
					.addGap(30)
					.addComponent(btnPlayai, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
					.addGap(30))
		);
		gl_mid.setVerticalGroup(
			gl_mid.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_mid.createSequentialGroup()
					.addGap(24)
					.addComponent(comboBox, GroupLayout.DEFAULT_SIZE, 27, Short.MAX_VALUE)
					.addGap(5)
					.addComponent(comboBox_1, GroupLayout.DEFAULT_SIZE, 27, Short.MAX_VALUE)
					.addGap(125)
					.addComponent(btnPlay, GroupLayout.DEFAULT_SIZE, 29, Short.MAX_VALUE)
					.addGap(5)
					.addComponent(btnPlayai, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
					.addGap(29))
		);
		mid.setLayout(gl_mid);
		
		
		JPanel right = new JPanel();
		right.setOpaque(false);
		add(right);
		right.setLayout(new BoxLayout(right, BoxLayout.Y_AXIS));
		right.setBounds(0, 0, this.getWidth()/3, this.getHeight()/2);
		
		player2 = new PaintedPanel(BMLibrary.readImages("player2.png"));
		right.add(player2);
		player2.setLayout(new GridLayout(1, 0, 0, 0));
		
		player3 = new PaintedPanel(BMLibrary.readImages("player3.png"));
		right.add(player3);
		
		
		
		
		
		
	}
}
