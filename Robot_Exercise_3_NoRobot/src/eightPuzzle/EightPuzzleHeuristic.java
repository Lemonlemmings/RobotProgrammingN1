package eightPuzzle;

import eightPuzzle.EightPuzzle.PuzzleMove;
import search.EqualityGoalTest;
import search.Heuristic;
import search.Node;

public class EightPuzzleHeuristic implements Heuristic<EightPuzzle, PuzzleMove>
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
	public int calculateCost(Node<EightPuzzle, PuzzleMove> node, EqualityGoalTest<EightPuzzle> goal)
	{
		int similarity = 0;
		int[] cBoard = node.getState().getBoard();
		int[] gBoard = goal.getGoal().getBoard();
		
		for(int i = 0; i < cBoard.length; i++)
		{
			if(cBoard[i] == gBoard[i])
			{
				similarity++;
			}
		}
		
		return (9 - similarity) + node.getDepth(0);
	}
}