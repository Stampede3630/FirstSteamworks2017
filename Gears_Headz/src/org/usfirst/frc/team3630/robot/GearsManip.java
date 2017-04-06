package org.usfirst.frc.team3630.robot;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class GearsManip {
	Talon gearMotor = new Talon(Consts.gearTalonPWM);
	Joystick m_joystick= new Joystick(Consts.joytsickChannel);
	
	DigitalInput limitOpen; 
	DigitalInput limitClose; 

	public GearsManip(){
		limitOpen= new DigitalInput(Consts.gearLimitSwitchOpen);
		limitClose= new DigitalInput(Consts.gearLimitSwitchClosed);
	}
	public void stop(){
		gearMotor.set(0);
	}
	public void open(){
		/*while (limitOpen.get()) {
			gearMotor.set(-Consts.gearSpeed);
		}
		stop();*/
		if(limitOpen.get()){	
			gearMotor.set(-Consts.gearSpeed);
		}
		else stop();
	}
	
	public void autoOpen(){
		while(limitOpen.get()){	
			gearMotor.set(-Consts.gearSpeed);
		}
		stop();
	}
	
	public void close(){
		if(limitClose.get()){	
		
			gearMotor.set(Consts.gearSpeed);
		}
		else stop();
	}
	
	public boolean isOpened () {
		return !limitOpen.get();
	}
	
	public  int getValue(){
		if (m_joystick.getRawButton(Consts.openButton)){
			return (int)1;
		}

		else if(m_joystick.getRawButton(Consts.closeButton)){
			return (int)2;
		}
		else{
			return 0;
		}
	}
		
		public void telopPeridic(){
			SmartDashboard.putBoolean("LimitOpen", limitOpen.get());
			SmartDashboard.putBoolean("LimitClose", limitClose.get());
//			SmartDashboard.putBoolean("OpenButton", m_joystick.getRawButton(Consts.openButton));
//			SmartDashboard.putBoolean("CloseButton", m_joystick.getRawButton(Consts.closeButton));
			switch (getValue()) {
				case 1:
					open();
					break;
				
				case 2: 
					close();
					break;
				default:
					stop();
					break;
			}
		}
}
