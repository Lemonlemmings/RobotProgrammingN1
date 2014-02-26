package eightpuzzlepackage;

public class Node<StateT, ActionT>
{
	private StateT state;
	private ActionT[] actions;
	
	@SuppressWarnings("unchecked")
	public Node(StateT _state)
	{
		this.actions = (ActionT[]) (new Object[0]);
		this.state = _state;
	}
	
	@SuppressWarnings("unchecked")
	public Node(Node<StateT, ActionT> _parent, StateT _state, ActionT _action)
	{
		this.state = _state;
		
		ActionT[] parentArray = _parent.getActions();
		
		this.actions = (ActionT[])(new Object[parentArray.length + 1]); 

		for (int i = 0; i < parentArray.length; i++) {
			actions[i] = parentArray[i];
		}
		
		actions[parentArray.length] = _action;
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