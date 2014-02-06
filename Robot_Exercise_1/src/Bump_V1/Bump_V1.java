package Bump_V1;

import lejos.nxt.Button;


public class Bump_V1 
{	
	public static void main(String[] args) 
	{
		//Waits for a button press to begin the program
		Button.waitForAnyPress();
		//Sets up the class for the Run() function
		Bump_V1_Run mainBump = new Bump_V1_Run();
		//Starts the run function
		mainBump.run();
		//Waits for a button press to end the program
		Button.waitForAnyPress();
	}
}
