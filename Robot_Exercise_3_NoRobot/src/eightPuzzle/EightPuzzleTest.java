package eightPuzzle;

import java.util.LinkedList;

import eightPuzzle.EightPuzzle.PuzzleMove;
import search.EqualityGoalTest;
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

		Node<EightPuzzle, PuzzleMove> node = search.search(SearchType.DFS, start, 31);
		//Node<EightPuzzle, PuzzleMove> node = search.search(new EightPuzzleHeuristic(), start);
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