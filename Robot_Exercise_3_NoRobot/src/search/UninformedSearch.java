package search;

import java.util.Deque;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import rp13.search.interfaces.SuccessorFunction;
import rp13.search.util.ActionStatePair;

public class UninformedSearch<StateT, ActionT>
{
	private UninformedSearchType searchType;
	
	private Deque<Node<StateT, ActionT>> frontier;
	private Set<StateT> explored;
	
	private SuccessorFunction<ActionT, StateT> successorFn;
	
	private StateT initialState;
	private StateT goalState;
	
	/** Constructs an instance of UninformedSearch
	 * 
	 * @param searchType The type of search to perform (DFS or BFS)
	 * @param successorFn The function which provides successor states
	 * @param initialState The initial state that the search will start with
	 * @param goalState The goal state that the search needs to reach
	 */
	public UninformedSearch(UninformedSearchType searchType, SuccessorFunction<ActionT, StateT> successorFn, StateT initialState, StateT goalState)
	{
		this.searchType = searchType;
		
		this.successorFn = successorFn;
		
		this.initialState = initialState;
		this.goalState = goalState;
	}
	
	/**
	 * Performs the search
	 * 
	 * @return The node with a path to its parents if a
	 *         goal is found, otherwise null if all
	 *         possible states have been exhausted.
	 */
	public Node<StateT, ActionT> search()
	{
		// Frontier list 
		frontier = new LinkedList<Node<StateT, ActionT>>();
		frontier.add(new Node<StateT, ActionT>(initialState));
		
		// Explored set
		explored = new HashSet<StateT>();
		explored.add(initialState);
		
		// Search loop
		while (!frontier.isEmpty()) {
			// Retrieve node from frontier
			Node<StateT, ActionT> node;
			
			if (searchType == UninformedSearchType.BFS) {
				node = frontier.pollFirst(); // Use frontier as a queue
			} else { // DFS
				node = frontier.pollLast(); // Use frontier as a stack
			}
			
			// Are we at the goal?
			if (goalState.equals(node.getState())) {
				return node;
			}
			
			// Otherwise generate successors
			List<ActionStatePair<ActionT, StateT>> expanded = new LinkedList<ActionStatePair<ActionT, StateT>>();
			successorFn.getSuccessors(node.getState(), expanded);
			
			// Add unexplored successors to frontier
			for (ActionStatePair<ActionT, StateT> child : expanded) {
				
				// Have we explored this state already?
				if (!explored.contains(child.getState())) {
					// If not, we have now
					explored.add(child.getState());
					
					// Also put the new node in the frontier
					frontier.add(new Node<StateT, ActionT>(node, child.getState(), child.getAction()));
				}
			}
		}
		
		// If there is no solution
		return null;
	}
}