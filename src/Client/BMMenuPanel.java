package Client;
import java.awt.Image;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;

import customUI.PaintedPanel;
public class BMMenuPanel extends PaintedPanel{
	private JLabel title;
	private JButton start;
	private JButton joinG;
	private JButton ranking;
	private JPanel titlePanel = new JPanel();
	private JPanel b1 = new JPanel();
	private JPanel b2 = new JPanel();
	private JPanel b3 = new JPanel();
	public JTextField port;
	public JTextField IP;
	public JTextField ipField;
	public JTextField portField;
	
	
	BMMenuPanel(ActionListener host, ActionListener join, ActionListener rank, Image image)
	{
		super(image,true);
		ipField = new JTextField();
		ipField.setText("localhost");
		ipField.setColumns(10);
		ipField.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseClicked(MouseEvent e){
                ipField.setText("");
            }
        });
		
		portField = new JTextField();
		portField.setText("6666");
		portField.setColumns(10);
		portField.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseClicked(MouseEvent e){
                portField.setText("");
            }
        });
		
		JButton btnStart = new JButton("start");
		
		JButton btnJoin = new JButton("join");
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(99)
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addComponent(portField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(ipField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(133)
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addComponent(btnJoin)
								.addComponent(btnStart))))
					.addContainerGap(217, Short.MAX_VALUE))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(47)
					.addComponent(ipField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(portField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(btnStart)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(btnJoin)
					.addContainerGap(121, Short.MAX_VALUE))
		);
		setLayout(groupLayout);
//		super(null, true);
//		title = new JLabel("Bomberman");
//		titlePanel.add(title);
//		start = new PaintedButton("Start", null, null, 20);
//		
//		port = new JTextField(30);
//		IP = new JTextField(40);
//		
//		b1.setLayout(new FlowLayout());
//		b1.add(port);
//		b1.add(IP);
		btnStart.addActionListener(host);
//		b1.add(start);
//		joinG = new PaintedButton("Join", null, null, 20);
		btnJoin.addActionListener(join);
//		b2.add(joinG);
//		ranking = new PaintedButton ("Ranking", null, null, 20);
		//ranking.addActionListener(rank);
//		b3.add(ranking);
//		setLayout(new GridBagLayout());
//		GridBagConstraints gbc = new GridBagConstraints();
//		gbc.gridy = 1;
//		add(titlePanel,gbc);
//		gbc.gridy = 2;
//		add(b1,gbc);
//		gbc.gridy = 3;
//		add(b2,gbc);
//		gbc.gridy = 4;
//		add(b3,gbc);
	}
	}