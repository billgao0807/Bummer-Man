package centralServer;

import java.awt.BorderLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

/*
 * Copied from Factory Code
 */
public class BMCentralServerGUI extends JFrame {

	public static final long serialVersionUID = 1;
	private static JTextArea textArea;
	private JScrollPane textAreaScrollPane;
	private JButton connectButton;
	private JButton disconnectButton;

	public BMCentralServerGUI() {
		super("Central Server");
		initializeVariables();
		createGUI();
		addActionAdapters();
		setVisible(true);
	}
	
	private void initializeVariables() {
		textArea = new JTextArea();
		textArea.setEditable(false);
		textAreaScrollPane = new JScrollPane(textArea);
		
		connectButton = new JButton("Connect");
		disconnectButton = new JButton("Disconnect");
	}
	
	private void createGUI() {
		setSize(500, 500);
		JPanel northPanel = new JPanel();
		northPanel.add(connectButton);
		northPanel.add(disconnectButton);
		add(northPanel, BorderLayout.NORTH);
		add(textAreaScrollPane, BorderLayout.CENTER);
	}
	
	private void addActionAdapters() {
		addWindowListener(new WindowAdapter() {
			  public void windowClosing(WindowEvent we) {
				  System.exit(0);
			  }
		});
		
		//connectButton.addActionListener(new ConfigureFactoryListener(selectFactoryComboBox));
		
		//disconnectButton.addActionListener(new FactoryInfoListener(selectFactoryComboBox));
	}
	
	public static void addMessage(String msg) {
		if (textArea.getText() != null && textArea.getText().trim().length() > 0) {
			textArea.append("\n" + msg);
		}
		else {
			textArea.setText(msg);
		}
	}
	
	public static void main(String args[]) {
		new BMCentralServerGUI();
	}
}

