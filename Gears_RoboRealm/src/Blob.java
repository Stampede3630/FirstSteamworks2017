import java.util.Arrays;
import java.lang.Math;

// A blob will have four corners
class Blob {
	private Corner[] myCorner;
	private int cornerCount;

	public Blob() {
		myCorner = new Corner[Consts.cornersPerBlob];
		for (int i=0; i < Consts.cornersPerBlob; i++) {
			myCorner[i] = new Corner();
		}
		cornerCount = 0;
	}
	
	public void addCorner(Corner corner) {
		if (cornerCount < Consts.cornersPerBlob) {
			myCorner[cornerCount].setX(corner.getX());
			myCorner[cornerCount].setY(corner.getY());
			cornerCount += 1;
		}
	}
	
	public Corner topRight() {
		if (cornerCount == Consts.cornersPerBlob)
			return myCorner[0];
		else
			return new Corner(); // Return a (0,0) coordinate corner if there are not enough corners.
	}
	
	public Corner topLeft() {
		if (cornerCount == Consts.cornersPerBlob)
			return myCorner[1];
		else
			return new Corner(); // Return a (0,0) coordinate corner if there are not enough corners.
	}
	
	public Corner botLeft() {
		if (cornerCount == Consts.cornersPerBlob)
			return myCorner[2];
		else
			return new Corner(); // Return a (0,0) coordinate corner if there are not enough corners.
	}
	
	public Corner botRight() {
		if (cornerCount == Consts.cornersPerBlob)
			return myCorner[3];
		else
			return new Corner(); // Return a (0,0) coordinate corner if there are not enough corners.
	}
	
	public double getWidthPx() {
		double dDistTopWidthPx = 1 + Math.sqrt(
			Math.pow(topLeft().getX() - topRight().getX(), 2) 
			+ Math.pow(topLeft().getY() - topRight().getY(), 2));
		double dDistBotWidthPx = 1 + Math.sqrt(
			Math.pow(botLeft().getX() - botRight().getX(), 2) 
			+ Math.pow(botLeft().getY() - botRight().getY(), 2));
		return (dDistTopWidthPx + dDistBotWidthPx) / 2.0; // Average of the left and right heights
	}

	public double getLeftHeightPx() {
		double dDistLeftHeightPx = 1 + Math.sqrt(
			Math.pow(topLeft().getX() - botLeft().getX(), 2) 
			+ Math.pow(topLeft().getY() - botLeft().getY(), 2));
		return dDistLeftHeightPx;
	}

	public double getRightHeightPx() {
		double dDistRightHeightPx = 1 + Math.sqrt(
			Math.pow(topRight().getX() - botRight().getX(), 2) 
			+ Math.pow(topRight().getY() - botRight().getY(), 2));
		return dDistRightHeightPx;
	}

	public double getAverageHeightPx() {
		return (getLeftHeightPx() + getRightHeightPx()) / 2.0; // Average of the left and right heights
	}

	public double distanceFromWidthInches() {
		return Utils.cvtWidthPxToDistInches(getWidthPx(), Consts.gearTapeWidthInches); // A single tape width.
	}

	public double distanceFromHeightInches() {
		return Utils.cvtHeightPxToDistInches(getAverageHeightPx(), Consts.gearTapeHeightInches);
	}

	// Puts the corners in this order:
	// topright, topleft, botleft, botright
	public void orderCorners() {
		if (cornerCount > 0) {
			// First calculate the average x and y coordinate over all the corners.
			double x = 0.0;
			double y = 0.0;
			for (int i=0; i < cornerCount; i++) {
				x += myCorner[i].getX();
				y += myCorner[i].getY();
			}
			x = x / cornerCount;
			y = y / cornerCount;
			// Now determine the angle from this 'centerpoint' to each of the corners.
			for (int i=0; i < cornerCount; i++) {
				// See http://stackoverflow.com/questions/1709283/how-can-i-sort-a-coordinate-list-for-a-rectangle-counterclockwise
				myCorner[i].setAngle((Math.atan2(myCorner[i].getY() - y, 
				                                 myCorner[i].getX() - x) + 2 * Math.PI) % (2*Math.PI));
			}
			// Put corners in increasing angle order
			Arrays.sort(myCorner, Corner.AngleComparator);
		}
	}
	
	public String toDistString() {
		double dDistLeftVertPx1 = Math.pow(topLeft().getX() - botLeft().getX(), 2) 
			+ Math.pow(topLeft().getY() - botLeft().getY(), 2);
		double dDistLeftVertPx2 = Math.sqrt(dDistLeftVertPx1);
		// This is an experiment: dist to target is 51.5"
		double tapeHeightInches = 51.5 * 2 * 
			Math.tan(Utils.cvtDegreesToRadians((dDistLeftVertPx2/Consts.imageHeightPx) * Consts.imageHeightDeg/2.0));

		// Convert px dist to inches
		return Double.toString(tapeHeightInches) + "; ";
	}

	public String toString() {
		StringBuilder str = new StringBuilder(64).append("{");
		String sep = ",";
		for (int i=0; i < cornerCount; i++) {
			if (i == (cornerCount - 1)) {
				sep = "}";
			}
			str.append(myCorner[i].toString() + sep);
		}
		return str.toString();
	}

	public String toCSV() {
		StringBuilder str = new StringBuilder(64);
		for (int i=0; i < cornerCount; i++) {
			str.append(myCorner[i].toCSV());
		}
		return str.toString();
	}
}
