package eightpuzzlepackage;

import java.util.List;

import rp13.search.interfaces.Agenda;
import rp13.search.interfaces.SuccessorFunction;
import rp13.search.problem.puzzle.EightPuzzleSuccessorFunction;

public class Search<ActionT, StateT>
{
	private List<Node<ActionT, StateT>> frontier;
	private Agenda<StateT> explored;
	
	
	
	public Search(List<Node<ActionT, StateT>> frontier, Agenda<StateT> explored)
	{
		this.frontier = frontier;
		this.explored = explored;
	}
	
	public Node<ActionT, StateT> search()
	{
		Node<ActionT, StateT> node;
		Node<ActionT, StateT> goal;
		
		while (!frontier.isEmpty()) {
			node = frontier.get(2);//CHANGE THIS!!!
			
			if (node.equals(goal)) {
				return node;
			}
			
			

			//EightPuzzleSuccessorFunction successorFn = new EightPuzzleSuccessorFunction();
			
			//successorFn.getSuccessors(node, frontier);
			
		}
		return null;
	}
}