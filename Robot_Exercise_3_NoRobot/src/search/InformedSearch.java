package search;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Deque;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import rp13.search.interfaces.SuccessorFunction;
import rp13.search.util.ActionStatePair;
import rp13.search.util.EqualityGoalTest;

public class InformedSearch<StateT, ActionT>
{
	public enum SearchType
	{
		ASTAR // A* search
	}

	private ArrayList<Node<StateT, ActionT>> frontier;
	
	private Set<StateT> explored;

	private SuccessorFunction<ActionT, StateT> successorFn;

	private EqualityGoalTest<StateT> goalTest;

	/**
	 * Constructs an instance of UninformedSearch
	 * 
	 * @param searchType
	 *            The type of search to perform (DFS or BFS)
	 * @param successorFn
	 *            The function which provides successor states
	 * @param initialState
	 *            The initial state that the search will start with
	 * @param goalState
	 *            The goal state that the search needs to reach
	 */
	public InformedSearch(SuccessorFunction<ActionT, StateT> successorFn,
			EqualityGoalTest<StateT> goalTest)
	{
		this.successorFn = successorFn;
		this.goalTest = goalTest;
	}

	/**
	 * Performs the search
	 * 
	 * @return The node with a path to its parents if a goal is found, otherwise
	 *         null if all possible states have been exhausted.
	 */
	public Node<StateT, ActionT> search(SearchType searchType, StateT initialState, int maxDepth)
	{
		return uninformedSearch(searchType, initialState, maxDepth);		
	}
	

	private Node<StateT, ActionT> uninformedSearch(SearchType searchType,
			StateT initialState, int maxDepth)
	{
		// Frontier list
		frontier = new ArrayList<Node<StateT, ActionT>>();
		frontier.add(new Node<StateT, ActionT>(initialState));

		// Explored set
		explored = new HashSet<StateT>();
		explored.add(initialState);

		// Search loop
		while (!frontier.isEmpty())
		{
			Collections.sort(frontier);
			
			
			// Retrieve node from frontier
			Node<StateT, ActionT> node = frontier.remove(0);

			// Are we at the goal?
			if (goalTest.isGoal(node.getState()))
			{
				return node;
			}

			// Otherwise generate successors
			if (node.getDepth(0) <= maxDepth)
			{
				List<ActionStatePair<ActionT, StateT>> expanded = new LinkedList<ActionStatePair<ActionT, StateT>>();
				successorFn.getSuccessors(node.getState(), expanded);

				// Add unexplored successors to frontier
				for (ActionStatePair<ActionT, StateT> child : expanded)
				{
					// Have we explored this state already?
					if (!explored.contains(child.getState()))
					{
						// If not, we have now
						explored.add(child.getState());

						// Also put the new node in the frontier
						frontier.add(new Node<StateT, ActionT>(node, child.getState(), child.getAction()));
					}
				}
			}
		}

		// If there is no solution
		return null;
	}
}

/*
 * package search;
 * 
 * import java.util.LinkedList; import java.util.List; import java.util.Random;
 * 
 * import rp13.search.interfaces.SuccessorFunction; import
 * rp13.search.util.ActionStatePair; import rp13.search.util.EqualityGoalTest;
 * 
 * public class Search<StateT, ActionT> { private List<ActionT> actionsP;
 * private SuccessorFunction<ActionT, StateT> successorFn; private
 * EqualityGoalTest<StateT> goaltest;
 * 
 * public Search() { }
 * 
 * public List<ActionT> search(StateT initialState, EqualityGoalTest<StateT>
 * goaltest, SuccessorFunction<ActionT, StateT> successorFn, String _searchtype)
 * { this.actionsP = new LinkedList<ActionT>(); this.successorFn = successorFn;
 * this.goaltest = goaltest; String searcht = new String(); String maxDepth =
 * new String(); searcht = _searchtype.substring(0, 2); maxDepth =
 * _searchtype.substring(2);
 * 
 * if(maxDepth.isEmpty()) { maxDepth = "300"; //Default max depth for the tree }
 * 
 * if(searcht.equals("df")) { System.out.println(nextDepth(1, new
 * ActionStatePair<ActionT, StateT>(null, initialState),
 * Integer.parseInt(maxDepth)).toString()); } else if(searcht.equals("bf")) {
 * //Put Breadth First search here } else if(searcht.equals("a*")) { //Put A*
 * search here }
 * 
 * return actionsP; } }
 */