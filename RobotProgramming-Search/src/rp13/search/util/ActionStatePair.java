/**
 * 
 */
package rp13.search.util;

/**
 * A class to store an action and the state it has produced together.
 * 
 * @author nah
 * 
 */
public class ActionStatePair<ActionT, StateT>
{

	private final ActionT m_action;
	private final StateT m_state;

	public ActionStatePair(ActionT _action, StateT _state)
	{
		m_action = _action;
		m_state = _state;
	}

	/**
	 * Get action.
	 * 
	 * @return
	 */
	public ActionT getAction()
	{
		return m_action;
	}

	/**
	 * Get state.
	 * 
	 * @return
	 */
	public StateT getState()
	{
		return m_state;
	}

	public boolean equals(ActionStatePair<ActionT, StateT> e)
	{
		return ((e.getState().equals(m_state)) && (e.getAction().equals(m_action)));
	}
	
	@Override
	public String toString()
	{
		StringBuilder sb = new StringBuilder();
		sb.append(m_state);
		if (m_action != null)
		{
			sb.append("\n -> \n");
			sb.append(m_action);
		}

		return sb.toString();
	}
}
