package org.usfirst.frc.team3630.robot;
import edu.wpi.first.wpilibj.AnalogInput;

public class Ultrasonics {
	AnalogInput bitStream;
	// get voltage called fuuny 
	public void ultraInit(int channel) {
		bitStream = new AnalogInput(channel);// gives us analog input 
	}
	public int getRawValue(){
		int sensorReading = bitStream.getValue();
		return sensorReading;
	}
	// something wrong hear do not know
	public double sensorPeriodic(){
			double FormulaStepOne= (double)getRawValue();
			double FormulaTwo = FormulaStepOne*(.10362);
			return FormulaTwo;
		}
}
