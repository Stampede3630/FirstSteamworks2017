package org.usfirst.frc.team3630.robot;


public final class Consts {

//Mecanum Drive adjustments
public static final double motorDriveAdjustment = 1;
public static final double mecanumPositionConstant = 90;
public static final double mecanumWheelRadiusInches = 3.0;

//Motor PWM Pin assignments
public static final int driveMotorFrontLeft = 0;
public static final int driveMotorFrontRight = 3;
public static final int driveMotorBottomLeft = 1;
public static final int driveMotorBottomRight = 2;

//Gear PWM Assignment
public static final int gearTalonPWM = 5;

//Winch talon PWM assignment
public static final int winchTalonPWM = 4;

//Joystick Assignments
public static final int joytsickChannel = 0;
public static final int joystickComPort = 0;
public static final int openButton = 3;
public static final int closeButton = 1;
public static final int joystickWinchButton = 6;
public static final int buttonSprint = 5;
public static final int buttonSprintAlternate = 5;

//Gear limit switch DIO Assignments
public static final int gearLimitSwitchOpen = 8;
public static final int gearLimitSwitchClosed = 9;

//speed constants
public static final double gearSpeed = 1;
public static final double slowK = .2;
public static final double fastK = 1;

//winch encoder assignment
public static final int winchEncoderPinA = 11;
public static final int winchEncoderPinB = 12;

//winch engaged DIO pin assignment
public static final int winchStopPin = 10;

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


}
