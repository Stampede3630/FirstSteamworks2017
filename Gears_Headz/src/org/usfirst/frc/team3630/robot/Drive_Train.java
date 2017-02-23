package org.usfirst.frc.team3630.robot;


import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.JoystickBase;
import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Drive_Train  {

	Joystick m_Joystick;// 2 for shooting and driving 
 
 //  AnalogInput ai0;

    //boolean driveAutoCorrect;
   // double driveStrength;
    
    //Made new gyro class
    //AnalogGyro gyro;
    //NAVX
    //Sensors sensors;
    

    // initialize drives 
    RobotDrive m_robotDrive;
    HomebrewMecanum mecanumDrive; 

   public Drive_Train() {
	   mecanumDrive = new HomebrewMecanum(Consts.driveMotorFrontLeft,Consts.driveMotorBottomLeft, Consts.driveMotorFrontRight, Consts.driveMotorBottomRight);
	   m_Joystick= new Joystick(Consts.joystickComPort);
    	
    }

public  void telopPeriodic(){
	//WPILIB Version
	/*
	 * m_robotDrive.mecanumDrive_Cartesian(m_Joystick.getX()/5, m_Joystick.getTwist(), m_Joystick.getY(),0);
	 */
	//Homebrew Version

	 	
}
}
