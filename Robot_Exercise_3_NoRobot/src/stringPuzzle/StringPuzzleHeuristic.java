package stringPuzzle;

import search.EqualityGoalTest;
import search.Heuristic;
import search.Node;
import stringPuzzle.StringMove;

public class StringPuzzleHeuristic implements Heuristic<String, StringMove>
{

	/*
	 * this method checks to see how many characters in the string are in the
	 * same place, and calculates the similarity using that. the lower the
	 * similarity the more similar they are (bit counterintuitive, but easier to
	 * calculate lowest cost moves)
	 * 
	 * @see search.Heuristic#calculateCost(rp13.search.util.ActionStatePair,
	 * rp13.search.util.EqualityGoalTest, int)
	 */
	public int calculateCost(Node<String, StringMove> node,	EqualityGoalTest<String> goal)
	{

		int differences = 0;

		for (int i = 0; i < node.getState().length(); i++)
		{

			if (!(node.getState().charAt(i) == goal.toString().charAt(i)))
			{

				differences++;
			}
		}

		return differences + node.getDepth(0);
	}
}
