package org.usfirst.frc.team3630.robot.subsystems;

import edu.wpi.first.wpilibj.XboxController;


import org.usfirst.frc.team3630.robot.helpers.TalonSR;

import org.usfirst.frc.team3630.robot.helpers.XBoxHelper;
import org.usfirst.frc.team3630.robot.Consts;
import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;


/// need to impliment DRIVETRAin wraper ??
public class DriveTrain {


	XBoxHelper m_Joystick;// 2 for shooting and driving
	TalonSR fL, rL, fR, rR;
	boolean directionForward = true;
	RobotDrive rDrive;
	
	public DriveTrain() {
		
		m_Joystick = new XBoxHelper(Consts.joystickComPort);
		/// need to put encoder chanels in  
		fL= new TalonSR(Consts.driveMotorFrontLeft, 0, 0, directionForward, 0, directionForward)
		rL= new TalonSR(Consts.driveMotorBottomLeft,0,0,);
		fR= new  TalonSR(Consts.driveMotorFrontRight,0,0,);
		rR= new TalonSR(Consts.driveMotorBottomRight,0,0,);
		

		 rDrive = new RobotDrive(Consts.driveMotorFrontLeft, Consts.driveMotorBottomLeft,Consts.driveMotorFrontRight, Consts.driveMotorBottomRight);
		
		}
	
		
		
		//SmartDashboard.putBoolean("Auto Control", false);
	


	public void teleopInit() {
		/// reset encoders 
	}

	public void telopPeriodic() {

		/*if (SmartDashboard.getBoolean("Auto Control", false)) {
	/// iniliatise PID contrlers
		} else {
		*/
			double speedy;

			if (m_Joystick.getBumper(GenericHID.Hand.kLeft))
				speedy = Consts.fastK;
			else
				speedy = Consts.slowK;

			if (m_Joystick.getBButton()){
				directionForward = !directionForward;
				Timer.delay(.250);
			}
			if (!directionForward)
				speedy *= -1;

			
			
	/// need to figure out NAVX inputs 
			
		
			rDrive.mecanumDrive_Cartesian(m_Joystick.getX(), m_Joystick.getY(), m_Joystick.getRoundTwist(),0);
		}
	}


