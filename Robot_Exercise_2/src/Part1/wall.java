package Part1;

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
		while(m_run)
		{
			float measuredDistance = ranger.getRange();
			float diff = idealDistance - measuredDistance;
			
			float speed = Math.min(Math.abs(diff) * 10, 800f);
			pilot.setTravelSpeed(speed);
			
			if (diff > 0) 
			{
				pilot.forward();
			}
			else if (diff < 0) 
			{
				pilot.backward();
			}
		}
	}
}
