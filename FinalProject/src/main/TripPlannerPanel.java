package main;

import javax.swing.JPanel;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

public class TripPlannerPanel extends JPanel {
	private JTextField destination1;

	private final int Y_CHANGER = 41;
	private final int X_POSITION = 115;
	private final int WIDTH = 117;
	private final int HEIGHT = 20;
	private final int LBL_WIDTH = 95;
	private final int LBL_X = 10;
	
	
	private int startY = 11;
	
	private int destCounter = 1;
	
	protected ArrayList<JTextField> textfields = new ArrayList<>();
	protected ArrayList<JLabel> labels = new ArrayList<>();

	/**
	 * Create the panel.
	 */
	public TripPlannerPanel() {
		setBounds(521, 11, 242, 529);
		setLayout(null);
		
		destination1 = new JTextField();
		destination1.setBounds(X_POSITION, startY, WIDTH, HEIGHT);
		add(destination1);
		destination1.setColumns(10);
		
		JLabel dest1Label = new JLabel("Destination " + destCounter);
		dest1Label.setBounds(LBL_X, startY, LBL_WIDTH, HEIGHT);
		add(dest1Label);

		textfields.add(destination1);
		labels.add(dest1Label);
		
		JButton addWaypoint = new JButton("Add Waypoint");
		addWaypoint.setBounds(115, 495, 117, 23);
		add(addWaypoint);
		
		JButton removeBtn = new JButton("Remove");
		removeBtn.setBounds(10, 495, 89, 23);
		add(removeBtn);
		
		addWaypoint.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				
				destCounter++;
				if (!(destCounter > 12)) {
					JTextField tempDest = new JTextField();
					startY += Y_CHANGER;
					tempDest.setBounds(X_POSITION, startY, WIDTH, HEIGHT);
					add(tempDest);
										
					JLabel tempLabel = new JLabel("Destination " + destCounter);
					tempLabel.setBounds(LBL_X, startY, LBL_WIDTH, HEIGHT);
					add(tempLabel);
									
					textfields.add(tempDest);
					labels.add(tempLabel);
					
					validate();
					repaint();
				} else {
					String message = "MAXIMUM DESTINATIONS: 12 \n Please remove destinations";
					JOptionPane.showMessageDialog(null, message, "Destination Planner Error", JOptionPane.ERROR_MESSAGE);
				}
				
			}
		});
		
		removeBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				
				if (destCounter > 1) {
					Component lastLbl = getComponentAt(LBL_X, startY);
					Component lastTextField = getComponentAt(X_POSITION, startY);
					
					remove(lastLbl);
					remove(lastTextField);
					
					startY -= Y_CHANGER;
					destCounter--;
					
					textfields.remove(destCounter-1);
					labels.remove(destCounter-1);
					
					validate();
					repaint();
				}
				
			}
		});
		
	}
}
