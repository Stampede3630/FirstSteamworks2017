import java.util.Arrays;
import java.lang.Math;

// A blob will have four corners
class Blob {
	private Corner[] myCorner;
	private int cornerCount;
	private double[] mySide; // mySide[0] is topWidth, 1 is leftHeight, 2 is botWidth, 3 is rightHeight
	private double myDiag02; // diagonal distance from topRight to botLeft corner (used for area calc).
	private double myArea;
	private boolean myValid;
	private boolean isOrdered; // true if the corners have been sorted into TR,TL,BL,BR order.
	private boolean isValidated;
	private boolean isBuilt; // true if all construction and validity methods have run.

	/////////////////////////
	// OBJECT CONSTRUCTION //
	/////////////////////////

	public Blob() {
		myCorner = new Corner[Consts.cornersPerBlob];
		for (int i=0; i < Consts.cornersPerBlob; i++) {
			myCorner[i] = new Corner();
		}
		mySide = new double[Consts.cornersPerBlob];
		for (int i=0; i < Consts.cornersPerBlob; i++) {
			mySide[i] = 0.0;
		}
		myDiag02 = 0.0;
		myArea = 0.0;
		cornerCount = 0;
		myValid = false;
		isValidated = false;
		isOrdered = false;
		isBuilt = false;
	}
	
	/**
	 * Clear each of this Blob's corners.
	 */
	public void clear() {
		for (int i=0; i < cornerCount; i++) {
			myCorner[i].clear();
		}
		for (int i=0; i < Consts.cornersPerBlob; i++) {
			mySide[i] = 0.0;
		}
		myDiag02 = 0.0;
		myArea = 0.0;
		// cornerCount = 0; // Do not clear the corner count. The corners still exist, they are just cleared.
		myValid = false;
		isValidated = false;
		isOrdered = false;
		isBuilt = false;
	}

	/**
	 * Add a corner to the blob
	 * @param corner object to add
	 */
	public void addCorner(Corner corner) {
		if (cornerCount < Consts.cornersPerBlob) {
			myCorner[cornerCount].setX(corner.getX());
			myCorner[cornerCount].setY(corner.getY());
			cornerCount += 1;
			myValid = false;
			isValidated = false;
			isOrdered = false;
			isBuilt = false;
		}
	}
	
	public void addCorner(int x, int y) {
		if (cornerCount < Consts.cornersPerBlob) {
			myCorner[cornerCount].setX(x);
			myCorner[cornerCount].setY(y);
			cornerCount += 1;
			myValid = false;
			isValidated = false;
			isOrdered = false;
			isBuilt = false;
		}
	}
	
	/**
	 * Puts the corners in this order: topright, topleft, botleft, botright
	 */
	public void orderCorners() {
		if (!isOrdered) {
			myValid = false;
			isValidated = false;
			isBuilt = false;
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
				isOrdered = true;
			}
		}
	}
	
	//////////////////////
	// MEMBER ACCESSORS //
	//////////////////////

	public Corner topRight() {
		if (cornerCount == Consts.cornersPerBlob) {
			orderCorners();
			return myCorner[0];
		} else {
			return new Corner(); // Return a (0,0) coordinate corner if there are not enough corners.
		}
	}
	
	public Corner topLeft() {
		if (cornerCount == Consts.cornersPerBlob) {
			orderCorners();
			return myCorner[1];
		} else {
			return new Corner(); // Return a (0,0) coordinate corner if there are not enough corners.
		}
	}
	
	public Corner botLeft() {
		if (cornerCount == Consts.cornersPerBlob) {
			orderCorners();
			return myCorner[2];
		} else {
			return new Corner(); // Return a (0,0) coordinate corner if there are not enough corners.
		}
	}
	
	public Corner botRight() {
		if (cornerCount == Consts.cornersPerBlob) {
			orderCorners();
			return myCorner[3];
		} else {
			return new Corner(); // Return a (0,0) coordinate corner if there are not enough corners.
		}
	}
	
	// // mySide[0] is topWidth, 1 is leftHeight, 2 is botWidth, 3 is rightHeight
	
	public double sideTopPx() {
		build();
		return mySide[0];
	}

	public double sideLeftPx() {
		build();
		return mySide[1];
	}

	public double sideBottomPx() {
		build();
		return mySide[2];
	}

	public double sideRightPx() {
		build();
		return mySide[3];
	}

	public double getArea() {
		build();
		return myArea;
	}

	//////////////////////////////////////////
	// METHODS RELATED TO VALIDITY CHECKING //
	//////////////////////////////////////////

	/**
	 * @return true if this blob's corners meet consistency checks.
	 */
	private void validate() {
		if (!isValidated) {
			orderCorners();
			myValid = cornerCount == 4;
			double botLimit = 0.0;
			double topLimit = Math.PI / 2.0;
			for (int i=0; myValid && (i < cornerCount); i++) {
				myValid = myValid && myCorner[i].getAngle() >= botLimit;
				myValid = myValid && myCorner[i].getAngle() <= topLimit;
				botLimit = topLimit;
				topLimit = botLimit + (Math.PI / 2.0);
			}
			isValidated = true;
		}
	}

	/**
	 * @return true if this blob's corners meet consistency checks.
	 */
	public boolean isValid() {
		validate();
		return myValid;
	}

	//////////////////////////////////////////////////////////////////////////
	// METHODS RELATED TO DISTANCES BETWEEN BLOB CORNERS. I.E. SIDE LENGTHS //
	//////////////////////////////////////////////////////////////////////////
	
	/**
	 * Construct the side distances and area metrics.
	 */
	public void build() {
		if (!isBuilt) {
			validate(); // Also orders the corners.

			// Construct the side distances and area metrics
			if (myValid) {
				// Calculate each side distance in pixels.
				// mySide[0] is topWidth, 1 is leftHeight, 2 is botWidth, 3 is rightHeight
				for (int i=0; i < cornerCount; i++) {
					mySide[i] = 1.0 + Math.sqrt(
							Math.pow(myCorner[i].getX() - myCorner[(i+1)%cornerCount].getX(), 2) 
							+ Math.pow(myCorner[i].getY() - myCorner[(i+1)%cornerCount].getY(), 2) );
				}
				myDiag02 = 1.0 + Math.sqrt(
						Math.pow(myCorner[0].getX() - myCorner[2].getX(), 2) 
						+ Math.pow(myCorner[0].getY() - myCorner[2].getY(), 2) );
				// Most likely myDiag02 is greatest, then height, then width, so it will run fastest.
				myArea = Utils.heronsMethod(myDiag02, mySide[1], mySide[0])
						+ Utils.heronsMethod(myDiag02, mySide[3], mySide[2]);
			}
			
			isBuilt = true;
		}
	}
	
	public double getWidthPx() {
		return (sideTopPx() + sideBottomPx()) / 2.0; // Average of the left and right heights
	}

	public double getHeightPx() {
		return (sideLeftPx() + sideRightPx()) / 2.0; // Average of the left and right heights
	}
	
	/////////////////////////////////////////////////
	// HELPER METHODS FOR AVERAGING MULTIPLE BLOBS //
	/////////////////////////////////////////////////

	/**
	 * @param blob add the corners values of blob to this object's corners values
	 */
	public void sum(Blob blob) {
		boolean valid = (cornerCount == 4) && (blob.cornerCount == 4);
		if (valid) {
			for (int i=0; i < cornerCount; i++) {
				myCorner[i].sum(blob.myCorner[i]);
			}		
		}
	}

	/**
	 * @param divisor divide all the corners values by this
	 */
	public void divide(int divisor) {
	boolean valid = (cornerCount == 4) && (divisor != 0);
		if (valid) {
			for (int i=0; i < cornerCount; i++) {
				myCorner[i].divide(divisor);
			}		
		}
	}

	//////////////////////////////
	// DISTANCE RELATED METHODS //
	//////////////////////////////

	public double distanceFromWidthInches() {
		return Utils.cvtWidthPxToDistInches(getWidthPx(), Consts.gearTapeWidthInches); // A single tape width.
	}

	public double distanceFromHeightInches() {
		return Utils.cvtHeightPxToDistInches(getHeightPx(), Consts.gearTapeHeightInches);
	}

	public String toString() {
		double temp;
		StringBuilder str = new StringBuilder(96).append("{");
		String sep = ",";
		for (int i=0; i < cornerCount; i++) {
			if (i == (cornerCount - 1)) {
				sep = "}";
			}
			str.append(myCorner[i].toString() + sep);
		}
		// Append the Sides in the form S(top,left,bottom,right)
		str.append("S(");
		sep = ",";
		for (int i=0; i < cornerCount; i++) {
			if (i == (cornerCount - 1)) {
				sep = ")";
			}
			temp = Math.round(mySide[i] * 10.0) / 10.0;
			str.append(Double.toString(temp) + sep);
		}
		temp = Math.round(getArea() * 10.0) / 10.0;
		str.append("A(" + Double.toString(temp) + ")");
		return str.toString();
	}

	//////////////////////////////////////////
	// STRING GENERATION methods for OUTPUT //
	//////////////////////////////////////////

	/**
	 * @return Comma separated variable format string of this blob.
	 */
	public String toCSV() {
		double temp;
		StringBuilder str = new StringBuilder(96);
		for (int i=0; i < cornerCount; i++) {
			str.append(myCorner[i].toCSV());
		}
		for (int i=0; i < cornerCount; i++) {
			temp = Math.round(mySide[i] * 10.0) / 10.0;
			str.append(Double.toString(temp) + ",");
		}
		temp = Math.round(getArea() * 10.0) / 10.0;
		str.append(Double.toString(temp) + ",");
		return str.toString();
	}

}
