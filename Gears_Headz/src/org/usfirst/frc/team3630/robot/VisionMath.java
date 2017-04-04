package org.usfirst.frc.team3630.robot;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

// Calculations are done in two steps.
// 1. Robot (R) to Spring (S) is calculated. The RS vector is converted into the rectangular coordinate (xRS,yRS).
//    RS = dRS at angle (90 degrees plus theta)
//    RS = dRS at angle (90 degrees plus offsetXDeg)
//    RS = Rectangular coordinate (xRS,yRS)
//    xRS = dRS * cos(90 + offsetXDeg)
//    yRS = dRS * sin(90 + offsetXDeg)
// 2. Spring (S) to Front (F) is calculated. The SF vector is converted into the rectangular coordinate (xSF,ySF).
//    SF = dSF at angle (Phi minus 90 degrees)
//    SF = Rectangular coordinate (xSF,ySF)
//    xSF = dSF * cos(Phi - 90)
//    ySF = dSF * sin(Phi - 90)

// Robot (R) to Front (F)
// Combine steps 1 and 2 from above
// RF = Rectangular coordinate (xRF,yRF)
// xRF = xRS + xSF
// yRF = yRS + ySF
// Rotate Robot by Phi = perspecDeg

// Fundamental changes to the notes above due to how sensitive perspecDeg is to the relative blob heights.
// A change of one pixel can change perspecDeg by over 20 degrees. So in the Gears_RoboRealm code it will
// filter based on the number of pixels the blobs differ by. If the difference is under a threshold,
// it will give us a perspecDeg of 0.0. We will use positive perspecDeg values to indicate we need to make
// right blob heights grow relative to the left blob heights, so the robot should move right. A negative
// value means robot should move left. Note that based on offsetXDeg 'right' and 'left' must be adjusted
// per the robot's rotation.

// All variables with the word 'Angle' have units of radians.

public class VisionMath {
	private double perspecDeg;
	private double offsetXDeg;
	private double distanceX; // Direct distance from between the camera and the gear target in inches.

	////////////////////////////////////
	// Robot (R) to Spring (S) Variables
	////////////////////////////////////
	private double drsAngle; // Radians from robot to spring
	private double xRS; // The x component for distance to spring
	private double yRS; // The y component for distance to spring

	////////////////////////////////////
	// Spring (S) to Front (F) Variables
	////////////////////////////////////
	private double dsfAngle;
	private double xSF;
	private double ySF;

    ////////////////////////////////////
	// Robot (R) to  Front (F) Variables
	////////////////////////////////////
	
	private double xRF; // Vision system X-dimension is left-right with respect to robot. +X is left; -X is right.
	private double yRF; // Vision system Y-dimension is forward-backward with respect to robot. +Y is forward; -Y is backward.

	VisionMath() {
		perspecDeg = 0.0;
		offsetXDeg = 0.0;
		distanceX = 0.0;
		drsAngle = 0.0;
		xRS = 0.0;
		yRS = 0.0;
		dsfAngle = 0.0;
		xSF = 0.0;
		ySF = 0.0;
		xRF = 0.0;
		yRF = 0.0;
	}
	
	/**
	 * @return Robot (R) to Spring (S) Rectangular coordinate X-Dimension
	 */
	private double dRobotToSpringX() {
		xRS = distanceX * Math.cos(drsAngle);
		return xRS;
	}

	/**
	 * @return Robot (R) to Spring (S) Rectangular coordinate Y-Dimension
	 */
	private double dRobotToSpringY() {
		yRS = distanceX * Math.sin(drsAngle);
		return yRS;
	}

	/**
	 * @return Spring (S) to Front (F) Rectangular coordinate X-Dimension
	 */
	private double dSpringToFrontX(double dsfIN) {// this value should be in
													// inches
		xSF = dsfIN * Math.cos(dsfAngle);
		return xSF;
	}

	/**
	 * @return Spring (S) to Front (F) Rectangular coordinate Y-Dimension
	 */
	private double dSpringToFrontY(double dsfIN) {
		ySF = dsfIN * Math.sin(dsfAngle);
		return ySF;
	}

	/////////////////////////////////////////
	// Methods used globally in robot system
	/////////////////////////////////////////
	
	/**
	 * The vision system coordinate system is X-dimension is left-right, Y-dimension is forward-backward.
	 * @param dsfIN the distance in inches directly in front of the gear target
	 * @return the distance in inches the robot should move in the X-dimension
	 */
	public double robotToFrontDX(double dsfIN) {
		xRF = dRobotToSpringX() + dSpringToFrontX(dsfIN);
		return xRF;
	}

	/**
	 * The vision system coordinate system is X-dimension is left-right, Y-dimension is forward-backward.
	 * @param dsfIN the distance in inches directly in front of the gear target
	 * @return the distance in inches the robot should move in the Y-dimension
	 */
	public double robotToFrontDY(double dsfIN) {
		yRF = dRobotToSpringY() + dSpringToFrontY(dsfIN);
		return yRF;

	}

	/**
	 * @return radians to rotate the robot such that it will be facing directly towards the gear-target.
	 */
	public double rotateRobotAngle() {
		double rRotate = perspecDeg * (Math.PI / 180);
		return rRotate;
	}

	/**
	 * Call this function to pull Vision System values from the Smart Dashboard before calling the other functions.
	 */
	public void refereshImageValues() {
		perspecDeg = 0;
		//perspecDeg = SmartDashboard.getNumber("perspecDeg", 0);
		offsetXDeg = SmartDashboard.getNumber("OFFSET_X_DEG", 0);
		distanceX = SmartDashboard.getNumber("DISTANCE_X", 0);
		drsAngle = (offsetXDeg + 90) * (Math.PI / 180); // Robot to Spring angle
		dsfAngle = (perspecDeg - 90) * (Math.PI / 180); // Spring to Front angle
	}
}
