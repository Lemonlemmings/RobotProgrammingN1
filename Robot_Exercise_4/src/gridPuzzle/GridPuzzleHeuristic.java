package gridPuzzle;

import search.EqualityGoalTest;
import search.Heuristic;
import search.Node;

public class GridPuzzleHeuristic implements Heuristic<GridNode, Direction>
{
	public int calculateCost(Node<GridNode, Direction> node,	EqualityGoalTest<GridNode> goal)
	{
		
		int x1 = node.getState().getX();
		int y1 = node.getState().getY();
		
		int x2 = goal.getGoal().getX();
		int y2 = goal.getGoal().getY();
		
		// Get the Manhattan distance from the node to the goal
		int manhattan = Math.abs(x1 - x2) + Math.abs(y1 - y2);
		
		return manhattan + node.getDepth(0);
	}

}