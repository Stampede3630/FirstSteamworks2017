////////////////////////////////////////
// REQUIREMENTS to run:
// In RoboRealm open Settings, goto API Server tab and enable 'Activate RoboRealm API Server'.
////////////////////////////////////////

import java.util.*;

////////////////////////////////////////////////////////////////////////////////
// Class gears
////////////////////////////////////////////////////////////////////////////////

public class gears {

	////////////////////////////////////////////
	// This is where the program first starts //
	////////////////////////////////////////////
	
	public static void main(String[] args) {

		BlobsHist blobsHist = new BlobsHist(Consts.blobsHistorySize);
		RR_API rr = new RR_API();

		if (!rr.connect("localhost")) {
			System.out.println("Could not connect to localhost!");
			return;
		}
		
		String[] names = new String[RRVar.RRVarCount.ordinal()];
		for (int i=0; i < RRVar.RRVarCount.ordinal(); i++) {
			names[i] = RRVar.name(i);
		}

		while (true) {
			System.out.println("=================================================");
			try {
				// Get multiple variables
				Vector<String> rrVec = rr.getVariables(names);
				if (rrVec == null) {
					System.out.println("Error in GetVariables, did not return any results");
					return;
				} else {
					for (int i = 0; i < rrVec.size(); i++) {
						System.out.println(names[i] + " = " + rrVec.elementAt(i));
						RRVar myRRVar = RRVar.value(i); // This is the enum instance for the ith variable.
						myRRVar.clearVal();
						if (myRRVar.isDouble()) {
							try {
								myRRVar.setDouble(Double.parseDouble(rrVec.elementAt(i)),0);
							}
							catch (Exception e1) {
								try {
									int j = 0;
									for (String s : rrVec.elementAt(i).split(",")) {
										myRRVar.setDouble(Double.parseDouble(s), j);
										j += 1;
									}
								}
								catch (Exception e2) {
									myRRVar.clearVal();
								}
							};
						} else if (myRRVar.isInt()) {
							try {
								myRRVar.setInt(Integer.parseInt(rrVec.elementAt(i)),0);
							}
							catch (Exception e1) {
								try {
									int j = 0;
									for (String s : rrVec.elementAt(i).split(","))
									{
										myRRVar.setInt(Integer.parseInt(s), j);
										j += 1;
									}
								}
								catch (Exception e2) {
									myRRVar.clearVal();
								}
							};
						} else if (myRRVar.isCorners()) {
							try {
								int j = 0;
								for (String s : rrVec.elementAt(i).split(","))
								{
									if ((j % 2) == 0) {
										myRRVar.setCornerX(Integer.parseInt(s), j / 2);
									} else {
										myRRVar.setCornerY(Integer.parseInt(s), j / 2);
									}
									j += 1;
								}
							}
							catch (Exception e2) {
								myRRVar.clearVal();
							}
						}
					}
					
					// Only do processing when we are given two blobs that are side by side.
					int nBlobs = RRVar.BLOB_COUNT.getInt(0);
					double distanceXInches = 0.0;
					double distanceBlobsXInches = 0.0;
					double distanceBlobsYInches = 0.0;
					double offsetXDeg = 0.0;
					double perspecDeg = 0.0;
					if ((nBlobs == 1) || (nBlobs == 2)) {
						// Transform HARRIS_CORNERS into blobs
						Blobs curBlobs = new Blobs(nBlobs, RRVar.HARRIS_CORNERS.getCorners());
						
						if (curBlobs.isSideBySide()) {
							blobsHist.add(curBlobs);
							RRVar.HARRIS_CORNERS.sortByX(); // Not needed, but in case they are printed.
							
							Blobs blobs = blobsHist.getRecentAverage();

							// Reduce distance result to 2 decimal places.
							distanceXInches = Math.round(blobs.distanceFromTargetWidthInches() * 100d)/100d;
							distanceBlobsXInches = Math.round(blobs.distanceFromWidthInches() * 100d)/100d;
							distanceBlobsYInches = Math.round(blobs.distanceFromHeightInches() * 100d)/100d;
							offsetXDeg = Math.round(blobs.getOffsetXDeg() * 100d)/100d;
							perspecDeg = Math.round(blobs.perspectiveFormulaDeg()*100d)/100d;

							System.out.println("======================");
							System.out.println("Blobs: " + blobs.toString());
							System.out.println("BlobsCSV: " + blobs.toCSV());
							System.out.println("======================");
						}
					}
					System.out.print("Results," + Double.toString(distanceXInches) + ",");
					System.out.print(Double.toString(distanceBlobsXInches) + ",");
					System.out.print(Double.toString(distanceBlobsYInches) + ",");
					System.out.print(Double.toString(offsetXDeg) + ",");
					System.out.println(Double.toString(perspecDeg));
					
					System.out.println("DISTANCE_X  = " + Double.toString(distanceXInches));
					System.out.println("DIST_BLOBS_X  = " + Double.toString(distanceBlobsXInches));
					System.out.println("DIST_BLOBS_Y  = " + Double.toString(distanceBlobsYInches));
					System.out.println("OFFSET_X_DEG = " + Double.toString(offsetXDeg));
					System.out.println("perspecDeg = " + Double.toString(perspecDeg));
					
					System.out.println("======================");
					RRVar.printAll();
					System.out.println("======================");
					
					// Tell RoboRealm distance to target, x-offsetPx and target orientation.
					rr.setDistances(distanceXInches, distanceBlobsXInches, distanceBlobsYInches,
						offsetXDeg, perspecDeg);
				}
			}
			catch (Exception e) {
				// Disconnect from API Server
				System.out.println("Exception");
				e.printStackTrace();
				rr.disconnect();
				break;
			}
			try {
				Thread.sleep(33); // Wake up 30 times per second
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				// break;
			} 
		}
	}
}
