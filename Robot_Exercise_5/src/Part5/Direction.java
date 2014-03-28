package Part5;

public enum Direction 
{
    //An enumerator which has 4 different values
    //These correspond to numbers, as the toInt() method shows
	UP,
	DOWN,
	LEFT,
	RIGHT;
	
    /*
     * A simple toString() method which simply returns the beginning letter of the direction
     * @return String This is the letter of the direction which is returned
     */
	public String toString() 
	{		
		switch(this) 
		{
			case UP: return "U";
			case DOWN: return "D";
			case LEFT: return "L";
			case RIGHT: return "R";
			default: return "N";
		}
	}
	
    /*
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
}
