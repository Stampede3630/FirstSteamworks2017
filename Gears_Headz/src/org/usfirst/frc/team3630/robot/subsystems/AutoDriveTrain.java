package org.usfirst.frc.team3630.robot.subsystems;

import org.usfirst.frc.team3630.robot.Consts;
import org.usfirst.frc.team3630.robot.helpers.AutoWheelInput;
import edu.wpi.first.wpilibj.RobotDrive;

public class AutoDriveTrain {
	private RobotDrive positionCalculator;
	private RobotDrive velocityCalculator;
	
	private AutoWheelInput p_fL, p_bL, p_fR, p_bR;
	private AutoWheelInput v_fL, v_bL, v_fR, v_bR;
	
	private long startTime;

	public AutoDriveTrain () {
		positionCalculator = new RobotDrive (p_fL, p_bL, p_fR, p_bR);
		velocityCalculator = new RobotDrive (v_fL, v_bL, v_fR, v_bR);	

	}
	
	public void autoInit () {
		startTime = System.currentTimeMillis();
	}
	public void autoIterative () {
		double timeElapsed = System.currentTimeMillis() - startTime;
		timeElapsed = timeElapsed - (timeElapsed % Consts.period); //Rounded to the nearest 50ms
		timeElapsed /= 100; //Conversion from seconds to milliseconds
		
		
		
	}
	
	public void setPosition (double x) { //This is the current method that will be used, as motion path is only 1D.
		x/= Consts.mecanumSanitizer; //This makes sure that the values going through the robotdrive remain between -1 and 1
		
		positionCalculator.mecanumDrive_Cartesian(x, 0,0,0);
	}
	
	public void setPosition (double x, double y, double rotation) { //This may be used in the future for 2D motion.\
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
