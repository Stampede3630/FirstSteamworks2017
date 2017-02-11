package org.usfirst.frc.team3630.robot;


import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.RobotDrive;
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
    

    
    public Drive_Train(){
    
    	m_Joystick= new Joystick(0);
    	m_robotDrive= new RobotDrive(2,0,1,3);
}

public  void telopPeriodic(){
	m_robotDrive.mecanumDrive_Cartesian(m_Joystick.getX()/5, m_Joystick.getTwist(), m_Joystick.getY(),0);
	
}
}
