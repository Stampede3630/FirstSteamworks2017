package org.usfirst.frc.team3630.robot;

 /**
 * @author robotics
 *
 */

public interface Wheel {
	 /*SpeedController _motorController;
	 PIDController _pPIDController;
	 Encoder _encoder;
	 */
	 /**
	  * Sets PID or power mode for motor controller
	 * @param PIDControl whether to activate PID control or just use power values
	 */
	public void setMode (boolean PIDControl);
	
	/**
	 * Sets velocity in wheel power
	 * @param velocity desired velocity
	 */
	 public void setVelocity (int velocity);
	 
	 /**
	  * gets the position of the encoder
	  * @return position of the encoder in defined units
	  */

	 /**
	  * gets the velocity of the encoder
	  * @return velocity of encoder in defined units (usually units/second)
	  */
	 public double getEncoderVelocity ();
	 /**
	  * Sets the velocity PID controller. Would recommend keeping it to defined constants
	  * @param position if setting position controller true, if setting velocity controller false
	  * @param kP
	  * @param kI
	  * @param kD
	  */
	 public void setPID (boolean position, double kP, double kI, double kD, double kF);
	 /**
	  * Reset the encoder distance
	  */

	 /**
	  * runs velocity PID
	  * @param requestedVelocity 
	  * @return power level for motor controller
	  */
	 
	 public void selfTest ();
		 //implement this
		 // can bus double check sensor 
	 public void resetEncoder ();

	double getEncoderPosition();
	 
}
