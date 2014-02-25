package eightpuzzlepackage;

public class Node<ACTION, STATE>
{
	private ACTION[] _actions;
	private STATE _state;
	
	@SuppressWarnings
	public Node(Node<ACTION, STATE> _parent, STATE _state)
	{
		super();
		_actions = new (ACTION)(Object [_parent.actionSize() + 1]); 
		this._actions = _parent.getActions();
		this._state = _state;
	}
	
	private int actionSize()
	{
		return _actions.length;
	}

	public STATE getState()
	{
		return _state;
	}
	
	public Node<ACTION, STATE> getActions()
	{
		return _actions;
	}
}