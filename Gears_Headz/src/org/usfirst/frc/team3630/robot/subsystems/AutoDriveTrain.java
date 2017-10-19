package org.usfirst.frc.team3630.robot.subsystems;

import org.usfirst.frc.team3630.robot.Consts;
import org.usfirst.frc.team3630.robot.helpers.AutoWheelInput;

import edu.wpi.first.wpilibj.RobotDrive;

public class AutoDriveTrain {
	private RobotDrive positionCalculator;
	private RobotDrive velocityCalculator;
	
	private AutoWheelInput p_fL, p_bL, p_fR, p_bR;
	private AutoWheelInput v_fL, v_bL, v_fR, v_bR;
	
	
	public AutoDriveTrain () {
		positionCalculator = new RobotDrive (p_fL, p_bL, p_fR, p_bR);
		velocityCalculator = new RobotDrive (v_fL, v_bL, v_fR, v_bR);	
	}
	
	
	public void setPosition (double x) {
		x/= Consts.mecanumSanitizer;
		
		positionCalculator.mecanumDrive_Cartesian(x, 0,0,0);
	}
	public void setPosition (double x, double y, double rotation) {
		x/= Consts.mecanumSanitizer;
		y/= Consts.mecanumSanitizer;
		rotation /= Consts.mecanumSanitizer;
		
		positionCalculator.mecanumDrive_Cartesian(x, y, rotation, 0);
	}
	
	
	public void setVelocity (double x) {
		x/= Consts.mecanumSanitizer;
		velocityCalculator.mecanumDrive_Cartesian(x, 0,0,0);
	}
	public void setVelocity (double x, double y, double rotation) {
		x/= Consts.mecanumSanitizer;
		y/= Consts.mecanumSanitizer;
		rotation /= Consts.mecanumSanitizer;

		velocityCalculator.mecanumDrive_Cartesian(x, y, rotation, 0);
	}
	
}
