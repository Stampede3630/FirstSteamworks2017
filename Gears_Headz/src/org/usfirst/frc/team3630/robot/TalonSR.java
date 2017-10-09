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
	private AltEncoderPID velocityEncoderValues;
	private VelocityAdjuster _velocityAdjuster;
	
	class VelocityAdjuster implements PIDOutput, PIDSource {
		double vAdjust;
		double vFeed;
		
		public void pidWrite(double output) {
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
		Encoder _e;
		/**
		 * 
		 * @param e encoder  
		 * @param pidSource wheather input is a dispacement or a rate 
		 */
		public AltEncoderPID(Encoder e, PIDSourceType pidSource){
			_e=e;
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
				return _e.getDistance();
				
			}
			else if ( PIDSourceType.kRate.equals(_PIDSourceType)) {
				return _e.getRate();
			}
			else {
				return -1;
			}
		}
		
	}
	
	public TalonSR (int talonPin, int encoderPinA, int encoderPinB, boolean talonReversed,int distPerPulse, boolean encoderReversed,
			double kp, double ki,double kd,double kf, double kpp, double kip, double  kdp, double kfp) {
		
		_encoder = new Encoder(encoderPinA, encoderPinB, encoderReversed );
		_encoder.setDistancePerPulse(distPerPulse );
		_encoder.setPIDSourceType(PIDSourceType.kDisplacement);
		
		_talon = new Talon(talonPin);
		_talon.setInverted(talonReversed);
		
		velocityEncoderValues= new AltEncoderPID(_encoder,PIDSourceType.kRate);
		
		vPID = new PIDController (kp,ki,kd,kf,_velocityAdjuster,_talon);
		pPID = new PIDController(kpp,kip,kdp,kfp,_encoder,_velocityAdjuster);		
		
	}
	
	/* (non-Javadoc)
	 * @see org.usfirst.frc.team3630.robot.Wheel#setMode(boolean)
	 */
	@Override
	public void setMode(boolean PIDControl) {
		if (PIDControl) {
			vPID.enable();
			pPID.enable();
		}
		else {
			vPID.disable();
			pPID.disable();
		}
	}

	/* (non-Javadoc)
	 * @see org.usfirst.frc.team3630.robot.Wheel#setVelocity(int)
	 */
	@Override
	public void setVelocity(int velocity) {
		_velocityAdjuster.setVDesired(velocity);
	}

	/* (non-Javadoc)
	 * @see org.usfirst.frc.team3630.robot.Wheel#getEncoderPosition()
	 */
	@Override
	public double getEncoderPosition() {
		return _encoder.getDistance();
	}

	/* (non-Javadoc)
	 * @see org.usfirst.frc.team3630.robot.Wheel#getEncoderVelocity()
	 */
	@Override
	public double getEncoderVelocity() {
		return _encoder.getRate();
	}

	/* (non-Javadoc)
	 * @see org.usfirst.frc.team3630.robot.Wheel#setPID(boolean, float, float, float)
	 */
	@Override
	public void setPID(boolean position, double kP, double kI, double kD, double kF) {
		if(position) {
			pPID.setPID(kP, kI, kD, kF);
		}
		else {
			vPID.setPID(kP, kI, kD, kF);
		}
	}

	/* (non-Javadoc)
	 * @see org.usfirst.frc.team3630.robot.Wheel#resetEncoder()
	 */
	@Override
	public void resetEncoder() {
		_encoder.reset();
	}
	@Override
	public void selfTest() {
		//TODO: implement this
	}

}
