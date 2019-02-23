package main;
public class Connection {
	
	Destination start;
	Destination end;
	double distance;
	double time;
	
	public Connection(Destination s, Destination e,  double d, int t)
	{
		start = s;
		end = e;
		time = t;
		distance = d;	
	}
	
	public double getTime() 
	{
		return time;
	}
	
//<<<<<<< HEAD
//	public double getDistnace() 
//	{
//=======
	public double getDistance() 
	{
//>>>>>>> branch 'master' of https://github.com/Vanshika2703/CSSE230-Graphs-Project.git
		return distance;
	}
	
	public String toString()
	{
		String s = "Starting at "+ start.name + " and ending at "+ end.name + " is "+distance +" miles and takes "+ time + " minutes."; 
		return s;
	}
	
}
