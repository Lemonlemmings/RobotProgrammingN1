package eightpuzzlepackage;

import java.util.LinkedList;
import java.util.Set;

import rp13.search.util.EqualityGoalTest;

public class Search<StateT, ActionT>
{
	private LinkedList<Node<StateT, ActionT>> frontier;
	private Set<StateT> explored;
	
	public Search(LinkedList<Node<StateT, ActionT>> frontier, Set<StateT> explored)
	{
		this.frontier = frontier;
		this.explored = explored;
	}
	
	public Node<StateT, ActionT> search(StateT initialState, StateT goalState, Successor<StateT, ActionT> successorFn)
	{
		//EqualityGoalTest<StateT> goal = new EqualityGoalTest<StateT>(goalState);
		
		Node<StateT, ActionT> initialNode = new Node<StateT, ActionT>(initialState);
		
		frontier.push(initialNode);
		
		int i = 0;
		
		while (!frontier.isEmpty()) {
			Node<StateT, ActionT> node = frontier.pop();
			
			// Node is at the goal
			if (node.getState().equals(goalState)) {
				return node;
			}
			
			// Otherwise generate successors
			
			explored.add(node.getState());
			
			successorFn.getSuccessors(node, frontier, explored);
			

			//EightPuzzleSuccessorFunction successorFn = new EightPuzzleSuccessorFunction();
			
			//successorFn.getSuccessors(node, frontier);
			
			System.out.println("Iteration: " + ++i + " Explored:" + explored.size() + " Fronter:" + frontier.size());
			
			
			
		}
		return null;
	}
}