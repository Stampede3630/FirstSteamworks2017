package org.usfirst.frc.team3630.robot.helpers;

import edu.wpi.first.wpilibj.SpeedController;

import edu.wpi.first.wpilibj.PIDController;


public class AutoWheelInput implements SpeedController{
 private  PIDController _pid ;
 private double speed; 
public AutoWheelInput(PIDController _pid ) {
	
}
	public void pidWrite(double output) {
		// TODO Auto-generated method stub
		
		
	}

	public double get() {
		// TODO Auto-generated method stub
		return  _pid.getSetpoint();
	}

	public void set(double speed) {
		// TODO Auto-generated method stub
		_pid.setSetpoint(speed);
	}

	public void setInverted(boolean isInverted) {
		// TODO Auto-generated method stub
		if (isInverted ==true){
			
		}
		else {
		
		}
		
	}

	public boolean getInverted() {
		// TODO Auto-generated method stub
		return false;
	}

	public void disable() {
	
		/// disables pid controller 
		_pid.disable();
		
	}

	public void stopMotor() {
		// TODO Auto-generated method stub
		/// set motors to 0 
		_pid.setSetpoint(0);

	}

}
