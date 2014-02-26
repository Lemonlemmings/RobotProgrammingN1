package eightpuzzle;

import java.util.List;

import eightpuzzle.EightPuzzle.PuzzleMove;
import eightpuzzlepackage.Node;
import eightpuzzlepackage.Successor;

/**
 * An example eight-puzzle successor function.
 * 
 * @author Nick Hawes
 * 
 * @param <ActionT>
 * @param <StateT>
 */
public class EightPuzzleSuccessorFunction implements
		Successor<EightPuzzle, PuzzleMove> {

	/**
	 * 
	 * Get the possible successors of an eight-puzzle state. Only returns legal
	 * moves.
	 * 
	 */
	@Override
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

}
