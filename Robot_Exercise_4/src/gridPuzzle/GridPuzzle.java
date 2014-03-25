package gridPuzzle;

import java.util.ArrayList;

public class GridPuzzle
{
	private GridNode[][] map;
	
	public GridPuzzle(int width, int height, ArrayList<CoordinatePair> blocked)
	{	
		map = new GridNode[width][height];
		
		// Initialise map
		for (int x = 0; x < map.length; x++)
		{
			for (int y = 0; y < map[x].length; y++)
			{
				GridNode node = new GridNode(x, y);
				map[x][y] = node;
			}
		}
		
		// Link adjacent nodes together
		for (int x = 0; x < map.length; x++)
		{
			for (int y = 0; y < map[x].length; y++)
			{
				GridNode curr = map[x][y];
				
				// Left
				if (x > 0)
				{
					CoordinatePair pair = new CoordinatePair(x, y, x-1, y);
					
					if (!blocked.contains(pair))
					{
						curr.addNode(Direction.LEFT, map[x-1][y]);
					}
				}
				
				// Right
				if (x < map.length - 1)
				{
					CoordinatePair pair = new CoordinatePair(x, y, x+1, y);
					
					if (!blocked.contains(pair))
					{
						curr.addNode(Direction.RIGHT, map[x+1][y]);
					}
				}
				
				// Up
				if (y > 0)
				{
					CoordinatePair pair = new CoordinatePair(x, y, x, y-1);
					
					if (!blocked.contains(pair))
					{
						curr.addNode(Direction.DOWN, map[x][y-1]);
					}
				}
				
				// Down
				if (y < map[x].length - 1)
				{
					CoordinatePair pair = new CoordinatePair(x, y, x, y+1);
					
					if (!blocked.contains(pair))
					{
						curr.addNode(Direction.UP, map[x][y+1]);
					}
				}
				
				/*CoordinatePair pair = new CoordinatePair(x, y, x-1, y);
				
				if (blocked.contains(pair))
				{
					continue;
				}*/
			}
		}

	}
	
	public GridNode get(int x, int y)
	{
		return map[x][y];
	}
}
