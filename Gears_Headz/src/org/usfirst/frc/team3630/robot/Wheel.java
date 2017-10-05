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
	 public int getEncoderPosition ();
	 /**
	  * gets the velocity of the encoder
	  * @return velocity of encoder in defined units (usually units/second)
	  */
	 public int getEncoderVelocity ();
	 /**
	  * Sets the velocity PID controller. Would reccomend keeping it to defined constants
	  * @param position if setting position controller true, if setting velocity controller false
	  * @param kP
	  * @param kI
	  * @param kD
	  */
	 public void setPID (boolean position, float kP, float kI, float kD);
	 /**
	  * Reset the encoder distance
	  */
	 /**
	  * 
	  * @param idealPosition the desired position of the wheel
	  * @param actualPosition the actual position of the wheel
	  * @param idealVelocity the desired velocity of the wheel, without position adjustment
	  * @return
	  */
	 public int positionAdjustment (int idealPosition, int actualPosition, int idealVelocity);
	 /**
	  * runs velocity PID
	  * @param requestedVelocity 
	  * @return power level for motor controller
	  */
	 
	 public void selfTest () {
		 //implement this
	 }
	 public int velocityAdjustment (int requestedVelocity);
	 public void resetEncoder ();
	 
}
