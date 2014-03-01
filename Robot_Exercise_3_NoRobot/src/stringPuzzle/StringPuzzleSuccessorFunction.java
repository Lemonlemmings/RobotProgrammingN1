package stringPuzzle;

import java.util.List;

import rp13.search.interfaces.SuccessorFunction;
import rp13.search.util.ActionStatePair;

public class StringPuzzleSuccessorFunction implements SuccessorFunction<StringMove, String>
{

	@Override
	public void getSuccessors(String str,
			List<ActionStatePair<StringMove, String>> successors)
	{
		assert (successors != null);
		
		// Create all possible moves
		for (int i = 0; i < str.length(); i++) {
			for (int j = i + 1; j < str.length(); j++) {
				// Generate new move
				StringMove action = new StringMove(i, j);
				
				if (StringPuzzle.isValid(str, action)) {
					// Add new successor to list
					String child = StringPuzzle.performMove(str, action);
	
					successors.add(new ActionStatePair<StringMove, String>(action, child));
				}
			}
		}
		
	}

}
