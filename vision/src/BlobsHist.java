
public class BlobsHist {
	private Blobs myAverage;
	private Blobs[] myHistBlobs;
	private int myMaxCount;
	private int myCount;
	private int myLastIndex;

	public BlobsHist(int nBlobsCount) {
		myAverage = new Blobs(Consts.gearImageBlobCount, null);
		// Create a history of nBlobsCount Blobs Objects. 
		myMaxCount = nBlobsCount;
		myHistBlobs = new Blobs[myMaxCount];
		// Do not allocate Blobs objects yet, we will collect them as 'they come in'.
		myCount = 0; // Currently we hold no Blobs objects.
		myLastIndex = 0; // Currently point to the 0th entry.
	}

	/**
	 * @param blobs object to add to history
	 */
	public void add(Blobs blobs) {
		if (myCount < myMaxCount) {
			myHistBlobs[myCount] = blobs;
			myLastIndex = myCount;
			myCount++;
		} else {
			// We already have a full set of Blobs objects, eliminate the oldest object.
			int nextIndex = (myLastIndex + 1) % myMaxCount;
			myHistBlobs[nextIndex] = blobs;
			myLastIndex = nextIndex;
		}
	}
	
	/**
	 * @return a Blobs object holding a recent average from the Blobs history.
	 */
	public Blobs getRecentAverage() {
		// Calculate an average from the most recent Consts.blobsAverageCount, place in myAverage.
		myAverage.clear(); // Also sets it to invalid
		
		// First determine how many blobs are in each history item.
		int[] nBlobCnt = new int[Consts.gearImageBlobCount + 1];
		for (int i=0; i < Consts.gearImageBlobCount + 1; i++) {
			nBlobCnt[i] = 0;
		}
		int nHistCount = myCount; // Only go over History items that exist.
		if (Consts.blobsAverageCount < nHistCount) {
			nHistCount = Consts.blobsAverageCount;
		}
		for (int i=0; i < nHistCount; i++) {
			int index = (myMaxCount + myLastIndex - i) %  myMaxCount;
			nBlobCnt[myHistBlobs[index].getBlobCount()]++; // Histogram of blob counts.
		}
		
		// Initial version will require all blobsAverageCount Blobs to have the same number of blobs.
		for (int i=0; i < Consts.gearImageBlobCount + 1; i++) {
			if (nBlobCnt[i] == nHistCount) {
				myAverage.setBlobCount(i);
				myAverage.setValid(true);
				break;
			}
		}
		
		if (myAverage.isValid()) {
			// All the recent history has myAverage.getBlobCount() blobs.
			for (int i=0; i < nHistCount; i++) {
				int index = (myMaxCount + myLastIndex - i) %  myMaxCount;
				// Add myHistBlobs[index] into myAverage
				myAverage.sum(myHistBlobs[index]);
			}
			myAverage.divide(nHistCount); // Divide the sum by the number of elements in the average.
		}
		
		myAverage.build(); // Generate all sides and area attributes.
		
		return myAverage;
	}
}
