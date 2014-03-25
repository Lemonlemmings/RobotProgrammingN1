package search;

import java.util.LinkedList;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import eightPuzzle.EightPuzzle;
import eightPuzzle.EightPuzzle.PuzzleMove;

@Test
public class NodeTest
{
	private Node<EightPuzzle, PuzzleMove> test;
	private Node<EightPuzzle, PuzzleMove> test2;
	
	@BeforeMethod
	public void setUp()
	{
		test  = new Node<EightPuzzle, PuzzleMove>(EightPuzzle.orderedEightPuzzle());
		test2 = new Node<EightPuzzle, PuzzleMove>(test, EightPuzzle.orderedEightPuzzle(), PuzzleMove.DOWN);
	}
	
	public void testGetState()
	{
		Assert.assertEquals(test.getState(), EightPuzzle.orderedEightPuzzle());
	}
	
	public void testGetAction()
	{
		Assert.assertEquals(test2.getAction(), PuzzleMove.DOWN);
		
		Assert.assertEquals(test.getAction(), null);
	}
	
	public void testGetParent()
	{
		Assert.assertEquals(test2.getParent(), test);
	}
	
	public void testConstructor()
	{
		Node<EightPuzzle, PuzzleMove> testing = new Node<EightPuzzle, PuzzleMove>(EightPuzzle.orderedEightPuzzle());
		
		Assert.assertEquals(testing.getState(), EightPuzzle.orderedEightPuzzle());
		Assert.assertEquals(testing.getAction(), null);
		Assert.assertEquals(testing.getParent(), null);
		
		EightPuzzle puzzleT = EightPuzzle.randomEightPuzzle();
		Node<EightPuzzle, PuzzleMove> testing2 = new Node<EightPuzzle, PuzzleMove>(test, puzzleT, PuzzleMove.RIGHT);
		
		Assert.assertEquals(testing2.getParent(), test);
		Assert.assertEquals(testing2.getState(), puzzleT);
		Assert.assertEquals(testing2.getAction(), PuzzleMove.RIGHT);		
	}
	
	public void testGetDepth()
	{
		Assert.assertEquals(test.getDepth(0), 1);
		Assert.assertEquals(test2.getDepth(0), 2);
	}
	
	public void testGetActionArray()
	{
		LinkedList<PuzzleMove> actionArray = new LinkedList<PuzzleMove>();
		actionArray.add(PuzzleMove.DOWN);
		
		LinkedList<PuzzleMove> testArray = new LinkedList<PuzzleMove>();
		test2.getActionArray(testArray);
		
		Assert.assertEquals(testArray, actionArray);
	}


}
