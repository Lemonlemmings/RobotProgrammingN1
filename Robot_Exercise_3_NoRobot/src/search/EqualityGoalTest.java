package search;


/**
 * A goal test which calls the equals methods of an object.
 * 
 * @author Nick Hawes
 * 
 * @param <StateT>
 */
public class EqualityGoalTest<StateT> implements GoalTest<StateT>
{

	private final StateT m_goal;

	public EqualityGoalTest(StateT goal)
	{
		super();
		m_goal = goal;
	}

	public boolean isGoal(StateT _state)
	{
		return m_goal.equals(_state);
	}

	public StateT getGoal()
	{
		return m_goal;
	}
}
