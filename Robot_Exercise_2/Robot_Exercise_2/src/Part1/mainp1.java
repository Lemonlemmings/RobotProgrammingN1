package Part1;

import lejos.nxt.Button;

public class mainp1 
{
	public static void main(String[] args)
	{
		Button.waitForAnyPress();
		
		wall WallRun = new wall();
		WallRun.run();
		
		Button.waitForAnyPress();		
	}
}
