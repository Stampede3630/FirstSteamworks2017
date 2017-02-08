package org.usfirst.frc.team3630.robot;
import edu.wpi.first.wpilibj.Encoder;

public class WheelEncoder extends Robot{
	Encoder encoder;
	public void encoderInit(int channel1, int channel2){
		encoder = new Encoder(channel1, channel2, false, Encoder.EncodingType.k4X);
		encoder.setMaxPeriod(.1);
		encoder.setMinRate(10);
		encoder.setDistancePerPulse(5);
		encoder.setReverseDirection(true);
		encoder.setSamplesToAverage(7);
	}

	public double get() {
		return encoder.get();
	}

}
