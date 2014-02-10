package Part3;

import java.util.ArrayList;

import lejos.nxt.Button;
import lejos.nxt.ButtonListener;
import lejos.nxt.LightSensor;
import lejos.nxt.Motor;
import lejos.nxt.NXTRegulatedMotor;
import lejos.nxt.SensorPort;
import lejos.nxt.SensorPortListener;
import lejos.nxt.Sound;
import lejos.robotics.Color;
import lejos.robotics.navigation.DifferentialPilot;
import lejos.util.Delay;

public class GridRunnable implements Runnable 
{
	private DifferentialPilot pilot;
	private LightSensor l_light;
	private LightSensor r_light;
	
	private ArrayList<Direction> path = new ArrayList<Direction>();
	
	private boolean m_run;
	private final int darkNum = 15;
	private final int rotaten = 15;
	
	private boolean l_dark;
	private boolean r_dark;
	
	private boolean l_cal;
	private boolean h_cal;

	public GridRunnable()
	{
		pilot = new DifferentialPilot(300, 1720, Motor.A, Motor.B, false);		
		l_light = new LightSensor(SensorPort.S1);
		r_light = new LightSensor(SensorPort.S2);
		
		m_run = true;
		
		l_dark = false;
		r_dark = false;
		
		l_cal = false;
		h_cal = false;
		
		path.add(Direction.UP);
		path.add(Direction.LEFT);
		path.add(Direction.RIGHT);
		path.add(Direction.DOWN);
	}
	
	public void run() 
	{
		Button.ENTER.addButtonListener(new ButtonListener()
		{
			public void buttonPressed(Button b) 
			{
				r_light.calibrateLow();
				l_light.calibrateLow();
				
				l_cal = true;
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
				r_light.calibrateHigh();
				l_light.calibrateHigh();
				
				h_cal = true;
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
				if(l_light.getLightValue() < darkNum)
				{
					l_dark = true;
				}
				else
				{
					l_dark = false;
				}
			}
			
		});
		
		SensorPort.S2.addSensorPortListener(new SensorPortListener()
		{
			public void stateChanged(SensorPort aSource, int aOldValue,	int aNewValue) 
			{
				if(r_light.getLightValue() < darkNum)
				{
					r_dark = true;
				}
				else
				{
					r_dark = false;
				}
			}
			
		});
	
		while(!l_cal || !h_cal)
		{}
		
		pilot.forward();
		pilot.setTravelSpeed(600);
		Sound.setVolume(Sound.VOL_MAX);
		
		while(m_run)
		{
			calibrate();
			line();
		}
	}
	
	public void calibrate()
	{
		if(l_dark && r_dark)
		{
			//Code for moving the robot off of the black lines
			double c_speed = pilot.getTravelSpeed();
			boolean move = true; 
			pilot.setTravelSpeed(100);
			pilot.forward();
			
			while(move)
			{
				if(!l_dark || !r_dark)
				{
					move = false;
					pilot.stop();
					Sound.twoBeeps();
				}
			}
			move = true;
			pilot.setTravelSpeed(c_speed);
			Delay.msDelay(1000);
			
			
			//Code for rotating the robot so it moves correctly
			NXTRegulatedMotor A_motor = Motor.A;
			NXTRegulatedMotor B_motor = Motor.B;
			
			if(l_dark == false)
			{
				A_motor.setSpeed(300);
				A_motor.forward();
				while(move)
				{
					if(r_dark == false)
					{
						move = false;
						A_motor.stop();
						Sound.beep();
					}
				}
				move = true;
			}
			
			if(r_dark == false)
			{
				B_motor.setSpeed(300);
				B_motor.forward();
				while(move)
				{
					if(l_dark == false)
					{
						move = false;
						B_motor.stop();
						Sound.beep();
					}			
				}
				move = true;
			}
			
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
			
			pilot.forward();
		}
	}
	
	public void line()
	{
		if(l_dark || r_dark && !(l_dark && r_dark))
		{
			if(r_dark)
			{
				pilot.steer(50, 2);
			}
			if(l_dark)
			{
				pilot.steer(50, -2);
			}
		}
		pilot.forward();
	}
	
	public void right()
	{
		pilot.travel(575);
		pilot.rotate(85);
	}
	
	public void left()
	{
		pilot.travel(575);
		pilot.rotate(-85);
	}
	
	private void backwards()
	{
		pilot.rotate(170);
	}
	
	/*-> Turn according to pattern
	 * -> Behaviours
	 * public void setPattern()   <- Method for setting up the pattern
	 * public void followLine()   <- Method for following the line
	 * public void junction()     <- Method for what to do at a junction
	 * private void right()       <- Method for turning right at a junction
	 * private void left()        <- Method for turning left at a junction
	 * private void backwards()   <- Method for turning around at a junction
	 * private void wall()        <- Method for turning around when a wall is detected
	 */
}
