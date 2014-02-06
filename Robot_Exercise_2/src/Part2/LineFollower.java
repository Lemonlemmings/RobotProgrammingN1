package Part2;

import lejos.nxt.Button;

public class LineFollower 
{
	public static void main(String[] args) 
	{
		Button.waitForAnyPress();
		
		LineFollowerRun program = new LineFollowerRun();		
		program.run();
		
		Button.waitForAnyPress();
	}
}
