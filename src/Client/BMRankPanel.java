package Client;

import java.awt.event.ActionListener;
import java.util.Queue;
import java.util.Vector;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import centralServer.GameRecord;
import centralServer.RankContainer;

public class BMRankPanel extends JPanel{
	/**
	 * Vector for myrank
	 * Queue for wrdrank 
	 * 
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private JTable myRcdTable;
	private JTable wrdRcdTable; 
	
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
		data[0][0] = "adsf";
		myRcdTable = new JTable(data, columnNames);
		JScrollPane myRcdScrollPane = new JScrollPane(myRcdTable);
		myRcdTable.setFillsViewportHeight(true);
		
		myRecord.add(myRcdScrollPane);
		
		
		wrdRcdTable = new JTable(data, columnNames);
		JScrollPane wrdRcdScrollPane = new JScrollPane(wrdRcdTable);
		wrdRcdTable.setFillsViewportHeight(true);
		
		wrdRecord.add(wrdRcdScrollPane);
		
		tabbedPane.add("My Record", myRecord);
		tabbedPane.add("Word Record", wrdRecord);
		
		add(tabbedPane);
		
	}
	
	public void setupTable(Queue<RankContainer> wrdRankQueue, Vector<GameRecord> myRankVector) {
		/* personal record
		this.points = points;
		this.kills = kills;
		this.deaths = deaths;
		this.time = time;
		*/
		
		String[] myColumnNames = {
                "Points",
                "Kills",
                "Death", 
                "Time"};
		 myRcdTable = new JTable();
		 DefaultTableModel dtm = new DefaultTableModel(0, 0);
		 dtm.setColumnIdentifiers(myColumnNames);
		       myRcdTable.setModel(dtm);
		
		for (GameRecord gr : myRankVector) {
			dtm.addRow(new Object[] { gr.getPoints(), gr.getKillCount(), gr.getDeathCount(),
	                gr.getTime() });
		}
		
		String[] wrdColumnNames = {
                "Name", 
                "Total Points"};
		wrdRcdTable = new JTable();
		 DefaultTableModel dtm1 = new DefaultTableModel(0, 0);
		 dtm1.setColumnIdentifiers(wrdColumnNames);
		       myRcdTable.setModel(dtm1);
		
		while(!wrdRankQueue.isEmpty()) {
			RankContainer temp = wrdRankQueue.poll();
			dtm1.addRow(new Object[] { temp.getUsername(), temp.getRating() });
		}
	}
}
