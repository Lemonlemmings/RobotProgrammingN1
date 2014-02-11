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
    //Variables which are used for the robot
	private DifferentialPilot pilot;
	private LightSensor left;
	private LightSensor right;
	
    //Other variables
	private boolean m_run = true;
	private boolean right_steer = false;
	private boolean left_steer  = false;
	private boolean calilow_bool = false;
	private boolean calihigh_bool = false;
	private final int darkNum = 15;
	private final int rotaten = 15;
	
    /*
     * This constructor initalises all of the important variables in the program.
     */
	LineFollowerRun() 
	{
		pilot = new DifferentialPilot(300, 1720, Motor.A, Motor.B, false);
		
		left  = new LightSensor(SensorPort.S1);
		right = new LightSensor(SensorPort.S2);
	}
	
    /*
     * This is the main running point of the program
     */
	public void run() 
	{	
        //This is a button listener designed to set the calibration of the "dark" patches
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
		
        //This is a button listener designed to set the calibration of the "light" patches
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
		
        //This is a sensorport listener for the light sensor
		SensorPort.S1.addSensorPortListener(new SensorPortListener()
		{
			public void stateChanged(SensorPort aSource, int aOldValue,	int aNewValue) 
			{
				if(left.getLightValue() < darkNum)
				{
					left_steer = true;
					Delay.msDelay(1000);
				}
			}
			
		});
		
        //This is a sensorport listener for the light sensor
		SensorPort.S2.addSensorPortListener(new SensorPortListener()
		{
			public void stateChanged(SensorPort aSource, int aOldValue,	int aNewValue) 
			{
				if(right.getLightValue() < darkNum)
				{
					right_steer = true;
					Delay.msDelay(1000);
				}
			}
			
		});
		
		while (m_run) 
		{
            //A while loop that does nothing, but looks like it should!
			while((calilow_bool == false) || (calihigh_bool == false))
			{
				
			}
			
            //Starts the robot moving forward.
			pilot.forward();
			
            //This block of code makes the robot rotate based on which LightSensor has crossed the black line
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
