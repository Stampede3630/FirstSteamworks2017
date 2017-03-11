package org.usfirst.frc.team3630.robot;


import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.JoystickBase;
import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * @author StampedeRobotics
 *
 */
public class DriveTrain  {

	XboxController m_Joystick;// 2 for shooting and driving 
 
 //  AnalogInput ai0;

    //boolean driveAutoCorrect;
   // double driveStrength;
    
    //Made new gyro class
    //AnalogGyro gyro;
    //NAVX
    //Sensors sensors;
    

    // initialize drives 
	//HomebrewMecanum mecanumDrive; 
	Wheel fL, rL, fR, rR;
	boolean directionForward = true;
   
	public DriveTrain() {
	   //mecanumDrive = new HomebrewMecanum(Consts.driveMotorFrontLeft,Consts.driveMotorBottomLeft, Consts.driveMotorFrontRight, Consts.driveMotorBottomRight);
	   m_Joystick= new XboxController(Consts.joystickComPort);
		
	    fL = new Wheel(Consts.driveEncoderFrontLeftA, Consts.driveEncoderFrontLeftB, Consts.driveMotorFrontLeft, false);
		rL = new Wheel(Consts.driveEncoderRearLeftA, Consts.driveEncoderRearLeftB, Consts.driveMotorBottomLeft, false);
		fR = new Wheel(Consts.driveEncoderFrontRightA, Consts.driveEncoderFrontRightB, Consts.driveMotorFrontRight, true);
		rR = new Wheel(Consts.driveEncoderRearRightA, Consts.driveEncoderRearRightB, Consts.driveMotorBottomRight, true);
		
	}

   public double getRoundX() {
	   double result = m_Joystick.getX(GenericHID.Hand.kLeft);
	   SmartDashboard.putNumber("Joystick X", result);
	   return result;
   }
   
   public double getRoundY() {
	   double result = m_Joystick.getY(GenericHID.Hand.kLeft);
	   SmartDashboard.putNumber("Joystick Y", result);

	   return result;
   }
   
   public double getRoundTwist() {
	   double result = m_Joystick.getX(GenericHID.Hand.kRight);
	   SmartDashboard.putNumber("Joystick Twist", result);

	   return result;
   }
   

   
   public void telopPeriodic(){
	//WPILIB Version
	/*
	 * m_robotDrive.mecanumDrive_Cartesian(m_Joystick.getX()/5, m_Joystick.getTwist(), m_Joystick.getY(),0);
	 */
	//Homebrew Version
	   

	/*double speedy = 1;
	if (m_Joystick.getRawButton(Consts.buttonSprint)||m_Joystick.getRawButton(Consts.buttonSprintAlternate)) speedy = Consts.fastK;
	else speedy = Consts.slowK;
	
	if (m_Joystick.getRawButton(Consts.buttonSwitchDirection)) directionForward = !directionForward;
	if  (!directionForward) speedy *= -1;
	
	mecanumDrive.driveImplementation(getRoundY()*speedy,getRoundX()*speedy,getRoundTwist()*speedy*360, false);
	//Expecting numbers between -1 and 1.
	mecanumDrive.setAllPID();
	*/
	 double speed = SmartDashboard.getNumber("Desired Speed", 0) * Math.PI*2;
	 
	 fL.setWheelSpeed(speed);
	 rL.setWheelSpeed(speed);
	 fR.setWheelSpeed(speed);
	 rR.setWheelSpeed(speed);
}

}
