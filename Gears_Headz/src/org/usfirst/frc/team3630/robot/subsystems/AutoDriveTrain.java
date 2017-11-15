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
	
	public AutoWheelInput p_fL;
	
	public AutoWheelInput p_rL;
	public AutoWheelInput  p_rR;
	public AutoWheelInput	p_fR;
	// Neec to define velocity setter
	public VelocitySetter v_fL, v_rL, v_fR, v_rR;
	
	private long startTime;
	// fixed this you made thees local varibles when you were init them
	// neeed to put a pid controller in them 
	public AutoDriveTrain () {
		//constr null neer put in  a pid controller
		 p_fL = new AutoWheelInput (null);
		p_rL = new AutoWheelInput (null);
		p_fR = new AutoWheelInput (null);
		p_rR = new AutoWheelInput (null);
		System.out.println("init auto wheel input");
		
		
		v_fL = new VelocitySetter (null);
		 v_rL = new VelocitySetter (null);
		 v_fR = new VelocitySetter (null);
		 v_rR = new VelocitySetter (null);
			System.out.println("init velocity controlers");
		positionCalculator = new RobotDrive (p_fL, p_rL, p_fR, p_rR);
		System.out.println(" positon calculator not null");
		velocityCalculator = new RobotDrive (v_fL, v_rL, v_fR, v_rR);	
		System.out.println(" velocity calculator not null");
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

	public void setPosition (double x) { //This is the current method that will be used, as motion path is only 1D.
		x/= Consts.mecanumSanitizer; //This makes sure that the values going through the robotdrive remain between -1 and 1
		
		positionCalculator.mecanumDrive_Cartesian(x, 0,0,0);
	}
	public void setVelocity (double x) {
		x/= Consts.mecanumSanitizer;
		velocityCalculator.mecanumDrive_Cartesian(x, 0,0,0);
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
	
	public void setPosition (double x, double y, double rotation) { //This may be used in the future for 2D motion.\
		x/= Consts.mecanumSanitizer;
		y/= Consts.mecanumSanitizer;
		rotation /= Consts.mecanumSanitizer;
		
		positionCalculator.mecanumDrive_Cartesian(x, y, rotation, 0);
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
