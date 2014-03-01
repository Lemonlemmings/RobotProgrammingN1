package eightpuzzlepackage;


import java.util.LinkedList;

import rp13.search.interfaces.SuccessorFunction;
import rp13.search.problem.puzzle.EightPuzzle;
import rp13.search.problem.puzzle.EightPuzzle.PuzzleMove;
import rp13.search.problem.puzzle.EightPuzzleSuccessorFunction;

public class eightpuzzlemain
{
	public static void main(String[] args)
	{
		// Create the two states
		EightPuzzle puzzle = EightPuzzle.randomEightPuzzle();
		EightPuzzle goal = EightPuzzle.orderedEightPuzzle();
		
		System.out.println("Initial state:");
		System.out.println(puzzle.toString());

		System.out.println("Goal state:");
		System.out.println(goal.toString());
		
		SuccessorFunction<PuzzleMove, EightPuzzle> successorFn =
				new EightPuzzleSuccessorFunction();
		
		UninformedSearchType searchType =
				UninformedSearchType.BFS;
		
		UninformedSearch<EightPuzzle, PuzzleMove> search =
				new UninformedSearch<EightPuzzle, PuzzleMove>(searchType, successorFn, puzzle, goal);
		
		Node<EightPuzzle, PuzzleMove> node = search.search();
	
		System.out.println("Solution found!\n");
		
		System.out.println("Depth: " + node.getDepth());
		System.out.println();
	
		LinkedList<PuzzleMove> actions = node.getActionArray();
		
		System.out.println("Actions:");
		
		for (PuzzleMove i : actions) {
			System.out.println(i.toString());
		}
	}
}