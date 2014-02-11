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
    //Setting up the variables used to control the robot throughout this class
	private DifferentialPilot pilot;
	private LightSensor l_light;
	private LightSensor r_light;
	
    //Setting up the enumerator for the path
	private ArrayList<Direction> path = new ArrayList<Direction>();
	
    //Setting up "final" values used within the program
	private boolean m_run;
	private final int darkNum = 15;
	private final int rotaten = 15;
	
    //Setting up the booleans which are triggered by the SensorListeners
	private boolean l_dark;
	private boolean r_dark;
	
    //Setting up the booleans which are used by the ButtonListeners for calibration
	private boolean l_cal;
	private boolean h_cal;

    /*
     * This constructor just sets all the needed initial values for the code to run.
     * The pilot is still "sketchy" but it wasn't designed for tracks, which is unfortunate.
     * Since it's a Monday evening and this is dull, I can imagine you know what this does
     */
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
		
        //This is the scriptable path, I hope this is what you wanted
		path.add(Direction.UP);
		path.add(Direction.UP);
		path.add(Direction.LEFT);
		path.add(Direction.RIGHT);
		path.add(Direction.DOWN);
	}
	
    /*
     * This is more of a large block of code which needs to be looked at in more detail.
     * However this is the main block of the code which runs
     */
	public void run() 
	{
        //This is a button listener designed to set the calibration of the "dark" patches
		Button.ENTER.addButtonListener(new ButtonListener()
		{
			public void buttonPressed(Button b) 
			{
				r_light.calibrateLow();
				l_light.calibrateLow();
				
                //The calibration boolean mentioned earlier.
                //It remains true for the remainder of the program
				l_cal = true;
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
				r_light.calibrateHigh();
				l_light.calibrateHigh();
				
                //The calibration boolean mentioned earlier. 
                //It remains true for the remainder of the program
				h_cal = true;
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
				if(l_light.getLightValue() < darkNum)
				{
					l_dark = true;
				}
                //This else statement is a "reset" when the LightSensor passes back into the "light"
                //Darkness cannot drive out darkness; only light can do that; - Martin Luther King
                //Evidently he didn't have pilot.forward()
				else
				{
					l_dark = false;
				}
			}
			
		});
		
        //This is a sensorport listener for the light sensor
		SensorPort.S2.addSensorPortListener(new SensorPortListener()
		{
			public void stateChanged(SensorPort aSource, int aOldValue,	int aNewValue) 
			{
                //This if statement is too check the LightSensor is in a "dark" patch
				if(r_light.getLightValue() < darkNum)
				{
					r_dark = true;
				}
                //This else statement is a "reset" when the LightSensor passes back into the "light"
                //"Darkness cannot drive out darkness; only light can do that;" - Martin Luther King
                //Evidently he didn't have pilot.forward()
				else
				{
					r_dark = false;
				}
			}
			
		});
	
        //A while loop that does nothing, or does it????!
		while(!l_cal || !h_cal)
		{}
		
        //This starts the robot moving and sets the TravelSpeed, as well as popping the sound up to max
		pilot.forward();
		pilot.setTravelSpeed(600);
		Sound.setVolume(Sound.VOL_MAX);
		
        //The MAIN while statement. It simply checks for a junction, and then to see if it's gone over
        //the line it's following
		while(m_run)
		{
			junction();
			line();
		}
		
		pilot.stop();
	}
	
    /*
     * This method checks for a junction, if it's not at one then it skips straight to the end.
     */
	public void junction()
	{
        //This if statement checks whether it's at a junction or not, which means both light sensors have
        //gone "dark".
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
			
            //Code for following the path as God meant us too! Or maybe the enumerator class...
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
			
			if(path.isEmpty())
			{
				m_run = false;
			}
			
            //Start the robot moving forward again, no point in hanging around here.
			pilot.forward();
		}
	}
	
    /*
     * This method will steer the robot away from a line if he crosses over it, which he shouldn't.
     * Whenever this function is run, although we shouldn't tell you, we tell the robot off!
     */
	public void line()
	{
        //This is an XOR statement pretty much. Makes sure we're not actually at a junction!
        //It'd be awesome if you just do "if(Logic.XOR(l_dark, r_dark))". Actually, if it's as easy as I've made it out to be here
        //Then I think it probably exists
		if((l_dark || r_dark) && !(l_dark && r_dark))
		{
			boolean r_false;
			
			if(l_dark)
			{
				r_false = true;
			}
			else
			{
				r_false = false;
			}
			
            //This if statement checks which trigger sets off the XOR statement
			if(r_dark)
			{
				pilot.steer(50, 2);
			}
            //This if statement checks which trigger sets off the XOR statement
			if(r_false)
			{
				pilot.steer(50, -2);
			}
		}
        //Start the robot moving forward again, no point in hanging around here.
		pilot.forward();
	}
	
    /*
     * This function makes the robot make a right turn
     * Of course if he always did things right this we wouldn't have to tell him off for setting off line()
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
	
    //The below could be interesting. It was a list of functions I thought we'd need in this implementation.
    //Setting the path could be done in a seperate function, however the constructors so small it might as well
    //stay there
    
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
