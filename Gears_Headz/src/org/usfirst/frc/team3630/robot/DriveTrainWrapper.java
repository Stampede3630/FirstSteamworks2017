public interface DriveTrainWrapper {
	/**
	 * sets PID or power/teleop control
	 * @param PIDControl if true, is auto/PID control. If false, power/teleop control.
	 */
	public void setState (boolean PIDControl);
	/**
	 * defines velocity to be run through wpilib drivetrain
	 * @param velocity defines velocity in units
	 */
	public void defineVelocity (int velocity); 
	
 }