package search;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import rp13.search.problem.puzzle.EightPuzzle;
import rp13.search.problem.puzzle.EightPuzzleSuccessorFunction;
import rp13.search.problem.puzzle.EightPuzzle.PuzzleMove;
import rp13.search.util.EqualityGoalTest;
import search.Search.SearchType;
import stringPuzzle.StringMove;
import stringPuzzle.StringPuzzleSuccessorFunction;

@Test
public class SearchTest
{
	Search<EightPuzzle, PuzzleMove> search;
	Search<String, StringMove>      search2;
	
	@BeforeMethod
	public void setUp()
	{
		/*EightPuzzle goal = EightPuzzle.orderedEightPuzzle();		
		Search<EightPuzzle, PuzzleMove> search = new Search<EightPuzzle, PuzzleMove>(new EightPuzzleSuccessorFunction(),
				 																	 new EqualityGoalTest<EightPuzzle>(goal));
		
		
		String goal1 = "dictionary";
		Search<String, StringMove> search2 = new Search<String, StringMove>( new StringPuzzleSuccessorFunction(),
				 new EqualityGoalTest<String>(goal1));*/
	}
	
	public void testDFS()
	{	
		String goal1 = "dictionary";
		EightPuzzle goal = EightPuzzle.orderedEightPuzzle();
		
		Search<EightPuzzle, PuzzleMove> search = new Search<EightPuzzle, PuzzleMove>(new EightPuzzleSuccessorFunction(), new EqualityGoalTest<EightPuzzle>(goal));
		
		Search<String, StringMove> search2     = new Search<String, StringMove>( new StringPuzzleSuccessorFunction(), new EqualityGoalTest<String>(goal1));

		Node<EightPuzzle, PuzzleMove> eightPNode = search.search(SearchType.DFS, EightPuzzle.randomEightPuzzle(), 31); //According to wikipedia, the maximum amount of moves is 31 to find a solution
		Assert.assertEquals(eightPNode.getState(), EightPuzzle.orderedEightPuzzle());
		
		Node<String, StringMove> stringPNode = search2.search(SearchType.DFS, "ndicatiory", 20); //Should always complete it in half the amount of letters which are in the word.
		Assert.assertEquals(stringPNode.getState(), "dictionary");
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
	
}
