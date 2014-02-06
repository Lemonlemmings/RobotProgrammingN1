package Diff_Patt_Pt1;

import lejos.nxt.Button;
import lejos.nxt.Motor;
import lejos.robotics.navigation.DifferentialPilot;

public class Diff_Patt 
{
	static Diff_Patt_Run mainPatterns;
	static DifferentialPilot pilot;
	
	public static void main(String[] args) 
	{
		//Waits for a buton press before starting the program
		Button.waitForAnyPress();
		//Creates a Differential pilot with the settings correctly setup
		pilot = new DifferentialPilot(250, 1270, Motor.A, Motor.B, false);
		//Feeds the pilot into a Runnable class
		mainPatterns = new Diff_Patt_Run(pilot);
		//Runs the main stages of the progrsm
		mainPatterns.run();
		//After the main stage is finished, it waits for another button press before finishing
		Button.waitForAnyPress();
	}

}
