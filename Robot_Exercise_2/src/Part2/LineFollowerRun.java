package Part2;

import lejos.nxt.Button;
import lejos.nxt.ButtonListener;
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
	private boolean left_steer  = false;
	private boolean calilow_bool = false;
	private boolean calihigh_bool = false;
	private final int darkNum = 15;
	private final int rotaten = 25;
	
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
		Button.ENTER.addButtonListener(new ButtonListener()
		{
			public void buttonPressed(Button b) 
			{
				right.calibrateLow();
				left.calibrateLow();
				calilow_bool = true;
			}

			public void buttonReleased(Button b) 
			{
				//Leaving this implemented but unused	
			}
			
		});
		
		Button.ESCAPE.addButtonListener(new ButtonListener()
		{
			public void buttonPressed(Button b) 
			{
				right.calibrateHigh();
				left.calibrateHigh();
				calihigh_bool= true;
			}

			public void buttonReleased(Button b) 
			{
				//Leaving this implemented but unused	
			}
			
		});
		
		SensorPort.S1.addSensorPortListener(new SensorPortListener()
		{
			public void stateChanged(SensorPort aSource, int aOldValue,	int aNewValue) 
			{
				if(left.getLightValue() < darkNum)
				{
					System.out.println("Balls");
					left_steer = true;
					Delay.msDelay(1000);
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
					Delay.msDelay(1000);
				}
			}
			
		});
		
		while (m_run) 
		{
			while((calilow_bool == false) && (calihigh_bool == false))
			{
				
			}
			
			pilot.forward();
			
			if(right_steer)
			{
				pilot.rotate(rotaten);
				right_steer = false;
			}
			if(left_steer)
			{
				pilot.rotate(-rotaten);
				left_steer  = false;
			}
		}
	}
}
