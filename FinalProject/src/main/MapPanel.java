package main;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class MapPanel extends JPanel {

	protected ImageIcon mapIcon;
	protected JLabel mapLabel;
	protected JButton btnNewButton;
	protected Graph g;
	protected ArrayList<Destination> ds;
	protected ArrayList<JButton> bs;
	
	protected HashMap<Integer, Integer> positions;
	protected HashMap<String, HashMap<Integer, Integer>> destToPos = new HashMap<>();
	
	protected String[] type = {"House", "Nature and Parks", "Store", "Hospital", "Institution","Restaurant","Entertainment" };
	protected Color[] colors = {Color.WHITE, Color.GREEN, Color.PINK, Color.BLACK, Color.DARK_GRAY, Color.CYAN, Color.YELLOW};
 	
	/**
	 * Create the panel.
	 * @param g 
	 */
	public MapPanel(Graph g)
	{

		this.g = g;
		initiateButtons();
		
		setBounds(10, 63, 486, 4487);
		mapIcon = new ImageIcon("map.png");
		mapLabel = new JLabel(mapIcon);
		mapLabel.setBounds(0, 0, 486, 486);
		
		add(mapLabel);		

		setLayout(null);
	}
	
	private void initiateButtons() 
	{
		ds = new ArrayList<Destination>();
		bs = new ArrayList<JButton>();
		
		for(Destination d : g.locations.values())
		{
			ImageIcon buttonIcon = new ImageIcon("pin.png");
			JButton j = new JButton(buttonIcon);
		
			j.addMouseListener(new MouseAdapter() {
				public void mouseEntered(MouseEvent evt) {
					j.setToolTipText(d.getName() + " - " + d.getAddress());
				}
			});
			j.setBounds(d.x, d.y, 18, 18);

			this.add(j);
			
			for (int i = 0; i < type.length; i ++)
			{
				if (d.type.equals(type[i]))
				{
					j.setBackground(colors[i]);
				}
			}
			ds.add(d);
			bs.add(j);
		}		
	}

}
