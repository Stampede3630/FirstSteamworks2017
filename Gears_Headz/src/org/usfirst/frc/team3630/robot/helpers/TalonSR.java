/**
 * 
 */
package org.usfirst.frc.team3630.robot.helpers;



import org.usfirst.frc.team3630.robot.Consts;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;

/**
 * @author sam a
 *
 */
public class TalonSR implements Wheel {

	private Encoder _encoder;
	public Talon _talon;
	public PIDController pPID;
	public PIDController vPID;
	
	/// need to define Alt Encoder PID and Velocity Adjuster
	private EncoderPIDSource velocityEncoderValues;
	private PositionToVelocityPIDConverter _velocityAdjuster;

	public TalonSR(int talonPin, int encoderPinA, int encoderPinB, boolean talonReversed, int distPerPulse,
			boolean encoderReversed) {

		_encoder = new Encoder(encoderPinA, encoderPinB, encoderReversed);
		_encoder.setDistancePerPulse(distPerPulse);
		_encoder.setPIDSourceType(PIDSourceType.kDisplacement);

		_talon = new Talon(talonPin);
		_talon.setInverted(talonReversed);

		velocityEncoderValues = new EncoderPIDSource(_encoder, PIDSourceType.kRate);

		vPID = new PIDController(Consts.kP_velocity, Consts.kI_velocity, Consts.kD_velocity, Consts.kF_velocity,  _velocityAdjuster, _talon);
		pPID = new PIDController(Consts.kP_position, Consts.kI_position, Consts.kD_position, Consts.kF_position, _encoder, _velocityAdjuster);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.usfirst.frc.team3630.robot.Wheel#setMode(boolean)
	 */
	@Override
	public void setMode(boolean PIDControl) {
		if (PIDControl) {
			vPID.enable();
			pPID.enable();
		} else {
			vPID.disable();
			pPID.disable();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.usfirst.frc.team3630.robot.Wheel#setVelocity(int)
	 */
	@Override
	public void setVelocity(int velocity) {
		_velocityAdjuster.setVDesired(velocity);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.usfirst.frc.team3630.robot.Wheel#getEncoderPosition()
	 */
	@Override
	public double getEncoderPosition() {
		return _encoder.getDistance();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.usfirst.frc.team3630.robot.Wheel#getEncoderVelocity()
	 */
	@Override
	public double getEncoderVelocity() {
		return _encoder.getRate();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.usfirst.frc.team3630.robot.Wheel#setPID(boolean, float, float,
	 * float)
	 */
	@Override
	public void setPID(boolean position, double kP, double kI, double kD, double kF) {
		if (position) {
			pPID.setPID(kP, kI, kD, kF);
		} else {
			vPID.setPID(kP, kI, kD, kF);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.usfirst.frc.team3630.robot.Wheel#resetEncoder()
	 */
	@Override
	public void resetEncoder() {
		_encoder.reset();
		
		
	}

	@Override
	public void selfTest() {
		// TODO: implement this
	}

}
