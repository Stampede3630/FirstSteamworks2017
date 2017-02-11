package org.usfirst.frc.team3630.robot;

public class HomebrewMecanum extends Robot {

	public double[] mecanumDrive (float velocityX, float velocityY, float angularVelocityDeg ){
		//This function takes the velocity in x and y and theta and converts them to 4 wheel speeds, from the top left counterclockwise.
		double angularVelocityRad = angularVelocityDeg * Math.PI / 180; //Converts degrees to radians for you, liam
		double[] wheelspeedResult;
		wheelspeedResult = new double[4];
		//For more information about this formula, see the mecanum kinematics 
		wheelspeedResult[0] = velocityX - velocityY -ContsClass.mecanumPositionConstant * angularVelocityRad;	
		wheelspeedResult[1] = velocityX + velocityY - ContsClass.mecanumPositionConstant * angularVelocityRad;
		wheelspeedResult[2] = velocityX - velocityY + ContsClass.mecanumPositionConstant * angularVelocityRad;
		wheelspeedResult[3] = velocityX + velocityY + ContsClass.mecanumPositionConstant * angularVelocityRad;
		
		return wheelspeedResult;
	}
	//public void driveImplementation (float v1, float v2, float v3, float v4){}
	
	
	
}