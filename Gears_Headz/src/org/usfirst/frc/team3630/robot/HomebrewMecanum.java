package org.usfirst.frc.team3630.robot;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class HomebrewMecanum  {
	public Wheel fL, rL, fR, rR;
	private VisionMath myVisionMath;
	public double intendedRobotAngle = 0.0;


	/**
	 * @param frontLeft
	 *            pin for front left motor
	 * @param rearLeft
	 *            pin for rear left motor
	 * @param frontRight
	 *            pin for front right motor
	 * @param rearRight
	 *            pin for rear right motor
	 */
	 public  HomebrewMecanum (int frontLeft, int rearLeft, int frontRight, int rearRight) {
		fL = new Wheel(Consts.driveEncoderFrontLeftA, Consts.driveEncoderFrontLeftB, frontLeft, false);
		rL = new Wheel(Consts.driveEncoderRearLeftA, Consts.driveEncoderRearLeftB, rearLeft, false);
		fR = new Wheel(Consts.driveEncoderFrontRightA, Consts.driveEncoderFrontRightB, frontRight, true);
		rR = new Wheel(Consts.driveEncoderRearRightA, Consts.driveEncoderRearRightB, rearRight, true);

		SmartDashboard.putNumber("Vision Retrycount", 0);
		myVisionMath = new VisionMath();
		
	    SmartDashboard.putBoolean("PID Control?", false);
	    SmartDashboard.putBoolean("Vision Pipe?", false);
	    SmartDashboard.putNumber("Desired Distance X", 0);
	    SmartDashboard.putNumber("Desired Distance Y", 0);
	    SmartDashboard.putNumber("Desired Distance theta", 0);
	    
	}
	 
	 public  HomebrewMecanum (int frontLeft, int rearLeft, int frontRight, int rearRight, NavX myNavX) {
			fL = new Wheel(Consts.driveEncoderFrontLeftA, Consts.driveEncoderFrontLeftB, frontLeft, false);
			rL = new Wheel(Consts.driveEncoderRearLeftA, Consts.driveEncoderRearLeftB, rearLeft, false);
			fR = new Wheel(Consts.driveEncoderFrontRightA, Consts.driveEncoderFrontRightB, frontRight, true);
			rR = new Wheel(Consts.driveEncoderRearRightA, Consts.driveEncoderRearRightB, rearRight, true);

			myVisionMath = new VisionMath();
			
		    SmartDashboard.putBoolean("PID Control?", false);
		    SmartDashboard.putBoolean("Vision Pipe?", false);
		     SmartDashboard.putNumber("Desired Distance X", 0);
		    SmartDashboard.putNumber("Desired Distance Y", 0);
		    SmartDashboard.putNumber("Desired Distance theta", 0);
	 }


	/**
	 * @param velocityX
	 *            desired velocity in x
	 * @param velocityY
	 *            desired velocity in y
	 * @param angularVelocityDeg
	 *            desired angular velocity in degrees
	 * @param postDiagnostics
	 *            whether you want smart dashboard to put wheel and input
	 *            velocities. default should be false
	 * @return 4 wheel speeds, from the top left counterclockwise.
	 */

	public double[] forwardMecanum(double frontLeft, double rearLeft, double frontRight, double rearRight) {
		double[] result = new double[3];
		result[0] = (frontLeft + rearLeft + frontRight + rearRight) / 4;
		result[1] = (-frontLeft + rearLeft - rearRight + frontRight) / 4;
		result[2] = (-frontLeft - rearLeft + rearRight + frontRight) / (4 * Consts.mecanumPositionConstant);

		return result;
	}

	/**
	 * @param velocityX
	 *            desired velocity in inches/second
	 * @param velocityY
	 *            desired velocity in inches/second
	 * @param angularVelocityDeg
	 *            desired angular velocity in degrees per second
	 * @param postDiagnostics
	 *            default should be false. Post junk to smartDashboard?
	 * @return wheelspeeds in radians/second
	 */
	public double[] mecanumCalc(double velocityX, double velocityY, double angularVelocityDeg,
			boolean postDiagnostics) {
		// This function takes the velocity in x and y and theta and converts
		// them to input is in inches/second, deg/sec

		double angularVelocityRad = angularVelocityDeg * Math.PI / 180;
		//Converts degrees to radians for you, liam
		
		double[] wheelspeedResult = new double[4];
		//For more information about this formula, see the mecanum kinematics 
		wheelspeedResult[0] = velocityX - velocityY - Consts.mecanumPositionConstant * angularVelocityRad;	
		wheelspeedResult[1] = velocityX + velocityY - Consts.mecanumPositionConstant * angularVelocityRad;
		wheelspeedResult[2] = velocityX - velocityY + Consts.mecanumPositionConstant * angularVelocityRad;
		wheelspeedResult[3] = velocityX + velocityY + Consts.mecanumPositionConstant * angularVelocityRad;

		if (postDiagnostics) {
			SmartDashboard.putNumber("HM FrontLeft", wheelspeedResult[0]);
			SmartDashboard.putNumber("HM RearLeft", wheelspeedResult[1]);
			SmartDashboard.putNumber("HM FrontRight", wheelspeedResult[2]);
			SmartDashboard.putNumber("HM RearRight", wheelspeedResult[3]);

			SmartDashboard.putNumber("HM Vx", velocityX);
			SmartDashboard.putNumber("HM Vy", velocityY);
			SmartDashboard.putNumber("HM Vtheta", angularVelocityRad);

		}
		return wheelspeedResult; // in rad/sec
	}
	public double[] distanceCalc(double velocityX, double velocityY, double angularVelocityDeg,
			boolean postDiagnostics) {
		double angularVelocityRad = angularVelocityDeg * Math.PI / 180;

		double[] wheelspeedResult;
		wheelspeedResult = new double[4];
		// For more information about this formula, see the mecanum kinematics
		wheelspeedResult[0] = velocityX - velocityY - Consts.mecanumPositionConstant * angularVelocityRad;
		wheelspeedResult[1] = velocityX + velocityY - Consts.mecanumPositionConstant * angularVelocityRad;
		wheelspeedResult[2] = velocityX - velocityY + Consts.mecanumPositionConstant * angularVelocityRad;
		wheelspeedResult[3] = velocityX + velocityY + Consts.mecanumPositionConstant * angularVelocityRad;


		if (postDiagnostics) {
			SmartDashboard.putNumber("FrontLeftDist", wheelspeedResult[0]);
			SmartDashboard.putNumber("RearLeftDist", wheelspeedResult[1]);
			SmartDashboard.putNumber("FrontRightDist", wheelspeedResult[2]);
			SmartDashboard.putNumber("RearRightDist", wheelspeedResult[3]);

			SmartDashboard.putNumber("delta x", velocityX);
			SmartDashboard.putNumber("delta y", velocityY);
			SmartDashboard.putNumber("delta theta", angularVelocityRad);

		}
		return wheelspeedResult; // in rad/sec
	}

	public void motorDrive(Wheel myWheel, double motorSpeed, boolean postDiagnostics) {
		// This function maps the motor drive to the talon speed. More tuning
		// from the constant is needed.
		double adjustedMotorSpeed = motorSpeed * Consts.motorDriveAdjustment;
		myWheel.talon.set(adjustedMotorSpeed);

		SmartDashboard.putNumber("motorDrive commandSpeed" + String.valueOf(myWheel.talon.getChannel()),
				adjustedMotorSpeed);
	}
	

	/**
	 * this function does the full drive implementation for teleop
	 * 
	 * @param velocityX
	 *            desired velocity in x
	 * @param velocityY
	 *            desired velocity in y
	 * @param angularVelocityDeg
	 *            desired angular velocity in degrees
	 * @param postDiagnostics
	 *            whether you want smart dashboard to put wheel and input
	 *            velocities. default should be false
	 */
	public void driveImplementation(double velocityX, double velocityY, double angularVelocityDeg,
			boolean postDiagnostics) {
	
			double[] wheelSpeeds = mecanumCalc(velocityX, velocityY, angularVelocityDeg, postDiagnostics);
			if (fL.pid.isEnabled()) fL.pid.disable();
			if (fR.pid.isEnabled())fR.pid.disable();
			if (rL.pid.isEnabled())rL.pid.disable();
			if (rR.pid.isEnabled())rR.pid.disable();

			motorDrive(fL, wheelSpeeds[0], postDiagnostics);
			motorDrive(rL, wheelSpeeds[1], postDiagnostics);
			motorDrive(rR, wheelSpeeds[2], postDiagnostics);
			motorDrive(fR, wheelSpeeds[3], postDiagnostics);
			
			
			fL.getInfo();
			fR.getInfo();
			rL.getInfo();
			rR.getInfo();
	}

	public void setAllPID() {
		double kP = SmartDashboard.getNumber("drivetrain kP", .02);
		double kI = SmartDashboard.getNumber("drivetrain kI", 0);
		double kD = SmartDashboard.getNumber("drivetrain kD", 0);

		fL.pid.setPID(kP, kI, kD);
		fR.pid.setPID(kP, kI, kD);
		rL.pid.setPID(kP, kI, kD);
		rR.pid.setPID(kP, kI, kD);
	}

	public void resetEncoders() {
		   fL.encoder.reset();
		   fR.encoder.reset();
		   rL.encoder.reset();
		   rR.encoder.reset();
	}

	public void pidDrive() {
		double speedX, speedY, speedTheta;
		if (SmartDashboard.getBoolean("PID Control?",false)) {
			if (!fL.pid.isEnabled()) fL.pid.enable();
			if (!fR.pid.isEnabled()) fR.pid.enable();
			if (!rL.pid.isEnabled()) rL.pid.enable();
			if (!rR.pid.isEnabled()) rR.pid.enable();

			if (SmartDashboard.getBoolean("Vision Pipe?", false)){
				myVisionMath.refereshImageValues();
				speedX = myVisionMath.robotToFrontDY(48);
				speedY = myVisionMath.robotToFrontDX(48);
				speedTheta = myVisionMath.rotateRobotAngle();
				SmartDashboard.putNumber("Deisred Distance X", speedX);
				SmartDashboard.putNumber("Desired Distance Y", speedY);
				SmartDashboard.putNumber("Desired Distance theta", speedTheta);
			}
			else {
					 speedX = SmartDashboard.getNumber("Desired Distance X", 0);
					 speedY = SmartDashboard.getNumber("Desired Distance Y", 0);
					 speedTheta = SmartDashboard.getNumber("Desired Distance theta", 0);
			}

			double wheelDistances [] = distanceCalc(speedX, speedY, speedTheta, true);
			
			fL.setWheelDistance(wheelDistances[0]);
			rL.setWheelDistance(wheelDistances[1]);
			rR.setWheelDistance(wheelDistances[2]);
			fR.setWheelDistance(wheelDistances[3]);
		/**/
			SmartDashboard.putBoolean("PID at Target? " + String.valueOf(fL.talon.getChannel()), fL.pid.onTarget());
			SmartDashboard.putBoolean("PID at Target? " + String.valueOf(rL.talon.getChannel()), rL.pid.onTarget());
			SmartDashboard.putBoolean("PID at Target? " + String.valueOf(fR.talon.getChannel()), fR.pid.onTarget());
			SmartDashboard.putBoolean("PID at Target? " + String.valueOf(rR.talon.getChannel()), rR.pid.onTarget());

	}
	}
	
	public void pidDrive(boolean pidControl, boolean visionPipe) {
		double speedX, speedY, speedTheta;
		if (pidControl) {
			if (!fL.pid.isEnabled()) fL.pid.enable();
			if (!fR.pid.isEnabled()) fR.pid.enable();
			if (!rL.pid.isEnabled()) rL.pid.enable();
			if (!rR.pid.isEnabled()) rR.pid.enable();

			if (visionPipe){
				SmartDashboard.putBoolean("Vision Pipe?", true);
				myVisionMath.refereshImageValues();
				speedX = myVisionMath.robotToFrontDY(Consts.visionCutoff); //X is front and back, left and right in Sams code. 
																			//This is correct.
				speedY = myVisionMath.robotToFrontDX(Consts.visionCutoff);
				speedTheta = myVisionMath.rotateRobotAngle();
				SmartDashboard.putNumber("Desired Distance X", speedX);
				SmartDashboard.putNumber("Desired Distance Y", speedY);
				SmartDashboard.putNumber("Desired Distance theta", speedTheta);
				//intendedRobotAngle = speedTheta;
				double wheelDistances [];
				if (SmartDashboard.getBoolean("USE DRIVE STRAIGHT", false)) {
					wheelDistances = distanceCalc(speedX, speedY, intendedRobotAngle, true);
				} else {
					wheelDistances = distanceCalc(speedX, speedY, speedTheta, true);
				}

				int retryCount = (int) SmartDashboard.getNumber("Vision Retrycount", 0);
				if (speedX == 0 && speedY == 0){ //if vision is not working
					retryCount++;
					SmartDashboard.putNumber("Vision Retrycount", retryCount);	
					if (retryCount > Consts.maxVisionRetryCount) {
						pidDrive(true, Consts.visionMakeupDistance, 0, 0);
					}
				
				}	else {
					fL.setWheelDistance(wheelDistances[0]+fL.encoder.getDistance());
					rL.setWheelDistance(wheelDistances[1]+rL.encoder.getDistance());
					rR.setWheelDistance(wheelDistances[2]+rR.encoder.getDistance());
					fR.setWheelDistance(wheelDistances[3]+fR.encoder.getDistance());
				}
			}
			else {
					
					 speedX = SmartDashboard.getNumber("Desired Distance X", 0);
					 speedY = SmartDashboard.getNumber("Desired Distance Y", 0);
					 speedTheta = SmartDashboard.getNumber("Desired Distance theta", 0);
						double wheelDistances [];
						if (SmartDashboard.getBoolean("USE DRIVE STRAIGHT", false)) {
							wheelDistances = distanceCalc(speedX, speedY, intendedRobotAngle, true);
						} else {
							wheelDistances = distanceCalc(speedX, speedY, speedTheta, true);
						}

						fL.setWheelDistance(wheelDistances[0]);
						rL.setWheelDistance(wheelDistances[1]);
						rR.setWheelDistance(wheelDistances[2]);
						fR.setWheelDistance(wheelDistances[3]);

			}

			
		
			SmartDashboard.putBoolean("PID at Target? " + String.valueOf(fL.talon.getChannel()), fL.pid.onTarget());
			SmartDashboard.putBoolean("PID at Target? " + String.valueOf(rL.talon.getChannel()), rL.pid.onTarget());
			SmartDashboard.putBoolean("PID at Target? " + String.valueOf(fR.talon.getChannel()), fR.pid.onTarget());
			SmartDashboard.putBoolean("PID at Target? " + String.valueOf(rR.talon.getChannel()), rR.pid.onTarget());

	}
	}
	
	
	public void pidDrive(boolean pidControl, double desiredDistanceX, double desiredDistanceY, double desitedDistanceTheta) {
		double speedX, speedY, speedTheta;
		if (pidControl) {
			if (!fL.pid.isEnabled()) fL.pid.enable();
			if (!fR.pid.isEnabled()) fR.pid.enable();
			if (!rL.pid.isEnabled()) rL.pid.enable();
			if (!rR.pid.isEnabled()) rR.pid.enable();

			speedX = desiredDistanceX;
			speedY = desiredDistanceY;
			//SJV
			intendedRobotAngle = desitedDistanceTheta;
			speedTheta = desitedDistanceTheta;

			double wheelDistances [] = distanceCalc(speedX, speedY, speedTheta, true);
			
			fL.setWheelDistance(wheelDistances[0]);
			rL.setWheelDistance(wheelDistances[1]);
			rR.setWheelDistance(wheelDistances[2]);
			fR.setWheelDistance(wheelDistances[3]);
					
			SmartDashboard.putBoolean("PID at Target? " + String.valueOf(fL.talon.getChannel()), fL.pid.onTarget());
			SmartDashboard.putBoolean("PID at Target? " + String.valueOf(rL.talon.getChannel()), rL.pid.onTarget());
			SmartDashboard.putBoolean("PID at Target? " + String.valueOf(fR.talon.getChannel()), fR.pid.onTarget());
			SmartDashboard.putBoolean("PID at Target? " + String.valueOf(rR.talon.getChannel()), rR.pid.onTarget());

		}
	}
	//SJV
	public void pidDriveStraight(boolean pidControl, double desiredDistanceX, double desiredDistanceY) {
		double errorAngle = intendedRobotAngle - Robot.ahrs.getYaw();
		this.pidDrive(pidControl,desiredDistanceX,desiredDistanceY,-errorAngle);
	}
	

	public void pidWrite(double output) {

			if (!fL.pid.isEnabled()) fL.pid.enable();
			if (!fR.pid.isEnabled()) fR.pid.enable();
			if (!rL.pid.isEnabled()) rL.pid.enable();
			if (!rR.pid.isEnabled()) rR.pid.enable();


			double wheelDistances [] = distanceCalc(0, 0, output, true);
			
			fL.setWheelDistance(wheelDistances[0]);
			rL.setWheelDistance(wheelDistances[1]);
			rR.setWheelDistance(wheelDistances[2]);
			fR.setWheelDistance(wheelDistances[3]);
		
			SmartDashboard.putBoolean("PID at Target? " + String.valueOf(fL.talon.getChannel()), fL.pid.onTarget());
			SmartDashboard.putBoolean("PID at Target? " + String.valueOf(rL.talon.getChannel()), rL.pid.onTarget());
			SmartDashboard.putBoolean("PID at Target? " + String.valueOf(fR.talon.getChannel()), fR.pid.onTarget());
			SmartDashboard.putBoolean("PID at Target? " + String.valueOf(rR.talon.getChannel()), rR.pid.onTarget());

	}
	
	public boolean pidAtTarget () { //This is the workaround to avoid using PID on target. This is functional.
		double rateMax = SmartDashboard.getNumber("RateMin", .5);
		//may want to implement navX, and escape clause
		boolean result = 
				(
						(
							(Math.abs(fL.encoder.getDistance()-fL.pid.getSetpoint())< Consts.distanceMarginOfError) &&
							(Math.abs(fR.encoder.getDistance()-fR.pid.getSetpoint())< Consts.distanceMarginOfError) &&
							(Math.abs(rL.encoder.getDistance()-rL.pid.getSetpoint())< Consts.distanceMarginOfError) &&
							(Math.abs(rR.encoder.getDistance()-rR.pid.getSetpoint())< Consts.distanceMarginOfError)
								) &&
				
						(
							(Math.abs(fL.encoder.getRate()) < rateMax) &&
							(Math.abs(fR.encoder.getRate()) < rateMax) &&
							(Math.abs(rL.encoder.getRate()) < rateMax) &&
							(Math.abs(rR.encoder.getRate()) < rateMax)
						)
					)
					||
					(
							(Math.abs(fL.encoder.getDistance()-fL.pid.getSetpoint())< Consts.strictDistanceMarginOfError) &&
							(Math.abs(fR.encoder.getDistance()-fR.pid.getSetpoint())< Consts.strictDistanceMarginOfError) &&
							(Math.abs(rL.encoder.getDistance()-rL.pid.getSetpoint())< Consts.strictDistanceMarginOfError) &&
							(Math.abs(rR.encoder.getDistance()-rR.pid.getSetpoint())< Consts.strictDistanceMarginOfError)
							);
		
		return result;
	}
}