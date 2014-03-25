package eightPuzzle;

import java.util.ArrayList;
import java.util.List;

import search.ActionStatePair;
import search.SuccessorFunction;
import eightPuzzle.EightPuzzle.PuzzleMove;

/**
 * An example eight-puzzle successor function.
 * 
 * @author Nick Hawes
 * 
 * @param <ActionT>
 * @param <StateT>
 */
public class EightPuzzleSuccessorFunction implements SuccessorFunction<PuzzleMove, EightPuzzle>
{

	/**
	 * 
	 * Get the possible successors of an eight-puzzle state. Only returns legal
	 * moves.
	 * 
	 */
	public void getSuccessors(EightPuzzle _state, List<ActionStatePair<PuzzleMove, EightPuzzle>> _successors)
	{
		assert (_successors != null);

		// for each of the moves that are available
		for (PuzzleMove move : PuzzleMove.values())
		{

			// check if it is possible
			if (_state.isPossibleMove(move))
			{

				// create a copy of the input state as we don't want to change
				// it
				EightPuzzle successor = new EightPuzzle(_state);
				// apply the move
				successor.makeMove(move);
				// store the move and action together in a pair and add to
				// successor list
				_successors.add(new ActionStatePair<EightPuzzle.PuzzleMove, EightPuzzle>(move, successor));
			}

		}

	}

	public static void main(String[] args)
	{
		EightPuzzle state = EightPuzzle.orderedEightPuzzle();
		EightPuzzleSuccessorFunction successorFn = new EightPuzzleSuccessorFunction();

		// successors are added to the end of this list by the successor
		// function
		List<ActionStatePair<PuzzleMove, EightPuzzle>> successors = new ArrayList<ActionStatePair<PuzzleMove, EightPuzzle>>();
		;
		// get the successors of the given state
		successorFn.getSuccessors(state, successors);

		for (ActionStatePair<PuzzleMove, EightPuzzle> successor : successors)
		{

			System.out.println("Applied move " + successor.getAction()
					+ " and got:");

			System.out.println(successor.getState());

		}

	}
}
