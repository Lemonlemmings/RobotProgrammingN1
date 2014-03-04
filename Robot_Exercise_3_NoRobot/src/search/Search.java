package search;

import java.util.Deque;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import rp13.search.interfaces.SuccessorFunction;
import rp13.search.util.ActionStatePair;
import rp13.search.util.EqualityGoalTest;

public class Search<StateT, ActionT>
{
	public enum SearchType
	{
		BFS, // Breadth-first search
		DFS // Depth-first search
	}

	private Deque<Node<StateT, ActionT>> frontier;
	private List<ActionT> actionsP;
	LinkedList<ActionStatePair<ActionT, StateT>> closed = new LinkedList<ActionStatePair<ActionT, StateT>>();
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
	
	public StateT search(Heuristic heur, StateT initialState)
	{
		actionsP = new LinkedList<ActionT>();
		return nextAStar(0, new ActionStatePair<ActionT, StateT>(null, initialState), heur);
	}

	private Node<StateT, ActionT> uninformedSearch(SearchType searchType,
			StateT initialState, int maxDepth)
	{
		// Frontier list
		frontier = new LinkedList<Node<StateT, ActionT>>();
		frontier.add(new Node<StateT, ActionT>(initialState));

		// Explored set
		explored = new HashSet<StateT>();
		explored.add(initialState);

		// Search loop
		while (!frontier.isEmpty())
		{
			// Retrieve node from frontier
			Node<StateT, ActionT> node;

			if (searchType == SearchType.BFS)
			{
				node = frontier.pollFirst(); // Use frontier as a queue
			} else
			{ // DFS
				node = frontier.pollLast(); // Use frontier as a stack
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
						frontier.add(new Node<StateT, ActionT>(node, child.getState(), child.getAction()));
					}
				}
			}
		}

		// If there is no solution
		return null;
	}

	private StateT nextAStar(int d, ActionStatePair<ActionT, StateT> node,	Heuristic heur)
	{
		LinkedList<ActionStatePair<ActionT, StateT>> open = new LinkedList<ActionStatePair<ActionT, StateT>>();

		if(closed.contains(node))
		{
			System.out.println("Bqalls");
		}
		else
		{
			closed.add(node);
		}
		
		actionsP.add(node.getAction());

		if (goalTest.isGoal(node.getState()))
		{
			return node.getState();
		} 
		else
		{
			open.clear();
			successorFn.getSuccessors(node.getState(), open);
		}

		while (!open.isEmpty())
		{

			//closed.add(open.get(open.size() - 1));

			ActionStatePair<ActionT, StateT> nextNode = nextNode(d, open, closed, heur);

			System.out.println(nextNode.toString());

			StateT complete = nextAStar(d + 1, nextNode, heur);

			if (goalTest.isGoal(complete))
			{

				return complete;
			}
		}

		return null;
	}

	private ActionStatePair<ActionT, StateT> nextNode(int d,
			LinkedList<ActionStatePair<ActionT, StateT>> open,
			LinkedList<ActionStatePair<ActionT, StateT>> closed, Heuristic heur)
	{
		ActionStatePair<ActionT, StateT> node = open.get(0);
		int lowVal = 10;
		
		for (int i = 0; i < open.size(); i++)
		{		
			node = open.get(i);
			lowVal = Math.min(heur.calculateCost(node, goalTest, d), lowVal);
			if(closed.contains(node))
			{			
			}			
			else// (( < lowestcost)	&& (!(closed.contains(node))))
			{
				return node;
			}
		}

		return node;
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