package eightpuzzlepackage;

import rp13.search.interfaces.SuccessorFunction;

public class Node<ActionT, StateT extends SuccessorFunction<ActionT, StateT>>
{
	private ActionT[] actions;
	private StateT state;
	
	@SuppressWarnings("unchecked")
	public Node(Node<ActionT, StateT> _parent, ActionT _action, StateT _state)
	{
		ActionT[] parentArray = _parent.getActions();
		
		this.actions = (ActionT[])(new Object[parentArray.length + 1]); 

		for (int i = 0; i < parentArray.length; i++) {
			actions[i] = parentArray[i];
		}
		
		actions[parentArray.length] = _action;
	
		this.state = _state;
	}

	public StateT getState()
	{
		return state;
	}
	
	public ActionT[] getActions()
	{
		return actions;
	}
}