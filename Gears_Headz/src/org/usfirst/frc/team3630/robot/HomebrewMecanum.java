package org.usfirst.frc.team3630.robot;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;

public class HomebrewMecanum {
	private Wheel fL, rL, fR, rR;

//	private PIDController fLPID, rLPID, frPID, rRPID;
//	private PIDOutput fLOut, rLOut, fROut, rROut;
	private double kP, kI, kD;

	/**
	 * @param frontLeft pin for front left motor
	 * @param rearLeft pin for rear left motor
	 * @param frontRight pin for front right motor
	 * @param rearRight pin for rear right motor
	 */
	public HomebrewMecanum (int frontLeft, int rearLeft, int frontRight, int rearRight){
		fL = new Wheel(Consts.driveEncoderFrontLeftA, Consts.driveEncoderFrontLeftB, frontLeft, false);
		rL = new Wheel(Consts.driveEncoderRearLeftA, Consts.driveEncoderRearLeftB, rearLeft, false);
		fR = new Wheel(Consts.driveEncoderFrontRightA, Consts.driveEncoderFrontRightB, frontRight, true);
		rR = new Wheel(Consts.driveEncoderRearRightA, Consts.driveEncoderRearRightB, rearRight, true);
	}
	/**
	 * @param velocityX desired velocity in x
	 * @param velocityY desired velocity in y
	 * @param angularVelocityDeg desired angular velocity in degrees
	 * @param postDiagnostics whether you want smart dashboard to put wheel and input velocities. default should be false
	 * @return 4 wheel speeds, from the top left counterclockwise.
	 */

	public double[] forwardMecanum (double frontLeft, double rearLeft, double frontRight, double rearRight){
		double[] result = new double[3];
		result[0] = (frontLeft + rearLeft + frontRight + rearRight)/4;
		result[1] = (-frontLeft + rearLeft - rearRight + frontRight)/4;
		result[2] = (-frontLeft - rearLeft + rearRight + frontRight)/(4*Consts.mecanumPositionConstant);
	
		return result;
	}
	
	/**
	 * @param velocityX desired velocity in inches/second
	 * @param velocityY desired velocity in inches/second
	 * @param angularVelocityDeg desired angular velocity in degrees per second
	 * @param postDiagnostics default should be false. Post junk to smartDashboard?
	 * @return wheelspeeds in radians
	 */
	public double[] mecanumCalc (double velocityX, double velocityY, double angularVelocityDeg, boolean postDiagnostics){
		//This function takes the velocity in x and y and theta and converts them to
		//input is in inches/second, deg/sec

		double angularVelocityRad = angularVelocityDeg * Math.PI / 180; //Converts degrees to radians for you, liam
		
		double[] wheelspeedResult;
		wheelspeedResult = new double[4];
		//For more information about this formula, see the mecanum kinematics 
		wheelspeedResult[0] = velocityX - velocityY - Consts.mecanumPositionConstant * angularVelocityRad;	
		wheelspeedResult[1] = velocityX + velocityY - Consts.mecanumPositionConstant * angularVelocityRad;
		wheelspeedResult[2] = velocityX - velocityY + Consts.mecanumPositionConstant * angularVelocityRad;
		wheelspeedResult[3] = velocityX + velocityY + Consts.mecanumPositionConstant * angularVelocityRad;
		
		for(int i = 0; i<wheelspeedResult.length; i++) wheelspeedResult[i]/= Consts.mecanumWheelRadiusInches;
		
		if(postDiagnostics){
			SmartDashboard.putNumber("FrontLeft", wheelspeedResult[0]);
			SmartDashboard.putNumber("RearLeft", wheelspeedResult[1]);
			SmartDashboard.putNumber("FrontRight", wheelspeedResult[2]);
			SmartDashboard.putNumber("RearRight", wheelspeedResult[3]);

			SmartDashboard.putNumber("Vx", velocityX);
			SmartDashboard.putNumber("Vy", velocityY);
			SmartDashboard.putNumber("Vtheta", angularVelocityRad);

		}
		return wheelspeedResult; //in rad/sec
	}

	

	public void motorDrive (Wheel myWheel, double motorSpeed, boolean postDiagnostics){
		//This function maps the motor drive to the talon speed. More tuning from the constant is needed.
		double adjustedMotorSpeed = motorSpeed * Consts.motorDriveAdjustment;
		myWheel.setWheelSpeed(adjustedMotorSpeed);
		SmartDashboard.putNumber("motorDrive commandSPeed" + String.valueOf(myWheel.talon.getChannel()), adjustedMotorSpeed);
		System.out.println(adjustedMotorSpeed);
		SmartDashboard.putNumber("adjusted"+String.valueOf(myWheel.talon.getChannel()), myWheel.talon.getSpeed());
		SmartDashboard.putNumber("Encoder"+String.valueOf(myWheel.talon.getChannel()), myWheel.talon.getSpeed());
		SmartDashboard.putNumber("kP actual" + String.valueOf(myWheel.talon.getChannel()), myWheel.pid.getP());
		SmartDashboard.putNumber("kD actual" + String.valueOf(myWheel.talon.getChannel()), myWheel.pid.getD());
		SmartDashboard.putNumber("kI actual" + String.valueOf(myWheel.talon.getChannel()), myWheel.pid.getI());
		SmartDashboard.putNumber("kF actual" + String.valueOf(myWheel.talon.getChannel()), myWheel.pid.getF());

	}

	/**
	 * this function does the full drive implementation for teleop
	 * @param velocityX desired velocity in x
	 * @param velocityY desired velocity in y
	 * @param angularVelocityDeg desired angular velocity in degrees
	 * @param postDiagnostics whether you want smart dashboard to put wheel and input velocities. default should be false
	 */
	public void driveImplementation (double velocityX, double velocityY, double angularVelocityDeg, boolean postDiagnostics){

		double [] wheelSpeeds = mecanumCalc(velocityX, velocityY, angularVelocityDeg, postDiagnostics);

		motorDrive(fL, wheelSpeeds[0], postDiagnostics);
		motorDrive(rL, wheelSpeeds[1], postDiagnostics);
		motorDrive(rR, wheelSpeeds[2], postDiagnostics);
		motorDrive(fR, wheelSpeeds[3], postDiagnostics);


	}

	public void setAllPID (){
		double kP = SmartDashboard.getNumber("drivetrain kP", .1);
		double kI = SmartDashboard.getNumber("drivetrain kI", 0);
		double kD = SmartDashboard.getNumber("drivetrain kD", 0);
		double kF = SmartDashboard.getNumber("drivetrain kF", 1/50);
		
		fL.pid.setPID(kP, kI, kD, kF);
		fR.pid.setPID(kP, kI, kD, kF);
		rL.pid.setPID(kP, kI, kD, kF);
		rR.pid.setPID(kP, kI, kD, kF);
		
	}

}