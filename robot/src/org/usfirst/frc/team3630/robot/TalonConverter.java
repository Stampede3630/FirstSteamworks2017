package org.usfirst.frc.team3630.robot;

import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class TalonConverter implements PIDOutput {
	private Talon talon;
	private double previousSpeed;
	public TalonConverter (Talon t){
		talon = t;
	}
	
	public void pidWrite(double output) {
		double maxAccel =previousSpeed +Consts.maxAcceleration;
		if (output > maxAccel) output = maxAccel; 
		if (Math.abs(output)<= 1) {
			talon.set(output);
		//	SmartDashboard.putNumber("Talon Output" + String.valueOf(talon.getChannel()), output);
		}
		else if (output > 0) talon.set(1);
		else if (output < 0) talon.set(-1);
	}

}
