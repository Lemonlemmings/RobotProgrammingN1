package Part2;

import lejos.nxt.LightSensor;
import lejos.nxt.Motor;
import lejos.nxt.SensorPort;
import lejos.nxt.SensorPortListener;
import lejos.robotics.Color;
import lejos.robotics.navigation.DifferentialPilot;
import lejos.util.Delay;


public class LineFollowerRun 
{
	private DifferentialPilot pilot;
	private LightSensor left;
	private LightSensor right;
	
	private boolean m_run = true;
	private boolean right_steer = false;
	private boolean left_steer = false;
	private final int darkNum = 50; 
	
	LineFollowerRun() 
	{
		pilot = new DifferentialPilot(300, 1720, Motor.A, Motor.B, false);
		
		left  = new LightSensor(SensorPort.S1);
		right = new LightSensor(SensorPort.S2);
		
		left.setFloodlight(Color.WHITE);
		right.setFloodlight(Color.WHITE);
	}
	
	public void run() 
	{
		SensorPort.S1.addSensorPortListener(new SensorPortListener()
		{
			public void stateChanged(SensorPort aSource, int aOldValue,	int aNewValue) 
			{
				if(left.getLightValue() < darkNum)
				{
					System.out.println("Balls");
					left_steer = true;
					Delay.msDelay(2000);
				}
			}
			
		});
		
		SensorPort.S2.addSensorPortListener(new SensorPortListener()
		{
			public void stateChanged(SensorPort aSource, int aOldValue,	int aNewValue) 
			{
				if(right.getLightValue() < darkNum)
				{
					System.out.println("Cocks");
					right_steer = true;
					Delay.msDelay(2000);
				}
			}
			
		});
		
		while (m_run) 
		{
			pilot.forward();
			if(right_steer)
			{
				pilot.steer(30);
				right_steer = false;
			}
			if(left_steer)
			{
				pilot.steer(-30);
				left_steer = false;
			}
		}
	}
}
