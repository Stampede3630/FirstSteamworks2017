package org.usfirst.frc.team3630.robot.helpers;

import org.usfirst.frc.team3630.robot.Consts;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;

public class VelocitySetter extends AutoWheelInput implements PIDOutput {
	
	double positionAdjustment;
	double setSpeed;
	
	PIDController _pid;
	public VelocitySetter(PIDController pid) {
		super(pid);
		_pid = pid;
	}

	
	public void pidWrite(double output) {
		positionAdjustment = output;
	}
	
	public void set(double speed) {
		setSpeed = speed * Consts.mecanumSanitizer; //Used to make sure that the sanitization isn't activated beforehand
		_pid.setSetpoint(setSpeed + positionAdjustment); //Added position adjustment from position PID controller
		if(!_pid.isEnabled()) enable();
	}

}
