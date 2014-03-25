package search;

import org.testng.Assert;
import org.testng.annotations.Test;

import eightPuzzle.EightPuzzle;
import eightPuzzle.EightPuzzleHeuristic;
import eightPuzzle.EightPuzzleSuccessorFunction;
import eightPuzzle.EightPuzzle.PuzzleMove;
import search.Search.SearchType;
import stringPuzzle.StringMove;
import stringPuzzle.StringPuzzleHeuristic;
import stringPuzzle.StringPuzzleSuccessorFunction;

@Test
public class SearchTest
{	
	public void testDFS()
	{	
		String goal1 = "javasco";
		EightPuzzle goal = EightPuzzle.orderedEightPuzzle();
		
		Search<EightPuzzle, PuzzleMove> search = new Search<EightPuzzle, PuzzleMove>(new EightPuzzleSuccessorFunction(), new EqualityGoalTest<EightPuzzle>(goal));
		
		Search<String, StringMove> search2     = new Search<String, StringMove>( new StringPuzzleSuccessorFunction(), new EqualityGoalTest<String>(goal1));

		Node<EightPuzzle, PuzzleMove> eightPNode = search.search(SearchType.DFS, EightPuzzle.randomEightPuzzle(), 31); //According to wikipedia, the maximum amount of moves is 31 to find a solution
		Assert.assertEquals(eightPNode.getState(), EightPuzzle.orderedEightPuzzle());
		
		Node<String, StringMove> stringPNode = search2.search(SearchType.DFS, "avscoja", 20); //Should always complete it in half the amount of letters which are in the word.
		Assert.assertEquals(stringPNode.getState(), "javasco");
	}
	
	public void testBFS()
	{	
		String goal1 = "dictionary";
		EightPuzzle goal = EightPuzzle.orderedEightPuzzle();
		
		Search<EightPuzzle, PuzzleMove> search = new Search<EightPuzzle, PuzzleMove>(new EightPuzzleSuccessorFunction(), new EqualityGoalTest<EightPuzzle>(goal));
		
		Search<String, StringMove> search2     = new Search<String, StringMove>( new StringPuzzleSuccessorFunction(), new EqualityGoalTest<String>(goal1));

		Node<EightPuzzle, PuzzleMove> eightPNode = search.search(SearchType.BFS, EightPuzzle.randomEightPuzzle(), 31); //According to wikipedia, the maximum amount of moves is 31 to find a solution
		Assert.assertEquals(eightPNode.getState(), EightPuzzle.orderedEightPuzzle());
		
		Node<String, StringMove> stringPNode = search2.search(SearchType.BFS, "ndicatiory", 20); //Should always complete it in half the amount of letters which are in the word.
		Assert.assertEquals(stringPNode.getState(), "dictionary");
	}
	
	public void testAstar()
	{
		String goal1 = "javasco";
		EightPuzzle goal = EightPuzzle.orderedEightPuzzle();
		
		Search<EightPuzzle, PuzzleMove> search = new Search<EightPuzzle, PuzzleMove>(new EightPuzzleSuccessorFunction(), new EqualityGoalTest<EightPuzzle>(goal));
		
		Search<String, StringMove> search2     = new Search<String, StringMove>( new StringPuzzleSuccessorFunction(), new EqualityGoalTest<String>(goal1));

		Node<EightPuzzle, PuzzleMove> eightPNode = search.search(new EightPuzzleHeuristic(), EightPuzzle.randomEightPuzzle()); //According to wikipedia, the maximum amount of moves is 31 to find a solution
		Assert.assertEquals(eightPNode.getState(), EightPuzzle.orderedEightPuzzle());
		
		Node<String, StringMove> stringPNode = search2.search(new StringPuzzleHeuristic(), "avscoja"); //Should always complete it in half the amount of letters which are in the word.
		Assert.assertEquals(stringPNode.getState(), "javasco");		
	}	
}
