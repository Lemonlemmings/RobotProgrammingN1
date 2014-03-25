package search;

import java.util.LinkedList;

import utils.Deque;

public class Node<StateT, ActionT> implements Comparable<Node<StateT, ActionT>>
{
	private Node<StateT, ActionT> parent;

	private StateT state;
	private ActionT action;

	// DELETE THIS HACKERY!!!!
	private Heuristic<StateT, ActionT> h;
	private EqualityGoalTest<StateT> goal;
	
	
	/**
	 * This creates an initial state node. Otherwise it would require the
	 * previous node and an action which was required to get here. This
	 * constructor will only be called when it's creating an "inital state"
	 * 
	 * @param _state
	 *            This is the initial state.
	 */
	public Node(StateT state)
	{
		this.state = state;
		this.action = null;
		this.parent = null;
		this.h = null;
		this.goal = null;
	}

	public Node(Node<StateT, ActionT> parent, StateT state, ActionT action)
	{
		this.parent = parent;
		this.state = state;
		this.action = action;
		this.h = null;
		this.goal = null;
	}
	
	public Node(Node<StateT, ActionT> parent, StateT state, ActionT action, Heuristic<StateT, ActionT> h, EqualityGoalTest<StateT> goal)
	{
		this.parent = parent;
		this.state = state;
		this.action = action;
		this.h = h;
		this.goal = goal;
	}

	public Node<StateT, ActionT> getParent()
	{
		return parent;
	}

	public int getDepth(int depth)
	{
		if (parent == null)
		{
			return depth + 1;
		} 
		else
		{
			return parent.getDepth(depth + 1);
		}
	}

	public StateT getState()
	{
		return state;
	}

	public ActionT getAction()
	{
		return action;
	}

	public void getActionArray(Deque<ActionT> actionList)
	{
		if (!(parent == null))
		{
			actionList.addFirst(action); // Push action to front
			parent.getActionArray(actionList);
		}
	}

	public int getNodeValue()
	{
		return h.calculateCost(this, goal);
	}

	public int compareTo(Node<StateT, ActionT> that) 
	{
		return this.getNodeValue() - that.getNodeValue();
	}
	
	/*public boolean equals(Object _that)
	{
		if(_that instanceof Node)
		{
			Node<?, ?> that = (Node<?, ?>) _that;
			return (this.getState().equals(that.getState())) && (this.getAction().equals(that.getAction())) && (this.getParent().equals(that.getParent()));
		}
		else
		{
			return false;
		}
	}*/
}