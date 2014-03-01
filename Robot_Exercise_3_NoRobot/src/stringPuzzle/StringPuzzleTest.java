package stringPuzzle;

import java.util.LinkedList;

import rp13.search.interfaces.SuccessorFunction;
import search.Node;
import search.UninformedSearch;
import search.UninformedSearchType;

public class StringPuzzleTest
{
	public static void main(String[] args)
	{
		// Create the two states
		String start = "dictionary";
		String goal = "indicatory";
		
//		for (int i = 0; i < 1000; i++) {
//			start = StringPuzzle.performMove(start, StringMove.randomMove(start));
//		}
		
		System.out.println("Initial state:");
		System.out.println(start);

		System.out.println("Goal state:");
		System.out.println(goal);
		
		SuccessorFunction<StringMove, String> successorFn =
				new StringPuzzleSuccessorFunction();
		
		UninformedSearchType searchType =
				UninformedSearchType.BFS;
		
		UninformedSearch<String, StringMove> search =
				new UninformedSearch<String, StringMove>(searchType, successorFn, start, goal);
		
		Node<String, StringMove> node = search.search();
	
		System.out.println("Solution found!\n");
		
		System.out.println("Depth: " + node.getDepth());
		System.out.println();
	
		LinkedList<StringMove> actions = node.getActionArray();
		
		System.out.println("Actions:");
		
		for (StringMove i : actions) {
			System.out.println(i.toString());
		}
	}
}
