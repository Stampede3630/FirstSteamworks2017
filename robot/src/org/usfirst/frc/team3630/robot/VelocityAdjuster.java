package src.org.usfirst.frc.team3630.robot;
import org.usfirst.frc.team3630.robot.TalonSR;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.PWMSpeedController;
import edu.wpi.first.wpilibj.Talon;

//import edu.wpi.first.wpilibj.SpeedController;

public class VelocityAdjuster  {

TalonSR fl;
TalonSR fr;
TalonSR bl;
TalonSR br;
Encoder _enc;

	public VelocityAdjuster(int flc, int blc,) {
		fl= new TalonSR(0, 0, 0, false, 0, false);
		bl= new TalonSR(0, 0, 0, false, 0, false);
		br= new TalonSR(0, 0, 0, false, 0, false);
	}
	

	public void setVDesired(int velocity) {
		/// set motor controller velcoity
		
	}
}
