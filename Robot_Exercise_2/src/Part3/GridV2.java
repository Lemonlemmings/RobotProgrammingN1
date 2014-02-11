package Part3;

import lejos.nxt.Button;

public class GridV2
{
	public static void main(String[] args)
	{
        //Wait for a button press to start the program
		Button.waitForAnyPress();
		
        //Create an object with instance run() which executes
        //the main bulk of the program
		GridRunnable runG = new GridRunnable();
		runG.run();
		
        //Wait for a button press to finish the program
		Button.waitForAnyPress();
	}
}
