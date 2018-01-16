package org.usfirst.frc.team3630.robot;

import edu.wpi.first.wpilibj.*;

public class GearsManipulator {
	Talon _talon;
	XboxController _xBoxController;
	DigitalInput opened, closed;
	
	public GearsManipulator ()
	{
		_talon = new Talon(Consts.gearTalonPWM);
		_xBoxController = new XboxController (0);
		opened = new DigitalInput (Consts.gearLimitSwitchOpen);
		closed = new DigitalInput(Consts.gearLimitSwitchClosed);	
	}
	private void open() {
		_talon.set(Consts.gearSpeedOpen);
	}
	private void close() {
		_talon.set(Consts.gearSpeedClose);
	}
	private void stop() {
		_talon.set(0);
	}
	public void teleopPeriodic () {
		boolean commandOpen = _xBoxController.getRawButton(Consts.openButton);
		boolean commandClose = _xBoxController.getRawButton(Consts.closeButton);
		boolean limmitOpen = opened.get();
		boolean limmitClose = closed.get();
		
		if(commandOpen) { 
			if(limmitOpen) {
				stop();
			}
			else {
				open();
			}
		}
		else if (commandClose) {
			if(limmitClose) {
				stop();	
			}
			else {
				close();	
			}	
		}
		else {
			stop();
		}	
	}
}
