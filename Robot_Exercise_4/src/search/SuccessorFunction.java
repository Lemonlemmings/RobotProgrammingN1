/**
 * 
 */
package search;

import java.util.List;

/**
 * 
 * Defines an interface that can be used by a search algorithm to get the
 * successor of a given state.
 * 
 * @author Nick Hawes
 * 
 */
public interface SuccessorFunction<ActionT, StateT>
{

	/**
	 * Adds each successor of the given state to the end of the _successors
	 * list, along with the action that generated it. The _successors list is
	 * not cleared by this method.
	 * 
	 * @param node
	 * @param frontier
	 */
	public void getSuccessors(StateT node, List<ActionStatePair<ActionT, StateT>> frontier);

}
