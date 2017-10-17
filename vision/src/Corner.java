import java.util.Comparator;

/**
 * Corner class containing x,y pixel coordinates and angle from blob-centers.
 */
public class Corner { // implements Comparable<Corner> {

	private int cornerX;
	private int cornerY;
	private double angle; // Angle in radians of point from center of blob. Use for sorting.

	/////////////////////////
	// OBJECT CONSTRUCTION //
	/////////////////////////

	/**
	 * Clear the corner's x, y and angle values.
	 */
	public void clear() {
		cornerX = 0;
		cornerY = 0;
		angle = 0.0;
	}

	/**
	 * Construct Corner with default values
	 */
	public Corner() {
		super();
		this.clear();
	}

	/**
	 * Construct Corner with initial X,Y position
	 * @param cornerX pixel x-coordinate
	 * @param cornerY pixel y-coordinate
	 */
	public Corner(int cornerX, int cornerY) {
		super();
		this.cornerX = cornerX;
		this.cornerY = cornerY;
		this.angle = 0.0;
	}

	//////////////////////
	// MEMBER ACCESSORS //
	//////////////////////

	/**
	 * @return Corner pixel x-coordinate
	 */
	public int getX() {
		return cornerX;
	}
	/**
	 * @param cornerX set Corner pixel x-coordinate
	 */
	public void setX(int cornerX) {
		this.cornerX = cornerX;
	}
	/**
	 * @return Corner pixel y-coordinate
	 */
	public int getY() {
		return cornerY;
	}
	/**
	 * @param cornerY set Corner pixel y-coordinate
	 */
	public void setY(int cornerY) {
		this.cornerY = cornerY;
	}
	/**
	 * @return Corner angle from blob center in radians
	 */
	public double getAngle() {
		return angle;
	}
	
	public void setAngle(double angle) {
		this.angle = angle;
	}
	
	///////////////////////////////////////
	// METHODS RELATED TO CORNER SORTING //
	///////////////////////////////////////

	// public int compareTo(Corner compareCorner) {
    // 
	// 	int compareX = ((Corner) compareCorner).getX();
    // 
	// 	//ascending order
	// 	return this.cornerX - compareX;
    // 
	// 	//descending order
	// 	//return compareX - this.cornerX;
	// }

	/**
	 * A comparator to allow sorting corners by pixel x-coordinates.
	 */
	public static Comparator<Corner> CornerXComparator
                          = new Comparator<Corner>() {

	    public int compare(Corner corner1, Corner corner2) {

	      int cornerX1 = corner1.getX();
	      int cornerX2 = corner2.getX();

	      // Ascending order
	      return cornerX1 - cornerX2;

	      // Descending order
	      // return cornerX2 - cornerX1;
	    }
	};
	/**
	 * A comparator to allow sorting corners by angle from blob center.
	 */
	public static Comparator<Corner> AngleComparator
                          = new Comparator<Corner>() {

	    public int compare(Corner corner1, Corner corner2) {

	      double angle1 = corner1.getAngle();
	      double angle2 = corner2.getAngle();

	      // Ascending order
	      return (int)(1000 * (angle1 - angle2)); // preserves 3 fractional digits of accuracy

	      // Descending order
	      // return angle2 - angle1;
	    }
	};

	/////////////////////////////////////////////////
	// HELPER METHODS FOR AVERAGING MULTIPLE BLOBS //
	/////////////////////////////////////////////////
	
	/**
	 * @param corner add the corner values to this object's corner values
	 */
	public void sum(Corner corner) {
		cornerX += corner.cornerX;
		cornerY += corner.cornerY;
	}

	/**
	 * @param divisor divide all the corners values by this
	 */
	public void divide(int divisor) {
		if (divisor != 0) {
			cornerX /= divisor;
			cornerY /= divisor;
		}
	}

	//////////////////////////////////////////
	// STRING GENERATION methods for OUTPUT //
	//////////////////////////////////////////

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		double tempAngle = Math.round(angle * 100.0) / 100.0;
		return "(" + Integer.toString(cornerX) + "," + Integer.toString(cornerY) + "/" + Double.toString(tempAngle) + ")";
	}

	/**
	 * @return Corner in Comma Separated Variable format
	 */
	public String toCSV() {
		return Integer.toString(cornerX) + "," + Integer.toString(cornerY) + ",";
	}

}