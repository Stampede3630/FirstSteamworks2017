package org.usfirst.frc.team3630.robot;


import java.lang.reflect.Array;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.Talon;
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
    

    
    public Drive_Train(){
    
    	m_Joystick= new Joystick(0);
    	m_robotDrive= new RobotDrive(2,0,1,3);
    	mecCalc = new HomebrewMecanum();
    	frontLeft = new Talon(ContsClass.driveMotorFrontLeft);
    	frontRight = new Talon(ContsClass.driveMotorFrontRight);
    	bottomLeft = new Talon(ContsClass.driveMotorBottomLeft);
    	bottomRight = new Talon(ContsClass.driveMotorBottomRight);
    	
    }

public  void telopPeriodic(){
	//WPILIB Version
	/*
	 * m_robotDrive.mecanumDrive_Cartesian(m_Joystick.getX()/5, m_Joystick.getTwist(), m_Joystick.getY(),0);
	 */
	//Homebrew Version
	 double[] wheelSpeeds;
	 wheelSpeeds = new double[4];
	 wheelSpeeds = mecCalc.mecanumCalc(m_Joystick.getX(), m_Joystick.getTwist(), m_Joystick.getY());
	 
	 mecCalc.motorDrive(frontLeft, wheelSpeeds[0]);
	 mecCalc.motorDrive(bottomLeft, wheelSpeeds[1]);
	 mecCalc.motorDrive(bottomRight, wheelSpeeds[2]);
	 mecCalc.motorDrive(frontRight, wheelSpeeds[3]);
	 	
}
}
