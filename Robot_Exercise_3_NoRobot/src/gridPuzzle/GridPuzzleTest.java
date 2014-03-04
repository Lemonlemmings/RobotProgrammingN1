package gridPuzzle;

import java.util.ArrayList;
import java.util.LinkedList;

import rp13.search.interfaces.SuccessorFunction;
import rp13.search.util.EqualityGoalTest;
import search.Node;
import search.Search;
import search.Search.SearchType;
import Part3.Direction;

public class GridPuzzleTest
{
	public static void main(String[] args)
	{
		ArrayList<CoordinatePair> blocked = new ArrayList<CoordinatePair>();	
		blocked.add(new CoordinatePair(1, 0, 1, 1));
		blocked.add(new CoordinatePair(2, 0, 2, 1));
		blocked.add(new CoordinatePair(3, 0, 3, 1));
		blocked.add(new CoordinatePair(0, 1, 1, 1));
		blocked.add(new CoordinatePair(0, 2, 1, 2));
		blocked.add(new CoordinatePair(1, 2, 2, 2));
		blocked.add(new CoordinatePair(3, 1, 3, 2));
		blocked.add(new CoordinatePair(2, 2, 2, 3));
		blocked.add(new CoordinatePair(2, 3, 3, 3));
		blocked.add(new CoordinatePair(3, 1, 4, 1));
		blocked.add(new CoordinatePair(4, 1, 5, 1));
		blocked.add(new CoordinatePair(3, 2, 4, 2));
		blocked.add(new CoordinatePair(4, 2, 4, 3));
		blocked.add(new CoordinatePair(5, 1, 5, 2));
		blocked.add(new CoordinatePair(5, 2, 5, 3));
		
		/*
		 * This massive list of coordinate pairs defines the map
		 * 
		 * O O O|O O G
		 *     -   - -
		 * O|O|O O|O O
		 *       -   -
		 * O|O O O|O|O
		 *   - - -
		 * S O O O O O
		 * 
		 * The only solution is:
		 * 
		 * # # O|# # #
		 *     -   - -
		 * #|#|# #|O O
		 *       -   -
		 * #|# # O|O|O
		 *   - - -
		 * # O O O O O
		 * 
		 */
		
		GridPuzzle puzzle = new GridPuzzle(6, 4, blocked);
		
		GridNode start = puzzle.get(0, 0);
		GridNode goal = puzzle.get(5, 3);
		
		System.out.println("Initial state:");
		System.out.println(start);
		
		System.out.println("Goal state:");
		System.out.println(goal);
		
		SuccessorFunction<Direction, GridNode> successorFn =
				new GridPuzzleSuccessorFunction();
		
		Search<GridNode, Direction> search =
				new Search<GridNode, Direction>(successorFn, new EqualityGoalTest<GridNode>(goal));
		
		Node<GridNode, Direction> node = search.search(SearchType.BFS, start, 6*4);
		
		System.out.println("Solution found!\n");
		
		System.out.println("Depth: " + node.getDepth(0));
		System.out.println();
		
		LinkedList<Direction> actions = new LinkedList<Direction>();
		node.getActionArray(actions);
		
		System.out.println("Actions:");
		
		for (Direction i : actions) {
			System.out.println(i);
		}
	}
}
