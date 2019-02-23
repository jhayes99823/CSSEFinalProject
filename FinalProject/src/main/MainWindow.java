package main;

import javax.swing.*;
import java.util.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Line2D;

import javax.swing.text.BadLocationException;

public class MainWindow extends JFrame implements DocumentListener {

	private JTextField txtEnterStartingLocation;
	private JTextField txtEnterEndingLocation;
	private JPanel panel;
	private JPanel panel_1;
	private SideDisplayPanel sidePanel;
	private JLabel label;
	private MapPanel mapPanel;
	private JLabel lblNewLabel;
	private JLabel lblStart;
	private Graph g;
	private JButton tripPlannerBtn;
	private TripPlannerPanel tripPlannerPanel;
	private MyBoolean b;

	private boolean isSidePanelThere = true;

	private static final String COMMIT_ACTION = "commit";

	private static enum Mode {
		INSERT, COMPLETION
	};

	private final List<String> words;
	private Mode mode = Mode.INSERT;

	protected BtnPath btnpath = new BtnPath(null);

	public MainWindow() {
		super("Map");
		b = new MyBoolean();
		g = new Graph(b);

		initComponents();

		txtEnterStartingLocation.getDocument().addDocumentListener(this);
		InputMap im2 = txtEnterStartingLocation.getInputMap();
		ActionMap am2 = txtEnterStartingLocation.getActionMap();
		im2.put(KeyStroke.getKeyStroke("ENTER"), COMMIT_ACTION);
		am2.put(COMMIT_ACTION, new CommitAction());

		words = new ArrayList<String>();
		for (Destination d : g.locations.values()) {
			words.add(d.getName());
			words.add(d.getAddress());
		}
	}

	private void initComponents() {

		getContentPane().setFont(new Font("Microsoft Sans Serif", Font.PLAIN, 12));
		setBounds(100, 100, 789, 590);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(null);
		setResizable(false);
		LinkedList list1 = new LinkedList();
		LinkedList list2 = new LinkedList();


		list1.add("");
		list2.add("");

		for (Destination d : g.locations.values())
		{
			list1.add(d.name);
			list1.add(d.address);
			
			list2.add(d.name);
			list2.add(d.address);
			
			String s = d.type + "'s near me";
			if (!list1.contains(s))
			{
				list1.add(s);
			}
		}
			
		
		txtEnterStartingLocation = new JAutoTextField(list1);
		txtEnterStartingLocation.setFont(new Font("Microsoft Sans Serif", Font.PLAIN, 11));
		txtEnterStartingLocation.setBounds(76, 11, 213, 20);
		getContentPane().add(txtEnterStartingLocation);
		txtEnterStartingLocation.setColumns(10);
		
		txtEnterEndingLocation = new JAutoTextField(list2);
		txtEnterEndingLocation.setFont(new Font("Microsoft Sans Serif", Font.PLAIN, 11));
		txtEnterEndingLocation.setBounds(76, 35, 213, 20);
		getContentPane().add(txtEnterEndingLocation);
		txtEnterEndingLocation.setColumns(10);
		
		
		JRadioButton rdbtnSortByDistance = new JRadioButton("SORT BY DISTANCE");
		rdbtnSortByDistance.setFont(new Font("Microsoft Sans Serif", Font.PLAIN, 11));
		rdbtnSortByDistance.setBounds(295, 10, 141, 23);
		getContentPane().add(rdbtnSortByDistance);

		JRadioButton rdbtnSortByTime = new JRadioButton("SORT BY TIME");
		rdbtnSortByTime.setFont(new Font("Microsoft Sans Serif", Font.PLAIN, 11));
		rdbtnSortByTime.setBounds(295, 34, 129, 23);
		getContentPane().add(rdbtnSortByTime);

		ButtonGroup radioBtnGroup = new ButtonGroup();
		radioBtnGroup.add(rdbtnSortByTime);
		radioBtnGroup.add(rdbtnSortByDistance);

		panel = new JPanel();
		panel.setBounds(753, 530, -77, -528);
		getContentPane().add(panel);

		JButton btnGo = new JButton("GO");

		btnGo.setFont(new Font("Microsoft Sans Serif", Font.BOLD, 11));
		btnGo.setBounds(442, 5, 54, 23);

		btnGo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) 
			{
				if (rdbtnSortByDistance.isSelected()) 
				{
					g.isDistance.setTrue();
				}
				if (rdbtnSortByTime.isSelected()) 
				{
					g.isDistance.setFalse();	
				}
				
				String dest1 = txtEnterStartingLocation.getText();
				String dest2 = txtEnterEndingLocation.getText();
				String infoMessage = "";
				boolean nearMe = false;

				if (dest1.contains("'s near me"))
				{
					String type = dest1.substring(0,dest1.indexOf("'s near me")).trim();
					ArrayList<String> types = g.getByType(type);
					infoMessage = type +"'s near you: \n";
					for (String s : types) 
					{
						infoMessage += "+ " + s + "\n";
					}
					nearMe = true;
					
				}
				
				ArrayList<String> path = new ArrayList<String>();
				
				Destination d1 = null ;
				Destination d2 = null ;
				
				if  (g.locations.get(dest1)  != null)
				{
					d1 = g.locations.get(dest1);
				}
				else if  (g.getDestinationByAddress(dest1) != null)
				{
					d1 = g.getDestinationByAddress(dest1);
				}						
				if (g.locations.get(dest2) != null )
				{
					d2 = g.locations.get(dest2);
				}
				else if  (g.getDestinationByAddress(dest2) != null)
				{
					d2 = g.getDestinationByAddress(dest2);
				}	
				if (d1 != null && d2 != null)
				{
					path = g.toArrayList(d1, d2);
				}
				else if (nearMe == false)
				{
					infoMessage = "Please enter a valid place.\n To help with this please click on a pin point to see the name of the place.";
				}
				if (path.isEmpty() && nearMe == false) 
				{
					infoMessage = "Please enter a valid place.\n To help with this please click on a pin point to see the name of the place.";
				} 
				else if (nearMe == false)
				{
					infoMessage = "To get from " + dest1 + " to " + dest2 + " you need to go to: \n";
					for (int i = 0; i < path.size(); i++) 
					{
						infoMessage += "+ " + path.get(i) + "\n";
					}
				}
				
				JOptionPane.showMessageDialog(null, infoMessage, "The Path", JOptionPane.INFORMATION_MESSAGE);
			}
		});

		tripPlannerBtn = new JButton("Planner");
		tripPlannerBtn.setBounds(425, 35, 80, 23);
		tripPlannerBtn.setFont(new Font("Microsoft Sans Serif", Font.BOLD, 11));
		tripPlannerBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (isSidePanelThere == true) {
					isSidePanelThere = false;
					tripPlannerPanel = new TripPlannerPanel();
					getContentPane().remove(sidePanel);
					getContentPane().add(tripPlannerPanel);
					validate();
					repaint();
				}
			}
		});

		getContentPane().add(tripPlannerBtn);

		getContentPane().add(btnGo);

		panel_1 = new JPanel();
		panel_1.setBounds(753, 530, -215, -534);
		getContentPane().add(panel_1);

		sidePanel = new SideDisplayPanel();
		getContentPane().add(sidePanel);

		lblStart = new JLabel("START");
		lblStart.setFont(new Font("Microsoft Sans Serif", Font.ITALIC, 13));
		lblStart.setBounds(10, 14, 56, 14);
		getContentPane().add(lblStart);

		label = new JLabel("END");
		label.setFont(new Font("Microsoft Sans Serif", Font.ITALIC, 13));
		label.setBounds(10, 38, 32, 14);
		getContentPane().add(label);

		mapPanel = new MapPanel(g);

		for (int i = 0; i < mapPanel.bs.size(); i++) {
			int tempIndex = i;
			JButton temp = mapPanel.bs.get(i);
			temp.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {

					Destination tempDest = mapPanel.ds.get(tempIndex);
					if (txtEnterStartingLocation.getText().isEmpty() || txtEnterStartingLocation.getText().length() < 6) {
						txtEnterStartingLocation.setText(tempDest.getName());
					} else {
						txtEnterEndingLocation.setText(tempDest.getName());
					}
					if (isSidePanelThere == false) {
						isSidePanelThere = true;
						getContentPane().remove(tripPlannerPanel);
						sidePanel = new SideDisplayPanel();
						getContentPane().add(sidePanel);
						sidePanel.setVisible(true);
						validate();
						repaint();
					}
					sidePanel.setSideTitle(tempDest.name);
					sidePanel.setSideAddress(tempDest.address);
					sidePanel.setSideDescription(tempDest.description);
					sidePanel.setSideImg(tempDest.getImgFile());
					sidePanel.setRatings(tempDest.rating);
				}
			});
		}

		getContentPane().add(mapPanel);
		// make sure to put stuff on top of buttons before doing the map label

		lblNewLabel = new JLabel("");
		lblNewLabel.setBounds(10, 34, 13, 13);
		mapPanel.add(lblNewLabel);

		setResizable(false);
		setLocationRelativeTo(null);
		setTitle("GPS System");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);

	}
	// Listener methods

	public void changedUpdate(DocumentEvent ev) {
	}

	public void removeUpdate(DocumentEvent ev) {
	}

	public void insertUpdate(DocumentEvent ev) {
		if (ev.getLength() != 1) {
			return;
		}

		int pos = ev.getOffset();
		String content = null;
		try {
			content = txtEnterStartingLocation.getText(0, pos + 1);
		} catch (BadLocationException e) {
			e.printStackTrace();
		}

		// Find where the word starts
		int w;
		for (w = pos; w >= 0; w--) {
			if (!Character.isLetter(content.charAt(w))) {
				break;
			}
		}
		if (pos - w < 2) {
			// Too few chars
			return;
		}

		String prefix = content.substring(w + 1).toLowerCase();
		int n = Collections.binarySearch(words, prefix);
		if (n < 0 && -n <= words.size()) {
			String match = words.get(-n - 1);
			if (match.startsWith(prefix)) {
				// A completion is found
				String completion = match.substring(pos - w);
				// We cannot modify Document from within notification,
				// so we submit a task that does the change later
				SwingUtilities.invokeLater(new CompletionTask(completion, pos + 1));
			}
		} else {
			// Nothing found
			mode = Mode.INSERT;
		}
	}

	private class CompletionTask implements Runnable {
		String completion;
		int position;

		CompletionTask(String completion, int position) {
			this.completion = completion;
			this.position = position;
		}

		public void run() {
			StringBuffer sb = new StringBuffer(txtEnterStartingLocation.getText());
			sb.insert(position, completion);
			txtEnterStartingLocation.setText(sb.toString());
			txtEnterStartingLocation.setCaretPosition(position + completion.length());
			txtEnterStartingLocation.moveCaretPosition(position);
			mode = Mode.COMPLETION;
		}
	}

	private class CommitAction extends AbstractAction {
		public void actionPerformed(ActionEvent ev) {
			if (mode == Mode.COMPLETION) {
				int pos = txtEnterStartingLocation.getSelectionEnd();
				StringBuffer sb = new StringBuffer(txtEnterStartingLocation.getText());
				sb.insert(pos, " ");
				txtEnterStartingLocation.setText(sb.toString());
				txtEnterStartingLocation.setCaretPosition(pos + 1);
				mode = Mode.INSERT;
			} else {
				txtEnterStartingLocation.replaceSelection("\n");
			}
		}
	}

	public static void main(String args[]) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				// Turn off metal's use of bold fonts
				UIManager.put("swing.boldMetal", Boolean.FALSE);
				new MainWindow().setVisible(true);
			}
		});
	}

	public void setSideDisplay(boolean set) {
		isSidePanelThere = set;
	}
	
}
