package Bump_V1;

import lejos.nxt.Button;
import lejos.nxt.ButtonListener;
import lejos.nxt.Motor;
import lejos.nxt.SensorPort;
import lejos.nxt.TouchSensor;
import lejos.robotics.navigation.DifferentialPilot;

public class Bump_V1_Run implements Runnable 
{
	private boolean run_bool = true;
	
	TouchSensor ts_right;
	TouchSensor ts_left;
	
	DifferentialPilot pilot;
	
	//A constructor setting up basic variables for the main runtime
	public Bump_V1_Run()
	{
		ts_right = new TouchSensor(SensorPort.S1);
		ts_left  = new TouchSensor(SensorPort.S2);
		pilot    = new DifferentialPilot(250, 1270, Motor.A, Motor.B, false);
	}
	
	public void run() 
	{
		//Sets up a button listener so the program can end
		Button.ENTER.addButtonListener(new ButtonListener()
		{
			public void buttonPressed(Button b)
			{
				run_bool = false;
			}

			public void buttonReleased(Button b) 
			{
				//Just leaving it implemented but blank
			}
		});
		
		//Sets up the right button to increase the speed for testing
		Button.RIGHT.addButtonListener(new ButtonListener()
		{
			public void buttonPressed(Button b) 
			{
				if(pilot.getTravelSpeed() + 100 <= pilot.getMaxTravelSpeed())
				{
					pilot.setTravelSpeed(pilot.getTravelSpeed() + 100);
				}
			}

			public void buttonReleased(Button b)
			{
				//Leave it implemented
			}
		});
		
		//Sets up the left button to decrease the speed for testing
		Button.LEFT.addButtonListener(new ButtonListener()
		{
			public void buttonPressed(Button b) 
			{
				if(pilot.getTravelSpeed() - 100 >= 1)
				{
					pilot.setTravelSpeed(pilot.getTravelSpeed() - 100);
				}
			}
			
			public void buttonReleased(Button b) 
			{
				//Leave it implemented
			}
			
		});
	
		//This is where the main runtime of the program begins
		while(run_bool)
		{
			pilot.forward();
			
			//When a bump is detected it will reverse and rotate by 90 degrees.
			if(ts_right.isPressed() || ts_left.isPressed())
			{
				pilot.travel(-700);
				pilot.rotate(90);
			}
		}
		//This will stop the robot from moving forward after the program has finished
		pilot.stop();
	}
}
