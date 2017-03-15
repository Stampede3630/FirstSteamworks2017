public class Utils {
	////////////////////////////////////////////
	// Gear application utility functions
	////////////////////////////////////////////

	public static double cvtRadiansToDegrees(double radians) {
		return radians * (180.0 / Math.PI);
	}

	public static double cvtDegreesToRadians(double degrees) {
		return degrees * (Math.PI / 180.0);
	}

	public static double cvtWidthPxToDistInches(double widthPx, double widthInches) {
		double targetWidthRadians = cvtDegreesToRadians((Consts.imageWidthDeg * widthPx / Consts.imageWidthPx) / 2.0);
		double targetDistanceInches = (widthInches/2.0) / Math.tan(targetWidthRadians);
		return targetDistanceInches;
	}

	public static double cvtHeightPxToDistInches(double heightPx, double heightInches) {
		double targetHeightRadians = cvtDegreesToRadians((Consts.imageHeightDeg * heightPx / Consts.imageHeightPx) / 2.0);
		double targetDistanceInches = (heightInches/2.0) / Math.tan(targetHeightRadians);
		return targetDistanceInches;
	}
	
}

