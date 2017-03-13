package org.usfirst.frc.team3630.robot;
import edu.wpi.first.wpilibj.CounterBase.EncodingType;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.SpeedController;

public class Wheel {
	private static final int pulsesPerRevolution = 250;
	
	public Encoder encoder;
	public Talon talon;

	private talonConverter pidToTalon;
	
	public PIDController pid;
	private double kP = 1, kI = 0, kD = 0;

	
	public Wheel (int encoderChannel1, int encoderChannel2, int talonChannel, boolean reversed){
		encoder = new Encoder(encoderChannel1, encoderChannel2, reversed, EncodingType.k4X);
		
		talon = new Talon(talonChannel);		
		talon.setInverted(reversed);
		
		pidToTalon = new talonConverter(talon);
		
		encoder.setMaxPeriod(1);
		// Define distance in terms of INCHES
		encoder.setDistancePerPulse(Consts.mecanumWheelRadiusInches * 2*Math.PI / pulsesPerRevolution);
		encoder.setMinRate(10);
		//encoder.setReverseDirection(reversed);
		encoder.setSamplesToAverage(7);
		encoder.setPIDSourceType(PIDSourceType.kRate);
		
		//PID WILL ONLY DEAL WITH INCHES/SECOND
		pid = new PIDController(kP, kI, kD, encoder, pidToTalon);
		pid.setInputRange(-50, 50);
		pid.setOutputRange(-50, 50);
		pid.enable();
	}
	
	public void setWheelSpeed (double speed){
		//Need to make this constant better.

		pid.setSetpoint(speed);
		
		SmartDashboard.putNumber("PID Setpoint "+String.valueOf(talon.getChannel()), pid.getSetpoint());
		SmartDashboard.putNumber("PID P"+String.valueOf(talon.getChannel()), pid.getP());
		SmartDashboard.putNumber("PID result"+String.valueOf(talon.getChannel()), pid.get());
		
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
