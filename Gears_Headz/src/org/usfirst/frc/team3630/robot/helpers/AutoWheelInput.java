package org.usfirst.frc.team3630.robot.helpers;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.PIDController;
import org.usfirst.frc.team3630.robot.Consts;


/**
 * @author aronsa
 * AutoWheelInput is used to convert the robotDrive values for position and velocity to PID setpoints
 */

public class AutoWheelInput implements SpeedController {
	private  PIDController _pid ;
	private double setSpeed; 
	private int speedK; //used for inversion
 
	/**
	 * constructor for converter of the robotDrive values for position and velocity to PID setpoints
	 * @param pid PID controller for wheel position or velocity
	 */
	public AutoWheelInput (PIDController pid) {
		speedK = 1;
		_pid = pid;
	}
	public void pidWrite(double output) {
		//Will not be used.
	}

	public double get()  {
		if(setSpeed ==_pid.getSetpoint())
			return setSpeed;
		else
			return -1; //There is a problem at this point, as the PIDcontroller isn't updating
	}

	public void set(double speed) {
		setSpeed = speed * Consts.mecanumSanitizer;
		_pid.setSetpoint(setSpeed); //Used to make sure that the sanitization isn't activated beforehand
		if(!_pid.isEnabled()) enable();
	}

	public void setInverted(boolean isInverted) {
		//shouldn't be of concern either, as everything SHOULD always be pointed forward. 
		if (isInverted){
			speedK = -1;
		}
		else {
			speedK = 1;
		}
		
	}
		
	public void disable() {
		/// disables pid controller 
		_pid.disable();
		
	}
	
	public void enable() {
		_pid.enable();
	}

	public void stopMotor() {
		/// set motors to 0 
		_pid.disable();
		
	}
	public boolean getInverted() {
		// TODO Auto-generated method stub
		if(speedK == 1)
			return true;
		else
			return false;
	}

}
