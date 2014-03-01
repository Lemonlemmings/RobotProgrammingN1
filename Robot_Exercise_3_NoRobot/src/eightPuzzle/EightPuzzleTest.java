package eightPuzzle;


import java.util.LinkedList;

import rp13.search.interfaces.SuccessorFunction;
import rp13.search.problem.puzzle.EightPuzzle;
import rp13.search.problem.puzzle.EightPuzzle.PuzzleMove;
import rp13.search.problem.puzzle.EightPuzzleSuccessorFunction;
import search.Node;
import search.UninformedSearch;
import search.UninformedSearchType;

public class EightPuzzleTest
{
	public static void main(String[] args)
	{
		// Create the two states
		EightPuzzle start = EightPuzzle.randomEightPuzzle();
		EightPuzzle goal = EightPuzzle.orderedEightPuzzle();
		
		System.out.println("Initial state:");
		System.out.println(start.toString());

		System.out.println("Goal state:");
		System.out.println(goal.toString());
		
		SuccessorFunction<PuzzleMove, EightPuzzle> successorFn =
				new EightPuzzleSuccessorFunction();
		
		UninformedSearchType searchType =
				UninformedSearchType.BFS;
		
		UninformedSearch<EightPuzzle, PuzzleMove> search =
				new UninformedSearch<EightPuzzle, PuzzleMove>(searchType, successorFn, start, goal);
		
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