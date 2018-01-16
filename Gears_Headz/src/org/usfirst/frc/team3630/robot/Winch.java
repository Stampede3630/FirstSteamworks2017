package org.usfirst.frc.team3630.robot;

import edu.wpi.first.wpilibj.*;

public class Winch {
	Talon talonWinch;
	XboxController xBox;
	public Winch() {
		talonWinch = new Talon(Consts.winchTalonPWM);
		xBox = new XboxController(Consts.joystickComPort);
	}
	private void climb() {
		talonWinch.set(Consts.winchTalonSpeed);
		
	}
	private void stop() {
		talonWinch.set(0);
	}
	private int onOff() {
		if(xBox.getRawButton(Consts.joystickWinchButton)) {
			return 1;
		} 
		
		else {
			return 0;
		}
		
	}
	public void winchPeriodic() {
		switch(onOff()) {
		case 1:
			climb();
			break;
		case 0:
			stop();
			break;
		default:
			stop();
			break;
			
		}
	
	}
}
