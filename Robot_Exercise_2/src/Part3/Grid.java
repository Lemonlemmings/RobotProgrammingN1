package Part3;

import lejos.nxt.Button;

public class Grid
{
	public static void main(String[] args)
	{
		Button.waitForAnyPress();
		
		GridRunnable runG = new GridRunnable();
		runG.run();
		
		Button.waitForAnyPress();
	}
}
