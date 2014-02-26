package rp13.search.problem.puzzle;

import java.util.ArrayList;
import java.util.List;

import eightpuzzle.EightPuzzle;
import eightpuzzle.EightPuzzle.PuzzleMove;
import eightpuzzlepackage.Node;
import rp13.search.interfaces.SuccessorFunction;
import rp13.search.util.ActionStatePair;

/**
 * An example eight-puzzle successor function.
 * 
 * @author Nick Hawes
 * 
 * @param <ActionT>
 * @param <StateT>
 */
public class EightPuzzleSuccessorFunction implements
		Successor<PuzzleMove, EightPuzzle> {

	/**
	 * 
	 * Get the possible successors of an eight-puzzle state. Only returns legal
	 * moves.
	 * 
	 */
	/*@Override
	public void getSuccessors(EightPuzzle _state,
			List<ActionStatePair<PuzzleMove, EightPuzzle>> _successors) {

		assert (_successors != null);

		// for each of the moves that are available
		for (PuzzleMove move : PuzzleMove.values()) {

			// check if it is possible
			if (_state.isPossibleMove(move)) {

				// create a copy of the input state as we don't want to change
				// it
				EightPuzzle successor = new EightPuzzle(_state);
				// apply the move
				successor.makeMove(move);
				// store the move and action together in a pair and add to
				// successor list
				_successors
						.add(new ActionStatePair<EightPuzzle.PuzzleMove, EightPuzzle>(
								move, successor));
			}

		}

	}*/
	
	public void getSuccessors(Node<EightPuzzle, PuzzleMove> _node,
			List<Node<EightPuzzle, PuzzleMove>> _successors) {

		assert (_successors != null);

		// for each of the moves that are available
		for (PuzzleMove move : PuzzleMove.values()) {

			// check if it is possible
			if (_node.getState().isPossibleMove(move)) {

				// create a copy of the input state as we don't want to change
				// it
				EightPuzzle successor = new EightPuzzle(_node.getState());
				// apply the move
				successor.makeMove(move);
				
				Node<EightPuzzle, PuzzleMove> newNode= new Node<EightPuzzle, PuzzleMove>(_node, successor, move);
				
				
				// store the move and action together in a pair and add to
				// successor list
				_successors.add(newNode);
			}

		}

	}

	public static void main(String[] args) {
		EightPuzzle state = EightPuzzle.orderedEightPuzzle();
		EightPuzzleSuccessorFunction successorFn = new EightPuzzleSuccessorFunction();

		// successors are added to the end of this list by the successor
		// function
		List<ActionStatePair<PuzzleMove, EightPuzzle>> successors = new ArrayList<ActionStatePair<PuzzleMove, EightPuzzle>>();
		;
		// get the successors of the given state
		successorFn.getSuccessors(state, successors);

		for (ActionStatePair<PuzzleMove, EightPuzzle> successor : successors) {

			System.out.println("Applied move " + successor.getAction()
					+ " and got:");

			System.out.println(successor.getState());

		}

	}

}
