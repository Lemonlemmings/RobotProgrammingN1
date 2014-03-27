package Part5;

import java.util.ArrayList;
import java.util.Random;

import lejos.nxt.Button;
import lejos.nxt.ButtonListener;
import lejos.nxt.LightSensor;
import lejos.nxt.Motor;
import lejos.nxt.SensorPort;
import lejos.nxt.SensorPortListener;
import lejos.nxt.Sound;
import lejos.nxt.addon.OpticalDistanceSensor;
import lejos.robotics.navigation.DifferentialPilot;
import lejos.util.Delay;
import rp.robotics.localisation.ActionModel;
import rp.robotics.localisation.GridPositionDistribution;
import rp.robotics.localisation.PerfectActionModel;
import rp.robotics.mapping.GridMap;
import rp.robotics.mapping.Heading;
import rp.robotics.mapping.LocalisationUtils;

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

	
	private Random rand = new Random(42);
	
	private OpticalDistanceSensor optical = new OpticalDistanceSensor(SensorPort.S3);
	
	
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
		//path.add(Direction.UP);

		
		//path.add(Direction.UP);
		//path.add(Direction.UP);
		//path.add(Direction.LEFT);
		/*path.add(Direction.UP);
		path.add(Direction.LEFT);
		path.add(Direction.RIGHT);
		path.add(Direction.DOWN);*/
		//for (int i = 0; i < 4; i++) {
			//path.add(Direction.UP);
		//	path.add(Direction.UP);
		//	path.add(Direction.UP);
		//	path.add(Direction.RIGHT);
			//path.add(Direction.UP);
			//path.add(Direction.UP);
			//path.add(Direction.UP);
			//path.add(Direction.UP);
			//path.add(Direction.RIGHT);
		//}
	}
	
	private int sample(int num)
	{
		int sum = 0;
		
		for (int i = 0; i < num; i++) {
			sum += optical.getDistance();
			Delay.msDelay(10);
		}
		
		int average = sum / num;
		
		return average;
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
		//pilot.forward();
		pilot.setTravelSpeed(pilot.getMaxTravelSpeed() * 0.75);
		Sound.setVolume(Sound.VOL_MAX / 2 + Sound.VOL_MAX / 3);
		
		

		GridMap gridMap = LocalisationUtils.create2014Map1();
		GridPositionDistribution distribution = new GridPositionDistribution(gridMap);
		distribution.normalise();
		
		PerfectActionModel actionModel = new PerfectActionModel();
		
		
		//pilot.forward();
		
        //The MAIN while statement. It simply checks for a junction, and then to see if it's gone over
        //the line it's following
		
		Heading relativeToGrid = Heading.PLUS_Y;
		
		int moves= 0;
		int turns = 0;
		
		while(m_run)
		{
			moves++;
			
			//junction();
			if (sample(5) >= 330) {
					line();
			}

			
			//Sound.beep();
			
			distribution = actionModel.updateAfterMove(distribution, relativeToGrid);

			//Sound.beep();

			float max = -1.0f / 0.0f;
			int maxX = -1;
			int maxY = -1;
			
			for (int y = 0; y < distribution.getGridHeight(); y++) {
				for (int x = 0; x < distribution.getGridWidth(); x++) {
					float prob = distribution.getProbability(x, y);
					
					if (prob > max) {
						max = prob;
						maxX = x;
						maxY = y;
					}
				}
			}
			
			System.out.println();
			System.out.println();
			System.out.println();
			System.out.println("Max X: " + maxX);
			System.out.println("Max Y: " + maxY);
			System.out.println("Prob: " + max);
			
			if (max > 0.65f) {
				
				Sound.setVolume(Sound.VOL_MAX);
				
				System.out.println("Moves = " + moves);
				System.out.println("Turns = " + turns);
				
				while(true) {
					Sound.beepSequence();
					Delay.msDelay(2000);
				}
			
			}
			
			
			
			
			
			
			//swing();

			boolean turnLeft = rand.nextBoolean();
			//boolean turnLeft = true;
			
			
			boolean wall = sample(5) < 330;
			
			while (wall) {
				turns++;
				//Sound.beepSequence();
				actionModel.cull(distribution, relativeToGrid);
				
				if (turnLeft) {
					left();
					
					// Rotate the relative position anti-clockwise 
					if (relativeToGrid == Heading.PLUS_Y) {
						relativeToGrid = Heading.PLUS_X;
					} else if (relativeToGrid == Heading.PLUS_X) {
						relativeToGrid = Heading.MINUS_Y;
					} else if (relativeToGrid == Heading.MINUS_Y) {
						relativeToGrid = Heading.MINUS_X;
					} else if (relativeToGrid == Heading.MINUS_X) {
						relativeToGrid = Heading.PLUS_Y;
					}
					
					//
				} else {
					right();
					
					// Rotate the relative position clockwise
					if (relativeToGrid == Heading.PLUS_Y) {
						relativeToGrid = Heading.MINUS_X;
					} else if (relativeToGrid == Heading.MINUS_X) {
						relativeToGrid = Heading.MINUS_Y;
					} else if (relativeToGrid == Heading.MINUS_Y) {
						relativeToGrid = Heading.PLUS_X;
					} else if (relativeToGrid == Heading.PLUS_X) {
						relativeToGrid = Heading.PLUS_Y;
					}
					
				}
				
				Delay.msDelay(100);
				
				wall = sample(5) < 330;
			}
			
			
			Sound.beep();
			actionModel.rull(distribution, relativeToGrid);
			Sound.beep();
			// wuz here
			
			pilot.forward();
		}
		
		// END
	}
	
	public void swing()
	{
        //Code for following the path as God meant us too! Or maybe the enumerator class...
		switch(path.get(0).toInt())
		{
			case 3 : right();
					 break;
			case 2 : left();
					 break;
			case 1 : backwards();
					 break;
			case 0 : //pilot.travel(500);
			         break;
		}
		path.remove(0);
		
		if(path.isEmpty())
		{
			m_run = false;
			return;
		}
		
        //Start the robot moving forward again, no point in hanging around here.
		pilot.forward();
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
		while(!(l_dark && r_dark))
		{
            //This if statement checks which trigger sets off the XOR statement
			if(r_dark)
			{
				pilot.steer(50, 4, true);
			}
           
			//This if statement checks which trigger sets off the XOR statement
			else if(l_dark)
			{
				pilot.steer(50, -4, true);
			}
			
			else
			{
				pilot.forward();
			}
			
		}
        //Start the robot moving forward again, no point in hanging around here.
		pilot.stop();
		pilot.travel(575);
		//pilot.stop
		
		//Sound.beepSequenceUp();
	}
	
    /*
     * This function makes the robot make a right turn
     * Of course if he always did things right this we wouldn't have to tell him off for setting off line()
     */
	public void right()
	{
		//pilot.travel(575);
		//pilot.rotate(85);

		pilot.rotate(35);
		
		double defaultRotationSpeed = pilot.getRotateSpeed();
		pilot.setRotateSpeed(defaultRotationSpeed * 0.6);
		
		pilot.rotate(90, true);
		
		while (!l_dark) {}
		
		pilot.stop();
		
		pilot.setRotateSpeed(defaultRotationSpeed);
		
		pilot.rotate(-20);
	}
	
    /*
     * This function makes the robot make a left turn
     */
	public void left()
	{
		//pilot.travel(575);
		//pilot.rotate(-85);
		
		pilot.rotate(-35);
		
		double defaultRotationSpeed = pilot.getRotateSpeed();
		pilot.setRotateSpeed(defaultRotationSpeed * 0.6);
		
		pilot.rotate(-90, true);
		
		while (!r_dark) {}
		
		pilot.stop();	
		
		pilot.setRotateSpeed(defaultRotationSpeed);
		
		pilot.rotate(20);
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
