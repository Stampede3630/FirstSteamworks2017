package org.usfirst.frc.team3630.robot;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.JoystickBase;
import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;


public class DriveTrain {

	XboxController m_Joystick;// 2 for shooting and driving
	VisionMath vision;
	// AnalogInput ai0;

	// boolean driveAutoCorrect;
	// double driveStrength;

	// Made new gyro class
	// AnalogGyro gyro;
	// NAVX
	// Sensors sensors;

	// initialize drives
	HomebrewMecanum mecanumDrive;
	boolean directionForward = true;

	public DriveTrain() {
		SmartDashboard.putBoolean("PID Control", false);
		SmartDashboard.putBoolean("vision pid", false);

		mecanumDrive = new HomebrewMecanum(Consts.driveMotorFrontLeft, Consts.driveMotorBottomLeft,
				Consts.driveMotorFrontRight, Consts.driveMotorBottomRight);
		m_Joystick = new XboxController(Consts.joystickComPort);
		SmartDashboard.putNumber("Desired Distance", 0);
	}

	public double getRoundX() {
		double result = m_Joystick.getX(GenericHID.Hand.kLeft);
		SmartDashboard.putNumber("Joystick X", result);
		return result;
	}

	public double getRoundY() {
		double result = m_Joystick.getY(GenericHID.Hand.kLeft);
		SmartDashboard.putNumber("Joystick Y", result);

		return result;
	}

	public double getRoundTwist() {
		double result = m_Joystick.getX(GenericHID.Hand.kRight);
		SmartDashboard.putNumber("Joystick Twist", result);

		return result;
	}

	public void teleopInit() {
		mecanumDrive.fL.encoder.reset();
		mecanumDrive.fR.encoder.reset();
		mecanumDrive.rL.encoder.reset();
		mecanumDrive.rR.encoder.reset();
	}

	public void telopPeriodic() {
		// WPILIB Version
		/*
		 * m_robotDrive.mecanumDrive_Cartesian(m_Joystick.getX()/5,
		 * m_Joystick.getTwist(), m_Joystick.getY(),0);
		 */
		// Homebrew Version


//
//		boolean usePid = SmartDashboard.getBoolean("PID Control", false);
//		boolean visionPid = SmartDashboard.getBoolean("vision pid", false);
//		SmartDashboard.putBoolean("Is PID1 Enabled?", mecanumDrive.fL.pid.isEnabled());
//		if (usePid) {
//			mecanumDrive.setAllPID();
//			mecanumDrive.fL.pid.enable();
//			mecanumDrive.fR.pid.enable();
//			mecanumDrive.rL.pid.enable();
//			mecanumDrive.rR.pid.enable();
//			double speedX, speedY, speedTheta;
//			if (!visionPid) {
//				speedX = SmartDashboard.getNumber("Desired Distance X", 0) * Math.PI * 2;
//				speedY = SmartDashboard.getNumber("Desired Distance Y", 0) * Math.PI * 2;
//				speedTheta = SmartDashboard.getNumber("Desired Distance theta", 0) * Math.PI * 2;
//				SmartDashboard.putString("Drive State: ", "Standard PID");
//			} else {
//				vision.refereshImageValues();
//				speedX = vision.robotToFrontDX(48);
//				speedY = vision.robotToFrontDY(48);
//				speedTheta = vision.rotateRobotAngle()*180/Math.PI;
//				SmartDashboard.putString("Drive State: ", "Vision PID");
//
//			}
//
//			double[] distanceResult = mecanumDrive.mecanumCalc(speedX, speedY, speedTheta, true);
//
//			SmartDashboard.putBoolean("PID at Target? " + String.valueOf(mecanumDrive.fL.talon.getChannel()),
//					mecanumDrive.fL.pid.onTarget());
//			SmartDashboard.putBoolean("PID at Target? " + String.valueOf(mecanumDrive.rL.talon.getChannel()),
//					mecanumDrive.rL.pid.onTarget());
//			SmartDashboard.putBoolean("PID at Target? " + String.valueOf(mecanumDrive.fR.talon.getChannel()),
//					mecanumDrive.fR.pid.onTarget());
//			SmartDashboard.putBoolean("PID at Target? " + String.valueOf(mecanumDrive.rR.talon.getChannel()),
//					mecanumDrive.rR.pid.onTarget());
//
//			mecanumDrive.fL.setWheelSpeed(distanceResult[0]);
//			mecanumDrive.rL.setWheelSpeed(distanceResult[1]);
//			mecanumDrive.fR.setWheelSpeed(distanceResult[3]);
//			mecanumDrive.rR.setWheelSpeed(distanceResult[2]);
//		} else {
//			mecanumDrive.fL.pid.disable();
//			mecanumDrive.fR.pid.disable();
//			mecanumDrive.rL.pid.disable();
//			mecanumDrive.rR.pid.disable();

			double speedy = 1;
			if (m_Joystick.getRawButton(Consts.buttonSprint)||m_Joystick.getRawButton(Consts.buttonSprintAlternate)) speedy = Consts.fastK;
			else speedy = Consts.slowK;
			if (m_Joystick.getRawButton(Consts.buttonSwitchDirection)) directionForward = !directionForward;
			if  (directionForward) speedy *= -1;
			
			mecanumDrive.driveImplementation(-getRoundY()*speedy,-getRoundX()*speedy,-getRoundTwist()*speedy/2, true);			
			SmartDashboard.putString("Drive State: ", "Driver Operation");
//		}
	}

}
