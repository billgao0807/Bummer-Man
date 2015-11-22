package Client;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.util.TreeMap;
import java.util.Vector;

import javax.swing.JLabel;
import javax.swing.JPanel;

import customUI.PaintedButton;
import customUI.PaintedLabel;

public class BMBoard_Player  extends JPanel {
	private JLabel TimeLabel, HPLabel,AbilityLabel;
	private PaintedLabel SpeedLabel, PowerLabel, Item1Label, Item2Label,DetonatedTime_Label,CoolingTimeLabel ;
	private PaintedButton QuitButton;
	private int time;
	private int total_hp;
	private int curr_hp;
	private String local_username;
			public BMBoard_Player(){
				setLayout(new GridBagLayout());
				GridBagConstraints gbc = new GridBagConstraints();
				TimeLabel = new JLabel("Time: "  );
				HPLabel = new JLabel("HP ");
				AbilityLabel = new JLabel("Ability:");
				SpeedLabel = new PaintedLabel(null);
				PowerLabel = new PaintedLabel(null);
				Item1Label= new PaintedLabel (null);
				Item2Label= new PaintedLabel (null);
				CoolingTimeLabel = new PaintedLabel (null);
				DetonatedTime_Label = new PaintedLabel (null);
				SpeedLabel.setText("Speed");
				PowerLabel.setText("Power");
				Item1Label.setText("Item1");
				Item2Label.setText("Item2");;

				AbilityLabel.setText("Ability");
				CoolingTimeLabel.setText("CoolingTime");
				DetonatedTime_Label.setText("DetonatedTime_Label");
				
				QuitButton = new PaintedButton ("Quit", null, null, 10);
				Item1Label.setPreferredSize(new Dimension(60, 50));
				SpeedLabel.setPreferredSize(new Dimension(60, 50));
				Item2Label.setPreferredSize(new Dimension(60, 50));
				PowerLabel.setPreferredSize(new Dimension(60, 50));
				CoolingTimeLabel.setPreferredSize(new Dimension(60, 50));
				DetonatedTime_Label.setPreferredSize(new Dimension(60, 50));
				//QuitButton.setPreferredSize(new Dimension(60, 50));


		//playerPanel add component
				gbc.gridx = 0;
				gbc.gridy = 0;
				gbc.gridwidth = 2;
				gbc.insets = new Insets(15,0,15,0);
				this.add(TimeLabel,gbc);
				
				gbc.gridx = 0;
				gbc.gridy = 1;
				this.add(HPLabel,gbc);
				
				gbc.gridx = 0;
				gbc.gridy = 2;
				gbc.gridwidth = 1;
				this.add(AbilityLabel,gbc);
				
				gbc.gridx = 0;
				gbc.gridy = 3;
				gbc.gridwidth = 1;
				gbc.insets = new Insets(0,5,0,5);

				this.add(SpeedLabel,gbc);
				
				gbc.gridx = 1;
				gbc.gridy = 3;
				gbc.gridwidth = 1;
				this.add(PowerLabel,gbc);
				
				gbc.gridx = 0;
				gbc.gridy = 6;
				gbc.gridwidth = 2;
				this.add(Item1Label,gbc);
				
				gbc.gridx = 0;
				gbc.gridy = 7;
				gbc.gridwidth = 2;
				this.add(Item2Label,gbc);
				
				gbc.gridx = 0;
				gbc.gridy = 5;
				gbc.gridwidth = 2;
				this.add(CoolingTimeLabel,gbc);
				
				gbc.gridx = 0;
				gbc.gridy = 4;
				gbc.gridwidth = 2;
				this.add(DetonatedTime_Label,gbc);


				gbc.gridx = 0;
				gbc.gridy = 8;
				gbc.gridwidth = 2;
				gbc.insets = new Insets(15,0,15,0);
				this.add(QuitButton,gbc);

			}
	//PlayerPanel initialize
			
			public void set_up (Vector<TreeMap<String, Object>> players , String username){
				this.local_username = username;
//				System.out.println("local_username" +username );

				for (int i = 0; i<players.size(); i++) {
//					System.out.println("all_user" +players.get(i).get("username") );

					if (((String)players.get(i).get("username")).equals(username))
					{
						
						curr_hp = (int)(players.get(i).get("hp"));
						total_hp=curr_hp;
//						System.out.println("inside Current_hp" +curr_hp + "total_hp" +total_hp );

						
					}
				}
				
				HPLabel.setText("HP " + curr_hp+ '/' + total_hp);
//				System.out.println("Current_hp" +curr_hp + "total_hp" +total_hp );
				repaint();

				
			}
			
			public void set_move(int time, Vector<TreeMap<String, Object>> players  ){
				for (int i = 0; i<players.size(); i++) {

					if (((String)players.get(i).get("username")).equals(local_username))
					{
						
						curr_hp = (int)(players.get(i).get("hp"));
//						System.out.println("inside Current_hp" +curr_hp + "total_hp" +total_hp );
						SpeedLabel.setText("Speed : " + (players.get(i).get("speed")) );
						CoolingTimeLabel.setText("C : " + (players.get(i).get("coolingTime")) );
						DetonatedTime_Label.setText("D : " + (players.get(i).get("denotated")) );
						PowerLabel.setText("Power : " + (players.get(i).get("power")) );
						Item1Label.setText("I1 : " + (players.get(i).get("item1")) );
						Item2Label.setText("I2 : " + (players.get(i).get("item2")) );


						
					}
				}
				HPLabel.setText("HP " + curr_hp+ '/' + total_hp);
				
				TimeLabel.setText("Time : " +(String)players.get(0).get("time"));
//				System.out.println("Current_hp" +curr_hp + "total_hp" +total_hp );
				repaint();
			}
			
			
}
