package Bump_V2;

import lejos.nxt.Button;
import lejos.nxt.ButtonListener;
import lejos.nxt.Motor;
import lejos.nxt.SensorPort;
import lejos.nxt.SensorPortListener;
import lejos.nxt.TouchSensor;
import lejos.robotics.navigation.DifferentialPilot;

public class Bump_V2_Run implements Runnable 
{
	private boolean run_bool  = true;
	private boolean crashed   = false;
	private boolean direction = false;
	
	
	TouchSensor ts_right;
	TouchSensor ts_left;
	
	DifferentialPilot pilot;
	
	public Bump_V2_Run()
	{
		ts_right = new TouchSensor(SensorPort.S1);
		ts_left  = new TouchSensor(SensorPort.S2);
		pilot = new DifferentialPilot(250, 1270, Motor.A, Motor.B, false);
	}

	public void run() 
	{
		//Sets up a sensorlistener to set the crashed boolean to be true
		SensorPort.S1.addSensorPortListener(new SensorPortListener()
		{
			public void stateChanged(SensorPort aSource, int aOldValue,	int aNewValue) 
			{
				crashed   = true;
				direction = true;
			}			
		});
		
		//Sets up a sensorlistener yo set the crashed boolean to be true
		SensorPort.S2.addSensorPortListener(new SensorPortListener()
		{
			public void stateChanged(SensorPort aSource, int aOldValue,	int aNewValue) 
			{
				crashed   = true;
				direction = false;
			}			
		});
		
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
		
		//Sets crashed to be false. Seems the sensorlisteners react at the beginning of the program
		//crashed is made true.
		crashed = false;
		while(run_bool)
		{
			pilot.forward();
			//If crashed is proc'd the following if statement will run
			if(crashed)
			{
				pilot.travel(-700);
				
				if(direction)
				{
					pilot.rotate(90);
				}
				else
				{
					pilot.rotate(-90);
				}
			}
				crashed = false;
		}
		//Stops the pilot after the end of the program, so it doesn't carry on moving forward
		pilot.stop();	
	}	
}

