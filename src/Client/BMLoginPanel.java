package Client;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import customUI.PaintedButton;
import customUI.PaintedPanel;

public class BMLoginPanel extends PaintedPanel{
	
	private JLabel title;
	private JLabel username;
	private JLabel password;
	private JTextField nameInput;
	private JTextField passwordInput;
	private JButton signupB;
	private JButton quickGameB;
	private JButton loginB;
	private JPanel titlePanel = new JPanel();
	private JPanel userPanel = new JPanel();
	private JPanel passwordPanel = new JPanel();
	private JPanel buttonPanel  = new JPanel();
	
	
	BMLoginPanel (ActionListener signup, ActionListener quickG, ActionListener login)
	{
		super(null,true);
		title = new JLabel("BomberMan");
		titlePanel.add(title);
		
		username = new JLabel("Username:");
		userPanel.add(username);
		nameInput = new JTextField();
		nameInput.setPreferredSize(new Dimension(200,20));
		userPanel.add(nameInput);
		
		password = new JLabel("Password:");
		passwordPanel.add(password);
		passwordInput = new JTextField();
		passwordInput.setPreferredSize(new Dimension(200,20));
		passwordPanel.add(passwordInput);
		
		signupB = new PaintedButton("Sign up", null, null, 20);
		signupB.addActionListener(signup);
		quickGameB = new PaintedButton("Quick Game", null, null, 20);
		quickGameB.addActionListener(quickG);
		loginB = new PaintedButton("Log In", null, null, 20);
		loginB.addActionListener(login);
		buttonPanel.add(signupB);
		buttonPanel.add(quickGameB);
		buttonPanel.add(loginB);
		
		setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		//gbc.insets = new Insets(40,40,40,40);
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.gridy = 1;
		gbc.gridy = 1;
		add(titlePanel,gbc);
		gbc.ipadx = 100;
		gbc.ipady = 25;
		gbc.fill = GridBagConstraints.NONE;
		gbc.gridy = 2;
		add(userPanel,gbc);
		gbc.ipadx = 100;
		gbc.ipady = 25;
		gbc.fill = GridBagConstraints.NONE;
		gbc.gridy = 3;
		add(passwordPanel,gbc);
		gbc.gridy = 4;
		add(buttonPanel,gbc);
	}

}
