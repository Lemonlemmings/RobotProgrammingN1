package rp.robotics.localisation;

import Part5.CoordinatePair;
import rp.robotics.mapping.GridMap;
import rp.robotics.mapping.Heading;

/**
 * Example structure for an action model that should move the probabilities 1
 * cell in the requested direction. In the case where the move would take the
 * robot into an obstacle or off the map, this model assumes the robot stayed in
 * one place. This is the same as the model presented in Robot Programming
 * Lecture 14.
 * 
 * Note that this class doesn't actually do this, instead it shows you a
 * <b>possible</b> structure for your action model.
 * 
 * @author nah
 * 
 */
public class PerfectActionModel implements ActionModel {

	@Override
	public GridPositionDistribution updateAfterMove(
			GridPositionDistribution _from, Heading _heading) {

		// Move the probability in the correct direction for the action
		if (_heading == Heading.PLUS_X) {
			movePlusX(_from);
		} else if (_heading == Heading.PLUS_Y) {
			movePlusY(_from);
		} else if (_heading == Heading.MINUS_X) {
			moveMinusX(_from);
		} else if (_heading == Heading.MINUS_Y) {
			moveMinusY(_from);
		}
		
		return _from;
	}

	/**
	 * Move probabilities from _from one cell in the plus x direction into _to
	 * 
	 * @param _from
	 * @param _to
	 */
	private void movePlusX(GridPositionDistribution dist) {

		GridMap gridMap = dist.getGridMap();
		
		// iterate through points updating as appropriate
		for (int y = 0; y < dist.getGridHeight(); y++) {
			for (int x = dist.getGridWidth() - 2; x >= 0; x--) {
				int toX = x + 1;
				int toY = y;
				
				if (gridMap.isValidTransition(x, y, toX, toY)) {
					float fromProb = dist.getProbability(x, y);
					float toProb = dist.getProbability(toX, toY);
					
					dist.setProbability(toX, toY, fromProb + toProb);
					dist.setProbability(x, y, 0.0f);
				}

			}
		}
		
		//_to.normalise();
	}
	
	private void moveMinusX(GridPositionDistribution dist) {

		GridMap gridMap = dist.getGridMap();
		
		// iterate through points updating as appropriate
		for (int y = 0; y < dist.getGridHeight(); y++) {
			for (int x = 1; x < dist.getGridWidth(); x++) {
				int toX = x - 1;
				int toY = y;
				
				if (gridMap.isValidTransition(x, y, toX, toY)) {
					float fromProb = dist.getProbability(x, y);
					float toProb = dist.getProbability(toX, toY);
					
					dist.setProbability(toX, toY, fromProb + toProb);
					dist.setProbability(x, y, 0.0f);
				}

			}
		}
		
		//_to.normalise();
	}
	
	private void movePlusY(GridPositionDistribution dist) {
		
		GridMap gridMap = dist.getGridMap();
		
		for (int y = dist.getGridHeight() - 2; y >= 0; y--) {
			for (int x = 0; x < dist.getGridWidth(); x++) {
				
				int toX = x;
				int toY = y + 1;
				
				if (gridMap.isValidTransition(x, y, toX, toY)) {
					float fromProb = dist.getProbability(x, y);
					float toProb = dist.getProbability(toX, toY);
					
					dist.setProbability(toX, toY, fromProb + toProb);
					dist.setProbability(x, y, 0.0f);
				}
				
				

			}
		}
	}
	
	
	private void moveMinusY(GridPositionDistribution dist) {

		GridMap gridMap = dist.getGridMap();
		
		// iterate through points updating as appropriate
		for (int x = 0; x < dist.getGridWidth(); x++) {
			for (int y = 1; y < dist.getGridHeight(); y++) {
				int toX = x;
				int toY = y - 1;
				
				if (gridMap.isValidTransition(x, y, toX, toY)) {
					float fromProb = dist.getProbability(x, y);
					float toProb = dist.getProbability(toX, toY);
					
					dist.setProbability(toX, toY, fromProb + toProb);
					dist.setProbability(x, y, 0.0f);
				}

			}
		}
		
		//_to.normalise();
	}
	
	
	// Culling!
	public void cull(GridPositionDistribution dist, Heading heading)
	{
		if (heading == Heading.PLUS_X) {
			//cullPlusX(dist);
			cullGeneric(dist, 1, 0);
		} else if (heading == Heading.PLUS_Y) {
			//cullPlusY(dist);
			cullGeneric(dist, 0, 1);
		} else if (heading == Heading.MINUS_X) {
			//cullMinusX(dist);
			cullGeneric(dist, -1, 0);
		} else if (heading == Heading.MINUS_Y) {
			//cullMinusY(dist);
			cullGeneric(dist, 0, -1);
		}
	}
	
	private void cullGeneric(GridPositionDistribution dist, int dX, int dY)
	{
		GridMap gridMap = dist.getGridMap();
		
		// iterate through points updating as appropriate
		for (int y = 0; y < dist.getGridHeight(); y++) {
			for (int x = 0; x < dist.getGridWidth(); x++) {
				
				int toX = x + dX;
				int toY = y + dY;
				
				if (gridMap.isValidTransition(x, y, toX, toY)) 
				{
						dist.setProbability(x, y, 0.0f);
				}
			}
		}
		
		dist.normalise();
	}
	
	/*private void cullPlusX(GridPositionDistribution dist)
	{
		GridMap gridMap = dist.getGridMap();
		
		// iterate through points updating as appropriate
		for (int y = 0; y < dist.getGridHeight(); y++) {
			for (int x = 0; x < dist.getGridWidth(); x++) {
				
				int toX = x + 1;
				int toY = y;
				
				if (gridMap.isValidTransition(x, y, toX, toY)) 
				{
						dist.setProbability(x, y, 0.0f);
				}
			}
		}
		
		dist.normalise();
	}
	
	private void cullMinusX(GridPositionDistribution dist)
	{
		GridMap gridMap = dist.getGridMap();
		
		// iterate through points updating as appropriate
		for (int y = 0; y < dist.getGridHeight(); y++) {
			for (int x = 0; x < dist.getGridWidth(); x++) {
				
				int toX = x - 1;
				int toY = y;
				
				if (gridMap.isValidTransition(x, y, toX, toY))
				{
					dist.setProbability(x, y, 0.0f);
				}

			}
		}
		
		dist.normalise();
	}

	private void cullPlusY(GridPositionDistribution dist)
	{
		GridMap gridMap = dist.getGridMap();
		
		// iterate through points updating as appropriate
		for (int y = 0; y < dist.getGridHeight(); y++) {
			for (int x = 0; x < dist.getGridWidth(); x++) {
				
				int toX = x;
				int toY = y + 1;
				
				if (gridMap.isValidTransition(x, y, toX, toY))
				{
					dist.setProbability(x, y, 0.0f);
				}

			}
		}
		
		dist.normalise();
	}
	
	private void cullMinusY(GridPositionDistribution dist)
	{
		GridMap gridMap = dist.getGridMap();
		
		// iterate through points updating as appropriate
		for (int y = 0; y < dist.getGridHeight(); y++) {
			for (int x = 0; x < dist.getGridWidth(); x++) {
				
				int toX = x;
				int toY = y - 1;
				
				if (gridMap.isValidTransition(x, y, toX, toY))
				{
					dist.setProbability(x, y, 0.0f);
				}

			}
		}
		
		dist.normalise();
	}*/
	
	// Reverse culling!!!
	
	public void rull(GridPositionDistribution dist, Heading heading)
	{
		if (heading == Heading.PLUS_X) {
			//rullPlusX(dist);
			rullGeneric(dist, 1, 0);
		} else if (heading == Heading.PLUS_Y) {
			//rullPlusY(dist);
			rullGeneric(dist, 0, 1);
		} else if (heading == Heading.MINUS_X) {
			//rullMinusX(dist);
			rullGeneric(dist, -1, 0);
		} else if (heading == Heading.MINUS_Y) {
			//rullMinusY(dist);
			rullGeneric(dist, 0, -1);
		}
	}
	
	
	
	private void rullGeneric(GridPositionDistribution dist, int dX, int dY)
	{
		GridMap gridMap = dist.getGridMap();
		
		// iterate through points updating as appropriate
		for (int y = 0; y < dist.getGridHeight(); y++) {
			for (int x = 0; x < dist.getGridWidth(); x++) {
				
				int toX = x + dX;
				int toY = y + dY;
				
				if (!gridMap.isValidTransition(x, y, toX, toY))
				{
					dist.setProbability(x, y, 0.0f);
				}
			}
		}
		
		dist.normalise();
	}
	
	/*CoordinatePair[] precullPlusX;
	
	
	//
	// Pre culling
	//
	
	private void precullPlusX(GridPositionDistribution dist)
	{
		GridMap gridMap = dist.getGridMap();
		
		// iterate through points updating as appropriate
		for (int y = 0; y < dist.getGridHeight(); y++) {
			for (int x = 0; x < dist.getGridWidth(); x++) {
				
				int toX = x + 1;
				int toY = y;
				
				if (gridMap.isValidTransition(x, y, toX, toY)) 
				{
					dist.setProbability(x, y, 0.0f);
					
					//precullPlusX
				}
			}
		}
		
		dist.normalise();
	}*/
}
