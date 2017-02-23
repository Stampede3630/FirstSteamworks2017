package org.usfirst.frc.team3630.robot;
import edu.wpi.first.wpilibj.Encoder;

// this code is just a placeholder - needs to be customized

public class WinchEncoder extends Encoder{
	private static final int pulsesPerRevolution = 16;

	public WinchEncoder (int channel1, int channel2){
		super(channel1, channel2, false, Encoder.EncodingType.k4X);
		setMaxPeriod(1);
		// Define distance in terms of radians
		setDistancePerPulse(pulsesPerRevolution / (2 * Math.PI));
		setMinRate(10);
		setReverseDirection(true);
		setSamplesToAverage(7);
	}

	double GetDistDegrees()
	{
		return getDistance() * 180.0 / Math.PI; // placeholder
	}

	double GetDistRadians()
	{
		return getDistance(); // placeholder
	}

	double GetDistInches()
	{
		return 0.0; // placeholder
	}

}
