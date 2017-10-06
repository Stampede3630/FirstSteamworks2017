/**
 * 
 */
package org.usfirst.frc.team3630.robot;

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
	private Talon _talon;
	public PIDController pPID;
	public PIDController vPID;
	private AltEncoderPID encoderPIDv;
	private vConverter convertT;
	
	class vConverter implements PIDOutput, PIDSource {
		double vAdjust;
		double vFeed;
		
		public void pidWrite(double output) {
			// TODO Auto-generated method stub
			vAdjust=output;
			
		}

		@Override
		public void setPIDSourceType(PIDSourceType pidSource) {
			pidSource= PIDSourceType.kRate;
			
			
		}

		@Override
		public PIDSourceType getPIDSourceType() {
			// TODO Auto-generated method stub
			return PIDSourceType.kRate;
		}

		
	
		public void setVDesired(double vAdjust) {
			vFeed= vAdjust;
			
		}
		public double pidGet() {
			// need to fix
			return vFeed+vAdjust;
		}
		
		
	}

	
	class  AltEncoderPID implements PIDSource{

		PIDSourceType _PIDSourceType;
		Encoder aEncoder;
		/**
		 * 
		 * @param e encoder 
		 * 
		 * @param pidSource wheather input is a dispacement or a rate 
		 */
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
	
	public TalonSR (int talonPin, int encoderPinA, int encoderPinB, boolean talonReversed,int distPerPulse, boolean encoderReversed,double kp, double ki,double kd,double kf, double kppp, double kip, double  kdp, double kfp) {
		_encoder = new Encoder(encoderPinA, encoderPinB, encoderReversed );
		_talon = new Talon(talonPin);
		_talon.setInverted(talonReversed);
		_encoder.setDistancePerPulse(distPerPulse );
		_encoder.setPIDSourceType(PIDSourceType.kDisplacement);
		encoderPIDv= new AltEncoderPID(_encoder,PIDSourceType.kRate);
		vPID = new PIDController (kp,ki,kd,kf,convertT,_talon);
		
		pPID = new PIDController(kpp,kip,kdp,kfp,_encoder,convertT);
		
		
		
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
	@Override
	public void selfTest() {
		// TODO Auto-generated method stub
		
	}

}
