package org.usfirst.frc.team3630.robot;


public class ContsClass {
public static final double motorDriveAdjustment = .3;

public static final int driveMotorFrontLeft = 0;
public static final int driveMotorFrontRight = 3;
public static final int joytsick_Gear_ButonTwo=2;
public static final int joytsickChanel = 0;
public static final int joytsick_Gear_ButonZero = 0;
public static final int joytsick_Gear_ButonOne = 1;

public static final int driveMotorBottomLeft = 1;
public static final int driveMotorBottomRight = 2;
public static final int gearLimmitswitchIsOPEN = 9;
public static final int gearLimmitswitchIsClosed = 8;
public static final double mecanumPositionConstant = 90;
public static final int gearTalonPWM = 4;
public static final double gearSpeed = 1;
int gearsMovePWM;
public void conts(){
	gearsMovePWM = 5;
}
}