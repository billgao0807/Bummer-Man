package Client;

import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import customUI.PaintedButton;
import customUI.PaintedPanel;

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
	
	BMRoomPanel(ActionListener startP, ActionListener startB, ActionListener quit)
	{
		super(null, true);
		time = new JLabel("Time:");
		HP = new JLabel("HP:");
		
		String[] t = new String[]{"1 min", "2 min", "30 secs"};
		timing = new JComboBox<>(t);
		Integer[] hp = new Integer[]{1,2,3};
		HPCounting = new JComboBox<>(hp);
		
		timeP.add(time);
		timeP.add(timing);		
		HPP.add(HP);
		HPP.add(HPCounting);
		
		start =  new PaintedButton("Start", null ,null, 20);		
		startPanel.add(start);
		
		startAI = new PaintedButton("Start(AI)", null, null, 20);
		startAIP.add(startAI);
		quitG = new PaintedButton("Quit", null, null, 20);
		quitP.add(quitG);
		
		setLayout(new GridLayout(0,3));
		//temp stands for the player pic
		JLabel temp = new JLabel("Player");
		add(temp);
		
		JPanel mid = new JPanel();
		mid.setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridy = 1;
		mid.add(timeP,gbc);
		gbc.gridy = 2;
		mid.add(HPP,gbc);
		gbc.gridy = 3;
		mid.add(startPanel,gbc);
		gbc.gridy = 4;
		mid.add(startAIP,gbc);
		gbc.gridy = 5;
		mid.add(quitP,gbc);
		add(mid);
		
		
	}

}
