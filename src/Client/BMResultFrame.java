package Client;

import java.awt.Dimension;
import java.util.Dictionary;
import java.util.Vector;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class BMResultFrame  extends JFrame{
	public BMResultFrame( Vector<Dictionary> result)
	{
		setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
		
		JPanel topP = new JPanel();
		topP.add(new JLabel("top"));
		
		JPanel midP = new JPanel();
		midP.setLayout(new BoxLayout(midP, BoxLayout.X_AXIS));
		
		for (int i=0; i< result.size(); i++) {
			JPanel single =new JPanel();
			
			
		}
		JPanel btmP = new JPanel();
		
		add(topP);
		add(midP);
		add(btmP);
		
		final long serialVersionUID = 5147395078473323173L;		
		final Dimension minSize = new Dimension(320,480);
		
		
		
	}

}
