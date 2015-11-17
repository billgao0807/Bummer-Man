package Client;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.event.ActionListener;

import javax.swing.AbstractButton;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import customUI.PaintedButton;
import customUI.PaintedPanel;

public class BMMenuPanel extends PaintedPanel{
	private JButton start;
	private JButton joinG;
	private JButton ranking;
	private PaintedPanel b1 = new PaintedPanel(null);
	private PaintedPanel b2 = new PaintedPanel(null);
	private PaintedPanel b3 = new PaintedPanel(null);
	
	
	
	BMMenuPanel(ActionListener host, ActionListener join, ActionListener rank, Image image)
	{
		super(image, true);
		start = new PaintedButton("Start", null, null, 20);
		
		start.addActionListener(host);
		b1.add(start);
		joinG = new PaintedButton("Join", null, null, 20);
		joinG.addActionListener(join);
		b2.add(joinG);
		ranking = new PaintedButton ("Ranking", null, null, 20);
		ranking.addActionListener(rank);
		b3.add(ranking);
		setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridy = 2;
		add(b1,gbc);
		gbc.gridy = 3;
		add(b2,gbc);
		gbc.gridy = 4;
		add(b3,gbc);
	}
	}


