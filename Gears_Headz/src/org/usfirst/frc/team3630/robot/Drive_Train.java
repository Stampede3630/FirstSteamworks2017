package org.usfirst.frc.team3630.robot;


import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.JoystickBase;
import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Drive_Train extends Robot  {

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
    HomebrewMecanum mecCalc; 
    Talon frontLeft;
    Talon frontRight;
    Talon bottomLeft;
    Talon bottomRight;

   public Drive_Train() {
    	frontLeft = new Talon(ContsClass.driveMotorFrontLeft);
    	frontRight = new Talon(ContsClass.driveMotorFrontRight);
    	bottomLeft = new Talon(ContsClass.driveMotorBottomLeft);
    	bottomRight = new Talon(ContsClass.driveMotorBottomRight);	
     
    	m_Joystick= new Joystick(0);
    
    	bottomLeft.setInverted(true);
    	frontLeft.setInverted(true);
    	
    }

public  void telopPeriodic(){
	//WPILIB Version
	/*
	 * m_robotDrive.mecanumDrive_Cartesian(m_Joystick.getX()/5, m_Joystick.getTwist(), m_Joystick.getY(),0);
	 */
	//Homebrew Version
	 double[] wheelSpeeds;
	 wheelSpeeds = new double[4];
	 wheelSpeeds = m_Joystick.getRawButton(10)? HomebrewMecanum.mecanumCalc(m_Joystick.getY()*2,m_Joystick.getX()*2, m_Joystick.getZ()):HomebrewMecanum.mecanumCalc(m_Joystick.getY(),m_Joystick.getX(), m_Joystick.getZ()) ;
	 SmartDashboard.putNumber("Wheel0", wheelSpeeds[0]);
	 SmartDashboard.putNumber("Wheel1", wheelSpeeds[1]);
	 SmartDashboard.putNumber("Wheel2", wheelSpeeds[2]);
	 SmartDashboard.putNumber("Wheel3", wheelSpeeds[3]);

	 
	
	 HomebrewMecanum.motorDrive(frontLeft, wheelSpeeds[0]);
	 HomebrewMecanum.motorDrive(bottomLeft, wheelSpeeds[1]);
	 HomebrewMecanum.motorDrive(bottomRight, wheelSpeeds[2]);
	 HomebrewMecanum.motorDrive(frontRight, wheelSpeeds[3]);
	 
	 	
}
}
