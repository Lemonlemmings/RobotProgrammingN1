package gridPuzzle;

import java.util.ArrayList;
import java.util.List;

import rp13.search.interfaces.SuccessorFunction;
import rp13.search.util.ActionStatePair;
import Part3.Direction;

public class GridPuzzleSuccessorFunction implements SuccessorFunction<Direction, GridNode>
{
	@Override
	public void getSuccessors(GridNode node,
			List<ActionStatePair<Direction, GridNode>> frontier)
	{
		ArrayList<ActionStatePair<Direction, GridNode>> successors = node.getNodes();
		
		for (ActionStatePair<Direction, GridNode> newNode : successors)
		{
			//CoordinatePair pair = new CoordinatePair(node.getX(), node.getY(), newNode.getX(), newNode.getY());
			
			frontier.add(newNode);
		}
	}
}
