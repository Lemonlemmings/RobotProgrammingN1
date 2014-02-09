package Part3;

import lejos.nxt.Button;

public class Grid
{
	public static void main(String[] args)
	{
		Button.waitForAnyPress();
		
		GridRun runG = new GridRun();
		runG.run();
		
		Button.waitForAnyPress();
	}
}
