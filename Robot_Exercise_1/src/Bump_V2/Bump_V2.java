package Bump_V2;

import lejos.nxt.Button;

public class Bump_V2 
{
	public static void main(String[] args)
	{
		//Waits for a button press to start
		Button.waitForAnyPress();
		//Creates the Ruunable class so we can run Run()
		Bump_V2_Run mainBump = new Bump_V2_Run();
		mainBump.run();
		//Waits for a button press to finish the program		
		Button.waitForAnyPress();
	}

}
