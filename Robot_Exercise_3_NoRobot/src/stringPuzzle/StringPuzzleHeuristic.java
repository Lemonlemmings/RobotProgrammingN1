package stringPuzzle;

import rp13.search.util.ActionStatePair;
import rp13.search.util.EqualityGoalTest;
import search.Heuristic;
import stringPuzzle.StringMove;

public class StringPuzzleHeuristic implements Heuristic<StringMove, String>
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
	public int calculateCost(ActionStatePair<StringMove, String> node,	EqualityGoalTest<String> goal, int cost)
	{

		int similarity = goal.toString().length() - 1;

		for (int i = 0; i < node.getState().length(); i++)
		{

			if (node.getState().charAt(i) == goal.toString().charAt(i))
			{

				similarity--;
			}
		}

		return similarity;
	}
}
