package org.usfirst.frc.team3630.robot;

import edu.wpi.first.wpilibj.Joystick;

import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.Talon;

public class winchSystem {
	Talon winch;
	Joystick mecXbox;
public winchSystem(){
	 winch =  new Talon(5);
	 mecXbox = new Joystick(0);
}

	

	public void upWinch(){
		winch.set(-1);
	}
	
	
	public void stopWinch() {
		winch.set(0.0);
	}
	
	public int onOff() {
		if (mecXbox.getRawButton(5)){
			return(int) 1;
		}
		else{
			return 0;
		}
	}
	
	public void telopMPeriodic(){
		switch(onOff()) {
			case 1:
				upWinch();
				break;
			default:
				stopWinch();
				break;
		}
	}
}
