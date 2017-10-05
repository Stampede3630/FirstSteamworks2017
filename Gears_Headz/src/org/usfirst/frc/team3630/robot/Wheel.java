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
	 * Sets velocity in defined units for specific wheel
	 * @param velocity desired velocity
	 */
	 public void setVelocity (int velocity);
	 
	 /**
	  * gets the position of the encoder
	  * @return position of the encoder in defined units
	  */
	 public int getEncoderPosition ();
	 /**
	  * gets the velocity of the encoder
	  * @return velocity of encoder in defined units (usually units/second)
	  */
	 public int getEncoderVelocity ();
	 /**
	  * Sets the velocity PID controller. Would reccomend keeping it to defined constants
	  * @param kP
	  * @param kI
	  * @param kD
	  */
	 public void setPID (float kP, float kI, float kD);
	 /**
	  * Reset the encoder distance
	  */
	 public void resetEncoder ();
	 
}
