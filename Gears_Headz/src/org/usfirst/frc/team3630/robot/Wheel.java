package org.usfirst.frc.team3630.robot;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSourceType;

public class Wheel {
	private static final int pulsesPerRevolution = 250;
	
	public Encoder encoder;
	public Talon talon;
	private talonConverter pidToTalon;
	
	public PIDController pid;
	private double kP = 1, kI = 0, kD = 0, kF = 1;
	
	public Wheel (int encoderChannel1, int encoderChannel2, int talonChannel, boolean reversed){
		encoder = new Encoder(encoderChannel1, encoderChannel2, reversed);
		talon = new Talon(talonChannel);
		pidToTalon = new talonConverter(talon);
		talon.setInverted(reversed);
		
		encoder.setMaxPeriod(1);
		// Define distance in terms of INCHES
		encoder.setDistancePerPulse(Consts.mecanumWheelRadiusInches * 2*Math.PI / pulsesPerRevolution);
		encoder.setMinRate(10);
		encoder.setReverseDirection(reversed);
		encoder.setSamplesToAverage(7);
		encoder.setPIDSourceType(PIDSourceType.kRate);
		
		//PID WILL ONLY DEAL WITH INCHES/SECOND
		pid = new PIDController(kP, kI, kD, kF, encoder, pidToTalon);
		pid.setInputRange(-2000, 2000);
		pid.setOutputRange(-2000, 2000);
		pid.enable();
	}
	
	public void setWheelSpeed (double speed){
		speed *= 50; //Need to make this constant better.
		pid.setSetpoint(speed);
		
		SmartDashboard.putNumber("PID setpoint drivetrain" + String.valueOf(talon.getChannel()) , pid.getSetpoint());

		SmartDashboard.putNumber("PID get drivetrain" + String.valueOf(talon.getChannel()) , pid.get());
		SmartDashboard.putNumber("Talon Speed" + String.valueOf(talon.getChannel()), talon.getSpeed());
		SmartDashboard.putBoolean("Is PID enabled"+ String.valueOf(talon.getChannel()), pid.isEnabled() );
	}

	
	
	
	
	
	double getDistDegrees()
	{
		return encoder.getDistance() * 180.0 / Math.PI; 
	}

	double getDistRadians()
	{
		return encoder.getDistance(); 
	}

	/**
	 * @return encoder distance in inches
	 */
	double getDistInches()
	{
		return getDistRadians() * Consts.mecanumWheelRadiusInches;
	}

}
