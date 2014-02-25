package eightpuzzlepackage;

import rp13.search.problem.puzzle.EightPuzzle;
import rp13.search.util.EqualityGoalTest;

public class eightpuzzlemain
{

	public static void main(String[] args)
	{
		EqualityGoalTest<EightPuzzle> _goal = new EqualityGoalTest<EightPuzzle>(EightPuzzle.orderedEightPuzzle());
		
		EightPuzzle puzzle = new EightPuzzle(EightPuzzle.randomEightPuzzle());
		EightPuzzle tempPuzzle = new EightPuzzle(puzzle);
		
		int i = 0;
		
		
		
		while(!(_goal.isGoal(puzzle)))
		{
			i++;
			
			if(i > 31)
			{
				puzzle = tempPuzzle;
			}
			
			puzzle.randomMove();
			
		}

		System.out.println(i);
		
		if(_goal.isGoal(puzzle))
		{
			System.out.println("Done!");
		}
		
		System.out.println(puzzle.toString() + "\n\n");
		System.out.println(EightPuzzle.orderedEightPuzzle().toString());
	}
}
