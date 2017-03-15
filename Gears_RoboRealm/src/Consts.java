public final class Consts {
	
	public static final int imageWidthPx = 640;
	public static final int imageHeightPx = 480;

	// public static final double imageWidthDeg  = 45.3; // Spec
	// public static final double imageHeightDeg = 34.5; // Spec
	public static final double imageWidthDeg  = 44.4;
	public static final double imageHeightDeg = 33.5;

	// This constant represents the number of target 'images' that get processed into blobs.
	public static final int gearImageBlobCount = 2;
	
	public static final double gearTapeHeightInches = 5.0;
	public static final double gearTapeWidthInches = 2.0;
	public static final double gearTapesWidthInches = 10.25;

	public static final int cornersPerBlob = 4;
	public static final int cornersCount = cornersPerBlob * gearImageBlobCount;

	/**
	   The caller references the constants using <tt>Consts.EMPTY_STRING</tt>, 
	   and so on. Thus, the caller should be prevented from constructing objects of 
	   this class, by declaring this private constructor. 
	  */
	  private Consts(){
	    // This prevents anyone from calling this constructor:
	    throw new AssertionError();
	  }

}
