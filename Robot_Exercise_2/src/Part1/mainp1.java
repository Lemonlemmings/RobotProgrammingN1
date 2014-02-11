package Part1;

import lejos.nxt.Button;

public class mainp1 
{
	public static void main(String[] args)
	{
        //Wait for a button press before the program will begin
		Button.waitForAnyPress();
		
        //Setting up the runnable object so that the main block the program can run
		wall WallRun = new wall();
		WallRun.run();
		
        //Wait for a button press to let the program exit
		Button.waitForAnyPress();		
	}
}
