import java.util.Arrays;
import java.lang.Math;

public class Blobs { // implements Comparable<Corner> {
	private Blob[] myBlobs;
	private int myBlobCount;
	private boolean myValid; // Is the blobs object valid?
	
	public Blobs(int nBlobs, Corner[] corners) {
		myValid = false;
		myBlobCount = 1;
		if ((nBlobs > 0) && (nBlobs <= 2)) {
			myValid = true;
			myBlobCount = nBlobs;
		}
		myBlobs = new Blob[myBlobCount];
		for (int i=0; i < myBlobCount; i++) {
			myBlobs[i] = new Blob();
		}
		if (corners == null) {
			myValid = false;
		} else if (myValid) {
			if (((corners.length/4) == myBlobCount) && ((corners.length % 4) == 0)) { // Must have a multiple of 4 corners
				// Put corners in increasing X order
				Arrays.sort(corners, Corner.CornerXComparator);
				// Split the corners into blobs
				for (int i=0; i < Consts.cornersCount; i++) {
					myBlobs[i/4].addCorner(corners[i]);
				}
				// Now re-order each blob's corners.
				for (int i=0; i < myBlobCount; i++) {
					myBlobs[i].orderCorners();
					if (!myBlobs[i].isValid()) {
						if (!myValid) {
							System.out.println("BLOB : " + 
									Integer.toString(i) + " NOT_VALID: CORNERS NOT IN QUADS");
						}
						myValid = false;
					}
				}
			} else {
				myValid = false;
			}
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
			double topXPx = 1;
			double botXPx = 1;
			if (myBlobCount == 1) {
				topXPx = 1 + myBlobs[0].topRight().getX() - myBlobs[0].topLeft().getX();
				botXPx = 1 + myBlobs[0].botRight().getX() - myBlobs[0].botLeft().getX();
			} else if (myBlobCount == 2) {
				topXPx = 1 + myBlobs[1].topRight().getX() - myBlobs[0].topLeft().getX();
				botXPx = 1 + myBlobs[1].botRight().getX() - myBlobs[0].botLeft().getX();
			}
			widthPx = (topXPx + botXPx) / 2.0;
		}
		return widthPx;
	}

	/**
	 * @return The average x-coordinate of the topLeft and botLeft corners.
	 */
	public double getAverageLeftXPx() {
		double averageLeftXPx = 0.0;
		if (myValid) {
			averageLeftXPx = (myBlobs[0].topLeft().getX() + myBlobs[0].botLeft().getX()) / 2.0;
		}
		return averageLeftXPx;
	}

	// Get distance based on individual tape widths
	public double distanceFromWidthInches() {
		double distanceInches = 0.0;
		if (myValid) {
			for (int i=0; i < myBlobCount; i++) {
				distanceInches += myBlobs[i].distanceFromWidthInches();
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
				distanceInches += myBlobs[i].distanceFromHeightInches();
			}
			// Return average distance to the blobs.
			// If 2 blobs, it will be the distance to the center point between the blobs.
			distanceInches = distanceInches / myBlobCount;
		}
		return distanceInches;
	}
	
	/**
	 * @return offset X px for image when we have 2 blobs.
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
	
	public double perspectiveFormulaDeg() { // this implements the professor's perspective formula needs further testing
		double perspecDeg = 0.0;
		if (myValid) {
			if (myBlobCount == 1) {
				double aValueXDistInches = (Consts.gearTapeWidthInches/2.0);
				double distanceToTargetInches = distanceFromHeightInches();
				double rValueRatio = (double) myBlobs[0].getLeftHeightPx() / (double) myBlobs[0].getRightHeightPx();
				double perspecRads = Math.asin((distanceToTargetInches/aValueXDistInches)*((rValueRatio-1)/(rValueRatio+1)));
				perspecDeg = Utils.cvtRadiansToDegrees(perspecRads);
			} else if (myBlobCount == 2) {
				double aValueXDistInches = (Consts.gearTapesWidthInches/2.0);
				double distanceToTargetInches = distanceFromHeightInches();
				double rValueRatio = (double) myBlobs[0].getLeftHeightPx() / (double) myBlobs[1].getRightHeightPx();
				double perspecRads = Math.asin((distanceToTargetInches/aValueXDistInches)*((rValueRatio-1)/(rValueRatio+1)));
				perspecDeg = Utils.cvtRadiansToDegrees(perspecRads);
			}
		}
		return perspecDeg;
	}


	public boolean isValid() {
		if (myValid) {
			int topYPx[] = new int[Consts.gearImageBlobCount];
			int botYPx[] = new int[Consts.gearImageBlobCount];

			for (int i=0; i < Consts.gearImageBlobCount; i++) {
				int tlYPx = myBlobs[i].topLeft().getY();
				int trYPx = myBlobs[i].topRight().getY();
				int blYPx = myBlobs[i].botLeft().getY();
				int brYPx = myBlobs[i].botRight().getY();

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

	public String toDistString() {
		StringBuilder str = new StringBuilder(128);
		for (int i=0; i < Consts.gearImageBlobCount; i++) {
			str.append(myBlobs[i].toDistString());
		}
		return str.toString();
	}

	public String toString() {
		StringBuilder str = new StringBuilder(128);
		for (int i=0; i < Consts.gearImageBlobCount; i++) {
			str.append(myBlobs[i].toString());
		}
		return str.toString();
	}

	public String toCSV() {
		StringBuilder str = new StringBuilder(128);
		for (int i=0; i < Consts.gearImageBlobCount; i++) {
			str.append(myBlobs[i].toCSV());
		}
		return str.toString();
	}

}
