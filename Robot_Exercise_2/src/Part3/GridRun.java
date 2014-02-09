package Part3;

import java.util.ArrayList;

import lejos.nxt.Button;
import lejos.nxt.ButtonListener;
import lejos.nxt.LightSensor;
import lejos.nxt.Motor;
import lejos.nxt.SensorPort;
import lejos.nxt.SensorPortListener;
import lejos.robotics.Color;
import lejos.robotics.navigation.DifferentialPilot;
import lejos.util.Delay;

public class GridRun implements Runnable 
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
	private final int rotaten = 15;
	private ArrayList<Direction> path = new ArrayList<Direction>();
	
	public GridRun()
	{		
		pilot = new DifferentialPilot(300, 1720, Motor.A, Motor.B, false);
		
		left  = new LightSensor(SensorPort.S1);
		right = new LightSensor(SensorPort.S2);
		
		left.setFloodlight(Color.WHITE);
		right.setFloodlight(Color.WHITE);
	}

	public void run() 
	{
		path.add(Direction.UP);//Default which is needed for Path due to sensor values
		path.add(Direction.UP);
		path.add(Direction.UP);
		path.add(Direction.LEFT);
		
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
					left_steer = true;
				}
			}
			
		});
		
		SensorPort.S2.addSensorPortListener(new SensorPortListener()
		{
			public void stateChanged(SensorPort aSource, int aOldValue,	int aNewValue) 
			{
				if(right.getLightValue() < darkNum)
				{
					right_steer = true;
				}
			}
			
		});
		
		while(!calilow_bool || !calihigh_bool)
		{}
		
		while(m_run)
		{			
			//EXCEPTION125
			pilot.forward();
			Delay.msDelay(200);
			
			if(path.isEmpty())
			{
				m_run = false;
			}
			
			if(right_steer && left_steer && m_run)
			{
				switch(path.get(0).toInt())
				{
					case 3 : right();
							 break;
					case 2 : left();
							 break;
					case 1 : backwards();
							 break;
					case 0 : break;
				}
				path.remove(0);
				Delay.msDelay(500);
			}					
			else if(right_steer)
			{
				pilot.rotate(rotaten);
				right_steer = false;
			}
			else if(left_steer)
			{
				pilot.rotate(-rotaten);
				left_steer  = false;
			}
			
			right_steer = false;
			left_steer  = false;
		}
		
		pilot.stop();
	}
	
	private void right()
	{
		pilot.travel(700);
		pilot.rotate(85);
	}
	
	private void left()
	{
		pilot.travel(700);
		pilot.rotate(-85);
	}
	
	private void backwards()
	{
		pilot.rotate(170);
	}
}
