package search;


public interface Heuristic<StateT, ActionT>
{
	int calculateCost(Node<StateT, ActionT> node,
					  EqualityGoalTest<StateT> equalityGoalTest);
}