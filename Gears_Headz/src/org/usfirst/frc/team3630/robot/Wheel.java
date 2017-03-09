package org.usfirst.frc.team3630.robot;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.PIDSourceType;

public class Wheel {
	private static final int pulsesPerRevolution = 250;
	public PIDController pid;
	public Encoder encoder;
	public Talon talon;
	private double kP = -.0001, kI = 0, kD = 0, kF = 1;
	
	public Wheel (int encoderChannel1, int encoderChannel2, int talonChannel, boolean reversed){
		encoder = new Encoder(encoderChannel1, encoderChannel2, reversed);
		
		talon = new Talon(talonChannel);		
		talon.setInverted(reversed);
		
		
		encoder.setMaxPeriod(.5);
		// Define distance in terms of radians
		encoder.setDistancePerPulse(2 * Math.PI / pulsesPerRevolution); //distance is in radians.
		encoder.setMinRate(10);
		//encoder.setReverseDirection(reversed);
		encoder.setSamplesToAverage(7);
		encoder.setPIDSourceType(PIDSourceType.kRate);
		
		
		pid = new PIDController(kP, kI, kD, kF, encoder, talon);
		pid.setInputRange(-50, 50);
		pid.setOutputRange(-1, 1);
		pid.enable();
	}
	
	public void setWheelSpeed (double speed){
		pid.setSetpoint(speed);
		
		
		SmartDashboard.putNumber("PID get " + String.valueOf(talon.getChannel()) , pid.get());
		SmartDashboard.putNumber("PID get setPoint" + String.valueOf(talon.getChannel()) , pid.getSetpoint());
		
		SmartDashboard.putNumber("Encoder PID Get" + String.valueOf(talon.getChannel()) , encoder.pidGet());

		SmartDashboard.putNumber("Talon Speed" + String.valueOf(talon.getChannel()), talon.getSpeed());
	}

	public double getDistDegrees()
	{
		return encoder.getDistance() * 180.0 / Math.PI; // placeholder
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
