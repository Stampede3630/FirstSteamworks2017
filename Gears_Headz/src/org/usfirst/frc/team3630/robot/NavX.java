package org.usfirst.frc.team3630.robot;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import com.kauailabs.navx.frc.*;
import edu.wpi.first.wpilibj.SPI;

public class NavX {
	
	AHRS ahrs;
	
	public NavX() {
		ahrs = new AHRS(SPI.Port.kMXP);
	}
	
	public void teleopPeriodic() {
		SmartDashboard.putNumber("Yaw", ahrs.getYaw());
		SmartDashboard.putNumber("Pitch", ahrs.getPitch());
		SmartDashboard.putBoolean("Connected", ahrs.isConnected());
		SmartDashboard.putBoolean("Calibrating", ahrs.isCalibrating());
		SmartDashboard.putNumber("Rolling", ahrs.getRoll());
		SmartDashboard.putNumber("Compass", ahrs.getCompassHeading());
		SmartDashboard.putNumber("Fused Heading", ahrs.getFusedHeading());
		SmartDashboard.putNumber("Total Yaw", ahrs.getAngle());
		SmartDashboard.putNumber("Yaw Rate", ahrs.getRate());
		SmartDashboard.putNumber("Acceleration X", ahrs.getWorldLinearAccelX());
		SmartDashboard.putNumber("Acceleration Y", ahrs.getWorldLinearAccelY());
		SmartDashboard.putBoolean("Is Moving", ahrs.isMoving());
		SmartDashboard.putBoolean("Is Rotating", ahrs.isRotating());
		SmartDashboard.putNumber("X Velocity", ahrs.getVelocityX());
		SmartDashboard.putNumber("Y Velocity", ahrs.getVelocityY());
		SmartDashboard.putNumber("X Displacement", ahrs.getDisplacementX());
		SmartDashboard.putNumber("Y Displacement", ahrs.getDisplacementY());
	
		ahrs.startLiveWindowMode();
	}
}
