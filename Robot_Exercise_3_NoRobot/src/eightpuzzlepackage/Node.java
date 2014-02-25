package eightpuzzlepackage;

public class Node<ACTION, STATE>
{
	private ACTION[] actions;
	private STATE state;
	
	@SuppressWarnings("unchecked")
	public Node(Node<ACTION, STATE> _parent, ACTION _action, STATE _state)
	{
		ACTION[] parentArray = _parent.getActions();
		
		this.actions = (ACTION[])(new Object[parentArray.length + 1]); 
	
		for (int i = 0; i < parentArray.length; i++) {
			actions[i] = parentArray[i];
		}
		
		actions[parentArray.length] = _action;
	
		this.state = _state;
	}

	public STATE getState()
	{
		return state;
	}
	
	public ACTION[] getActions()
	{
		return actions;
	}
}
