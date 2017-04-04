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
	
	
	/**
	 * Apply Heron's Method to calculate the area of the triangle with side lengths a, b and c
	 * in a mathematically stable way. See https://en.wikipedia.org/wiki/Heron%27s_formula.
	 * Formula: A = 0.25 * sqrt((a+(b+c))(c-(a-b))(c+(a-b))(a+(b-c))) where a >= b >= c.
	 * @param a Triangle side length
	 * @param b Triangle side length
	 * @param c Triangle side length
	 * @return Area of triangle
	 */
	public static double heronsMethod(double a, double b, double c) {
		double temp;
		if (a < b) { // swap a, b
			temp = a;
			a = b;
			b = temp;
		} // now a >= b
		if (b < c) { // swap b, c
			temp = b;
			b = c;
			c = temp;
		} // now b >= c
		if (a < b) { // swap a, b
			temp = a;
			a = b;
			b = temp;
		} // now a >= b >= c
		// Calculate Area A = 0.25 * sqrt((a+(b+c))(c-(a-b))(c+(a-b))(a+(b-c)) Parenthesis are important.
		temp = 0.25 * Math.sqrt((a+(b+c))*(c-(a-b))*(c+(a-b))*(a+(b-c)));
		return temp;
	}
}

