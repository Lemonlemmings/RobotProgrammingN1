package gridPuzzle;

import java.util.ArrayList;
import java.util.LinkedList;

import search.EqualityGoalTest;
import search.Node;
import search.Search;
import search.Search.SearchType;
import search.SuccessorFunction;

public class GridPuzzleTest
{
	public static void main(String[] args)
	{
		ArrayList<CoordinatePair> blocked = new ArrayList<CoordinatePair>();
		/*blocked.add(new CoordinatePair(1, 0, 1, 1));
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
		blocked.add(new CoordinatePair(5, 2, 5, 3));*/
		blocked.add(new CoordinatePair(3, 0, 4, 0));
		blocked.add(new CoordinatePair(5, 0, 5, 1));
		blocked.add(new CoordinatePair(6, 1, 6, 2));
		blocked.add(new CoordinatePair(3, 2, 4, 2));
		blocked.add(new CoordinatePair(3, 2, 3, 3));
		blocked.add(new CoordinatePair(1, 1, 1, 2));
		blocked.add(new CoordinatePair(0, 3, 0, 4));
		blocked.add(new CoordinatePair(0, 5, 1, 5));
		blocked.add(new CoordinatePair(2, 4, 2, 5));
		blocked.add(new CoordinatePair(2, 6 ,2 ,7));
		blocked.add(new CoordinatePair(6, 5, 6, 6));
		blocked.add(new CoordinatePair(3, 5, 4, 5));
		blocked.add(new CoordinatePair(4, 4, 4, 5));
		blocked.add(new CoordinatePair(5, 4, 5, 5));
		

		/*
		 * This massive list of coordinate pairs defines the map
		 * 
		 * O O O|O O G - - - O|O|O O|O O - - O|O O O|O|O - - - S O O O O O
		 * 
		 * The only solution is:
		 * 
		 * # # O|# # # - - - #|#|# #|O O - - #|# # O|O|O - - - # O O O O O
		 */

		GridPuzzle puzzle = new GridPuzzle(7, 10, blocked);

		//GridNode start = puzzle.get(0, 0);
		//GridNode goal = puzzle.get(5, 3);
		GridNode start = puzzle.get(6, 2);
		GridNode goal  = puzzle.get(2, 2);

		System.out.println("Initial state:");
		System.out.println(start);

		System.out.println("Goal state:");
		System.out.println(goal);

		SuccessorFunction<Direction, GridNode> successorFn = new GridPuzzleSuccessorFunction();

		Search<GridNode, Direction> search = new Search<GridNode, Direction>(successorFn, new EqualityGoalTest<GridNode>(goal));

		//Node<GridNode, Direction> node = search.search(SearchType.BFS, start, 6 * 4);
		Node<GridNode, Direction> node = search.search(new GridPuzzleHeuristic(), start); 

		System.out.println("Solution found!\n");

		System.out.println("Depth: " + node.getDepth(0));
		System.out.println();

		LinkedList<Direction> actions = new LinkedList<Direction>();
		node.getActionArray(actions);

		System.out.println("Actions:");
		 
		for(Direction i : actions)
		{
			i = i.toRobot(i);
			System.out.println(i);
		}
	}
}
