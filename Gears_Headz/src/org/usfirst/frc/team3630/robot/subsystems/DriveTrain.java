package org.usfirst.frc.team3630.robot.subsystems;

import org.usfirst.frc.team3630.robot.Consts;
import org.usfirst.frc.team3630.robot.helpers.TalonSR;

import org.usfirst.frc.team3630.robot.helpers.XBoxHelper;
import org.usfirst.frc.team3630.robot.Consts;
import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.Timer;


/// need to impliment DRIVETRAin wraper ??
public class DriveTrain {


	XBoxHelper m_Joystick;// 2 for shooting and driving
	TalonSR Fl, Rl, fR, rR;
	boolean directionForward = true;
	RobotDrive rDrive;
	
	public DriveTrain() {

<<<<<<< HEAD
=======
	public DriveTrain() {
>>>>>>> origin/newAuto

		
		
		///declare talon sr
		////////////////////
		
		m_Joystick = new XBoxHelper(Consts.joystickComPort);
		
		 Fl= new TalonSR(0, 0, 0, directionForward, 0, directionForward)
		Rl= new TalonSR(0,0,0,);
rDrive = new RobotDrive(Consts.driveMotorFrontLeft, Consts.driveMotorBottomLeft,Consts.driveMotorFrontRight, Consts.driveMotorBottomRight);
		
		}
	
		
		/// clean up joystick 
	
		//SmartDashboard.putBoolean("Auto Control", false);
	


	public void teleopInit() {
		/// reset encoders 
	}

	public void telopPeriodic() {

		/*if (SmartDashboard.getBoolean("Auto Control", false)) {
			mecanumDrive.setAllPID();
			mecanumDrive.pidDrive();
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


