package main;

import java.util.ArrayList;

import javax.swing.JButton;

public class BtnPath {

	static ArrayList<JButton> temp;
	
	public BtnPath(ArrayList<JButton> btnPath) {
		temp = btnPath;
	}
	
	public static ArrayList<JButton> getBtnPath() {
		return temp;
	}
	
}
