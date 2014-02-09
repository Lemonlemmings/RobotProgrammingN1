package Part1;

import lejos.nxt.Button;
import lejos.nxt.ButtonListener;
import lejos.nxt.Motor;
import lejos.nxt.SensorPort;
import lejos.nxt.addon.OpticalDistanceSensor;
import lejos.robotics.RangeFinder;
import lejos.robotics.navigation.DifferentialPilot;



public class wall implements Runnable 
{
	protected boolean m_run = true;
	protected RangeFinder ranger;
	protected DifferentialPilot pilot;
	private final float idealDistance = 25f;
	
	public wall()
	{
		pilot    = new DifferentialPilot(250, 1270, Motor.A, Motor.B, false);
		ranger   = new OpticalDistanceSensor(SensorPort.S3);
	}
	
	public void run() 
	{
		//Sets up a button listener so the program can end
		Button.ENTER.addButtonListener(new ButtonListener()
		{
			public void buttonPressed(Button b)
			{
				m_run = false;
			}

			public void buttonReleased(Button b) 
			{
				//Just leaving it implemented but blank
			}
		});
		
		while(m_run)
		{
			float measuredDistance = ranger.getRange();
			float diff = measuredDistance - idealDistance;
			boolean direction = true;
			
			if(diff < 0)
			{
				direction = false;
			}
			diff = Math.min(Math.abs(diff / 25), 1);
			float speed = diff * (float)pilot.getMaxTravelSpeed();
			
			System.out.println("Speed: "+ speed);
			pilot.setTravelSpeed(speed);
			
			if (direction) 
			{				
				pilot.forward();
			}
			else 
			{
				pilot.backward();
			}
		}
		pilot.stop();
	}
}
