package Part3;

public enum Direction 
{

	UP,
	DOWN,
	LEFT,
	RIGHT;
	
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
