package org.usfirst.frc.team3630.robot;


public final class Consts {

//Mecanum Drive adjustments
public static final double motorDriveAdjustment = .3;
public static final double mecanumPositionConstant = 90;
public static final double mecanumWheelRadiusInches = 3.0;

//Motor PWM Pin assignments
public static final int driveMotorFrontLeft = 0;
public static final int driveMotorFrontRight = 3;
public static final int driveMotorBottomLeft = 1;
public static final int driveMotorBottomRight = 2;

//Drivetrain Encoder DIO pin assignments
public static final int driveEncoderFrontLeftA = 0;
public static final int driveEncoderFrontLeftB = 1;
public static final int driveEncoderRearLeftA = 2;
public static final int driveEncoderRearLeftB = 3;
public static final int driveEncoderFrontRightA = 4;
public static final int driveEncoderFrontRightB = 5;
public static final int driveEncoderRearRightA = 6;
public static final int driveEncoderRearRightB = 7;

//Gear PWM Assignment
public static final int gearTalonPWM = 4;

//Joystick Assignments
public static final int joytsickChanel = 0;
public static final int joytsick_Gear_ButonZero = 0;
public static final int joytsick_Gear_ButonOne = 1;

//Gear limit switch DIO Assignments
public static final int gearLimitSwitchOpen = 9;
public static final int gearLimitSwitchClosed = 8;

//Gear PWN and speed constant
public static final int gearsPWM = 5;
public static final double gearSpeed = 1;

//Assigned joystick com port
public static final int joystickComPort = 0;

//Winch talon PWM assignment
public static final int winchTalonPWM = 5;

//winch engaged DIO pin assignment
public static final int winchStopPin = 12;

//winch encoder assignment
public static final int winchEncoderPinA = 10;
public static final int winchEncoderPinB = 11;

}
