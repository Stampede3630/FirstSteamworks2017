package org.usfirst.frc.team3630.robot;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
 
import org.usfirst.frc.team3630.robot.subsystems.*;

import com.kauailabs.navx.frc.*;

public class Robot extends IterativeRobot {
	final String defaultAuto = "Default";
	final String customAuto = "My Auto";
	String autoSelected;
	
	DriveTrain driveTrain;
	GearsManip gears;
	WinchSystem winch;
	Timer autoTimer;
	DigitalInput springEngaged;
	// auto
	int autoStage;
	boolean init;
	double adjustDegrees;
	double desiredSpin;
	int counter = 0;
	
	String [] autoStatus = {"initial drive straight", "rotate to angle", "vision tracking", "final approach", "final release", "recoil"};
	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	@Override
	public void robotInit() {
		springEngaged= new DigitalInput(Consts.springEngaged);
		SmartDashboard.putBoolean("GEAR DROP", true);
		SmartDashboard.putNumber("Auto Drive", 0);
		SmartDashboard.putNumber("MinRate", .5);
		//SmartDashboard.putBoolean("CHEAT AUTO", false);
		SmartDashboard.putBoolean("USE DRIVE STRAIGHT", true);
		driveTrain = new DriveTrain();
		winch = new WinchSystem();
		gears = new GearsManip();
		autoTimer = new Timer();
	}



	public void autonomousInit() {
			driveTrain.autoInit();
		
	}

	/**
	 * This function is called periodically during autonomous
	 */

	public void autonomousPeriodic() {
		driveTrain.autoPeriodic();
		
		}
		
	

	/**
	 * This function is called periodically during operator control
	 */

	public void teleopInit() {
		SmartDashboard.putNumber("drivetrain kP", Consts.wheelKP);
		SmartDashboard.putNumber("drivetrain kI", Consts.wheelKI);
		SmartDashboard.putNumber("drivetrain kD", Consts.wheelKD);
		driveTrain.teleopInit();
	}

	
	public void teleopPeriodic() {
		SmartDashboard.putBoolean("SPRING Switch", springEngaged.get());
		driveTrain.telopPeriodic();
		// rightFrontEnc.get();
		// ultraDistance.sensorPeriodic();
		//winch.telopPeriodic();
		//gears.telopPeridic();

	}

	/**
	 * This function is called periodically during test mode
	 */

	public void testPeriodic() {
	}
}
