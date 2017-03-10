package org.usfirst.frc.team3630.robot;

import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class talonConverter implements PIDOutput {
	private Talon talon;
	
	public talonConverter (Talon t){
		t = talon;
	}
	
	public void pidWrite(double output) {
		output /= 50;//ToDo: Redo this coefficient. Cannot currently do top speed.
		if (Math.abs(output)<= 1) {
			talon.set(output);
			SmartDashboard.putNumber("Talon Output" + String.valueOf(talon.getChannel()), output);
		}
	}

}
