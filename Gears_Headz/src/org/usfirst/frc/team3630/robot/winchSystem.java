package org.usfirst.frc.team3630.robot;

import edu.wpi.first.wpilibj.Joystick;

import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class WinchSystem {
	private Talon winch;
	private Joystick m_joystick;
	private WinchEncoder wEncoder;
	
	public WinchSystem(){
		winch =  new Talon(Consts.winchTalonPWM);
		m_joystick = new Joystick(Consts.joystickComPort);
		wEncoder = new WinchEncoder (Consts.winchEncoderPinA, Consts.winchEncoderPinB);
}

	public void upWinch(){
		winch.set(-1);
	}
	
	public void stopWinch() {
		winch.set(0.0);
	}
	
	public int onOff() {
		if (m_joystick.getRawButton(5)){
			return(int) 1;
		}
		else{
			return 0;
		}
	}
	
	public void printWinchValues () {
		SmartDashboard.putNumber("Winch Raw Encoder", wEncoder.getRaw());
		SmartDashboard.putNumber("Winch Radians Encoder", wEncoder.GetDistRadians());
		SmartDashboard.putNumber("Winch Degrees Encoder", wEncoder.GetDistDegrees());
		SmartDashboard.putNumber("Winch Inches Encoder", wEncoder.GetDistInches());
	}
	
	public void teleopPeriodic(){
		printWinchValues();
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
