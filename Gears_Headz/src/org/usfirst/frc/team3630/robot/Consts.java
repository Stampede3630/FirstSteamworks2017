package org.usfirst.frc.team3630.robot;


public class Consts {

//Mecanum Drive adjustments
public static final double motorDriveAdjustment = 1;
public static final double mecanumPositionConstant = 90;
public static final double mecanumWheelRadiusInches = 5.9863/2;

//Motor PWM Pin assignments
public static final int driveMotorFrontLeft = 0;
public static final int driveMotorFrontRight = 3;
public static final int driveMotorBottomLeft = 1;
public static final int driveMotorBottomRight = 2;

//Gear PWM Assignment
public static final int gearTalonPWM = 4;

//Winch talon PWM assignment
public static final int winchTalonPWM = 5;

//Joystick Assignments
public static final int joytsickChannel = 0;
public static final int joystickComPort = 0;
public static final int openButton = 3;
public static final int closeButton = 6;
public static final int joystickWinchButton = 1;
public static final int buttonSprint = 5;
public static final int buttonSprintAlternate = 5;

//Gear limit switch DIO Assignments
public static final int gearLimitSwitchOpen = 8;
public static final int gearLimitSwitchClosed = 9;

//speed constants
public static final double gearSpeed = -1;

public static final double slowK = .25;
public static final double fastK = 1;
public static final double joystickToInchesPerSecond = 90;

//winch encoder assignment
public static final int winchEncoderPinA = 11;
public static final int winchEncoderPinB = 12;

//winch engaged DIO pin assignment
public static final int winchStopPin = 10;
//Spring limit switch
public static final int springEngaged = 13;

//Drivetrain Encoder DIO pin assignments
public static final int driveEncoderFrontLeftA = 0;
public static final int driveEncoderFrontLeftB = 1;
public static final int driveEncoderRearLeftA = 2;
public static final int driveEncoderRearLeftB = 3;
public static final int driveEncoderFrontRightA = 4;
public static final int driveEncoderFrontRightB = 5;
public static final int driveEncoderRearRightA = 6;
public static final int driveEncoderRearRightB = 7;
public static final int buttonSwitchDirection = 2;
public static final double talonConversion = 1;
public static final double wheelKP = .05;
public static final double wheelKI = 0.00015;
public static final double wheelKD = 0;
public static final double rateMin = .5;


//Auto constants

/*
 * DISTANCE TO TAPE (base line):		93.25 inches
 * Total robot length:					35 inches
 * Vision minimum:						24-30 inches
 */	

public static final double driveDistance = 30; //in inches 
public static final double distanceMarginOfError = 5;
public static final double strictDistanceMarginOfError = 1;
public static final double adjustDegrees = 30;
public static final double visionCutoff = 30;
public static final double finalDriveDistance = 30;
public static final double recoilDistance = -22;//in inches

public static final double maxAcceleration = .35;
public static final double angleKp = .1;
public static final double maxAutoAbortTime = 10;


// Vision related constants
public static final double perspecLimitDeg = 5.0; // Must be positive.
public static final double nearZero = 0.1; // For comparing against zero in floating point.
public static final int maxVisionRetryCount = 10;
public static final double visionMakeupDistance = 8;



}
