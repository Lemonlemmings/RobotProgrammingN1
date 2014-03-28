package Part5;

/**
 * A container for a pair of coordinates
 */
public class CoordinatePair
{
	public int x1, y1, x2, y2;
	
	public CoordinatePair(int x1, int y1, int x2, int y2)
	{
		this.x1 = x1;
		this.y1 = y1;
		
		this.x2 = x2;
		this.y2 = y2;
	}
	
	@Override
	public boolean equals(Object obj)
	{
		if (!(obj instanceof CoordinatePair))
		{
			return false;
		}
		
		CoordinatePair that = (CoordinatePair) obj;
		
		if (this.x1 == that.x1 &&
		    this.x2 == that.x2 &&
		    this.y1 == that.y1 &&
		    this.y2 == that.y2)
		{
			return true;
		}
		
		if (this.x1 == that.x2 &&
			this.x2 == that.x1 &&
		    this.y1 == that.y2 &&
		    this.y2 == that.y1)
		{
				return true;
		}
		
		return false;
	}
}
