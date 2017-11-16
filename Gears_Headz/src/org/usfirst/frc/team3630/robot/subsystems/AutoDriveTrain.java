package org.usfirst.frc.team3630.robot.subsystems;

import org.usfirst.frc.team3630.robot.Consts;
import org.usfirst.frc.team3630.robot.helpers.AutoPaths;
import org.usfirst.frc.team3630.robot.helpers.AutoWheelInput;
import org.usfirst.frc.team3630.robot.helpers.VelocitySetter;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.RobotDrive;

public class AutoDriveTrain {
	private RobotDrive positionCalculator;
	private RobotDrive velocityCalculator;
	
	public AutoWheelInput p_fL, p_rL, p_rR, p_fR;
	public VelocitySetter v_fL, v_rL, v_fR, v_rR;
	private long startTime;
	
	public AutoDriveTrain () {
		//constr null neer put in  a pid controller
		//THIS IS STEP 1
		p_fL = new AutoWheelInput ();
		p_rL = new AutoWheelInput ();
		p_fR = new AutoWheelInput ();
		p_rR = new AutoWheelInput ();
				
		v_fL = new VelocitySetter ();
		v_rL = new VelocitySetter ();
		v_fR = new VelocitySetter ();
		v_rR = new VelocitySetter ();
		
		System.out.println("(1) Initialized autowheelinput and velocityssetter");
		
		//THS IS STEP 2
		positionCalculator = new RobotDrive (p_fL, p_rL, p_fR, p_rR);
		velocityCalculator = new RobotDrive (v_fL, v_rL, v_fR, v_rR);	
		
		System.out.println("(2) Initialized positioncalculator and velocitycalculator");
	}
	
	public void followUp (PIDController  pfL, PIDController prL, PIDController pfR, PIDController prR,PIDController vfL,PIDController  vrL, PIDController vfR, PIDController vrR ) {
		startTime = System.currentTimeMillis();
		//THIS IS STEP 4
		/// set motors to PID controllers 
		p_fL.setPIDController(pfL);
		p_rL.setPIDController(prL);
		p_fR.setPIDController(pfR);
		p_rR.setPIDController(prR);
		
		v_fL.setPIDController(vfL);
		v_fR.setPIDController(vfR);
		v_rL.setPIDController(vrL);
		v_rR.setPIDController(vrR);
		System.out.println("(4) Did follow-up initialization for velocitysetters and autowheelinputs.");
	// enable pid controllers
		pfL.enable();
		prL.enable();
		pfR.enable();
		prR.enable();
		vfL.enable();
		vfR.enable();
		vrL.enable();
		vrR.enable();
		
		
		
		
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
