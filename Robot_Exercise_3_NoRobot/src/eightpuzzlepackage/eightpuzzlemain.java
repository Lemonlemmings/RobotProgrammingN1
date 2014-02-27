package eightpuzzlepackage;


import rp13.search.interfaces.SuccessorFunction;
import rp13.search.problem.puzzle.EightPuzzle;
import rp13.search.problem.puzzle.EightPuzzle.PuzzleMove;
import rp13.search.problem.puzzle.EightPuzzleSuccessorFunction;
import rp13.search.util.EqualityGoalTest;

public class eightpuzzlemain
{
	public static void main(String[] args)
	{
		EightPuzzle puzzle = EightPuzzle.randomEightPuzzle(2);
		
		System.out.println(puzzle.toString());		
		System.out.println(puzzle.equals(EightPuzzle.orderedEightPuzzle())+"\n");
		
		SuccessorFunction<PuzzleMove, EightPuzzle> successorFn = new EightPuzzleSuccessorFunction();
		
		Search<EightPuzzle, PuzzleMove> search = new Search<EightPuzzle, PuzzleMove>();
		
		System.out.println(search.search(puzzle, new EqualityGoalTest<EightPuzzle>(EightPuzzle.orderedEightPuzzle()), successorFn, "df31").toString());		
	}
}

/*
EqualityGoalTest<EightPuzzle> goal = new EqualityGoalTest<EightPuzzle>(EightPuzzle.orderedEightPuzzle());

LinkedList<Node<EightPuzzle, PuzzleMove>> frontier = new LinkedList<Node<EightPuzzle, PuzzleMove>>();
Set<EightPuzzle> explored = new HashSet<EightPuzzle>();

SuccessorFunction<PuzzleMove,EightPuzzle> successorFn = new EightPuzzleSuccessorFunction();

Search<EightPuzzle, PuzzleMove> search = new Search<EightPuzzle, PuzzleMove>(frontier, explored);

Node<EightPuzzle, PuzzleMove> solution = search.search(puzzle, EightPuzzle.orderedEightPuzzle(), successorFn);

System.out.println(solution.toString() + "\n\n");
System.out.println(EightPuzzle.orderedEightPuzzle().toString());
*/





/*
 * Our solution without the search system
EqualityGoalTest<EightPuzzle> _goal = new EqualityGoalTest<EightPuzzle>(EightPuzzle.orderedEightPuzzle());

EightPuzzle puzzle = new EightPuzzle(EightPuzzle.randomEightPuzzle());
EightPuzzle tempPuzzle = new EightPuzzle(puzzle);

int i = 0;



while(!(_goal.isGoal(puzzle)))
{
	i++;
	
	if(i > 31)
	{
		puzzle = tempPuzzle;
	}
	
	puzzle.randomMove();
	
}

System.out.println(i);

if(_goal.isGoal(puzzle))
{
	System.out.println("Done!");
}

System.out.println(puzzle.toString() + "\n\n");
System.out.println(EightPuzzle.orderedEightPuzzle().toString());*/
