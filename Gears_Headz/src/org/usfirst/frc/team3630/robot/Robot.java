package org.usfirst.frc.team3630.robot;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
 
import org.usfirst.frc.team3630.robot.subsystems.*;

import com.kauailabs.navx.frc.*;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot {
	final String defaultAuto = "Default";
	final String customAuto = "My Auto";
	String autoSelected;
	SendableChooser<String> chooser = new SendableChooser<>();
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

	/**
	 * This autonomous (along with the chooser code above) shows how to select
	 * between different autonomous modes using the dashboard. The sendable
	 * chooser code works with the Java SmartDashboard. If you prefer the
	 * LabVIEW Dashboard, remove all of the chooser code and uncomment the
	 * getString line to get the auto name from the text box below the Gyro
	 *
	 * You can add additional auto modes by adding additional comparisons to the
	 * switch structure below with additional strings. If using the
	 * SendableChooser make sure to add them to the chooser code above as well.
	 */
	@Override
	public void autonomousInit() {
			driveTrain.autoInit();
		
	}

	/**
	 * This function is called periodically during autonomous
	 */
	@Override
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

	@Override
	public void teleopPeriodic() {
		SmartDashboard.putBoolean("SPRING Switch", springEngaged.get());
		driveTrain.telopPeriodic();
		// rightFrontEnc.get();
		// ultraDistance.sensorPeriodic();
		winch.telopPeriodic();
		gears.telopPeridic();

	}

	/**
	 * This function is called periodically during test mode
	 */
	@Override
	public void testPeriodic() {
	}
}
