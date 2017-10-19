package org.usfirst.frc.team3630.robot;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * @author StampedeRobotics
 *
 */

/// need to impliment DRIVETRAin wraper ??
public class DriveTrain {

//	XboxHelper m_Joystick;// 2 for shooting and driving
///	TalonSR fL, rL, fR, rR;
///	boolean directionForward = true;

	public DriveTrain(NavX myNavX) {
		////////////////////////////////////
		/// need to make a robot drive system 
		
		/////////////////////////////////
		
		
		///declare talon sr
		////////////////////
		
		
		////// Define SR 
		
		// FL
		//RL
		//RR
		///FR
		
		
		
		//// put it robot drive (FL, RL, FR,RR
		rDrive = new RobotDrive(Consts.driveMotorFrontLeft, Consts.driveMotorBottomLeft,
				Consts.driveMotorFrontRight, Consts.driveMotorBottomRight, myNavX);
	
		
		/// clean up joystick 
		///m_Joystick = new XboxController(Consts.joystickComPort);
		SmartDashboard.putBoolean("Auto Control", false);
	}


	public void teleopInit() {
		//// reset encoders usseing talon sr
		////mecanumDrive.resetEncoders();
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

			
			
			
			///// drive implmitation  need to do 
			
			//// wpilib mecanum drive 
			
			//_rDrive= New M Drive
			
		

		}
	}

//}
