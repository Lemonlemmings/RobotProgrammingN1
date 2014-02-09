package Part3;

import java.util.Random;

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
				}
			}
			
		});
		
		while(m_run)
		{
			while(!calilow_bool || !calihigh_bool)
			{}
			
			//EXCEPTION125
			pilot.forward();
			Delay.msDelay(200);
			
			if(right_steer && left_steer)
			{
				Random gen = new Random();
				switch(gen.nextInt(4))
				{
					case 0 : right();
							 break;
					case 1 : left();
							 break;
					case 2 : backwards();
							 break;
					case 3 : break;
				}
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
