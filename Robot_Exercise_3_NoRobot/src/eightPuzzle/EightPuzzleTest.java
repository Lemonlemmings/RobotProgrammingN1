package eightPuzzle;

import java.util.LinkedList;

import rp13.search.problem.puzzle.EightPuzzle;
import rp13.search.problem.puzzle.EightPuzzle.PuzzleMove;
import rp13.search.problem.puzzle.EightPuzzleSuccessorFunction;
import rp13.search.util.EqualityGoalTest;
import search.Node;
import search.Search;
import search.Search.SearchType;

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

		Search<EightPuzzle, PuzzleMove> search = new Search<EightPuzzle, PuzzleMove>(
												 new EightPuzzleSuccessorFunction(),
												 new EqualityGoalTest<EightPuzzle>(goal));

		Node<EightPuzzle, PuzzleMove> node = search.search(SearchType.BFS, start, 31);
		System.out.println("Solution found!\n");

		System.out.println("Depth: " + node.getDepth(0));
		System.out.println();

		LinkedList<PuzzleMove> actions = new LinkedList<PuzzleMove>();
		node.getActionArray(actions);

		System.out.println("Actions:");

		for (PuzzleMove i : actions)
		{
			System.out.println(i.toString());
		}
	}
}