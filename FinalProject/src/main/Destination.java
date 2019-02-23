package main;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

public class Destination {
	
	protected int x;
	protected int y;
	protected final int width = 30;
	protected final int height = 30;
	protected String name;
	protected String address;
	protected double rating;
	protected String description;
	protected String imgFile;
	protected BufferedImage iconImageFile;
	protected String type;
	protected ArrayList<Connection> connections;
	
	public Destination (String name, String address, double rating, String description, String type, int x, int y, String s)
	{
		this.x = x*27;
		this.y = y*27;
		
		this.name = name;
		this.address = address;
		this.rating = rating;
		this.description = description;
		try {
			iconImageFile = ImageIO.read(new File("destinationInformation.txt"));
		} catch (IOException e) {
			e.printStackTrace();
		}		this.type = type;
		
		imgFile = s;
		
		connections = new ArrayList<Connection>();
	}
	
	public void addConnection(Connection c) 
	{
		connections.add(c);
	}
	
	public String toString()
	{
		String s = "Destination: "+ name + ", Address: "+ address + ", Description: "+description+", Rating: "+ rating +"/5.0 Type: "+ type + " Coordinates: ("+x+","+y+")";
		return s;
	}

	public String getName() 
	{
		return name;
	}

	public void setName(String name) 
	{
		this.name = name;
	}

	public String getAddress() 
	{
		return address;
	}

	public void setAddress(String address) 
	{
		this.address = address;
	}

	public double getRating() 
	{
		return rating;
	}

	public void setRating(double rating) 
	{
		this.rating = rating;
	}

	public String getDescription() 
	{
		return description;
	}

	public void setDescription(String description) 
	{
		this.description = description;
	}

	public String getImgFile() 
	{
		return imgFile;
	}

	public void setImgFile(String imgFile) 
	{
		this.imgFile = imgFile;
	}

	public ArrayList<Connection> getConnections() 
	{
		return connections;
	}

	public void setConnections(ArrayList<Connection> connections) 
	{
		this.connections = connections;
	}

	public void draw(Graphics g) 
	{
		g.drawImage(iconImageFile, x, y, width, height, null);
		
	}
	
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
}
