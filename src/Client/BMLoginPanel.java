package Client;

<<<<<<< HEAD
=======
import java.awt.BorderLayout;
>>>>>>> Ellen
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
<<<<<<< HEAD
=======
import java.awt.GridLayout;
>>>>>>> Ellen
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
<<<<<<< HEAD
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
=======
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Scanner;
>>>>>>> Ellen

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import Utilities.BMLibrary;
import customUI.PaintedButton;
import customUI.PaintedPanel;


<<<<<<< HEAD
public class BMLoginPanel extends PaintedPanel{
=======
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.SwingConstants;
import java.awt.Font;


public class BMLoginPanel extends PaintedPanel implements KeyListener{
>>>>>>> Ellen
	
	private JLabel title;
	private JLabel username;
	private JLabel password;
	public JTextField nameInput;
	private JTextField passwordInput;
	private JButton signupB;
	private JButton quickGameB;
	private JButton loginB;
<<<<<<< HEAD
	private JPanel titlePanel = new JPanel();
	private PaintedPanel userPanel = new PaintedPanel(null);
	private PaintedPanel passwordPanel = new PaintedPanel(null);
	private PaintedPanel buttonPanel  = new PaintedPanel(null);
	
	
	BMLoginPanel (ActionListener signup, ActionListener quickG, ActionListener login, Image image)
	{
		super(image,true);
		Image titleImage = BMLibrary.getImage("images/logo.png");
		PaintedPanel titlePanel = new PaintedPanel(titleImage);		
		nameInput = new JTextField("UserName");
		nameInput.setForeground(Color.GRAY);
		nameInput.setPreferredSize(new Dimension(200,20));	
		nameInput.addMouseListener(new MouseAdapter(){
			   public void mouseReleased(MouseEvent e) {
				    //some stuff
				   nameInput.setText("");
				   }
				});
		userPanel.add(nameInput);		
		passwordInput = new JTextField("Password");
		passwordInput.setForeground(Color.GRAY);
		passwordInput.setPreferredSize(new Dimension(200,20));
		passwordInput.addMouseListener(new MouseAdapter(){
			   public void mouseReleased(MouseEvent e) {
				    //some stuff
				   passwordInput.setText("");
				   }
				});

			

		passwordPanel.add(passwordInput);
=======
	private PaintedPanel titlePanel = new PaintedPanel(null);
	private PaintedPanel passwordPanel = new PaintedPanel(null);
	private JPanel panel;
	private JLabel label;
	private JPanel panel_1;
	
	BMLoginPanel (ActionListener signup, ActionListener quickG, ActionListener login, Image image)
	{
		super(image);
		this.setFocusable(true);
		this.requestFocusInWindow();
		this.addKeyListener(this);
		Image buttonImage = BMLibrary.readImages("bin/Utilities/images/button.png");
		setLayout(new BorderLayout(0, 0));
		
		panel = new JPanel();
		panel.setOpaque(false);
		add(panel, BorderLayout.NORTH);
		panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
		
		nameInput = new JTextField();
		panel.add(nameInput);
		nameInput.setText("Username: ");
		nameInput.setForeground(Color.GRAY);
		nameInput.setPreferredSize(new Dimension(100, 20));
		
		
		passwordInput = new JTextField();
		panel.add(passwordInput);
		passwordInput.setText("Password:");
		passwordInput.setForeground(Color.GRAY);
		passwordInput.setPreferredSize(new Dimension(100, 20));

		panel.add(Box.createHorizontalStrut(100));
		signupB = new PaintedButton("Sign up", buttonImage, buttonImage, 20);
		panel.add(signupB);
		quickGameB = new PaintedButton("Quick Game", buttonImage, buttonImage, 20);
		panel.add(quickGameB);
		loginB = new PaintedButton("Log In", buttonImage, buttonImage, 20);
		panel.add(loginB);
>>>>>>> Ellen
		
		panel_1 = new JPanel();
		panel_1.setOpaque(false);
		add(panel_1, BorderLayout.SOUTH);
		
		label = new JLabel("Press Any Key To Proceed");
		panel_1.add(label);
		label.setFont(new Font("Lucida Grande", Font.PLAIN, 30));
		label.setHorizontalAlignment(SwingConstants.CENTER);
		loginB.addActionListener(login);
		quickGameB.addActionListener(quickG);
		signupB.addActionListener(signup);
		
		/*
		Scanner keyboard = new Scanner(System.in);
		if (keyboard.nextLine() != null)
		{
			JDialog jd = new JDialog();
			jd.setLocationRelativeTo(this);
			jd.setSize(400,150);
			jd.setLocation(400,250);
			jd.setModal(true);	
			jd.setLayout(new GridBagLayout());
			
			
		}
		*/
		
		new Thread(new Runnable(){
			@Override
			public void run() {
				int color = 0;
				boolean add = true;
				while (true){
					try {
						Thread.sleep(10);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						System.out.println(e.getMessage());
					}
					if (add == true) color++;
					else color--;
					BMLoginPanel.this.label.setForeground(new Color(color,255-color,color));
					if (color <= 0) add = true;
					else if (color >= 255) add = false;
				}
			}			
		}).start();
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		System.out.println("Hello1");
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		System.out.println("Hello2");
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		System.out.println("Hello3");
		
<<<<<<< HEAD
		setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.ipadx = titleImage.getWidth(null);
		gbc.ipady = titleImage.getHeight(null);
		gbc.insets = new Insets(20,20,20,20);
		gbc.fill = GridBagConstraints.HORIZONTAL;
		
		gbc.gridy = 1;
		add(titlePanel,gbc);
		gbc.ipadx = 180;
		gbc.ipady = 25;
		gbc.fill = GridBagConstraints.NONE;
		gbc.gridy = 2;
		add(userPanel,gbc);
		gbc.ipadx = 180;
		gbc.ipady = 25;
		gbc.fill = GridBagConstraints.NONE;
		gbc.gridy = 3;
		add(passwordPanel,gbc);
		gbc.gridy = 4;
		add(buttonPanel,gbc);
=======
>>>>>>> Ellen
	}

}
