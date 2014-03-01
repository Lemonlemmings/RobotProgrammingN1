package stringPuzzle;

public class StringPuzzle
{
	public static boolean isValid(String str, StringMove action)
	{
		int a = action.getA();
		int b = action.getB();
		
		int length = str.length();
		
		if (a > length || b > length) {
			return false;
		}
		
		if (str.charAt(a) == str.charAt(b)) {
			return false;
		}
		
		return true;
	}
	
	public static String performMove(String str, StringMove action)
	{
		if (!isValid(str, action)) {
			return str;
		}
		
		int a = action.getA();
		int b = action.getB();
		
		// Convert to array
		char[] array = str.toCharArray();
		
		// Swap the 2 characters
		char tmp = array[a];
		array[a] = array[b];
		array[b] = tmp;
		
		return new String(array);
	}
}
