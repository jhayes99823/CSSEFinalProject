package main;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.util.Stack;

/**
 * Represents a graph of places Graph holds many places and connections between
 * the places
 * 
 * @author reddyvs
 *
 */
public class Graph {
	protected HashMap<String, Destination> locations;
	protected MyBoolean isDistance;

	public Graph(MyBoolean b) {
		locations = new HashMap<String, Destination>();
		isDistance = b;
		this.readTextFile();
	}

	/**
	 * Reads the file which holds the places and connections adds the destinations
	 * in the HashMap locations
	 */
	public void readTextFile()

	{
		File f = new File("destinationInformation.txt");
		Scanner scan = null;
		boolean inConnections = false;

		try {
			scan = new Scanner(f);
		}

		catch (FileNotFoundException e) {
			System.out.println("file does not exist");
		}

		scan.nextLine();
		while (scan.hasNextLine()) {
			String s = scan.nextLine();

			if (inConnections)// Adds into the destinations connections
			{
				int comma1 = s.indexOf(",");
				Destination d1 = locations.get(s.substring(0, comma1));
				s = s.substring(comma1 + 1).trim();

				int comma2 = s.indexOf(",");
				Destination d2 = locations.get(s.substring(0, comma2));
				s = s.substring(comma2 + 1).trim();

				int comma3 = s.indexOf(",");
				double miles = Double.parseDouble(s.substring(0, comma3));
				s = s.substring(comma3 + 1).trim();

				int time = Integer.parseInt(s);

				if (d1 != null) {
					Connection c1 = new Connection(d1, d2, miles, time);
					d1.addConnection(c1);
				}

				if (d2 != null) {
					Connection c2 = new Connection(d2, d1, miles, time);
					d2.addConnection(c2);
				}

			}

			if (s.equals("Connections:")) {
				inConnections = true;
			}

			else if (!inConnections)// Adds destinations to the locations HashMaps
			{
				int comma1 = s.indexOf(";");
				String name = s.substring(0, comma1);
				s = s.substring(comma1 + 1).trim();

				int comma2 = s.indexOf(";");
				String address = s.substring(0, comma2);
				s = s.substring(comma2 + 1).trim();

				int comma3 = s.indexOf(";");
				double rating = Double.parseDouble(s.substring(0, comma3));
				s = s.substring(comma3 + 1).trim();

				int comma4 = s.indexOf(";");
				String description = s.substring(0, comma4);
				s = s.substring(comma4 + 1).trim();

				int comma5 = s.indexOf(";");
				String type = s.substring(0, comma5);
				s = s.substring(comma5 + 1).trim();

				int comma6 = s.indexOf(";");
				int x = Integer.parseInt(s.substring(0, comma6));
				s = s.substring(comma6 + 1).trim();

				int comma7 = s.indexOf(";");
				int y = Integer.parseInt(s.substring(0, comma7));
				s = s.substring(comma7 + 1).trim();
				
				String img = s.trim();

				Destination d = new Destination(name, address, rating, description, type, x, y, img);
				locations.put(name, d);			}

		}

		scan.close();
	}

	/**
	 * prints all the destinations along with their connections
	 */
	public void printMe() {
		int counter = 1;

		for (String s : locations.keySet()) {
			Destination d = locations.get(s);
			System.out.println("\t\t\t\tLOCATION " + counter);
			System.out.println(d.name + "'s" + " Information: ");
			System.out.println(d.toString() + "\n");

			System.out.println(d.name + "'s" + " Connections: ");
			for (Connection c : d.connections) {
				System.out.println(c.toString());
			}
			System.out.println("\n\n");
			counter++;
		}

	}

	/**
	 * returns the path which should be taken in a String form
	 * 
	 * @param start
	 * @param end
	 * @return String
	 */
	public String toString(Destination start, Destination end) {
		String path = "";
		Stack<Destination> route = path(start, end);
		while (!route.isEmpty()) {
			path += (route.pop().name + " --> ");
		}
		return path.substring(0, path.length() - 4);
	}

	/**
	 * returns an ArrayList which contains the path
	 * 
	 * @param start
	 * @param end
	 * @return ArrayList
	 */
	public ArrayList<String> toArrayList(Destination start, Destination end) {
		ArrayList<String> path = new ArrayList<String>();
		Stack<Destination> route = path(start, end);
		while (!route.isEmpty()) {
			path.add(route.pop().name);
		}
		return path;
	}

	public Destination getDestinationByAddress(String a)
	{
		for(Destination d : locations.values())
		{
			if (d.address.equals(a))
			{
				return d;
			}
		}
		return null;
	}
	/**
	 * Creates a stack to store paths Uses the Dijkstra algorithm
	 * 
	 * @param start
	 * @param end
	 * @return Stack
	 */
	public Stack path(Destination start, Destination end) {
		Stack<Destination> route = new Stack<Destination>();
		HashMap<Destination, Destination> paths = dijkstra(start);
		Destination temp = end;
		while (temp != null) {
			route.push(temp);
			temp = paths.get(temp);
		}
		return route;
	}
	

	/**
	 * Calculates the best path by going through the whole map in an efficient
	 * manner
	 * 
	 * @param start
	 * @return HashMap
	 */
	public HashMap<Destination, Destination> dijkstra(Destination start) {
		WrapperDestination temp = new WrapperDestination(start, 0, 0);// wrapper class object
		Connection toAdd = null;
		MyPriorityQueue<WrapperDestination> queue = new MyPriorityQueue<WrapperDestination>();// PriorityQue
		HashMap<Destination, Destination> visited = new HashMap<Destination, Destination>();
		queue.add(new WrapperDestination(temp.current, 0, 0));
		while (!queue.isEmpty()) {
			for (int i = 0; i < temp.current.connections.size(); i++) {
				toAdd = temp.current.connections.get(i);// gets the neighbors
				if (!myContainsHashMapList(visited, toAdd.end)) {
					WrapperDestination wd = new WrapperDestination(toAdd.end, temp.distance, temp.time);// creates a new
																										// Wrapper Class
																										// object with a
																										// different
																										// initial
																										// distance
					if (!myContainsPQueue(queue, toAdd.end)) {
						wd.distance += toAdd.distance;// distance changes
						wd.time += toAdd.time;// time changes
						wd.path = temp.current;
						queue.add(wd);// added to the queue
					} else {
						double newDistance = temp.distance + toAdd.distance;// checks for the other path
						double newTime = temp.time + toAdd.time;
						if (isDistance.getValue() && wd.distance > newDistance) {// if newDistance is better original
																					// distance is replaced
							wd.distance = newDistance;
						} else if (!isDistance.getValue() && wd.time > newTime) {

						}
					}
				}
			}
			visited.put(locations.get(queue.poll().current.name), temp.path);// adds the very top element to the Hashmap
			temp = queue.peek();
		}
		return visited;
	}

	/**
	 * checks if the HashMap contains the destination toAdd
	 * 
	 * @param visitedCopy
	 * @param destination
	 * @return boolean
	 */
	public boolean myContainsHashMapList(HashMap<Destination, Destination> visitedCopy, Destination destination) {
		for (Destination wd : visitedCopy.keySet()) {
			if (wd.equals(destination)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * checks if the PriorityQueue contains the destination toAdd
	 * 
	 * @param queueCopy
	 * @param destination
	 * @return boolean
	 */
	public boolean myContainsPQueue(MyPriorityQueue<WrapperDestination> queueCopy, Destination destination) {
		for (WrapperDestination wd : queueCopy) {
			if (wd.current.equals(destination)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Represents a wrapper class which holds the destination, path, distance and
	 * time
	 * 
	 * @author reddyvs
	 *
	 */
	public class WrapperDestination {
		public Destination path;
		public Destination current;
		public double distance, time;

		public WrapperDestination(Destination start, double initialDist, double initialTime) {
			path = null;
			current = start;
			distance = initialDist;
			time = initialTime;
		}

		/**
		 * returns time or distance depending on boolean value
		 * 
		 * @return double
		 */
		public double getValue() {
			if (isDistance.getValue())
				return distance;
			return time;
		}
	}
	
	public ArrayList<String> getByType(String s) 
	{
		ArrayList<String> type = new ArrayList<String>();
		
		for(Destination d : locations.values())
		{
			if (d.type.equals(s))
			{
				type.add(d.name);
			}
		}
		
		return type;
	}

}