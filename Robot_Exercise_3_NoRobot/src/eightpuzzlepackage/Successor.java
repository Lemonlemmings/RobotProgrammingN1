package eightpuzzlepackage;

import java.util.List;

public interface Successor<StateT, ActionT>  {
	
	public void getSuccessors(Node<StateT, ActionT> node,
			List<Node<StateT, ActionT>> _successors);
}


