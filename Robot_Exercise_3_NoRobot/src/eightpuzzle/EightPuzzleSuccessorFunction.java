package eightpuzzle;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;

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
	public void getSuccessors(Node<EightPuzzle, PuzzleMove> node,
			LinkedList<Node<EightPuzzle, PuzzleMove>> successors,
			Set<EightPuzzle> explored) {
		
		assert (successors != null);

		// for each of the moves that are available
		for (PuzzleMove move : PuzzleMove.values()) {

			// check if it is possible
			if (node.getState().isPossibleMove(move)) {

				// create a copy of the input state as we don't want to change
				// it
				EightPuzzle successor = new EightPuzzle(node.getState());
				// apply the move
				successor.makeMove(move);
				
				if (explored.contains(successor)) { // DOES THIS EVEN WORK OR USE .equals
					Node<EightPuzzle, PuzzleMove> newNode = new Node<EightPuzzle, PuzzleMove>(node, successor, move);
					successors.push(newNode);
				}
			}

		}
		
	}

}
