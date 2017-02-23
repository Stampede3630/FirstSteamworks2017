package org.usfirst.frc.team3630.robot;

import edu.wpi.first.wpilibj.IterativeRobot;
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
	Drive_Train driveTrain;
	Ultrasonics ultraDistance;
	GearsManip gears;
//	NavX navxmxp;
	WheelEncoder frontRightWheelEncoder;
	WheelEncoder frontLeftWheelEncoder;
	WheelEncoder rearRightWheelEncoder;
	WheelEncoder rearLeftWheelEncoder;
	WinchSystem winch;
	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	@Override
	public void robotInit() {
		//chooser.addDefault("Default Auto", defaultAuto);
		//hooser.addObject("My Auto", customAuto);
		//SmartDashboard.putData("Auto choices", chooser);
		driveTrain = new Drive_Train();
		//navxmxp = new NavX();
		//navxmxp.NavXInit();

		 frontLeftWheelEncoder = new WheelEncoder(0, 1, false);;
		 rearLeftWheelEncoder = new WheelEncoder(2, 3, false);;
		 frontRightWheelEncoder = new WheelEncoder(4, 5, true);
		 rearRightWheelEncoder = new WheelEncoder(6, 7, true);;
		// ultraDistance = new Ultrasonics();
		// ultraDistance.ultraInit(1);
		 winch = new WinchSystem();
		gears= new GearsManip();
	//	navxmxp = new NavX();
		//WheelEncoder rightFrontEnc = new WheelEncoder(); rightFrontEnc.encoderInit(0, 1);

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
		autoSelected = chooser.getSelected();
		// autoSelected = SmartDashboard.getString("Auto Selector",
		// defaultAuto);
		System.out.println("Auto selected: " + autoSelected);
	}

	/**
	 * This function is called periodically during autonomous
	 */
	@Override
	public void autonomousPeriodic() {
		switch (autoSelected) {
		case customAuto:
			// Put custom auto code here
			break;
		case defaultAuto:
		default:
			// Put default auto code here
			break;
		}
	}

	/**
	 * This function is called periodically during operator control
	 */
	@Override
	public void teleopPeriodic() {
		driveTrain.telopPeriodic();
		//navxmxp.teleopPeriodic();
		//rightFrontEnc.get();
		//ultraDistance.sensorPeriodic();
		winch.teleopPeriodic();
		gears.telopPeridic();
		SmartDashboard.putNumber("FL Wheel Raw Value", frontLeftWheelEncoder.getRaw());
		SmartDashboard.putNumber("FL Wheel Radians", frontLeftWheelEncoder.getDistRadians());
		SmartDashboard.putNumber("FL Wheel Degrees", frontLeftWheelEncoder.getDistDegrees());
		SmartDashboard.putNumber("FL Wheel Inches", frontLeftWheelEncoder.getDistInches());
		
		SmartDashboard.putNumber("RL Wheel Raw Value", rearLeftWheelEncoder.getRaw());
		SmartDashboard.putNumber("RL Wheel Radians", rearLeftWheelEncoder.getDistRadians());
		SmartDashboard.putNumber("RL Wheel Degrees", rearLeftWheelEncoder.getDistDegrees());
		SmartDashboard.putNumber("RL Wheel Inches", rearLeftWheelEncoder.getDistInches());
		
		SmartDashboard.putNumber("FR Wheel Raw Value", frontRightWheelEncoder.getRaw());
		SmartDashboard.putNumber("FR Wheel Radians", frontRightWheelEncoder.getDistRadians());
		SmartDashboard.putNumber("FR Wheel Degrees", frontRightWheelEncoder.getDistDegrees());
		SmartDashboard.putNumber("FR Wheel Inches", frontRightWheelEncoder.getDistInches());

		SmartDashboard.putNumber("RR Wheel Raw Value", rearRightWheelEncoder.getRaw());
		SmartDashboard.putNumber("RR Wheel Radians", rearRightWheelEncoder.getDistRadians());
		SmartDashboard.putNumber("RR Wheel Degrees", rearRightWheelEncoder.getDistDegrees());
		SmartDashboard.putNumber("RR Wheel Inches", rearRightWheelEncoder.getDistInches());
	}

	/**
	 * This function is called periodically during test mode
	 */
	@Override
	public void testPeriodic() {
		
	}
}

