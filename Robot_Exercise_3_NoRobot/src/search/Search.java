package search;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import rp13.search.interfaces.SuccessorFunction;
import rp13.search.util.ActionStatePair;
import rp13.search.util.EqualityGoalTest;

public class Search<StateT, ActionT>
{
	private List<ActionT> actionsP;
	private SuccessorFunction<ActionT, StateT> successorFn;
	private EqualityGoalTest<StateT> goaltest;

	public Search()
	{	
	}

	public List<ActionT> search(StateT initialState,
						 EqualityGoalTest<StateT> goaltest,
						 SuccessorFunction<ActionT, StateT> successorFn,
						 String _searchtype)
	{		
		this.actionsP    = new LinkedList<ActionT>(); 
		this.successorFn = successorFn;
		this.goaltest    = goaltest;
		String searcht   = new String();
		String maxDepth     = new String();
		searcht  = _searchtype.substring(0, 2);
		maxDepth = _searchtype.substring(2);
		
		if(maxDepth.isEmpty())
		{
			maxDepth = "300"; //Default max depth for the tree
		}				
		
		if(searcht.equals("df"))
		{
			System.out.println(nextDepth(1, new ActionStatePair<ActionT, StateT>(null, initialState), Integer.parseInt(maxDepth)).toString());
		}
		else if(searcht.equals("bf"))
		{
			//Put Breadth First search here
		}
		else if(searcht.equals("a*"))
		{
			//Put A* search here
		}
		
		return actionsP;
	}
	
	private StateT nextDepth(int d, ActionStatePair<ActionT, StateT> node, int maxDepth)
	{
		List<ActionStatePair<ActionT, StateT>> frontier = new LinkedList<ActionStatePair<ActionT, StateT>>();
		actionsP.add(node.getAction());
		
		if (goaltest.isGoal(node.getState()))
		{
			return node.getState();
		}
		else if(d <= maxDepth)
		{
			successorFn.getSuccessors(node.getState(), frontier);
		}
		else
		{
			actionsP.remove(actionsP.size() - 1);
			return node.getState();
		}
		
		while(!frontier.isEmpty())
		{
			ActionStatePair<ActionT, StateT> nextNode = frontier.remove((frontier.size() -1));
			if((frontier.size() > 1) && (actionsP.size() >= 3) && (nextNode.getAction().equals(actionsP.get(actionsP.size() - 2))))
			{
				Random gen = new Random();
				nextNode = frontier.remove(gen.nextInt(frontier.size()));
			}
			
			StateT potentialCompletion = nextDepth(d+1, nextNode, maxDepth);
			
			if(goaltest.isGoal(potentialCompletion))
			{
				return potentialCompletion;
			}
		}
		
		return null;		
	}
}