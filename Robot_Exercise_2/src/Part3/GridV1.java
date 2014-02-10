package Part3;

import lejos.nxt.Button;

public class GridV1
{
	public static void main(String[] args)
	{
        //Wait for a button press at the beginning of the program
		Button.waitForAnyPress();
		
        //Create an objectr of instance "Runnable" to run the main bulk of
        //the robot program
		GridRun runG = new GridRun();
		runG.run();
		
        //Wait for a button press to finish the program
		Button.waitForAnyPress();
	}
}
