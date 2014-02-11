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
    //Setting up variables
	protected boolean m_run = true;
	protected RangeFinder ranger;
	protected DifferentialPilot pilot;
	private final float idealDistance = 25f;
	
    /*
     * This constructor sets up the basic variables required to run the NXT robot.
     */
	public wall()
	{
		pilot    = new DifferentialPilot(250, 1270, Motor.A, Motor.B, false);
		ranger   = new OpticalDistanceSensor(SensorPort.S3);
	}
	
    /*
     * This is the run method, which is where the main block of code executes
     */
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
		
        //This is the main while loop for the program
		while(m_run)
		{
            //This finds the current distance and initiates direction to be 'true'
			float measuredDistance = ranger.getRange();
			float diff = measuredDistance - idealDistance;
			boolean direction = true;
			
            //This sets the direction to be false, it's too close to the object
			if(diff < 0)
			{
				direction = false;
			}
            
            //This will either find a percentage to multiply the speed by, or recognise that the speed doesn't need changing from full speed
			diff = Math.min(Math.abs(diff / idealDistance), 1);
            //In this the speed is set accordingly to the percentage driven value found above
			float speed = diff * (float)pilot.getMaxTravelSpeed();
			
            //Prints out the speed it's changing too
			System.out.println("Speed: "+ speed);
            //Sets the pilot to move at that speed
			pilot.setTravelSpeed(speed);
			
            //This decides whether the pilot needs to be moving forwards or backwards
			if (direction) 
			{				
				pilot.forward();
			}
			else 
			{
				pilot.backward();
			}
		}
        //This stops the robot from moving once the main block of code has run it's course.
        //Aren't we mean to this poor robot   :(
		pilot.stop();
	}
}
