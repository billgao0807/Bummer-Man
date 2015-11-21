package Client;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Scanner;
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
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.SwingConstants;
import java.awt.Font;
public class BMLoginPanel extends PaintedPanel implements KeyListener{
	
	
	public JTextField nameInput;
	private JTextField passwordInput;
	private JButton signupB;
	private JButton quickGameB;
	private JButton loginB;
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
		Image buttonImage = null;//BMLibrary.readImages(BMLibrary.path+"button.png");
		setLayout(new BorderLayout(0, 0));
		

		
		panel_1 = new JPanel();
		panel_1.setOpaque(false);
		add(panel_1, BorderLayout.SOUTH);
		
		label = new JLabel("Press Any Key To Proceed");
		panel_1.add(label);
		label.setFont(new Font("Lucida Grande", Font.PLAIN, 30));
		label.setHorizontalAlignment(SwingConstants.CENTER);
		
		
	
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
		 BMSigninPage signin = new BMSigninPage();
		signin.setVisible(true);
		
	}
	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		System.out.println("Hello3");
		
	}
}
