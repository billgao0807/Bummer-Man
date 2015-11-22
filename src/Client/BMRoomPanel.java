package Client;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionListener;
import java.util.TreeMap;
import java.util.Map.Entry;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import Utilities.BMFontLibrary;
import Utilities.BMLibrary;
import customUI.PaintedButton;
import customUI.PaintedPanel;
import javax.swing.SwingConstants;

public class BMRoomPanel extends PaintedPanel{
	private JLabel time;
	private JLabel HP;
	private JComboBox<String> timing;
	private JComboBox<Integer> HPCounting;
	private JButton start;
	private JButton startAI;
	private JButton quitG;
	private JPanel timeP = new JPanel();
	private JPanel HPP = new JPanel();
	private JPanel startPanel = new JPanel();
	private JPanel startAIP = new JPanel();
	private JPanel quitP = new JPanel();
	private boolean identity;
	private PaintedPanel player0;
	private PaintedPanel player1;
	private PaintedPanel player2;
	private PaintedPanel player3;
	public JPanel lt;
	public JPanel ll;
	public JPanel rt;
	public JPanel rl;

	BMRoomPanel(boolean identity,ActionListener startP, ActionListener startB, ActionListener quit,Image image)
	{
		super(image, true);
		time = new JLabel("Time:");
		HP = new JLabel("HP:");
		this.identity = identity;
		String[] t = new String[]{"1 min", "2 min", "30 secs"};
		//timing = new JComboBox<>(t);
		Integer[] hp = new Integer[]{1,2,3};
		//HPCounting = new JComboBox<>(hp);
		//		
		//		timeP.add(time);
		//		timeP.add(timing);	
		//		timeP.setOpaque(false);
		//		HPP.add(HP);
		//		HPP.add(HPCounting);
		//		HPP.setOpaque(false);

		start =  new PaintedButton("Start", null ,null, 20);		
		startPanel.add(start);
		start.addActionListener(startP);
		startPanel.setOpaque(false);

		startAI = new PaintedButton("Start(AI)", null, null, 20);
		startAIP.add(startAI);
		startAI.addActionListener(startB);
		startAIP.setOpaque(false);

		quitG = new PaintedButton("Quit", null, null, 20);
		quitP.add(quitG);
		quitG.addActionListener(quit);
		quitP.setOpaque(false);
		setLayout(new GridLayout(0, 3, 0, 0));


		JPanel left = new JPanel();
		left.setOpaque(false);
		add(left);
		left.setLayout(new BoxLayout(left, BoxLayout.Y_AXIS));
		left.setBounds(0, 0, this.getWidth()/3, this.getHeight()/2);

		lt = new JPanel();
		lt.setLayout(new BorderLayout());
		lt.setOpaque(false);
		JLabel username0 = new JLabel("player0");
		username0.setFont(BMFontLibrary.getFont("font.ttf", Font.PLAIN, 20));
		username0.setHorizontalAlignment(SwingConstants.CENTER);
		username0.setOpaque(false);
		username0.setAlignmentY(CENTER_ALIGNMENT);
		player0 = new PaintedPanel(BMLibrary.readImages("player0.png"));
		lt.add(username0, BorderLayout.NORTH);
		lt.add(player0,BorderLayout.CENTER);
		left.add(lt);
		player0.setLayout(new GridLayout(1, 0, 0, 0));
		
		ll = new JPanel();
		ll.setLayout(new BorderLayout());
		ll.setOpaque(false);
		JLabel username1 = new JLabel("player1");
		username1.setFont(BMFontLibrary.getFont("font.ttf", Font.PLAIN, 20));
		username1.setHorizontalAlignment(SwingConstants.CENTER);
		username1.setOpaque(false);
		username1.setAlignmentY(CENTER_ALIGNMENT);
		player1 = new PaintedPanel(BMLibrary.readImages("player1.png"));
		ll.add(username1, BorderLayout.NORTH);
		ll.add(player1, BorderLayout.CENTER);
		left.add(ll);

		

		JPanel mid = new JPanel();


		JComboBox comboBox = new JComboBox();
		comboBox.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));
		for (String str : t){
			comboBox.addItem(str);
			comboBox.setFont(BMFontLibrary.getFont("font.ttf", Font.PLAIN, 20));
		}
		JComboBox comboBox_1 = new JComboBox();
		for (Integer Int : hp){
			comboBox_1.addItem(Int);
			comboBox_1.setFont(BMFontLibrary.getFont("font.ttf", Font.PLAIN, 20));
		}
		comboBox_1.setBorder(BorderFactory.createEmptyBorder(0, 0, 60, 0));
		JButton btnPlay = new JButton("Play");
		btnPlay.setFont(BMFontLibrary.getFont("font.ttf", Font.PLAIN, 20));
		btnPlay.setAlignmentX(CENTER_ALIGNMENT);
		btnPlay.addActionListener(startP);
		JButton btnPlayai = new JButton("Play(AI)");
		btnPlayai.addActionListener(startB);
		btnPlayai.setAlignmentX(CENTER_ALIGNMENT);
		btnPlayai.setFont(BMFontLibrary.getFont("font.ttf", Font.PLAIN, 20));
		JButton btnQuit = new JButton("Quit");
		btnQuit.setFont(BMFontLibrary.getFont("font.ttf", Font.PLAIN, 20));
		btnQuit.addActionListener(quit);
		btnQuit.setAlignmentX(CENTER_ALIGNMENT);
		mid.setLayout(new BoxLayout(mid, BoxLayout.Y_AXIS));

		//		mid.add(Box.createVerticalGlue());
		mid.add(comboBox);
		mid.add(comboBox_1);
		mid.add(Box.createVerticalGlue());
		mid.add(btnPlay);
		mid.add(btnPlayai);
		mid.add(btnQuit);
		mid.add(Box.createVerticalGlue());

		mid.setOpaque(false);
		add(mid);


		

		JPanel right = new JPanel();
		right.setOpaque(false);
		add(right);
		right.setLayout(new BoxLayout(right, BoxLayout.Y_AXIS));
		right.setBounds(0, 0, this.getWidth()/3, this.getHeight()/2);

		rt = new JPanel();
		rt.setLayout(new BorderLayout());
		rt.setOpaque(false);
		JLabel username2 = new JLabel("player2");
		username2.setFont(BMFontLibrary.getFont("font.ttf", Font.PLAIN, 20));
		username2.setHorizontalAlignment(SwingConstants.CENTER);
		username2.setOpaque(false);
		username2.setAlignmentY(CENTER_ALIGNMENT);
		player2 = new PaintedPanel(BMLibrary.readImages("player2.png"));
		rt.add(username2, BorderLayout.NORTH);
		rt.add(player2,BorderLayout.CENTER);
		right.add(rt);
		player2.setLayout(new GridLayout(1, 0, 0, 0));

		rl = new JPanel();
		rl.setLayout(new BorderLayout());
		rl.setOpaque(false);
		JLabel username3 = new JLabel("player3");
		username3.setFont(BMFontLibrary.getFont("font.ttf", Font.PLAIN, 20));
		username3.setHorizontalAlignment(SwingConstants.CENTER);
		username3.setOpaque(false);
		username3.setAlignmentY(CENTER_ALIGNMENT);
		player3 = new PaintedPanel(BMLibrary.readImages("player3.png"));
		rl.add(username3, BorderLayout.NORTH);
		rl.add(player3, BorderLayout.CENTER);
		right.add(rl);

	}
	public void repaintPlayer(TreeMap<Integer, String> treeMap)
	{
		lt.setVisible(false);
		ll.setVisible(false);
		rt.setVisible(false);
		rl.setVisible(false);
		for(Entry<Integer, String> entry : treeMap.entrySet()) {
			 String value = entry.getValue();
			 System.out.println(value);
		}
		
	}
}
