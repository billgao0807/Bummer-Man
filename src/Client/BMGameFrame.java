package Client;

import java.awt.Dimension;

import javax.swing.JFrame;

import Client.BMLoginPanel;

public class BMGameFrame extends JFrame{
private static final long serialVersionUID = 5147395078473323173L;
	
	private final static Dimension minSize = new Dimension(640,480);
	private final static Dimension maxSize = new Dimension(960,640);
	
	{
		setTitle("Bomberman");
		setSize(minSize);
		setMinimumSize(minSize);
		setMaximumSize(maxSize);
		add(new BMClientPanel());
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
	
	public static void main(String[] args) {
		BMGameFrame bmgf = new BMGameFrame();
		bmgf.setVisible(true);
	}
}
