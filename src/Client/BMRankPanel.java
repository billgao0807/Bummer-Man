package Client;

import java.awt.event.ActionListener;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;

public class BMRankPanel extends JPanel{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	BMRankPanel(ActionListener returnMain)
	{
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		
		JPanel myRecord = new JPanel();
		JPanel wrdRecord = new JPanel();
		
		String[] columnNames = {"Rank",
                "Name",
                "Games",
                "Total Points",
                "Total Time"};
		
		Object[][] data = {
			    {"Kathy", "Smith",
			     "Snowboarding", new Integer(5), new Boolean(false)},
			    {"John", "Doe",
			     "Rowing", new Integer(3), new Boolean(true)},
			    {"Sue", "Black",
			     "Knitting", new Integer(2), new Boolean(false)},
			    {"Jane", "White",
			     "Speed reading", new Integer(20), new Boolean(true)},
			    {"Joe", "Brown",
			     "Pool", new Integer(10), new Boolean(false)}
			};
		JTable myRcdTable = new JTable(data, columnNames);
		JScrollPane myRcdScrollPane = new JScrollPane(myRcdTable);
		myRcdTable.setFillsViewportHeight(true);
		
		myRecord.add(myRcdScrollPane);
		
		
		JTable wrdRcdTable = new JTable(data, columnNames);
		JScrollPane wrdRcdScrollPane = new JScrollPane(wrdRcdTable);
		wrdRcdTable.setFillsViewportHeight(true);
		
		wrdRecord.add(wrdRcdScrollPane);
		
		tabbedPane.add("My Record", myRecord);
		tabbedPane.add("Word Record", wrdRecord);
		
		add(tabbedPane);
		
	}
}
