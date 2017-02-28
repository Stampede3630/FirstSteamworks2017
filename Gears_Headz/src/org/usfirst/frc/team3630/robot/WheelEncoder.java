package org.usfirst.frc.team3630.robot;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.PIDOutput;

public class WheelEncoder extends Encoder{
	private static final int pulsesPerRevolution = 250;
	private PIDController pid;
	private double kP = 1, kI = 0, kD = 0;
	
	public WheelEncoder (int channel1, int channel2, boolean reversed){
		super(channel1, channel2, reversed, Encoder.EncodingType.k4X);
		//pid = new PIDController(kP, kI, kD, this.pidGet(), wheelControl.pidWrite);
		setMaxPeriod(1);
		// Define distance in terms of radians
		setDistancePerPulse((2 * Math.PI) / pulsesPerRevolution);
		setMinRate(10);
		setReverseDirection(true);
		setSamplesToAverage(7);
	}

	double getDistDegrees()
	{
		return getDistance() * 180.0 / Math.PI; // placeholder
	}

	double getDistRadians()
	{
		return getDistance(); // placeholder
	}

	/**
	 * @return encoder distance in inches
	 */
	double getDistInches()
	{
		return getDistRadians() * Consts.mecanumWheelRadiusInches;
	}

}
