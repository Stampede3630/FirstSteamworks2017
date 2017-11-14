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
	boolean directionForward = false;
	RobotDrive rDrive;
	AutoDriveTrain autoDrive;
	
	public DriveTrain() {
		/// init velocity setter  and auto wheel input
		autoDrive = new AutoDriveTrain();
		m_Joystick = new XBoxHelper(Consts.joystickComPort);
 
		fL= new TalonSR(Consts.driveMotorFrontLeft, Consts.driveEncoderFrontLeftA, Consts.driveEncoderFrontLeftB, directionForward, directionForward, autoDrive.v_fL );
		rL= new TalonSR(Consts.driveMotorBottomLeft,Consts.driveEncoderRearLeftA, Consts.driveEncoderRearLeftB, directionForward, directionForward , autoDrive.v_rL);
		fR= new  TalonSR(Consts.driveMotorFrontRight, Consts.driveEncoderFrontRightA,Consts.driveEncoderFrontRightB, directionForward, directionForward, autoDrive.v_fR);
		rR= new TalonSR(Consts.driveMotorBottomRight, Consts.driveEncoderRearRightA, Consts.driveEncoderRearRightB, directionForward, directionForward, autoDrive.v_rR);
	

		// barphing over this I comited it out
		//rDrive = new RobotDrive(Consts.driveMotorFrontLeft, Consts.driveMotorBottomLeft,Consts.driveMotorFrontRight, Consts.driveMotorBottomRight);
		}
	
		
		

	


	public void teleopInit() {
		 
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

			
			
			
			
	/// verify TELOP Drive train works WPILB mcanum drive has ben sketcy in past 	
			//rDrive.mecanumDrive_Cartesian(m_Joystick.getX(), m_Joystick.getY(), m_Joystick.getRoundTwist(),0);
		}
	
	public void autoPeriodic(){
		autoDrive.autoIterative ();
	}
	
	public void autoInit() {
		autoDrive.autoInit(fL.pPID,rL.pPID,fR.pPID,rR.pPID, fL.vPID, rL.vPID, fR.vPID,rR.vPID);
		fL.resetEncoder();
		rL.resetEncoder();
		fR.resetEncoder();
		rR.resetEncoder();
	}
	}


