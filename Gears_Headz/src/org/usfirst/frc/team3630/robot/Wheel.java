package org.usfirst.frc.team3630.robot;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSourceType;

public class Wheel {
	private static final int pulsesPerRevolution = 250;
	public PIDController pid;
	public Encoder encoder;
	public Talon talon;
	private talonConverter pidToTalon;
	private double kP = .3, kI = 0, kD = 0, kF = 1/50;
	
	public Wheel (int encoderChannel1, int encoderChannel2, int talonChannel, boolean reversed){
		encoder = new Encoder(encoderChannel1, encoderChannel2, reversed);
		talon = new Talon(talonChannel);
		pidToTalon = new talonConverter(talon);
		talon.setInverted(reversed);
		//pid = new PIDController(kP, kI, kD, this.pidGet(), wheelControl.pidWrite);
		encoder.setMaxPeriod(.5);
		// Define distance in terms of radians
		encoder.setDistancePerPulse(2 * Math.PI / 250); //distance is in radians.
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
		SmartDashboard.putNumber("PID setpoint drivetrain" + String.valueOf(talon.getChannel()) , pid.getSetpoint());

		SmartDashboard.putNumber("PID get drivetrain" + String.valueOf(talon.getChannel()) , pid.get());
		SmartDashboard.putNumber("PID get setPoint" + String.valueOf(talon.getChannel()) , pid.getSetpoint());
		SmartDashboard.putNumber("PID get encoder in" + String.valueOf(talon.getChannel()) , encoder.pidGet());


		SmartDashboard.putNumber("Talon Speed" + String.valueOf(talon.getChannel()), talon.getSpeed());
		SmartDashboard.putBoolean("Is PID enabled"+ String.valueOf(talon.getChannel()), pid.isEnabled() );
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
