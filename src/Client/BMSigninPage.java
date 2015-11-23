package Client;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;

import Utilities.BMFontLibrary;

import javax.swing.SwingConstants;

public class BMSigninPage extends JFrame{
	private static final long serialVersionUID = 5147395078473323173L;

	private final static Dimension minSize = new Dimension(320,480);
		private final static Dimension maxSize = new Dimension(960,640);
	public JTextField txtUsername;
	public JTextField txtPassword;


	public BMSigninPage(ActionListener quickG, ActionListener signup, ActionListener login)
	{
		setTitle("Bomberman Sign In");
		setSize(minSize);
		setResizable(false);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		txtUsername = new JTextField(SwingConstants.CENTER);
		txtUsername.setFont(BMFontLibrary.getFont("font2.otf", Font.PLAIN, 20));
		txtUsername.setHorizontalAlignment(SwingConstants.CENTER);
		txtUsername.setText("Username");
		txtUsername.setColumns(10);
		txtUsername.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseClicked(MouseEvent e){
                txtUsername.setText("");
            }
        });
		
		txtPassword = new JTextField(SwingConstants.CENTER);
		txtPassword.setFont(BMFontLibrary.getFont("font2.otf", Font.PLAIN, 20));
		txtPassword.setHorizontalAlignment(SwingConstants.CENTER);
		txtPassword.setText("Password");
		txtPassword.setColumns(10);
		txtPassword.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseClicked(MouseEvent e){
                txtPassword.setText("");
            }
        });
		
		JButton btnQuickgame = new JButton("QuickGame");
		btnQuickgame.addActionListener(quickG);
		btnQuickgame.setFont(BMFontLibrary.getFont("font2.otf", Font.PLAIN, 20));
		
		JButton btnSignup = new JButton("Signup");
		btnSignup.addActionListener(signup);
		btnSignup.setFont(BMFontLibrary.getFont("font2.otf", Font.PLAIN, 20));
		btnSignup.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		
		JButton btnLogin_1 = new JButton("Login");
		btnLogin_1.addActionListener(login);
		btnLogin_1.setFont(BMFontLibrary.getFont("font2.otf", Font.PLAIN, 20));
		GroupLayout groupLayout = new GroupLayout(getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(Alignment.TRAILING, groupLayout.createSequentialGroup()
					.addGap(60)
					.addComponent(btnQuickgame, GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
					.addGap(60))
				.addGroup(Alignment.TRAILING, groupLayout.createSequentialGroup()
					.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(105)
							.addComponent(btnSignup, GroupLayout.PREFERRED_SIZE, 24, Short.MAX_VALUE))
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(105)
							.addComponent(btnLogin_1, GroupLayout.DEFAULT_SIZE, 110, Short.MAX_VALUE)))
					.addGap(105))
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(100)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(txtPassword, GroupLayout.DEFAULT_SIZE, 134, Short.MAX_VALUE)
							.addGap(100))
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(txtUsername, GroupLayout.DEFAULT_SIZE, 134, Short.MAX_VALUE)
							.addGap(100))))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(69)
					.addComponent(txtUsername, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addGap(18)
					.addComponent(txtPassword, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED, 125, Short.MAX_VALUE)
					.addComponent(btnQuickgame)
					.addGap(18)
					.addComponent(btnSignup)
					.addGap(18)
					.addComponent(btnLogin_1)
					.addGap(67))
		);
		getContentPane().setLayout(groupLayout);



		//		signIn si = new signIn(signup, quickG, login, BMLibrary.readImages("background4.png"));
		//		add(si);


	}
}
