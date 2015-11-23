package Client;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;

import Utilities.BMFontLibrary;
import customUI.PaintedPanel;
public class BMMenuPanel extends PaintedPanel{
	
	public JTextField port;
	public JTextField IP;
	public JTextField ipField;
	public JTextField portField;
	public JButton btnBecomeVip;

	BMMenuPanel(ActionListener host, ActionListener join, ActionListener rank, Image image)
	{
		super(image,true);
		ipField = new JTextField();
		ipField.setText("localhost");
		ipField.setColumns(10);
		ipField.setFont(BMFontLibrary.getFont("font.ttf", Font.PLAIN, 20));
		ipField.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseClicked(MouseEvent e){
                ipField.setText("");
            }
        });
		
		portField = new JTextField();
		portField.setText("6666");
		portField.setFont(BMFontLibrary.getFont("font.ttf", Font.PLAIN, 20));
		portField.setColumns(10);
		portField.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseClicked(MouseEvent e){
                portField.setText("");
            }
        });
		
		JButton btnStart = new JButton("start");
		btnStart.setFont(BMFontLibrary.getFont("font.ttf", Font.PLAIN, 20));
		JButton btnJoin = new JButton("join");
		btnJoin.setFont(BMFontLibrary.getFont("font.ttf", Font.PLAIN, 20));
		
		btnBecomeVip = new JButton("Become VIP");
		btnBecomeVip.setFont(BMFontLibrary.getFont("font.ttf", Font.PLAIN, 20));
		btnBecomeVip.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				vipClass vip = new vipClass(BMMenuPanel.this);
				vip.setVisible(true);
				
			}
		});
		
		
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(99)
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addComponent(portField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(ipField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(btnBecomeVip)))
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(133)
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addComponent(btnJoin)
								.addComponent(btnStart))))
					.addContainerGap(221, Short.MAX_VALUE))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(12)
					.addComponent(btnBecomeVip)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(ipField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(portField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(btnStart)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(btnJoin)
					.addContainerGap(125, Short.MAX_VALUE))
		);
		setLayout(groupLayout);
		btnStart.addActionListener(host);
		btnJoin.addActionListener(join);

	}

	public int getPort() {
		return Integer.parseInt(portField.getText());
	}

	public String getIP() {
		return ipField.getText();
	}
}
