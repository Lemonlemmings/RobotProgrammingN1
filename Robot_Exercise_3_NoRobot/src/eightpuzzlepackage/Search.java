package eightpuzzlepackage;

import rp13.search.interfaces.Agenda;
import rp13.search.interfaces.SuccessorFunction;

public class Search<ActionT, StateT extends SuccessorFunction<ActionT, StateT>> {
	Agenda<Node<ActionT, StateT>> frontier;
	Agenda<StateT> explored;
	
	public Search(Agenda<Node<ActionT, StateT>> frontier, Agenda<StateT> explored) {
		this.frontier = frontier;
		this.explored = explored;
	}
}
