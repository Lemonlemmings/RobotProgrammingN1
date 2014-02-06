package Maze_Solve;

import lejos.nxt.Button;

public class Maze_Solve 
{
	public static void main(String[] args) 
	{
		Button.waitForAnyPress();
			
		Maze_solve_Run main = new Maze_solve_Run();
		main.run();

		Button.waitForAnyPress();
	}
	

}
