package org.usfirst.frc.team3630.robot;


import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.Talon;

public class Drive_Train extends Robot {
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
    
    Talon robotFrontLeft;
    Talon robotFrontRight;
    Talon robotRearRight;
   	Talon robotRearLeft;
    
   	public Drive_Train() {
   		robotFrontLeft= new Talon(0);
   		robotFrontRight= new Talon(3);
   		robotRearRight = new Talon(2);
   		robotRearLeft = new Talon(1);
     
    	m_Joystick= new Joystick(0);
    	robotFrontLeft.setInverted(true);
    	robotRearLeft.setInverted(true);
    	m_robotDrive= new RobotDrive(robotFrontLeft,robotRearLeft,robotFrontRight,robotRearRight);
    }
   	
   	public void telopPeriodic() {
   		double slowDown = 3.0;
   		double xLeft = m_Joystick.getX();
   		double yRight =  m_Joystick.getY();
   		double rightSticktwist = m_Joystick.getTwist();
			
   		double gyroAngle = 0;
   		
		m_robotDrive.mecanumDrive_Cartesian(xLeft, -yRight, - rightSticktwist, gyroAngle);
		
		SmartDashboard.putNumber("xLeft", xLeft);
		SmartDashboard.putNumber("yRight", yRight);
		SmartDashboard.putNumber("yLeft", rightSticktwist);
	}
}
