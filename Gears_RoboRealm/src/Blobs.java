import java.util.Arrays;
import java.lang.Math;

public class Blobs { // implements Comparable<Corner> {
	private Blob[] myBlobs;
	
	public Blobs(Corner[] corners) {
		myBlobs = new Blob[Consts.gearImageBlobCount];
		for (int i=0; i < Consts.gearImageBlobCount; i++) {
			myBlobs[i] = new Blob();
		}
		if (corners != null) {
			if (corners.length == Consts.cornersCount) {
				// Put corners in increasing X order
				Arrays.sort(corners, Corner.CornerXComparator);
				// Split the corners into blobs
				for (int i=0; i < Consts.cornersCount; i++) {
					myBlobs[i/4].addCorner(corners[i]);
				}
				// Now re-order each blob's corners.
				for (int i=0; i < Consts.gearImageBlobCount; i++) {
					myBlobs[i].orderCorners();
				}
			}
		}
	}
	
	// Return the average horizontal distance from the left-side of the left blob to the right-side
	// of the right blob.
	public double getTargetWidthPx() {
		double widthPx = 1;
		if (Consts.gearImageBlobCount == 2) {
			double topXPx = 1 + myBlobs[1].topRight().getX() - myBlobs[0].topLeft().getX();
			double botXPx = 1 + myBlobs[1].botRight().getX() - myBlobs[0].botLeft().getX();
			widthPx = (topXPx + botXPx) / 2.0;
		}
		return widthPx;
	}

	public double getAverageLeftXPx() {
		double averageLeftXPx = 0.0;
		// Return the left-most corner XPx of the left-most blob.
		if (Consts.gearImageBlobCount == 2) {
			averageLeftXPx = (myBlobs[0].topLeft().getX() + myBlobs[0].botLeft().getX()) / 2.0;
		}
		return averageLeftXPx;
	}

	// Get distance based on individual tape widths
	public double distanceFromWidthInches() {
		double targetDistanceInches = 0.0;
		for (int i=0; i < Consts.gearImageBlobCount; i++) {
			targetDistanceInches += myBlobs[i].distanceFromWidthInches();
		}
		// Return average distance to the blobs.
		// We expect 2 blobs, so it will be the distance to the centerpoint between the blobs.
		return targetDistanceInches / Consts.gearImageBlobCount;
	}
	
	// Get distance from total two-tape width.
	public double distanceFromTargetWidthInches() {
		return Utils.cvtWidthPxToDistInches(getTargetWidthPx(), Consts.gearTapesWidthInches);
		}
	public double distanceFromTargetOneBlobInches(){
		return Utils.cvtWidthPxToDistInches(myBlobs[0].distanceFromWidthInches(), Consts.gearTapeWidthInches);
			}
	public double distanceFromHeightInches() {
		double targetDistanceInches = 0.0;
		for (int i=0; i < Consts.gearImageBlobCount; i++)
		{
			targetDistanceInches += myBlobs[i].distanceFromHeightInches();
		}
		// Return average distance to the blobs.
		// We expect 2 blobs, so it will be the distance to the centerpoint between the blobs.
		return targetDistanceInches / Consts.gearImageBlobCount;
	}
	
	public double getOffsetXDeg() { // Supposed to get offset px for image this needs further testing
		double averageLeftXPx = getAverageLeftXPx();
		double halfWidthPx = getTargetWidthPx() / 2.0;
		double MidXpx = halfWidthPx + averageLeftXPx;
		double pxOff = MidXpx - Consts.imageWidthPx / 2.0;
		double degOff = pxOff * Consts.imageWidthDeg / Consts.imageWidthPx;
		return degOff;
	}
	
	
	public double perspectiveFormulaDeg() { // this implements the professor's perspective formula needs further testing 
		double seperationOfStripes = (Consts.gearTapesWidthInches/2.0); // this is the a value returns inches
		double distanceToTarget = distanceFromHeightInches(); // this is the d value
		double ratioTape = (double) myBlobs[0].getLeftHeightPx() / (double) myBlobs[1].getRightHeightPx();// this is the r value
		double perRads = Math.asin((distanceToTarget/seperationOfStripes)*((ratioTape-1)/(ratioTape+1)));//  the output is radians
		return Utils.cvtRadiansToDegrees(perRads);
	}
	public double getOffsetOneBlobXDeg(){
		double averageLeftXPx = getAverageLeftXPx();
		double halfWidthPx = distanceFromTargetOneBlobInches() / 2.0;
		double MidXpx = halfWidthPx + averageLeftXPx;
		double pxOff = MidXpx - Consts.imageWidthPx / 2.0;
		double degOff = pxOff * Consts.imageWidthDeg / Consts.imageWidthPx;
		return degOff;
	}

	public boolean areSideBySide() {
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
		
		boolean areSideBySide = minTopsYPx > maxBotsYPx;
		
		return areSideBySide;
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
