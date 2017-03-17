package org.usfirst.frc.team3630.robot;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class VisionMath {
	private double prespecDeg;
	private double offsetXDeg;
	private double distanceX;// this is the drs value name is distance x for netowrk
						// table purposes
	private double drsAngle; // degrees from robot
															// to spring
	private double xrs; // the x componet vector for distanc to spring
	private double yrs; // the y componet vector for distance to spring

	////////////////////////////////
	// spring to front var seection//
	///////////////////////////////
	private double dsfAngle;
	private double xsf;
	private double ysf;

	////////////////
	// robot to front vars
	//////////////////
	// all vars. with word angle output in rads.
	private double xrf;
	private double yrf;

	private double dRobotToSpringX() {
		xrs = distanceX * Math.cos(drsAngle);
		return xrs;
	}

	private double dRobotToSpringY() {
		yrs = distanceX * Math.sin(drsAngle);
		return yrs;
	}

	private double dSpringToFrontX(double dsfIN) {// this value should be in
													// inches
		xsf = dsfIN * Math.cos(dsfAngle);
		return xsf;
	}

	private double dSpringToFrontY(double dsfIN) {
		ysf = dsfIN * Math.sin(dsfAngle);
		return ysf;
	}

	/////////////////////////////////////////
	// methods uesed globaly in robot system
	/////////////////////////////////////////
	public double robotToFrontDX(double dsfIN) {
		xrf = dRobotToSpringX() + dSpringToFrontX(dsfIN);
		return xrf; // this is in inches
	}

	public double robotToFrontDY(double dsfIN) {
		yrf = dRobotToSpringY() + dSpringToFrontY(dsfIN);
		return yrf;// this is inches

	}

	public double rotateRobotAngle() {// this is how much you need to rotate the
										// robot in radions
		double rRotate = prespecDeg * (Math.PI / 180);
		return rRotate;
	}

	public void refereshImageValues() {
		prespecDeg = SmartDashboard.getNumber("prespecDeg", 0);
		offsetXDeg = SmartDashboard.getNumber("OFFSET_X_DEG", 0);
		distanceX = SmartDashboard.getNumber("DISTANCE_X", 0);
		drsAngle = (offsetXDeg + 90) * (Math.PI / 180);// degrees from robot
		dsfAngle = (prespecDeg - 90) * (Math.PI / 180);
	}
}
