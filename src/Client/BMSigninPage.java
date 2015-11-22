package Client;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

import Utilities.BMFontLibrary;
import Utilities.BMLibrary;
import customUI.ClearPanel;
import customUI.PaintedButton;
import customUI.PaintedPanel;


public class BMSigninPage extends JFrame{
private static final long serialVersionUID = 5147395078473323173L;
	
	private final static Dimension minSize = new Dimension(320,480);
//	private final static Dimension maxSize = new Dimension(960,640);
	public JTextField nameInput;
	public JTextField passwordInput;
	
	public BMSigninPage(ActionListener signup, ActionListener quickG, ActionListener login)
	{
		setTitle("Bomberman Sign In");
		setSize(minSize);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		signIn si = new signIn(signup, quickG, login, BMLibrary.readImages("frame0.png"));
		add(si);
		

	}
class signIn extends PaintedPanel
{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private PaintedButton signupB;
	private PaintedButton quickGameB;
	private PaintedButton loginB;
	signIn(ActionListener signup, ActionListener quickG, ActionListener login, Image image)
	{
		super(image, true);
		setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		setVisible(true);
		
		nameInput = new JTextField();
		nameInput.setText("Username: ");
		nameInput.setForeground(Color.GRAY);
		nameInput.setPreferredSize(new Dimension(100, 20));
		nameInput.setFont(BMFontLibrary.getFont("font.ttf", Font.PLAIN, 13));
		nameInput.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseClicked(MouseEvent e){
                nameInput.setText("");
            }
        });
		JPanel userName = new JPanel();
		userName.setOpaque(false);
		userName.add(nameInput);
		
		passwordInput = new JTextField();
		passwordInput.setPreferredSize(new Dimension(12,30));
//		passwordInput.setFont(f);
		passwordInput.setText("Password:");
		passwordInput.setForeground(Color.GRAY);
	//	passwordInput.setFont();
		passwordInput.setFont(BMFontLibrary.getFont("font.ttf", Font.PLAIN, 13));

		passwordInput.setPreferredSize(new Dimension(100, 20));
		passwordInput.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseClicked(MouseEvent e){
                passwordInput.setText("");
            }
        });
		JPanel password = new JPanel();
		password.setLayout(new GridBagLayout());
		GridBagConstraints gbc1 = new GridBagConstraints();
		gbc1.gridx = 0;
		gbc1.gridy = 0;
		password.setOpaque(false);
		password.add(passwordInput, gbc1);
		
		signupB = new PaintedButton(
				"Start",
				BMLibrary.readImages("button0.png"),
				BMLibrary.readImages("button0-0.png"),
				22
				);
		signupB.addActionListener(signup);
		//signupB.setFont(BMFontLibrary.getFont("font.ttf", Font.PLAIN, 20));
		JPanel signupP = new JPanel();
		signupP.add(new ClearPanel());
		signupP.add(signupB);
		signupP.setOpaque(false);
		quickGameB = new PaintedButton("Quick Game", BMLibrary.readImages("button0.png") , BMLibrary.readImages("button0-0.png"), 18);
		quickGameB.addActionListener(quickG);
		quickGameB.setFont(BMFontLibrary.getFont("font.ttf", Font.PLAIN, 20));
		JPanel quickGame = new JPanel();
		quickGame.setOpaque(false);
		quickGame.add(quickGameB);
		loginB = new PaintedButton("Log In", BMLibrary.readImages("button0.png") , BMLibrary.readImages("button0-0.png"), 18);
		//loginB.addActionListener(
			//	);
		loginB.addActionListener(login);
		loginB.setFont(BMFontLibrary.getFont("font.ttf", Font.PLAIN, 20));
		JPanel loginP = new JPanel();
		loginP.setOpaque(false);
		loginP.add(loginB);
		
		gbc.gridy = 1;
		add(userName,gbc);
		gbc.gridy = 2;
		add(password,gbc);
		gbc.gridy = 3;
		add(quickGame,gbc);
		gbc.gridy = 4;
		add(signupP,gbc);
		gbc.gridy = 5;
		add(loginP,gbc);
		
	}
}
public void closeMe() {
	this.setVisible(false);
	
}
}
