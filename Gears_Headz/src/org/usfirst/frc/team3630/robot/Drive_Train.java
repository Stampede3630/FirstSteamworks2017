package org.usfirst.frc.team3630.robot;


<<<<<<< HEAD
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.Talon;

public class Drive_Train extends Robot {
=======

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.JoystickBase;
import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
public class Drive_Train extends Robot  {
>>>>>>> origin/newmecanumdrive
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
<<<<<<< HEAD
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
=======
    	
    	//mecCalc = new HomebrewMecanum();
    	frontLeft = new Talon(ContsClass.driveMotorFrontLeft);
    	frontRight = new Talon(ContsClass.driveMotorFrontRight);
    	bottomLeft = new Talon(ContsClass.driveMotorBottomLeft);
    	bottomRight = new Talon(ContsClass.driveMotorBottomRight);
    	bottomLeft.setInverted(true);
    	frontLeft.setInverted(true);
    	
    //	m_robotDrive= new RobotDrive(frontLeft,frontRight,bottomLeft,bottomRight);
    }

public  void telopPeriodic(){
	//WPILIB Version
	/*
	 * m_robotDrive.mecanumDrive_Cartesian(m_Joystick.getX()/5, m_Joystick.getTwist(), m_Joystick.getY(),0);
	 */
	//Homebrew Version
	 double[] wheelSpeeds;
	 wheelSpeeds = new double[4];
	 wheelSpeeds = HomebrewMecanum.mecanumCalc(m_Joystick.getY(), m_Joystick.getX(), -m_Joystick.getZ());
	 SmartDashboard.putNumber("Wheel0", wheelSpeeds[0]);
	 SmartDashboard.putNumber("Wheel1", wheelSpeeds[1]);
	 SmartDashboard.putNumber("Wheel2", wheelSpeeds[2]);
	 SmartDashboard.putNumber("Wheel3", wheelSpeeds[3]);

	 
	
	 HomebrewMecanum.motorDrive(frontLeft, wheelSpeeds[0]);
	 HomebrewMecanum.motorDrive(bottomLeft, wheelSpeeds[1]);
	 HomebrewMecanum.motorDrive(bottomRight, wheelSpeeds[2]);
	 HomebrewMecanum.motorDrive(frontRight, wheelSpeeds[3]);
	 
	 	
}
>>>>>>> origin/newmecanumdrive
}
