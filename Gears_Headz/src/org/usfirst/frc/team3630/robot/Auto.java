package org.usfirst.frc.team3630.robot;

public class Auto {
	private static int autoStage = 0;
	public static void runAuto () {
		switch (autoStage) {
			case 0:
				encoderApproach();
				break;
			
			case 1: 
				rotateToAngle();
				break;
			
			case 2:
				visionAdjust();
				break;
				
			case 3:
				finalApproach();
				break;
				
			case 4:
				gearRelease();
				break;
			
			case 5:
				springExit();
				break;
		}
	}
	private static void encoderApproach () {
		//will run via encoders to get to the front fo the gear

	}
	
	private static void rotateToAngle () {
		//will run via navX to get in sight of gear.
		
	}
	private static void visionAdjust () {
		//vision adjustment
		
	}
	private static void finalApproach () {
		//final encoder approach
		
	}
	private static void gearRelease () {
		
		
	}
	private static void springExit () {
		
	}
	
	
	
	public void resetAuto() {
		autoStage = 0;
	}
	
}
