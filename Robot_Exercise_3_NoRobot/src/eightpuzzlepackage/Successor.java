package eightpuzzlepackage;

import java.util.LinkedList;
import java.util.Set;

public interface Successor<StateT, ActionT>
{

	public void getSuccessors(Node<StateT, ActionT> node,
							  LinkedList<Node<StateT, ActionT>> successors,
							  Set<StateT> explored);
}