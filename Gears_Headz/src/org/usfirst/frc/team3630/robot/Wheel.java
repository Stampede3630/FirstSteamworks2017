package org.usfirst.frc.team3630.robot;
import edu.wpi.first.wpilibj.CounterBase.EncodingType;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.SpeedController;

public class Wheel {
	private static final int pulsesPerRevolution = 250;
	
	public Encoder encoder;
	public Talon talon;

	
	public PIDController pid;

	
	/**
	 * @param encoderChannel1
	 * @param encoderChannel2
	 * @param talonChannel
	 * @param reversed
	 */
	public Wheel (int encoderChannel1, int encoderChannel2, int talonChannel, boolean reversed){
		encoder = new Encoder(encoderChannel1, encoderChannel2, reversed, EncodingType.k4X);
		
		talon = new Talon(talonChannel);		
		talon.setInverted(reversed);
				
		encoder.setMaxPeriod(1);
		// Define distance in terms of INC
		encoder.setDistancePerPulse(Consts.mecanumWheelRadiusInches*2*Math.PI/pulsesPerRevolution);
		encoder.setMinRate(10);
		encoder.setReverseDirection(!reversed);
		encoder.setSamplesToAverage(7);

		encoder.setPIDSourceType(PIDSourceType.kDisplacement);
		
		//PID WILL ONLY DEAL WITH INCHES/SECONDz
		pid = new PIDController(
				SmartDashboard.getNumber("drivetrain kP", .05),
				SmartDashboard.getNumber("drivetrain kI", 0),
				SmartDashboard.getNumber("drivetrain kD", 0),
				encoder, 
				talon
				);
		//pid.setInputRange(-100, 100);
		pid.setOutputRange(-1, 1);
		pid.setAbsoluteTolerance(1);
		pid.disable();
	}
	
	public void setWheelSpeed (double speed){
		//Need to make this constant better.

		
		pid.setPID(
			SmartDashboard.getNumber("drivetrain kP", .1),
			SmartDashboard.getNumber("drivetrain kI", 0),
			SmartDashboard.getNumber("drivetrain kD", 0)
		);
		
		pid.setSetpoint(speed);//speed);
		SmartDashboard.putNumber("PID Encoder Input"+String.valueOf(talon.getChannel()), encoder.pidGet());
		SmartDashboard.putNumber("PID Setpoint "+String.valueOf(talon.getChannel()), pid.getSetpoint());
		SmartDashboard.putNumber("PID P"+String.valueOf(talon.getChannel()), pid.getP());
		SmartDashboard.putNumber("PID result"+String.valueOf(talon.getChannel()), pid.get());
		SmartDashboard.putNumber("Encoder Distance"+String.valueOf(talon.getChannel()), encoder.getDistance());
				
		}

	
	
	

	public double getDistDegrees()
	{
		return encoder.getDistance() * 180.0 / Math.PI; 
	}

	public double getDistRadians()
	{
		return encoder.getDistance(); 
	}

	/**
	 * @return encoder distance in inches
	 */
	public double getDistInches()
	{
		return getDistRadians() * Consts.mecanumWheelRadiusInches;
	}

}
