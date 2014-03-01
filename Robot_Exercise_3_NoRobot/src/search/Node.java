package search;

import java.util.LinkedList;

public class Node<StateT, ActionT>
{
	private Node<StateT, ActionT> parent;
	private int depth;
	
	private StateT state;
	private ActionT action;

	/**
	 * This creates an initial state node. Otherwise it would require the previous node and an action which was required to get here.
	 * This constructor will only be called when it's creating an "inital state" 
	 * @param _state This is the initial state.
	 */
	public Node(StateT state)
	{
		this.parent = null;
		this.depth = 0;
		
		this.state = state;
		this.action = null;
	}

	public Node(Node<StateT, ActionT> parent, StateT state, ActionT action)
	{
		this.parent = parent;
		this.depth = parent.getDepth() + 1;
		
		this.state = state;
		this.action = action;
	}
	
	public Node<StateT, ActionT> getParent()
	{
		return parent;
	}
	
	public int getDepth()
	{
		return depth;
	}

	public StateT getState()
	{
		return state;
	}

	public ActionT getAction()
	{
		return action;
	}
	
	public LinkedList<ActionT> getActionArray()
	{
		/*@SuppressWarnings("unchecked")
		ActionT[] actions = (ActionT[]) new Object[this.getDepth()];
		
		Node<StateT, ActionT> curr = this;
		
		while (curr != null && curr.getParent() != null) {
			actions[curr.getDepth() - 1] = curr.getAction();
			
			curr = curr.getParent();
		}
			
		return actions;*/
		
		LinkedList<ActionT> actions = new LinkedList<ActionT>();
		
		
		Node<StateT, ActionT> curr = this;
		
		while (curr != null && curr.getParent() != null) {
			actions.addFirst(curr.getAction());
			
			curr = curr.getParent();
		}
		
		return actions;
	}
}