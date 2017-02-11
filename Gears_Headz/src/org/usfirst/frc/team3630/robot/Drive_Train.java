package org.usfirst.frc.team3630.robot;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.Talon;


public class Drive_Train extends Robot implements PIDOutput{
	Joystick m_Joystick;// 2 for shooting and driving 
 
 //  AnalogInput ai0;

    //boolean driveAutoCorrect;
   // double driveStrength;
    
    //Made new gyro class
    //AnalogGyro gyro;
    //NAVX
    //Sensors sensors;
    

    // initialize drives 
    // initialize drives 
// all cometed out code needs testing
    RobotDrive m_robotDrive;

    PIDController turnController; 

    double rotateToAngleRate;

    static final double kP = 0.5;
    static final double kI = 0.00;
    static final double kD = 0.00;
    static final double kF = 0.00;
    static final double kToleranceDegrees = 2.0f;
    
    Talon robotFrontLeft;
    Talon robotFrontRight;
    Talon robotRearRight;
    Talon   robotRearLeft;

    public void Drive_TrainInit(){
    robotFrontLeft= new Talon(0);
     robotFrontRight= new Talon(3);
     robotRearRight = new Talon(2);
     robotRearLeft = new Talon(1);
     
    	m_Joystick= new Joystick(0);
    
    	robotFrontLeft.setInverted(true);
    	robotRearLeft.setInverted(true);
    	
    	m_robotDrive= new RobotDrive(robotFrontLeft,robotRearLeft,robotFrontRight,robotRearRight);
    	AHRS ahrs = new AHRS(SPI.Port.kMXP);;
    	turnController = new PIDController(kP, kI, kD, kF, ahrs, this);
    
        turnController.setInputRange(-180.0f,  180.0f);
        turnController.setOutputRange(-1.0, 1.0);
        turnController.setAbsoluteTolerance(kToleranceDegrees);
        turnController.setContinuous(true);
     //   LiveWindow.addActuator("DriveSystem", "RotateController", turnController);
        
    }
    
public  void telopPeriodic() {
	double slowDown = 3.0;
	double xLeft = m_Joystick.getX();
	double rightSticktwist = m_Joystick.getTwist();
	double yRight =  m_Joystick.getY();
			
	double gyroAngle = 0;

	SmartDashboard.putNumber("xLeft", xLeft);
	SmartDashboard.putNumber("yRight", yRight);
	SmartDashboard.putNumber("yLeft", rightSticktwist);

	boolean rotateToAngle = false;

     if  (m_Joystick.getRawButton(1)) {
         navxmxp.ahrs.reset();
     }
     if (m_Joystick.getRawButton(2)) {
         turnController.setSetpoint(0.0f);
         rotateToAngle = true;
     }
     else if (m_Joystick.getRawButton(3)) {
         turnController.setSetpoint(90.0f);
         rotateToAngle = true;
     }
     else if (m_Joystick.getRawButton(4)) {
         turnController.setSetpoint(179.9f);
         rotateToAngle = true;
     }
     else if (m_Joystick.getRawButton(5)) {
         turnController.setSetpoint(-90.0f);
         rotateToAngle = true;
     }
     double currentRotationRate;
     if (rotateToAngle) {
         turnController.enable();
         currentRotationRate = rotateToAngleRate;
     }
     else {
         turnController.disable();
         currentRotationRate = rightSticktwist;
     }
     
     
 	m_robotDrive.mecanumDrive_Cartesian(xLeft, -yRight, - rightSticktwist , gyroAngle);
	}
public void pidWrite(double output) {

    rotateToAngleRate = output;
 
}
}
