package org.usfirst.frc.team3630.robot;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Talon;

public class GearsManip {
	Talon gearMotor = new Talon(ContsClass.gearTalonPWM );
	Joystick mecxBox= new Joystick(ContsClass.joytsickChanel);
	
	DigitalInput limitOpen; 
	DigitalInput limitClose; 

	public void GearManip(){
		limitOpen= new DigitalInput(ContsClass.gearLimmitswitchIsOPEN);
		limitClose= new DigitalInput(ContsClass.gearLimmitswitchIsClosed);
	}
	
	public void open(){
		if(limitOpen.get()== false ){
			gearMotor.set(-ContsClass.gearSpeed);
		}
	}
	
	public void close(){
		if(limitClose.get()== false ){	
			gearMotor.set(ContsClass.gearSpeed);
		}
	}
	
	public void stop(){
		gearMotor.set(0);
	}
	public  int getValue(){
		if (mecxBox.getRawButton(ContsClass.joytsick_Gear_ButonZero)){
			return (int)1;
		}
		else if(mecxBox.getRawButton(ContsClass.joytsick_Gear_ButonOne)){
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
