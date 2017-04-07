package org.usfirst.frc.team3630.robot;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;


public class NavX {
	
	//public static Robot.ahrs Robot.ahrs;
	//Robot.ahrs = new Robot.ahrs(SPI.Port.kMXP);
	
	public void teleopPeriodic() {
		SmartDashboard.putNumber("Yaw", Robot.ahrs.getYaw());
//		SmartDashboard.putNumber("Pitch", Robot.ahrs.getPitch());
		SmartDashboard.putBoolean("Connected", Robot.ahrs.isConnected());
		SmartDashboard.putBoolean("Calibrating", Robot.ahrs.isCalibrating());
		SmartDashboard.putBoolean("Magnomitor Calibrated?", Robot.ahrs.isMagnetometerCalibrated());
//		SmartDashboard.putNumber("Rolling", Robot.ahrs.getRoll());
//		SmartDashboard.putNumber("Compass", Robot.ahrs.getCompassHeading());
		SmartDashboard.putNumber("Fused Heading", Robot.ahrs.getFusedHeading());
//		SmartDashboard.putNumber("Total Yaw", Robot.ahrs.getAngle());
//		SmartDashboard.putNumber("Yaw Rate", Robot.ahrs.getRate());
//		SmartDashboard.putNumber("Acceleration X", Robot.ahrs.getWorldLinearAccelX());
//		SmartDashboard.putNumber("Acceleration Y", Robot.ahrs.getWorldLinearAccelY());
		SmartDashboard.putBoolean("Is Moving", Robot.ahrs.isMoving());
		SmartDashboard.putBoolean("Is Rotating", Robot.ahrs.isRotating());
		SmartDashboard.putNumber("X Velocity", Robot.ahrs.getVelocityX());
		SmartDashboard.putNumber("Y Velocity", Robot.ahrs.getVelocityY());
		SmartDashboard.putNumber("X Displacement", Robot.ahrs.getDisplacementX());
		SmartDashboard.putNumber("Y Displacement", Robot.ahrs.getDisplacementY());
	
//		Robot.ahrs.startLiveWindowMode();
	}
	
	public void calibrateHeading () {
		Robot.ahrs.zeroYaw();	
	}
	
	public double getHeading () {
		return (double) Robot.ahrs.getYaw();
	}
	
	public double pidGet () {
		return (double) Robot.ahrs.getFusedHeading();
	}
	

}
