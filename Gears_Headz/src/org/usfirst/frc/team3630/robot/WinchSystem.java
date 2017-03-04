package org.usfirst.frc.team3630.robot;

import edu.wpi.first.wpilibj.CounterBase;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Joystick;

import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class WinchSystem {
	Talon winch;
	Joystick mecXbox;
	DigitalInput winchDown;
	Encoder winchEncoder;
public WinchSystem(){
	 winch =  new Talon(Consts.winchTalonPWM);
	 mecXbox = new Joystick(Consts.joystickComPort);
	 winchDown = new DigitalInput(Consts.winchStopPin);
	 winchEncoder = new Encoder(Consts.winchEncoderPinA, Consts.winchEncoderPinB, false, CounterBase.EncodingType.k4X);
	 winchEncoder.setDistancePerPulse(( 2*Math.PI) /(400000));

}

	

	public void upWinch(){
		winch.set(-1);
	}
	
	
	public void stopWinch() {
		winch.set(0.0);
	}
	
	public int onOff() {
		if (mecXbox.getRawButton(Consts.joystickWinchButton)){
			return(int) 1;
		}
		else{
			return 0;
		}
	}
	
	public void telopPeriodic(){
		SmartDashboard.putNumber("Winch Encoder Raw Value", winchEncoder.getRaw());
		SmartDashboard.putNumber("Winch Encoder Rad value", winchEncoder.getDistance()/(2*Math.PI));
		SmartDashboard.putBoolean("WinchDown Limit", winchDown.get());
		SmartDashboard.putBoolean("Winch Button", mecXbox.getRawButton(Consts.joystickWinchButton));
		SmartDashboard.putNumber("Winch Talon State", winch.get());
		switch(onOff()) {
			case 1:
				upWinch();
				Timer.delay(.05);
				break;
			case 0:
				stopWinch();
				break;
			default:
				stopWinch();
				break;
		}
	}
}
