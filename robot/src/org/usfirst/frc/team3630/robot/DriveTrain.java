package org.usfirst.frc.team3630.robot;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * @author StampedeRobotics
 *
 */
public class DriveTrain {

	XboxHelper m_Joystick;// 2 for shooting and driving
	Wheel fL, rL, fR, rR;
	boolean directionForward = true;

	public DriveTrain(NavX myNavX) {
		mecanumDrive = new HomebrewMecanum(Consts.driveMotorFrontLeft, Consts.driveMotorBottomLeft,
				Consts.driveMotorFrontRight, Consts.driveMotorBottomRight, myNavX);
		m_Joystick = new XboxController(Consts.joystickComPort);
		SmartDashboard.putBoolean("Auto Control", false);
	}


	public void teleopInit() {
		mecanumDrive.resetEncoders();
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

			mecanumDrive.driveImplementation(- getRoundY() * speedy,- getRoundX() * speedy,- getRoundTwist() * speedy,
					true);
			// Expecting numbers between -1 and 1.

		}
	}

//}
