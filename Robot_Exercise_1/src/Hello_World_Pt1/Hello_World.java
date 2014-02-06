package Hello_World_Pt1;

import lejos.nxt.Button;

public class Hello_World 
{

	public static void main(String[] args) 
	{
		//This program waits for a button press and then prints the hello world statement out on the console
		//Afterwards it waits for a button press before terminating the program
		Button.waitForAnyPress();
		System.out.println("Hello World");
		Button.waitForAnyPress();
	}

}
