package org.usfirst.frc.team3630.robot;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Talon;

public class GearsManip {
	Talon gearMotor = new Talon(Consts.gearTalonPWM );
	Joystick m_joystick= new Joystick(Consts.joytsickChanel);
	
	DigitalInput limitOpen; 
	DigitalInput limitClose; 

	public GearsManip(){
		limitOpen= new DigitalInput(Consts.gearLimitSwitchOpen);
		limitClose= new DigitalInput(Consts.gearLimitSwitchClosed);
	}
	
	public void open(){
		if(true){
			gearMotor.set(-Consts.gearSpeed);
		}
	}
	
	public void close(){
		if(true){	
			gearMotor.set(Consts.gearSpeed);
		}
	}
	
	public void stop(){
		gearMotor.set(0);
	}
	public  int getValue(){
		if (m_joystick.getRawButton(Consts.joytsick_Gear_ButonZero)){
			return (int)1;
		}
		else if(m_joystick.getRawButton(Consts.joytsick_Gear_ButonOne)){
			return (int)2;
		}
		else{
			return 0;
		}
	}
		
		public void telopPeridic(){
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
