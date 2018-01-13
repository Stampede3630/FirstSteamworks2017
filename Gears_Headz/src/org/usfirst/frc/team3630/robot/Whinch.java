package org.usfirst.frc.team3630.robot;

import edu.wpi.first.wpilibj.*;

public class Whinch {
	Talon move;
	XboxController xBox;
	public Whinch() {
		move = new Talon(Consts.winchTalonPWM);
		xBox = new XboxController(Consts.joystickComPort);
	}
	private void climb() {
		move.set(-1);
		
	}
	private void stop() {
		move.set(0);
	}
	private int onOff() {
		if(xBox.getRawButton(Consts.joystickWinchButton)) {
			return 1;
		} 
		
		else {
			return 0;
		}
		
	}
	public void whinchPeriodic() {
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
