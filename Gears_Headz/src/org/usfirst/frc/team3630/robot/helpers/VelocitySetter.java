package org.usfirst.frc.team3630.robot.helpers;

import org.usfirst.frc.team3630.robot.Consts;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;

public class VelocitySetter extends AutoWheelInput implements PIDOutput {
	
	double positionAdjustment;
	double setSpeed;
	
	
	private PIDController _pid;
	public VelocitySetter(PIDController pid) {
		super(pid);
		_pid = pid;
	}

	

	
	public VelocitySetter() {
		super();
	}

	public void setPIDController (PIDController pid) {
		//@liam, this is the follow-up constructor
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
