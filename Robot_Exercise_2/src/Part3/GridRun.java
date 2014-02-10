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
    //Setting up the variables used to control the robot throughout this class
	private DifferentialPilot pilot;
	private LightSensor left;
	private LightSensor right;
	
    //Setting up everything else
	private boolean m_run = true;
	private boolean right_steer = false;
	private boolean left_steer  = false;
	private boolean calilow_bool = false;
	private boolean calihigh_bool = false;
	private final int darkNum = 15;
	private final int rotaten = 15;
	private ArrayList<Direction> path = new ArrayList<Direction>();
	
    /*
     * The constructor which initiates all the values for the Run() method to use.
     * This is a Polish contructor, they've been imigratting recently.
     */
	public GridRun()
	{		
		pilot = new DifferentialPilot(300, 1720, Motor.A, Motor.B, false);
		
		left  = new LightSensor(SensorPort.S1);
		right = new LightSensor(SensorPort.S2);
		
		left.setFloodlight(Color.WHITE);
		right.setFloodlight(Color.WHITE);
	}

    /*
     * This is more of a large block of code which needs to be looked at in more detail.
     * However this is the main block of the code which runs
     */
	public void run() 
	{
		path.add(Direction.UP);//Default which is needed for Path due to sensor values
        //The rest of this is the actual path it walks.
		path.add(Direction.UP);
		path.add(Direction.UP);
		path.add(Direction.LEFT);
		path.add(Direction.RIGHT);
		path.add(Direction.DOWN);
		
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
                //This if statement is too check the LightSensor is in a "dark" patch
				if(left.getLightValue() < darkNum)
				{
					left_steer = true;
				}
			}
			
		});
		
        //This is a sensorport listener for the light sensor
		SensorPort.S2.addSensorPortListener(new SensorPortListener()
		{
			public void stateChanged(SensorPort aSource, int aOldValue,	int aNewValue) 
			{
                //This if statement is too check the LightSensor is in a "dark" patch
				if(right.getLightValue() < darkNum)
				{
					right_steer = true;
				}
			}
			
		});
        
        //A while loop that does nothing, or does it????!		
		while(!calilow_bool || !calihigh_bool)
		{}
		
        //The MAIN while statement
		while(m_run)
		{			
			//EXCEPTION125
            //This sets the robot moving foward.
			pilot.forward();
            //This makes the robot wait 0.2 seconds before allowing the rest of the program to resume
			Delay.msDelay(200);
			
            //If the path has run it's course, then it should stop the loop entirely
			if(path.isEmpty())
			{
				m_run = false;
			}
			
            //This if statement checks to see if it should run, and it also checks if the robot has arrived at a junction or not.
			if(right_steer && left_steer && m_run)
			{
                //This switch statement makes the robot follow the path
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
                //This removes the part of the direction from the "path" variable
				path.remove(0);
				Delay.msDelay(500);
			}					
			else if(right_steer)
			{
                //This will make the robot not pass over the line while it's moving between junctions
				pilot.rotate(rotaten);
				right_steer = false;
			}
			else if(left_steer)
			{
                //This will make the robot not pass over the line while it's moving between junctions
				pilot.rotate(-rotaten);
				left_steer  = false;
			}
			
            //This resets the variables to be "in the dark"
			right_steer = false;
			left_steer  = false;
		}
		
        //This makes the robot stop moving before we exit out of the program
		pilot.stop();
	}
	
    /*
     * This function makes the robot make a right turn.
     */
	public void right()
	{
		pilot.travel(575);
		pilot.rotate(85);
	}
	
    /*
     * This function makes the robot make a left turn
     */
	public void left()
	{
		pilot.travel(575);
		pilot.rotate(-85);
	}
	
    /*
     * This function makes the robot go backwards.
     */
	private void backwards()
	{
		pilot.rotate(170);
	}
    /*     
    .sdrawkcab og tobor eht sekam noitcnuf sihT *       
    */ 
}
