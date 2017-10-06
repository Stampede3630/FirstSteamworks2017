/**
 * 
 */
package org.usfirst.frc.team3630.robot;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;

/**
 * @author sam a
 *
 */
public class TalonSR implements Wheel {
	
	private Encoder _encoder;
	private Talon _talon;
	public PIDController pPID;
	public PIDController vPID;
	class  AltEncoderPID implements PIDSource{
		PIDSourceType _PIDSourceType;
		Encoder aEncoder;
		
		public AltEncoderPID(Encoder e, PIDSourceType pidSource){
			aEncoder=e;
			_PIDSourceType= pidSource;
		}
		@Override
		public void setPIDSourceType(PIDSourceType pidSource) {
			
			_PIDSourceType= pidSource;
			
		}

		@Override
		public PIDSourceType getPIDSourceType() {
			// TODO Auto-generated method stub
			return _PIDSourceType;
		}

		@Override
		public double pidGet() {
			// TODO Auto-generated method stub
			if (PIDSourceType.kDisplacement.equals(_PIDSourceType)) {
				return aEncoder.getDistance();
				
			}
			else if ( PIDSourceType.kRate.equals(_PIDSourceType)) {
				return aEncoder.getRate();
			}
			else {
				return -1;
			}
		}
		
	}
	
	public TalonSR (int talonPin, int encoderPinA, int encoderPinB, boolean talonReversed,int distPerPulse, boolean encoderReversed) {
		_encoder = new Encoder(encoderPinA, encoderPinB, encoderReversed );
		_talon = new Talon(talonPin);
		_talon.setInverted(talonReversed);
		_encoder.setDistancePerPulse(distPerPulse );
		_en
		vPID = new PIDController (kp,ki,kd,);
	}
	/* (non-Javadoc)
	 * @see org.usfirst.frc.team3630.robot.Wheel#setMode(boolean)
	 */
	@Override
	public void setMode(boolean PIDControl) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see org.usfirst.frc.team3630.robot.Wheel#setVelocity(int)
	 */
	@Override
	public void setVelocity(int velocity) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see org.usfirst.frc.team3630.robot.Wheel#getEncoderPosition()
	 */
	@Override
	public int getEncoderPosition() {
		// TODO Auto-generated method stub
		return 0;
	}

	/* (non-Javadoc)
	 * @see org.usfirst.frc.team3630.robot.Wheel#getEncoderVelocity()
	 */
	@Override
	public int getEncoderVelocity() {
		// TODO Auto-generated method stub
		return 0;
	}

	/* (non-Javadoc)
	 * @see org.usfirst.frc.team3630.robot.Wheel#setPID(boolean, float, float, float)
	 */
	@Override
	public void setPID(boolean position, float kP, float kI, float kD) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see org.usfirst.frc.team3630.robot.Wheel#positionAdjustment(int, int, int)
	 */
	@Override
	public int positionAdjustment(int idealPosition, int actualPosition, int idealVelocity) {
		// TODO Auto-generated method stub
		return 0;
	}

	/* (non-Javadoc)
	 * @see org.usfirst.frc.team3630.robot.Wheel#velocityAdjustment(int)
	 */
	@Override
	public int velocityAdjustment(int requestedVelocity) {
		// TODO Auto-generated method stub
		return 0;
	}

	/* (non-Javadoc)
	 * @see org.usfirst.frc.team3630.robot.Wheel#resetEncoder()
	 */
	@Override
	public void resetEncoder() {
		// TODO Auto-generated method stub

	}

}
