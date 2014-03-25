package stringPuzzle;

import java.util.LinkedList;

import search.EqualityGoalTest;
import search.Node;
import search.Search;
import search.Search.SearchType;

public class StringPuzzleTest
{
	public static void main(String[] args)
	{
		// Create the two states
		String goal = "javasco";
		String start = "avscoja";

		System.out.println("Initial state:");
		System.out.println(start);

		System.out.println("Goal state:");
		System.out.println(goal);

		Search<String, StringMove> search = new Search<String, StringMove>(new StringPuzzleSuccessorFunction(),
																		   new EqualityGoalTest<String>(goal));

		Node<String, StringMove> node = search.search(SearchType.BFS, start, 20);
        //Node<String, StringMove> node = search.search(new StringPuzzleHeuristic(), start);
		
		
		System.out.println("Solution found!\n");

		System.out.println("Depth: " + node.getDepth(0));
		System.out.println();

		LinkedList<StringMove> actions = new LinkedList<StringMove>();
		node.getActionArray(actions);

		System.out.println("Actions:");

		for (StringMove i : actions)
		{
			System.out.println(i.toString());
		}
	}
}
