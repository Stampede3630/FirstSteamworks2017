package org.usfirst.frc.team3630.robot;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
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
	public static AHRS ahrs;
	public NavX myNavX;
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
		driveTrain = new DriveTrain(myNavX);
		myNavX = new NavX();
		winch = new WinchSystem();
		gears = new GearsManip();
		autoTimer = new Timer();
		ahrs = new AHRS(SPI.Port.kMXP);
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
		SmartDashboard.putNumber("Vision Retrycount", 0);
		driveTrain.mecanumDrive.resetEncoders(); // resets encoders
		autoStage = 0;
		init = true;
		myNavX.calibrateHeading();
	}

	/**
	 * This function is called periodically during autonomous
	 */
	@Override
	public void autonomousPeriodic() {
		counter ++;
		SmartDashboard.putNumber("counter", counter);
		myNavX.teleopPeriodic();
		autoTimer.start();
		SmartDashboard.putNumber("autoTime", autoTimer.get());
		// runs through switch statement that goes through stages.
		// each stage has an init which usually resets encoders and moves on to
		// the next stage
		SmartDashboard.putBoolean("Gears Open?", gears.isOpened());
		SmartDashboard.putNumber("AutoStage", autoStage);
		SmartDashboard.putBoolean("PID At Target", driveTrain.mecanumDrive.pidAtTarget());
		SmartDashboard.putBoolean("SPRING Switch", springEngaged.get());
		SmartDashboard.putString("Auto status msg", autoStatus[autoStage]);
		if (!springEngaged.get()) { //spring is engaged. Cutout to gear release.
			autoStage = 4;
			//hi sam i have become sentient
			init = true;
			SmartDashboard.putNumber("drivetrain kP", .05);
		}
		switch (autoStage) {
		// drive straight
		
		case 0:
//			if(SmartDashboard.getBoolean("CHEAT AUTO", false)){ //Cheater non-vision auto. Should not be used.
//				driveTrain.mecanumDrive.pidDrive(true, 75, 0, 0 );
//				
//				if (driveTrain.mecanumDrive.pidAtTarget()) { // condition to move on
//					// to next step o
//					autoStage = 4; //moves to next stage
//					init = true;
//				}
//			}
//			else {
				if(init){
					//driveTrain.mecanumDrive.pidDrive(true, 84, 0, 0);
					//				autoTimer.reset();
					//				autoTimer.start();
					driveTrain.mecanumDrive.resetEncoders(); // resets encoders
					driveTrain.mecanumDrive.setAllPID(); //sets PID coefficients and updates desired distances

					if(SmartDashboard.getBoolean("USE DRIVE STRAIGHT", false)) //this should be used most of the time unless there is a NavX error.
		                             	 driveTrain.mecanumDrive.pidDriveStraight(true, Consts.driveDistance, 0);
					else
					   	driveTrain.mecanumDrive.pidDrive(true, Consts.driveDistance, 0, 0); //drives in a straight line for driveDistance
					
					init = false;
	
				}
				
				if(SmartDashboard.getBoolean("USE DRIVE STRAIGHT", false)) {
					driveTrain.mecanumDrive.pidDriveStraight(true, Consts.driveDistance, 0);
					Timer.delay(.1);
				} else {
				 driveTrain.mecanumDrive.pidDrive(true, Consts.driveDistance, 0, 0); //FOUND ERROR HERE THIS WAS INSIDE INIT LOOP MINOR ERROR
				 driveTrain.mecanumDrive.setAllPID();
				}
				
				if (driveTrain.mecanumDrive.pidAtTarget() /*|| autoTimer.get() > 3.0 */) { // condition to move on
																// to next step o
					autoStage=2; // = 94 (kicks us out); //moves to next stage
					init = true;
				}
//			}
			break;

		case 1:
			// rotate to angle. becuase we are usually using a NavX adjustment, this should go very quickly.
			if (init) {
//				autoTimer.reset();
	//			autoTimer.start();
				driveTrain.mecanumDrive.resetEncoders(); //resets encoders to go to desired distances				
				driveTrain.mecanumDrive.pidDrive(true, 0, 0, 0); //sets desired spin to line up with target
				driveTrain.mecanumDrive.setAllPID();

					
				if ((int)SmartDashboard.getNumber("Auto Drive", 0) == (int) 2) 			//POSITION 2 AUTONOMOUS
					adjustDegrees = 0;
				else if ((int)SmartDashboard.getNumber("Auto Drive", 0) == (int) 3) 	//POSITION 3 AUTONOMOUS
					adjustDegrees = -1 * Consts.adjustDegrees;
				else if ((int)SmartDashboard.getNumber("Auto Drive", 0) == (int) 1)  	//POSITION 1 AUTONOMOUS
					adjustDegrees = Consts.adjustDegrees;
				else 
					adjustDegrees = 0;
					
				driveTrain.mecanumDrive.pidDrive(true, 0, 0,adjustDegrees);
			}
				//this feels sketchy – sama
			if(SmartDashboard.getBoolean("USE DRIVE STRAIGHT", false)) {
				driveTrain.mecanumDrive.pidDriveStraight(true, 0, 0);
			} else {
				// normal code
				desiredSpin = adjustDegrees - ahrs.getYaw();
				driveTrain.mecanumDrive.resetEncoders();
				driveTrain.mecanumDrive.pidDrive(true, 0, 0, desiredSpin); //sets desired spin to line up with target
				
				driveTrain.mecanumDrive.setAllPID();
				
				 init = false;
				// Building rudimentary P-only PID because PID controller is
				// spitting out null pointers and this should be enough for what
				// we are doing.
				
			}
			
			if (Math.abs(myNavX.getHeading() - adjustDegrees) < 5/* || autoTimer.get() > 2*/) { //if we are within 3deg of our desired target
				 autoStage++; //moves to next stage
				 init = true;
			}
			 
			break;

		case 2:
			//vision tracking
			//I don't think we need this here, do we? – SamA
			/*
			  if (!(springEngaged.get())&& autoStage <= 4) {
				driveTrain.mecanumDrive.disablePID();

				autoStage = 4;
				//hi sam i have become sentient
				init = true;
				SmartDashboard.putNumber("drivetrain kP", .05);
				
			}
		       	*/
			if (init) {
//				autoTimer.reset();
//				autoTimer.start();
				init = false;
				driveTrain.mecanumDrive.resetEncoders();
				driveTrain.mecanumDrive.setAllPID();
				if(SmartDashboard.getBoolean("USE DRIVE STRAIGHT", false)) {
					driveTrain.mecanumDrive.pidDriveStraight(true, Consts.visionMakeupDistance, 0);
				} else {
					driveTrain.mecanumDrive.pidDrive(true, Consts.visionMakeupDistance, 0, 0);
				}
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
				SmartDashboard.putNumber("drivetrain kP", .05);

			}
			
			break;
			
		case 3:
			//final non-vision approach. see comments for stage 0 for more information.
			
			if (init) {
				init = false;
				driveTrain.mecanumDrive.resetEncoders(); 
				driveTrain.mecanumDrive.setAllPID();
				driveTrain.mecanumDrive.disablePID();
				if(SmartDashboard.getBoolean("USE DRIVE STRAIGHT", false)) {
					driveTrain.mecanumDrive.pidDriveStraight(true, Consts.finalDriveDistance, 0);
				} else {
					driveTrain.mecanumDrive.pidDrive(true, Consts.finalDriveDistance, 0, 0);
				}

			} 
			//CUTOVER ONLY WHEN BUTTON PRESSED
			/*else if (driveTrain.mecanumDrive.visionAtTarget()) {
				init = true;
				autoStage++;
			} 
	*/
			break;
			
		case 4:
			
			//if (SmartDashboard.getBoolean("GEAR DROP", true)){
			//opens gear manipulator
			if(init){
				driveTrain.mecanumDrive.resetEncoders();
				driveTrain.mecanumDrive.pidDrive(true, 0, 0, 0);

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
			
			break;

		case 5:
		
			//closes gear manipulator and moves back so spring can be pulled up
			if (SmartDashboard.getBoolean("GEAR DROP", true)){
				SmartDashboard.putNumber("drivetrain kP", .015);

			if (init) {
				//Timer.delay(1);
				driveTrain.mecanumDrive.disablePID();

				init = false;
				driveTrain.mecanumDrive.resetEncoders();
				if(SmartDashboard.getBoolean("USE DRIVE STRAIGHT", false)) {
					driveTrain.mecanumDrive.pidDriveStraight(true, Consts.recoilDistance, 0);
				} else {
					driveTrain.mecanumDrive.pidDrive(true, Consts.recoilDistance, 0, 0);
				}
			

			}
			if (driveTrain.mecanumDrive.pidAtTarget()) 	driveTrain.mecanumDrive.disablePID();

			//gears.close();
			driveTrain.mecanumDrive.setAllPID();
			if(SmartDashboard.getBoolean("USE DRIVE STRAIGHT", false)) {
				driveTrain.mecanumDrive.pidDriveStraight(true, Consts.recoilDistance, 0);
			} else {
				driveTrain.mecanumDrive.pidDrive(true, Consts.recoilDistance, 0, 0);
			}
			// this runs until the end.
			}
		break;
		case 90:
			
			break;
		case 91:
			
			break;
		case 92:
			
			break;
		case 93:			
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
		SmartDashboard.putBoolean("SPRING Switch", springEngaged.get());
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
