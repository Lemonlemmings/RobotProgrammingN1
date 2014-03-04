package gridPuzzle;

import rp13.search.util.ActionStatePair;
import rp13.search.util.EqualityGoalTest;
import search.Heuristic;
import Part3.Direction;

public class GridPuzzleHeuristic implements Heuristic<Direction, GridNode>
{
	@Override
	public int calculateCost(ActionStatePair<Direction, GridNode> node,
			EqualityGoalTest<GridNode> goal, int cost)
	{
		
		int x1 = node.getState().getX();
		int y1 = node.getState().getY();
		
		int x2 = goal.getGoal().getX();
		int y2 = goal.getGoal().getY();
		
		// Get the Manhattan distance from the node to the goal
		int manhattan = Math.abs(x1 - x2) + Math.abs(y1 - y2);
		
		return cost + manhattan;
	}

}