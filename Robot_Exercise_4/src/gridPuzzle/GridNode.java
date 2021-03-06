package gridPuzzle;

import java.util.ArrayList;

import search.ActionStatePair;

public class GridNode
{
	private ArrayList<ActionStatePair<Direction, GridNode>> linkedNodes;
	
	private int x, y;
	
	public GridNode(int x, int y)
	{
		this.x = x;
		this.y = y;
		
		linkedNodes = new ArrayList<ActionStatePair<Direction, GridNode>>(4);
	}
	
	public String toString()
	{
		return "(" + x + ", " + y + ")";
	}
	
	public void addNode(Direction dir, GridNode node)
	{
		linkedNodes.add(new ActionStatePair<Direction, GridNode>(dir, node));
	}
	
	public ArrayList<ActionStatePair<Direction, GridNode>> getNodes()
	{
		return linkedNodes;
	}
	
	public int getX()
	{
		return x;
	}
	
	public int getY()
	{
		return y;
	}
}
