package search;

import java.util.ArrayList;

import utils.Collections;
import utils.SimpleSet;
import utils.Deque;
import java.util.LinkedList;
import java.util.List;

public class Search<StateT, ActionT>
{
	public enum SearchType
	{
		BFS, // Breadth-first search
		DFS // Depth-first search
	}

	private Deque<Node<StateT, ActionT>> frontier;
	LinkedList<ActionStatePair<ActionT, StateT>> closed = new LinkedList<ActionStatePair<ActionT, StateT>>();
	private SimpleSet<StateT> explored;

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
	public Search(SuccessorFunction<ActionT, StateT> successorFn,
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
	
	public Node<StateT, ActionT> search(Heuristic<StateT, ActionT> heur, StateT initialState)
	{
		return informedSearch(initialState, heur);
	}

	private Node<StateT, ActionT> uninformedSearch(SearchType searchType,StateT initialState, int maxDepth)
	{
		// Frontier list
		frontier = new Deque<Node<StateT, ActionT>>();
		frontier.addFirst(new Node<StateT, ActionT>(initialState));

		// Explored set
		explored = new SimpleSet<StateT>();
		explored.add(initialState);

		// Search loop
		while (!frontier.isEmpty())
		{
			// Retrieve node from frontier
			Node<StateT, ActionT> node;

			if (searchType == SearchType.BFS)
			{
				node = frontier.removeFirst(); // Use frontier as a queue
			} else
			{ // DFS
				node = frontier.removeLast(); // Use frontier as a stack
			}

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
						frontier.addLast(new Node<StateT, ActionT>(node, child.getState(), child.getAction()));
					}
				}
			}
		}

		// If there is no solution
		return null;
	}
	
	private Node<StateT, ActionT> informedSearch(StateT initialState, Heuristic<StateT, ActionT> heur)
	{
		// Frontier list
		ArrayList<Node<StateT, ActionT>> frontierA = new ArrayList<Node<StateT, ActionT>>();
		frontierA.add(new Node<StateT, ActionT>(initialState));

		// Explored set
		explored = new SimpleSet<StateT>();
		explored.add(initialState);

		// Search loop
		while (!frontierA.isEmpty())
		{
			Collections.sort(frontierA);//Sorts thus is A*
			
			
			// Retrieve node from frontier
			Node<StateT, ActionT> node = frontierA.remove(0);

			// Are we at the goal?
			if (goalTest.isGoal(node.getState()))
			{
				return node;
			}

			// Otherwise generate successors
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
					frontierA.add(new Node<StateT, ActionT>(node, child.getState(), child.getAction(), heur, goalTest));
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