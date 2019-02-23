package main;
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
		public double getValue(boolean b) {
			if (b) //isDistance.getValue()
				return distance;
			return time;
		}
	}