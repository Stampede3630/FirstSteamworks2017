package org.usfirst.frc.team3630.robot.helpers;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;


public class XBoxHelper extends XboxController {
	
	public XBoxHelper (int port) {
		super (port);
	}
	
	
	public double getRoundX() {
		double result = super.getX(GenericHID.Hand.kLeft);
		SmartDashboard.putNumber("Joystick X", result);
		return result;
	}

	public double getRoundY() {
		double result = super.getY(GenericHID.Hand.kLeft);
		SmartDashboard.putNumber("Joystick Y", result);

		return result;
	}

	public double getRoundTwist() {
		double result = super.getX(GenericHID.Hand.kRight);
		SmartDashboard.putNumber("Joystick Twist", result);

		return result;
	}
}
