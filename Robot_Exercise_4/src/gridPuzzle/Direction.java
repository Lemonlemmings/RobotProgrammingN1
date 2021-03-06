package gridPuzzle;

//Commented by Andrew Walker
/**
 * @author Andrew Walker
 * @author Jamie Delo
 */

public enum Direction 
{
    //An enumerator which has 4 different values
    //These correspond to numbers, as the toInt() method shows
	UP,
	DOWN,
	LEFT,
	RIGHT;
	
	public static Direction currDir = UP;
	
    /**
     * A simple toString() method which simply returns the beginning letter of the direction
     * @return String This is the letter of the direction which is returned
     */
	public String toString() 
	{		
		switch(this) 
		{
			case UP: return "path.add(Direction.UP);";
			case DOWN: return "path.add(Direction.DOWN);";
			case LEFT: return "path.add(Direction.LEFT);";
			case RIGHT: return "path.add(Direction.RIGHT);";
			default: return "N";
		}
	}
	
    /**
     * A toInt() method which converts the direction to an equivilent number
     * for the switch statement on the otherside.
     * @return int This is number which is being returned. 0 for up, 1 for backwards,
     *             2 for left and 3 for right and a default of 4 if it's anything else.
     */
	public int toInt()
	{
		switch(this)
		{
		case UP: return 0;
		case DOWN: return 1;
		case LEFT: return 2;
		case RIGHT: return 3;
		default: return 4;		
		}
	}
	
	public Direction toRobot(Direction globDir)
	{
		switch(currDir)
		{
			case UP: currDir = globDir;
					 break;
			case DOWN:
			{
				switch(globDir)
				{
					case DOWN: globDir = UP;
							   break;
					case UP: globDir = DOWN;
							 currDir = UP;
						       break;
					case RIGHT: globDir = LEFT;
								currDir = RIGHT;
					           break;
					case LEFT: globDir = RIGHT;
							   currDir = LEFT;
					           break;
				}
				break;
			}
			case RIGHT:
			{
				switch(globDir)
				{
					case DOWN: globDir = RIGHT;
							   currDir = DOWN;
							   break;
					case UP: globDir = LEFT;
					         currDir= UP;
						       break;
					case RIGHT: globDir = UP;
					           break;
					case LEFT: globDir = DOWN;
					           currDir = LEFT;
					           break;
				}
				break;
			}
			case LEFT:
			{
				switch(globDir)
				{
					case DOWN: globDir = LEFT;
							   currDir = DOWN;
							   break;
					case UP: globDir = RIGHT;
					         currDir = UP;
						       break;
					case RIGHT: globDir = DOWN;
								currDir = RIGHT;
					           break;
					case LEFT: globDir = UP;
					           break;
				}
				break;
			}
			
		}
		return globDir;
	}
}
