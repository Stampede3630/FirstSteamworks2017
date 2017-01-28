package org.usfirst.frc.team3630.robot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.JoystickButton;

public class Xbox360 extends Robot{
	
	Joystick mecXbox = new Joystick(1);
	Button buttonA = new JoystickButton(mecXbox, 1);
	Button buttonB = new JoystickButton(mecXbox, 2);
	Button buttonX = new JoystickButton(mecXbox, 3);
	Button buttonY = new JoystickButton(mecXbox, 4);
	Button leftStick = new JoystickButton(mecXbox, 5);
	Button rightStick = new JoystickButton(mecXbox, 6);
	Button dPadUp = new JoystickButton(mecXbox, 7);
	Button dPadDown = new JoystickButton(mecXbox, 8);
	Button dPadLeft = new JoystickButton(mecXbox, 9);
	Button dPadRight = new JoystickButton(mecXbox, 10);
	Button buttonRightFront = new JoystickButton(mecXbox, 11);
	Button buttonLeftFront = new JoystickButton(mecXbox, 12);
	Button rightTrigger = new JoystickButton(mecXbox, 13);
	Button leftTrigger = new JoystickButton(mecXbox, 14);
	
	
}
