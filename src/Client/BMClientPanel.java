package Client;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JPanel;

public class BMClientPanel extends JPanel{
	private static final long serialVersionUID = 6415716059554739910L;
	private BMLoginPanel loginPanel;
	private BMMenuPanel menuPanel;
	private BMRoomPanel roomPanel;
	private BMRankPanel rankPanel;
	{
		System.out.println("enter login");
		loginPanel = new BMLoginPanel(new ActionListener() {
			@Override
			
			public void actionPerformed(ActionEvent ae) {
				BMClientPanel.this.removeAll();
				/*Signup
				BMClientPanel.this.add(roomPanel);
				*/
				BMClientPanel.this.revalidate();
				BMClientPanel.this.repaint();
			}
		},
				new ActionListener(){
				@Override
			public void actionPerformed(ActionEvent ae) {
				BMClientPanel.this.removeAll();
				/*QuickGame
				BMClientPanel.this.add(roomPanel);
				*/
				BMClientPanel.this.revalidate();
				BMClientPanel.this.repaint();
			}
		},
				new ActionListener(){
				@Override				
			public void actionPerformed(ActionEvent ae) {
					/*add check the correctness of the username and password*/
				BMClientPanel.this.removeAll();				
				BMClientPanel.this.add(menuPanel);
				BMClientPanel.this.revalidate();
				BMClientPanel.this.repaint();
			}});
		
		
		//Set up the panel to display
				setLayout(new BorderLayout());
				add(loginPanel);
				refreshComponents();
	}
	private void refreshComponents()
	{
		menuPanel = new BMMenuPanel(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				//host the game
				BMClientPanel.this.removeAll();
				BMClientPanel.this.add(roomPanel);
				BMClientPanel.this.revalidate();				
			}
		},
		new ActionListener()
		{
		@Override
			public void actionPerformed(ActionEvent e)
			{
			//joinGame
			BMClientPanel.this.removeAll();
			BMClientPanel.this.add(roomPanel);		
			BMClientPanel.this.revalidate();
				
			}
		},
		new ActionListener()
		{
		@Override
			public void actionPerformed(ActionEvent e)
			{
			//Login
				BMClientPanel.this.removeAll();
				BMClientPanel.this.add(rankPanel);
				BMClientPanel.this.revalidate();
				
			}
		}
	);
		
	roomPanel = new BMRoomPanel(
			new ActionListener(){
				@Override
				public void actionPerformed(ActionEvent e){
					//enter the game
					BMClientPanel.this.removeAll();
					BMClientPanel.this.add(roomPanel);
					BMClientPanel.this.revalidate();				
				}
			},
			new ActionListener()
			{
			@Override
				public void actionPerformed(ActionEvent e)
				{
				//enter Game
				BMClientPanel.this.removeAll();
				BMClientPanel.this.add(roomPanel);		
				BMClientPanel.this.revalidate();
					
				}
			},
			new ActionListener()
			{
			@Override
				public void actionPerformed(ActionEvent e)
				{
				//Login
					BMClientPanel.this.removeAll();
					BMClientPanel.this.add(menuPanel);
					BMClientPanel.this.revalidate();
					
				}
			}
			);
		
	rankPanel = new BMRankPanel(new ActionListener(){
		@Override
		public void actionPerformed(ActionEvent e)
		{
			BMClientPanel.this.removeAll();
			BMClientPanel.this.add(menuPanel);
			BMClientPanel.this.revalidate();
		}
	});
	}
	
	
}
