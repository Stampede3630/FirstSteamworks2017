package org.usfirst.frc.team3630.robot;

import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;

public class HomebrewMecanum {
	private static Talon fL, rL, fR, rR;
	private WheelEncoder fLe, rLe, fRe, rRe;
//	private PIDController fLPID, rLPID, frPID, rRPID;
//	private PIDOutput fLOut, rLOut, fROut, rROut;
	private double kP, kI, kD;
	/**
	 * @param frontLeft talon for front left motor
	 * @param rearLeft talon for rear left motor
	 * @param frontRight talon for front right motor
	 * @param rearRight talon for rear right motor
	 */
	public HomebrewMecanum (Talon frontLeft, Talon rearLeft, Talon frontRight, Talon rearRight){
		fL = frontLeft;
		rL = rearLeft;
		fR = frontRight;
		rR = rearRight;

		fLe = new WheelEncoder(Consts.driveEncoderFrontLeftA, Consts.driveEncoderFrontLeftB, false);
		rLe = new WheelEncoder(Consts.driveEncoderRearLeftA, Consts.driveEncoderRearLeftB, false);
		fRe = new WheelEncoder(Consts.driveEncoderFrontRightA, Consts.driveEncoderFrontRightB, true);
		rRe = new WheelEncoder(Consts.driveEncoderRearRightA, Consts.driveEncoderRearRightB, true);

		fR.setInverted(true);
		rR.setInverted(true);
	}
	/**
	 * @param frontLeft pin for front left motor
	 * @param rearLeft pin for rear left motor
	 * @param frontRight pin for front right motor
	 * @param rearRight pin for rear right motor
	 */
	public HomebrewMecanum (int frontLeft, int rearLeft, int frontRight, int rearRight){
		fL = new Talon(frontLeft);
		rL = new Talon(rearLeft);
		fR = new Talon(frontRight);
		rR = new Talon(rearRight);

		fLe = new WheelEncoder(Consts.driveEncoderFrontLeftA, Consts.driveEncoderFrontLeftB, false);
		rLe = new WheelEncoder(Consts.driveEncoderRearLeftA, Consts.driveEncoderRearLeftB, false);
		fRe = new WheelEncoder(Consts.driveEncoderFrontRightA, Consts.driveEncoderFrontRightB, true);
		rRe = new WheelEncoder(Consts.driveEncoderRearRightA, Consts.driveEncoderRearRightB, true);

		fR.setInverted(true);
		rR.setInverted(true);
	}
	/**
	 * @param velocityX desired velocity in x
	 * @param velocityY desired velocity in y
	 * @param angularVelocityDeg desired angular velocity in degrees
	 * @param postDiagnostics whether you want smart dashboard to put wheel and input velocities. default should be false
	 * @return 4 wheel speeds, from the top left counterclockwise.
	 */

	public double[] mecanumCalc (double velocityX, double velocityY, double angularVelocityDeg, boolean postDiagnostics){
		//This function takes the velocity in x and y and theta and converts them to


		double angularVelocityRad = angularVelocityDeg * Math.PI / 180; //Converts degrees to radians for you, liam
		double[] wheelspeedResult = new double[4];
		//For more information about this formula, see the mecanum kinematics 
		wheelspeedResult[0] = velocityX - velocityY - Consts.mecanumPositionConstant * angularVelocityRad;	
		wheelspeedResult[1] = velocityX + velocityY - Consts.mecanumPositionConstant * angularVelocityRad;
		wheelspeedResult[2] = velocityX - velocityY + Consts.mecanumPositionConstant * angularVelocityRad;
		wheelspeedResult[3] = velocityX + velocityY + Consts.mecanumPositionConstant * angularVelocityRad;

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

	
	private static double wheelPID () {
		//TODO: THIS
		return 0;
	}
	public void motorDrive (Talon talon, double motorSpeed, boolean postDiagnostics){
		//This function maps the motor drive to the talon speed. More tuning from the constant is needed.
		double adjustedMotorSpeed = motorSpeed * Consts.motorDriveAdjustment;
		talon.set(adjustedMotorSpeed);
		System.out.println(adjustedMotorSpeed);
		SmartDashboard.putNumber("adjusted"+String.valueOf(talon.getChannel()), talon.getSpeed());
		SmartDashboard.putNumber("Encoder"+String.valueOf(talon.getChannel()), talon.getSpeed());
	

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
		
		SmartDashboard.putNumber("FL Rate", fLe.getRate());
		SmartDashboard.putNumber("FR Rate", fRe.getRate());
		SmartDashboard.putNumber("RL Rate", rLe.getRate());
		SmartDashboard.putNumber("RR Rate", rRe.getRate());

	}
}