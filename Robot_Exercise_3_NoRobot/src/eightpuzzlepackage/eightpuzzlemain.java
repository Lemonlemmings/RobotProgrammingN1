package eightpuzzlepackage;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import eightpuzzle.EightPuzzle.PuzzleMove;
import eightpuzzle.EightPuzzle;
import eightpuzzle.EightPuzzleSuccessorFunction;
import rp13.search.util.EqualityGoalTest;

public class eightpuzzlemain
{

	public static void main(String[] args)
	{
		EightPuzzle puzzle = EightPuzzle.randomEightPuzzle(2);
		
		System.out.println(puzzle.equals(EightPuzzle.orderedEightPuzzle()));
		
		
		//EqualityGoalTest<EightPuzzle> goal = new EqualityGoalTest<EightPuzzle>(EightPuzzle.orderedEightPuzzle());
		
		LinkedList<Node<EightPuzzle, PuzzleMove>> frontier = new LinkedList<Node<EightPuzzle, PuzzleMove>>();
		Set<EightPuzzle> explored = new HashSet<EightPuzzle>();
		
		Successor<EightPuzzle, PuzzleMove> successorFn = new EightPuzzleSuccessorFunction();
		
		Search<EightPuzzle, PuzzleMove> search = new Search<EightPuzzle, PuzzleMove>(frontier, explored);
		
		Node<EightPuzzle, PuzzleMove> solution = search.search(puzzle, EightPuzzle.orderedEightPuzzle(), successorFn);
		
		System.out.println(solution.toString() + "\n\n");
		System.out.println(EightPuzzle.orderedEightPuzzle().toString());
		
		
		/*EqualityGoalTest<EightPuzzle> _goal = new EqualityGoalTest<EightPuzzle>(EightPuzzle.orderedEightPuzzle());
		
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
		System.out.println(EightPuzzle.orderedEightPuzzle().toString());*/
	}
}
