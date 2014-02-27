package eightpuzzlepackage;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import rp13.search.interfaces.SuccessorFunction;
import rp13.search.util.ActionStatePair;
import rp13.search.util.EqualityGoalTest;

public class Search<StateT, ActionT>
{
	private List<ActionStatePair<ActionT, StateT>> frontier;
	private SuccessorFunction<ActionT, StateT> successorFn;
	private EqualityGoalTest<StateT> goaltest;
	private Set<StateT> explored;

	public Search()
	{
		
	}

	public StateT search(StateT initialState,
						 EqualityGoalTest<StateT> goaltest,
						 SuccessorFunction<ActionT, StateT> successorFn)
	{
		ActionStatePair<ActionT, StateT> initialNode = new ActionStatePair<ActionT, StateT>(initialState);
		
		this.frontier    = new LinkedList<ActionStatePair<ActionT, StateT>>();
		this.explored    = new HashSet<StateT>();
		this.successorFn = successorFn;
		this.goaltest    = goaltest;
		
		frontier.add(initialNode);

		int depth = 0;
		
		return nextDepth(depth++, new ActionStatePair<ActionT, StateT>(null, initialState)); 
	}
	
	private StateT nextDepth(int d, ActionStatePair<ActionT, StateT> node)
	{
		List<ActionStatePair<ActionT, StateT>> frontier = new LinkedList<ActionStatePair<ActionT, StateT>>();
		
		if (goaltest.isGoal(node.getState()))
		{
			return node.getState();
		}
		else if(d <= 31)
		{
			successorFn.getSuccessors(node.getState(), frontier);
		}
		else
		{
			return node.getState();
		}
		
		while(!frontier.isEmpty())
		{
			StateT potentialCompletion = nextDepth(d+1, frontier.remove(frontier.size() - 1));
			
			if(goaltest.isGoal(potentialCompletion))
			{
				return potentialCompletion;
			}
		}
		
		return null;		
	}
}