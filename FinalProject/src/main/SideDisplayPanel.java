package main;
import java.awt.Font;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

public class SideDisplayPanel extends JPanel {

	public JLabel sideImg;
	public JLabel sideTitle;
	public JLabel sideAddress;
	public JTextArea  sideDescription;
	public JLabel sideRating;
	public ImageIcon imgIcon;
	
	/**
	 * Create the panel.
	 */
	public SideDisplayPanel() {
		sideImg = new JLabel("");
		sideImg.setBounds(10, 52, 222, 185);
		
		sideTitle = new JLabel("");
		sideTitle.setBounds(10, 11, 222, 30);
		
		sideAddress = new JLabel("");
		sideAddress.setBounds(10, 257, 222, 86);
		
		sideDescription = new JTextArea("");
		sideDescription.setBounds(10, 313, 222, 86);
		sideDescription.setEditable(false);
		
		sideRating = new JLabel("");
		sideRating.setBounds(10, 432, 222, 69);
		
		add(sideTitle);
		add(sideImg);
		add(sideAddress);
		add(sideDescription);
		add(sideRating);		
		setBounds(521, 11, 242, 529);
		setLayout(null);
	}
	
	public void setSideImg(String str) 
	{
		imgIcon = new ImageIcon(str);
		sideImg.setIcon(imgIcon);
	}
	
	public void setSideTitle(String str) 
	{
		sideTitle.setFont(new Font("Microsoft Sans Serif", Font.BOLD, 13));
		sideTitle.setText(str.toUpperCase());
	}
	
	public void setSideAddress(String str) 
	{
		sideAddress.setFont(new Font("Microsoft Sans Serif", Font.PLAIN, 12));
		sideAddress.setText(str);
	}
	
	public void setRatings(double d) 
	{
		sideRating.setFont(new Font("Microsoft Sans Serif", Font.PLAIN, 12));
		sideRating.setText("Rated: "+d+"/5 stars.");
	}
	
	public void setSideDescription(String str) {
		sideDescription.setFont(new Font("Microsoft Sans Serif", Font.ITALIC, 12));
		sideDescription.setWrapStyleWord(true);
		sideDescription.setLineWrap(true);
	    sideDescription.setText(str);
	    sideDescription.setEditable(false);
	}
	
}
