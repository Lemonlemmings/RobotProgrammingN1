package gridPuzzle;

import java.util.ArrayList;
import java.util.List;

import search.ActionStatePair;
import search.SuccessorFunction;

public class GridPuzzleSuccessorFunction implements SuccessorFunction<Direction, GridNode>
{
	public void getSuccessors(GridNode node, List<ActionStatePair<Direction, GridNode>> frontier)
	{
		ArrayList<ActionStatePair<Direction, GridNode>> successors = node.getNodes();
		
		for (ActionStatePair<Direction, GridNode> newNode : successors)
		{
			frontier.add(newNode);
		}
	}
}
