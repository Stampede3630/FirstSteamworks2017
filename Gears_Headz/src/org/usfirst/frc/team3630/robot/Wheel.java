package org.usfirst.frc.team3630.robot;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSourceType;

public class Wheel {
	private static final int pulsesPerRevolution = 250;
	public PIDController pid;
	public Encoder encoder;
	public Talon talon;
	private talonConverter pidToTalon;
	private double kP = 1, kI = 0, kD = 0, kF = 1;
	
	public Wheel (int encoderChannel1, int encoderChannel2, int talonChannel, boolean reversed){
		encoder = new Encoder(encoderChannel1, encoderChannel2, reversed);
		talon = new Talon(talonChannel);
		pidToTalon = new talonConverter(talon);
		talon.setInverted(reversed);
		//pid = new PIDController(kP, kI, kD, this.pidGet(), wheelControl.pidWrite);
		encoder.setMaxPeriod(1);
		// Define distance in terms of radians
		encoder.setDistancePerPulse((2 * Math.PI) / pulsesPerRevolution);
		encoder.setMinRate(10);
		encoder.setReverseDirection(reversed);
		encoder.setSamplesToAverage(7);
		encoder.setPIDSourceType(PIDSourceType.kRate);
		
		pid = new PIDController(kP, kI, kD, kF, encoder, pidToTalon);
	}
	
	public void setWheelSpeed (double speed){
		pid.setSetpoint(speed*2000);
	}

	double getDistDegrees()
	{
		return encoder.getDistance() * 180.0 / Math.PI; // placeholder
	}

	double getDistRadians()
	{
		return encoder.getDistance(); // placeholder
	}

	/**
	 * @return encoder distance in inches
	 */
	double getDistInches()
	{
		return getDistRadians() * Consts.mecanumWheelRadiusInches;
	}

}
