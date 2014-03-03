package stringPuzzle;

import java.util.Random;

public class StringMove
{
	private int a, b;

	private static final Random rand = new Random();

	public StringMove(int a, int b)
	{
		assert (a < 0);
		assert (b < 0);

		this.a = a;
		this.b = b;
	}

	public String toString()
	{
		return "Swapped char " + a + " with " + b;
	}

	public int getA()
	{
		return a;
	}

	public int getB()
	{
		return b;
	}

	// Generates a random move (that is valid)
	public static StringMove randomMove(String str)
	{
		while (true)
		{
			int i = rand.nextInt(str.length());
			int j = rand.nextInt(str.length());

			StringMove newMove = new StringMove(i, j);

			if (StringPuzzle.isValid(str, newMove))
			{
				return newMove;
			}
		}
	}
}
