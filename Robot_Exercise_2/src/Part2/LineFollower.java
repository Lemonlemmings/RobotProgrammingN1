package Part2;

import lejos.nxt.Button;

public class LineFollower 
{
	public static void main(String[] args) 
	{
        //Waits for a button press before running the bulk of the program
		Button.waitForAnyPress();
		
        //Creates a runnable instance of the "program" where the bulk of the code executes
		LineFollowerRun program = new LineFollowerRun();		
		program.run();
		
        //Waits for a button press before exiting the program
		Button.waitForAnyPress();
	}
}
