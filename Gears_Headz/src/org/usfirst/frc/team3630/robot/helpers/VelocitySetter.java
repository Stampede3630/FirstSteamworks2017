package org.usfirst.frc.team3630.robot.helpers;

import org.usfirst.frc.team3630.robot.Consts;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;

public class VelocitySetter extends AutoWheelInput implements PIDOutput {
	
	double positionAdjustment;
	double setSpeed;
	
	PIDController _pid;
	
	//we are setting a PID controller to a pid controller. Dosn't make scene to me. This PID controlller not 
	// This could cause us to return null
	public VelocitySetter(PIDController pid) {
	// we don't fully define a new pid
	// = new PID controller, and i don't think using super is working given that it isn't null anymore
	//pid= new super(PIDController());
		// wouldn't it need more defintion
		// 
		pid= new PIDController();
		_pid = pid;
	}
	
	public VelocitySetter () {
		super ();
	}
	
	public void setPIDController (PIDController pid) {
		_pid = pid;
	}
	public void pidWrite(double output) {
		positionAdjustment = output;
	}
	
	public void set(double speed) {
		if(_pid != null) {
		setSpeed = speed * Consts.mecanumSanitizer + positionAdjustment;
		_pid.setSetpoint(setSpeed); //Used to make sure that the sanitization isn't activated beforehand
	}
		else System.out.println("!!!! PID Controller Set ignored due to improper initialization !!!!");
	}

}
