package eightPuzzle;

import rp13.search.problem.puzzle.EightPuzzle;
import rp13.search.problem.puzzle.EightPuzzle.PuzzleMove;
import rp13.search.util.ActionStatePair;
import rp13.search.util.EqualityGoalTest;
import search.Heuristic;

public class EightPuzzleHeuristic implements Heuristic<PuzzleMove, EightPuzzle>
{

	/*
	 * this method returns the similarity between the goal state and the current
	 * state, letting us check whether we have a higher cost or not similarity
	 * starts at 9 and is decreased for each node that is the same as the goal
	 * state
	 * 
	 * @see search.Heuristic#calculateCost(rp13.search.util.ActionStatePair,
	 * rp13.search.util.EqualityGoalTest, int)
	 */
	public int calculateCost(ActionStatePair<PuzzleMove, EightPuzzle> node,	EqualityGoalTest<EightPuzzle> goal, int cost)
	{
		int similarity = 9;

		for (int i = 0; i < goal.toString().length(); i++)
		{

			if (node.toString().charAt(i) == goal.toString().charAt(i))
			{
				similarity--;
			}
		}

		return similarity;
	}
}