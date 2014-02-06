package Diff_Patt_Pt1;

import lejos.nxt.Button;
import lejos.nxt.ButtonListener;
import lejos.robotics.navigation.DifferentialPilot;

public class Diff_Patt_Run implements Runnable 
{
	private DifferentialPilot pilot;
	private boolean           button_prsd;
	
	/* This constructor sets up the pilot in this class, and sets the boolean
	 * "Button pressed" to be false.
	 */
	public Diff_Patt_Run(DifferentialPilot _pilot)
	{
		pilot       = _pilot;
		button_prsd = false;
	}
	
	public void run() 
	{
		Button.ENTER.addButtonListener(new ButtonListener()
		{
			/* This anonymous class simply checks for button presses on the enter button
			 * and updates a boolean flag for it.
			 */
			public void buttonPressed(Button b)
			{
				button_prsd = true;
			}

			public void buttonReleased(Button b) 
			{
				//Just leaving it implemented but blank
			}
		});
		
		//Beginning of patterns made and the main statement of "Run()"
		pattern1();
		pattern2();
	}
	
	/*
	 * This function is programmed to make the pattern of a square
	 * on the floor. It resets the boolean flag.
	 */
	public void pattern1()
	{
		while(button_prsd == false)
		{
			pilot.travel(2000);
			pilot.rotate(90);
		}
		button_prsd = false;
	}
	
	/*
	 * This function is programmed to make the pattern of a triangle
	 * on the floor. It resets the boolean flag.
	 */
	public void pattern2()
	{
		while(button_prsd == false)
		{
			pilot.travel(3000);
			pilot.rotate(120);			
		}
		button_prsd = false;
	}
}
