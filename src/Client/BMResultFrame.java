package Client;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Dictionary;
import java.util.TreeMap;
import java.util.Vector;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class BMResultFrame  extends JFrame{
	public BMResultFrame( Vector<TreeMap<String, Object>> result, ActionListener playingGame)
	{

		setTitle("Bomberman Result");
		setSize(640, 480);
		setLocationRelativeTo(null);

		setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
		
		JPanel topP = new JPanel();
		topP.add(new JLabel("top"));
		JPanel midP = new JPanel();
		midP.setLayout(new BoxLayout(midP, BoxLayout.X_AXIS));
		System.out.println(result);

		for (int i=0; i< result.size(); i++) {
			JPanel single =new JPanel();
			
			single.setLayout(new BoxLayout(single, BoxLayout.Y_AXIS));
			JLabel one = new JLabel ((String)result.get(i).get("username") + (int)result.get(i).get("points"));
			JLabel two = new JLabel ((int)result.get(i).get("Kill") +"kill ");
			JLabel three = new JLabel ((int)result.get(i).get("death") +"death");
			JLabel four = new JLabel ((int)result.get(i).get("item") +"items");
			single.add(one);
			single.add(two);
			single.add(three);
			single.add(four);
			midP.add(single);
			
		}
		JPanel btmP = new JPanel();
		JButton okButton = new JButton("OK");
		btmP.add(okButton);
		add(topP);
		add(midP);
		add(btmP);
		okButton.addActionListener( playingGame);
		okButton.addActionListener(new ActionListener (){

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				Component component = (Component) e.getSource();
		        JFrame frame = (JFrame) SwingUtilities.getRoot(component);
		        frame.setVisible(false);
			}
			
		});
		
		
		
		final long serialVersionUID = 5147395078473323173L;		
		final Dimension minSize = new Dimension(320,480);
		
		
	}

}
