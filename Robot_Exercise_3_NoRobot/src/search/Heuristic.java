package search;

import rp13.search.util.ActionStatePair;
import rp13.search.util.EqualityGoalTest;

public interface Heuristic<ActionT, StateT>
{
	int calculateCost(ActionStatePair<ActionT, StateT> node,
					  EqualityGoalTest<StateT> goal,
					  int cost);
}