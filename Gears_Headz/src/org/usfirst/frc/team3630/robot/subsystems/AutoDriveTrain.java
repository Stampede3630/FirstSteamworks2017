package org.usfirst.frc.team3630.robot.subsystems;

import org.usfirst.frc.team3630.robot.Consts;
import org.usfirst.frc.team3630.robot.helpers.AutoPaths;
import org.usfirst.frc.team3630.robot.helpers.AutoWheelInput;
import org.usfirst.frc.team3630.robot.helpers.Wheel;
import org.usfirst.frc.team3630.robot.helpers.TalonSR;
import org.usfirst.frc.team3630.robot.helpers.VelocitySetter;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.RobotDrive;

public class AutoDriveTrain {
	private RobotDrive positionCalculator;
	private RobotDrive velocityCalculator;
	
	private AutoWheelInput p_fL, p_rL, p_fR, p_rR;
	
	// Neec to define velocity setter
	public VelocitySetter v_fL, v_rL, v_fR, v_rR;
	
	private long startTime;

	public AutoDriveTrain () {
		
		AutoWheelInput p_fL = new AutoWheelInput ();
		AutoWheelInput p_rL = new AutoWheelInput ();
		AutoWheelInput p_fR = new AutoWheelInput ();
		AutoWheelInput p_rR = new AutoWheelInput ();
		
		AutoWheelInput v_fL = new VelocitySetter ();
		AutoWheelInput v_rL = new VelocitySetter ();
		AutoWheelInput v_fR = new VelocitySetter ();
		AutoWheelInput v_rR = new VelocitySetter ();
		
		positionCalculator = new RobotDrive (p_fL, p_rL, p_fR, p_rR);
		velocityCalculator = new RobotDrive (v_fL, v_rL, v_fR, v_rR);	

	}
	
	public void autoInit (PIDController  pfL, PIDController prL, PIDController pfR, PIDController prR,PIDController vfL,PIDController  vrL, PIDController vfR, PIDController vrR ) {
		startTime = System.currentTimeMillis();
		/// set motors to PID controlors 
		p_fL.setPIDController(pfL);
		p_rL.setPIDController(prL);
		p_fR.setPIDController(pfR);
		p_rR.setPIDController(prR);
		
		v_fL.setPIDController(vfL);
		v_fR.setPIDController(vfR);
		v_rL.setPIDController(vrL);
		v_rR.setPIDController(vrR);
		
	// enable pid contrpp;;ers
		p_fL.enable();
		p_rL.enable();
		p_fR.enable();
		p_rR.enable();
		v_fL.enable();
		v_rL.enable();
		v_fR.enable();
		v_rR.enable();
		
		
		
		
	}
	public  void autoIterative () {
		double timeElapsed = System.currentTimeMillis() - startTime;
		timeElapsed = timeElapsed - (timeElapsed % Consts.period); //Rounded to the nearest 50ms
		timeElapsed /= 100; //Conversion from seconds to milliseconds
		
		double desiredPosition = -1;
		double desiredVelocity = -1;
		
			for (int i = 0; i< AutoPaths.path.length; i++) {
				if (AutoPaths.path[i][0]>=timeElapsed) { 
					desiredPosition = AutoPaths.path[i][2];
					desiredVelocity = AutoPaths.path[i][1];
				}
			}
			
		setPosition(desiredPosition);
		setVelocity(desiredVelocity);
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
	
	public void resetDriveTrain() {
		
	}
	
}
