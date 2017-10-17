import java.util.Arrays;
import java.lang.Math;

public class Blobs { // implements Comparable<Corner> {
	private Blob[] myBlob;
	private int myBlobCount;
	private boolean myValid; // Is the blobs object valid?
	
	/////////////////////////
	// OBJECT CONSTRUCTION //
	/////////////////////////

	/**
	 * Construct a Blobs object.
	 * @param nBlobs Number of blobs
	 * @param corners Array of corners for the blobs, 4 corners per blob, need not be sorted.
	 */
	public Blobs(int nBlobs, Corner[] corners) {
		myValid = false;
		myBlobCount = 1;
		if ((nBlobs > 0) && (nBlobs <= 2)) {
			myValid = true;
			myBlobCount = nBlobs;
		}
		myBlob = new Blob[myBlobCount];
		for (int i=0; i < myBlobCount; i++) {
			myBlob[i] = new Blob();
		}
		if (corners == null) {
			// Fabricate a set of corners.
			myValid = true;
			for (int i=0; i < (myBlobCount * 4); i++) {
				myBlob[i/4].addCorner(0,0);
			}
		} else if (myValid) {
			if (corners.length >= (myBlobCount * 4)) { // Must have enough corners.
				// Must fix-up the number of corners, since the incoming array is enough for 2 blobs (8 corners),
				// and the sort below will sort all the elements.
				Corner[] newCorners = new Corner[myBlobCount * 4];
				for (int i=0; i < (myBlobCount * 4); i++) {
					newCorners[i] = corners[i];
				}
				// Put corners in increasing X order
				Arrays.sort(newCorners, Corner.CornerXComparator);
				// Split the corners into blobs
				for (int i=0; i < (myBlobCount * 4); i++) {
					myBlob[i/4].addCorner(newCorners[i]);
				}
				// Now re-order each blob's corners.
				for (int i=0; i < myBlobCount; i++) {
					myBlob[i].orderCorners();
					if (!myBlob[i].isValid()) {
						System.out.println("BLOB : " + 
								Integer.toString(i) + " NOT_VALID: CORNERS NOT IN QUADS");
						myValid = false;
					}
				}
			} else {
				myValid = false;
			}
		}
	}
	
	/**
	 * Clear each of the Blobs.
	 */
	public void clear() {
		myValid = false;
		for (int i=0; i < Consts.gearImageBlobCount; i++) {
			myBlob[i].clear();
		}
	}
	
	/**
	 * @param nBlobCount number of blobs in this Blobs object
	 */
	public void setBlobCount(int nBlobCount) {
		myBlobCount = nBlobCount;
	}
	
	/**
	 * @return The number of blobs in this Blobs object, or zero if not a valid Blobs object.
	 */
	public int getBlobCount() {
		int retBlobCount = 0;
		if (myValid) {
			retBlobCount = myBlobCount;
		}
		return retBlobCount;
	}
	
	//////////////////////////////////////////
	// METHODS RELATED TO VALIDITY CHECKING //
	//////////////////////////////////////////

	/**
	 * @param isValid indicator of Blobs object validity.
	 */
	public void setValid(boolean isValid) {
		myValid = isValid;
	}

	public boolean isValid() {
		return myValid;
	}

	/**
	 * @return true if there are two blobs and they are side by side. If one blob return its validity metric.
	 */
	public boolean isSideBySide() {
		if (myValid && (2 == myBlobCount)) {
			int topYPx[] = new int[2];
			int botYPx[] = new int[2];

			for (int i=0; i < 2; i++) {
				int tlYPx = myBlob[i].topLeft().getY();
				int trYPx = myBlob[i].topRight().getY();
				int blYPx = myBlob[i].botLeft().getY();
				int brYPx = myBlob[i].botRight().getY();

				topYPx[i] = Math.max(tlYPx, trYPx);
				botYPx[i] = Math.min(blYPx, brYPx);
			}
			// The first condition makes sure that there is overlap left to right.
			int minTopsYPx = Math.min(topYPx[0], topYPx[1]);
			int maxBotsYPx = Math.max(botYPx[0], botYPx[1]);
			
			myValid = minTopsYPx > maxBotsYPx;
			if (!myValid) {
				System.out.println("NOT_VALID: NOT SIDE BY SIDE");
			}
		}
		return myValid;
	}

	//////////////////////////////////////////////////////////////////////////
	// METHODS RELATED TO DISTANCES BETWEEN BLOB CORNERS. I.E. SIDE LENGTHS //
	//////////////////////////////////////////////////////////////////////////
	
	/**
	 * Construct the side distances and area metrics.
	 */
	public void build() {
		for (int i=0; i < myBlobCount; i++) {
			myBlob[i].build();
		}
	}
	
	/**
	 * @return The average horizontal distance from the left-side of the left blob to the right-side
	 * of the right blob if two blobs, otherwise the average blob width if one blob. 
	 */
	public double getTargetWidthPx() {
		double widthPx = 0.0;
		if (myValid)
		{
			if (myBlobCount == 1) {
				widthPx = myBlob[0].getWidthPx();
			} else if (myBlobCount == 2) {
				double topXPx = 1.0 + myBlob[1].topRight().getX() - myBlob[0].topLeft().getX();
				double botXPx = 1.0 + myBlob[1].botRight().getX() - myBlob[0].botLeft().getX();
				widthPx = (topXPx + botXPx) / 2.0;
			}
		}
		return widthPx;
	}

	/**
	 * @return The average x-coordinate of the topLeft and botLeft corners.
	 */
	public double getAverageLeftXPx() {
		double averageLeftXPx = 0.0;
		if (myValid) {
			averageLeftXPx = (myBlob[0].topLeft().getX() + myBlob[0].botLeft().getX()) / 2.0;
		}
		return averageLeftXPx;
	}

	/////////////////////////////////////////////////
	// HELPER METHODS FOR AVERAGING MULTIPLE BLOBS //
	/////////////////////////////////////////////////

	/**
	 * @param blobs add the corners values of blobs to this object's corners values
	 */
	public void sum(Blobs blobs) {
		for (int i=0; i < myBlobCount; i++) {
			myBlob[i].sum(blobs.myBlob[i]);
		}
	}

	/**
	 * @param divisor divide all the corners values by this
	 */
	public void divide(int divisor) {
		if (divisor != 0) {
			for (int i=0; i < myBlobCount; i++) {
				myBlob[i].divide(divisor);
			}
		}
	}

	//////////////////////////////
	// DISTANCE RELATED METHODS //
	//////////////////////////////

	/**
	 * @return Distance based on individual tape widths
	 */
	public double distanceFromWidthInches() {
		double distanceInches = 0.0;
		if (myValid) {
			for (int i=0; i < myBlobCount; i++) {
				distanceInches += myBlob[i].distanceFromWidthInches();
			}
			// Return average distance to the blobs.
			// We expect 2 blobs, so it will be the distance to the centerpoint between the blobs.
			distanceInches = distanceInches / myBlobCount;
		}
		return distanceInches;
	}
	
	/**
	 * @return Average distance based on one or two tape widths.
	 */
	public double distanceFromTargetWidthInches() {
		double distanceInches = 0.0;
		if (myValid) {
			double widthInches = Consts.gearTapesWidthInches; // Width if two blobs (two tapes seen).
			if (myBlobCount == 1) {
				widthInches = Consts.gearTapeWidthInches;
			}
			distanceInches = Utils.cvtWidthPxToDistInches(getTargetWidthPx(), widthInches);
		}
		return distanceInches;
	}
		
	/**
	 * @return Average distance calculated from the target tape heights
	 */
	public double distanceFromHeightInches() {
		double distanceInches = 0.0;
		if (myValid) {
			for (int i=0; i < myBlobCount; i++)
			{
				distanceInches += myBlob[i].distanceFromHeightInches();
			}
			// Return average distance to the blobs.
			// If 2 blobs, it will be the distance to the center point between the blobs.
			distanceInches = distanceInches / myBlobCount;
		}
		return distanceInches;
	}
	
	/**
	 * @return Offset X px for image when we have 2 blobs.
	 */
	public double getOffsetXDeg() {
		double offsetDeg = 0.0;
		if (myValid) {
			double averageLeftXPx = getAverageLeftXPx();
			double halfWidthPx = getTargetWidthPx() / 2.0;
			double MidXpx = halfWidthPx + averageLeftXPx;
			double pxOff = MidXpx - Consts.imageWidthPx / 2.0;
			offsetDeg = pxOff * Consts.imageWidthDeg / Consts.imageWidthPx;
		}
		return offsetDeg;
	}
	
	/**
	 * @return the angle of the target plane from perpendicular with the imaging camera direction
	 */
	public double perspectiveFormulaDeg() { // this implements the professor's perspective formula
		double perspecDeg = 0.0;
		double perspecRads = 0.0;
		if (myValid) {
			// Want to use a distance metric that uses the same pixel data that rValueRatio uses:
			double distanceToTargetInches = distanceFromHeightInches();
			
			if (myBlobCount == 1) {
				double heightDiffPx = Math.abs(myBlob[0].sideLeftPx() - myBlob[0].sideRightPx());
				if (heightDiffPx < Consts.blobHeightSensitivity) {
					perspecRads = 0.0; // Our robot orientation is good enough, do not rotate based on perspective angle.
				} else {
					double aValueXDistInches = (Consts.gearTapeWidthInches/2.0);
					double rValueRatio = myBlob[0].sideLeftPx() / myBlob[0].sideRightPx();
					
					double distRatio = distanceToTargetInches / aValueXDistInches;
					double rRatio = (rValueRatio-1.0) / (rValueRatio+1.0);
					perspecRads = Math.asin(distRatio * rRatio);
				}
			} else if (myBlobCount == 2) {
				// Use the width from the center of each tape.
				double aValueXDistInches = ((Consts.gearTapesWidthInches - Consts.gearTapeWidthInches)/2.0);
				
				double heightDiffPx = Math.abs(myBlob[0].getHeightPx() - myBlob[1].getHeightPx());
				if (heightDiffPx < Consts.blobsHeightSensitivity) {
					perspecRads = 0.0; // Our robot orientation is good enough, do not rotate based on perspective angle.
				} else {
					// Gets the average height of each tape, i.e. from its center.
					double rValueRatio = myBlob[0].getHeightPx() / myBlob[1].getHeightPx();
					
					double distRatio = distanceToTargetInches / aValueXDistInches;
					double rRatio = (rValueRatio-1.0) / (rValueRatio+1.0);
					double aSinParam = distRatio * rRatio;
					if (aSinParam > 1.0) {
						aSinParam = 1.0;
					} else if (aSinParam < -1.0) {
						aSinParam = -1.0;
					}
					perspecRads = Math.asin(aSinParam);
				}
			}
			perspecDeg = Utils.cvtRadiansToDegrees(perspecRads);
		}
		return perspecDeg;
	}

	//////////////////////////////////////////
	// STRING GENERATION methods for OUTPUT //
	//////////////////////////////////////////

	public String toString() {
		StringBuilder str = new StringBuilder(96 * myBlobCount);
		for (int i=0; i < myBlobCount; i++) {
			str.append(myBlob[i].toString());
		}
		return str.toString();
	}

	public String toCSV() {
		StringBuilder str = new StringBuilder(96 * myBlobCount);
		for (int i=0; i < myBlobCount; i++) {
			str.append(myBlob[i].toCSV());
		}
		return str.toString();
	}

}
