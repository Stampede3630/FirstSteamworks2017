package org.usfirst.frc.team3630.robot;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

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
	NavX myNavX;
	WinchSystem winch;
//	Timer autoTimer;
	// auto
	int autoStage;
	boolean init;
	double adjustDegrees;
	double desiredSpin;
	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	@Override
	public void robotInit() {
		SmartDashboard.putBoolean("Gear Drop", true);
		System.out.println("Init");
		SmartDashboard.putNumber("Auto Drive", 0);
		SmartDashboard.putNumber("MinRate", .5);
		SmartDashboard.putBoolean("Cheat Auto", false);
		driveTrain = new DriveTrain(myNavX);
		myNavX = new NavX();
		winch = new WinchSystem();
		gears = new GearsManip();
//		autoTimer = new Timer();
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
		autoStage = 0;
		init = true;
		myNavX.calibrateHeading();
	}

	/**
	 * This function is called periodically during autonomous
	 */
	@Override
	public void autonomousPeriodic() {
		myNavX.teleopPeriodic();
//		SmartDashboard.putNumber("autoTime", autoTimer.get());
		// runs through switch statement that goes through stages.
		// each stage has an init which usually resets encoders and moves on to
		// the next stage
		
		SmartDashboard.putNumber("AutoStage", autoStage);
		SmartDashboard.putBoolean("PID At Target", driveTrain.mecanumDrive.pidAtTarget());
		switch (autoStage) {
		// drive straight
		case 0:
			if(SmartDashboard.getBoolean("Cheat Auto", false)){
				driveTrain.mecanumDrive.pidDrive(true, 75, 0, 0 );
				
				if (driveTrain.mecanumDrive.pidAtTarget()) { // condition to move on
					// to next step o
					autoStage = 4; //moves to next stage
					init = true;
				}
			}
			else {
			if(init){
				//driveTrain.mecanumDrive.pidDrive(true, 84, 0, 0);
//				autoTimer.reset();
//				autoTimer.start();
				driveTrain.mecanumDrive.resetEncoders(); // resets encoders
				driveTrain.mecanumDrive.setAllPID(); //sets PID coefficients and updates desired distances
				driveTrain.mecanumDrive.pidDrive(true, Consts.driveDistance, 0, 0); //drives in a straight line for driveDistance
				init = false; // resets intitialization for each stage of auto to
							// reset encoders
			/*	//driveTrain.mecanumDrive.pidDrive(true, 86,0,0);
				if ((int)SmartDashboard.getNumber("Auto Drive", 0) == (int) 0)
					adjustDegrees = 0;
				else if ((int)SmartDashboa
				rd.getNumber("Auto Drive", 0) == (int) -1)
					adjustDegrees = -1 * Consts.adjustDegrees;
				else if ((int)SmartDashboard.getNumber("Auto Drive", 0) == (int) 1)
					adjustDegrees = Consts.adjustDegrees;
				else
					adjustDegrees = 0;
					
			}
			*/	
			 driveTrain.mecanumDrive.pidDrive(true, Consts.driveDistance, 0, 0); //sets desired spin to line up with target
			 driveTrain.mecanumDrive.setAllPID();
			}
			if (driveTrain.mecanumDrive.pidAtTarget() /*|| autoTimer.get() > 3.0 */) { // condition to move on
															// to next step o
				autoStage++; //moves to next stage
				init = true;
			}
			}
			break;

		case 1:
			// rotate to angle
			if (init) {
//				autoTimer.reset();
	//			autoTimer.start();
				driveTrain.mecanumDrive.resetEncoders(); //resets encoders to go to desired distances				
				driveTrain.mecanumDrive.pidDrive(true, 0, 0, 0); //sets desired spin to line up with target
				driveTrain.mecanumDrive.setAllPID();
				
				if ((int)SmartDashboard.getNumber("Auto Drive", 0) == (int) 0)
					adjustDegrees = 0;
				else if ((int)SmartDashboard.getNumber("Auto Drive", 0) == (int) -1)
					adjustDegrees = -1 * Consts.adjustDegrees;
				else if ((int)SmartDashboard.getNumber("Auto Drive", 0) == (int) 1)
					adjustDegrees = Consts.adjustDegrees;
				else
					adjustDegrees = 0;
				

			}

			// normal code
			desiredSpin = adjustDegrees - myNavX.ahrs.getYaw();
			driveTrain.mecanumDrive.resetEncoders();
			driveTrain.mecanumDrive.pidDrive(true, 0, 0, desiredSpin); //sets desired spin to line up with target
			driveTrain.mecanumDrive.setAllPID();
			
			 init = false;
			// Building rudimentary P-only PID because PID controller is
			// spitting out null pointers and this should be enough for what
			// we are doing.
				

				 if (Math.abs(myNavX.getHeading() - adjustDegrees) < 5/* || autoTimer.get() > 2*/) { //if we are within 3deg of our desired target
					autoStage=3; //moves to next stage
					init = true;
				}
			break;

		case 2:
			//vision tracking
			if (init) {
//				autoTimer.reset();
//				autoTimer.start();
				init = false;
				driveTrain.mecanumDrive.resetEncoders();
				driveTrain.mecanumDrive.setAllPID();
				//driveTrain.mecanumDrive.pidDrive(true, 19, 0, 0);

			}  
			else {
				// vision adjustment
				driveTrain.mecanumDrive.setAllPID();
				driveTrain.mecanumDrive.pidDrive(true, true); //visionpipe mecanumDrive enabled
			 
				// otherwise, we will drive forward if vision tracking isn't
				// working
			}
			
			if (driveTrain.mecanumDrive.pidAtTarget()) { //make sure this is being triggered when we want it to
				init = true;
				autoStage++;
			}
			
			break;
			
		case 3:
			//final non-vision approach. see comments for stage 0 for more information.
			if (init) {
				init = false;
				driveTrain.mecanumDrive.resetEncoders(); 
				driveTrain.mecanumDrive.setAllPID();
				driveTrain.mecanumDrive.pidDrive(true, Consts.finalDriveDistance, 0, 0);


			} 
			
			else if (driveTrain.mecanumDrive.pidAtTarget()) {
				init = true;
				autoStage++;
			}

			break;

		case 4:
			
			if (SmartDashboard.getBoolean("Gear Drop", true)){
			//opens gear manipulator
			if(init){
//				autoTimer.reset();
	//			autoTimer.start();
				gears.autoOpen();
				init = false;
		
			}
			
			if (gears.isOpened()){
				gears.stop();
				autoStage++;
				init = true;
			}
			}
			break;

		case 5:
			//closes gear manipulator and moves back so spring can be pulled up
			if (SmartDashboard.getBoolean("Gear Drop", true)){
			if (init) {
				init = false;
				driveTrain.mecanumDrive.resetEncoders();
				driveTrain.mecanumDrive.pidDrive(true, Consts.recoilDistance, 0, 0);

			}
			

			//gears.close();
			driveTrain.mecanumDrive.setAllPID();
			driveTrain.mecanumDrive.pidDrive(true, Consts.recoilDistance, 0, 0);
			// this runs until the end.
			}
			break;

		default:
			System.out.println("Uh Oh");
			break;
		}
	
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
		driveTrain.telopPeriodic();
		myNavX.teleopPeriodic();
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
		myNavX.teleopPeriodic();
	}
}
