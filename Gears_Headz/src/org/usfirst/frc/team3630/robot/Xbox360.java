package org.usfirst.frc.team3630.robot;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.JoystickButton;

public class Xbox360 extends XboxController{
	


	public static Button buttonA, buttonB, buttonX, buttonY, leftStick, rightStick, dPadUp, dPadDown, dPadLeft, dPadRight, rightFront, leftFront, rightTrigger, leftTrigger;
	
	
	public Xbox360 (int comPort){
	super (comPort);
	buttonA = new JoystickButton(this, 1);
	buttonB = new JoystickButton(this, 2);
	buttonX = new JoystickButton(this, 3);
	buttonY = new JoystickButton(this, 4);
	leftStick = new JoystickButton(this, 5);
	rightStick = new JoystickButton(this, 6);
	dPadUp = new JoystickButton(this, 7);
	dPadDown = new JoystickButton(this, 8);
	dPadLeft = new JoystickButton(this, 9);
	dPadRight = new JoystickButton(this, 10);
	rightFront = new JoystickButton(this, 11);
	leftFront = new JoystickButton(this, 12);
	rightTrigger = new JoystickButton(this, 13);
	leftTrigger = new JoystickButton(this, 14);
	}
}
