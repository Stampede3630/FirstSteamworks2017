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
	HomebrewMecanum mecanumDrive; 
	boolean directionForward = true;
   public Drive_Train() {
	   mecanumDrive = new HomebrewMecanum(Consts.driveMotorFrontLeft,Consts.driveMotorBottomLeft, Consts.driveMotorFrontRight, Consts.driveMotorBottomRight);
	   m_Joystick= new Xbox360(Consts.joystickComPort);
    
	   SmartDashboard.putNumber("drivetrain kP", .3);
	   SmartDashboard.putNumber("drivetrain kI", 0);
	   SmartDashboard.putNumber("drivetrain kD", 0);
	   SmartDashboard.putNumber("drivetrain kF", 1);


    }

   public double getRoundX() {
	   double result = m_Joystick.getX(GenericHID.Hand.kRight);
	   result *= 50;
	   result = Math.round(result);
	   result /= 50;
	   SmartDashboard.putNumber("Joystick 0", result);
	   return result;
   }
   
   public double getRoundY() {
	   double result = m_Joystick.getY(GenericHID.Hand.kRight);
	   result *= 50;
	   result = Math.round(result);
	   result /= 50;
	   SmartDashboard.putNumber("Joystick 1", result);

	   return result;
   }
   
   public double getRoundTwist() {
	   double result = m_Joystick.getX(GenericHID.Hand.kLeft);
	   result *= 50;
	   result = Math.round(result);
	   result /= 50;
	   SmartDashboard.putNumber("Joystick 2", result);

	   return result;
   }
   

   
   public void telopPeriodic(){
	//WPILIB Version
	/*
	 * m_robotDrive.mecanumDrive_Cartesian(m_Joystick.getX()/5, m_Joystick.getTwist(), m_Joystick.getY(),0);
	 */
	//Homebrew Version
	   

	double speedy = 1;
	if (m_Joystick.getRawButton(Consts.buttonSprint)||m_Joystick.getRawButton(Consts.buttonSprintAlternate)) speedy = Consts.fastK;
	else speedy = Consts.slowK;
	if (m_Joystick.getRawButton(Consts.buttonSwitchDirection)) directionForward = !directionForward;
	if  (directionForward) speedy *= -1;
	
	mecanumDrive.driveImplementation(-getRoundY()*speedy,-getRoundX()*speedy,-getRoundTwist()*speedy/2, true);
	mecanumDrive.setAllPID();
}
   
  public void teleopTest() {
		//WPILIB Version
		/*
		 * m_robotDrive.mecanumDrive_Cartesian(m_Joystick.getX()/5, m_Joystick.getTwist(), m_Joystick.getY(),0);
		 */
		//Homebrew Version
		   

		double speedy = 1;
		if (m_Joystick.getRawButton(Consts.buttonSprint)||m_Joystick.getRawButton(Consts.buttonSprintAlternate)) speedy = Consts.fastK;
		else speedy = Consts.slowK;
		if (m_Joystick.getRawButton(Consts.buttonSwitchDirection)) directionForward = !directionForward;
		if  (directionForward) speedy *= -1;
		speedy *= Consts.joystickToInchesPerSecond;
		
		mecanumDrive.driveImplementation(-getRoundY()*speedy,-getRoundX()*speedy,-getRoundTwist()*speedy, true);
		mecanumDrive.setAllPID();  
	  
  }
}
