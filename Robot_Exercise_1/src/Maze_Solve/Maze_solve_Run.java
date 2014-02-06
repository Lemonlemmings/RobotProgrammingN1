package Maze_Solve;

import lejos.nxt.Motor;
import lejos.nxt.SensorPort;
import lejos.nxt.TouchSensor;
import lejos.nxt.UltrasonicSensor;
import lejos.robotics.navigation.DifferentialPilot;
import lejos.util.Delay;


public class Maze_solve_Run {
	private final DifferentialPilot pilot = new DifferentialPilot(300, 1720, Motor.A, Motor.B, false);

	private final TouchSensor leftBumper  = new TouchSensor(SensorPort.S1);
	private final TouchSensor rightBumper = new TouchSensor(SensorPort.S2);
	
	private final UltrasonicSensor ultrasonic = new UltrasonicSensor(SensorPort.S3);
	
	// Rototation needed to rotate 45 degrees
	private static final double ROTATION = 41.25;
	
	private static final int STEPS = 8;
	
	/* This shows the the corresponding array indices to
	 * each direction from the robots perspective 
	 * 
	 *  7   0   1
	 *   \  |  /
	 *    \ | /
	 * 6 -- O -- 2
	 *    / | \
	 *   /  |  \   
	 *  5   4   3
	 *  
	 */   
	
	/*
	 * Spins the robot around in 45 degree steps, and returns
	 * the distance between the robot and the wall for each step.
	 */
	public float[] spin()
	{
		
		float[] dist = new float[STEPS];
		
		for (int i = 0; i < STEPS; i++) {
			//Tells the sensor to perform a distance check
			ultrasonic.ping();
				
			// Need to delay before getting back the distance
			Delay.msDelay(25);
			
			dist[i] = (float) ultrasonic.getDistance();
		
			//Spins the robot 45 degrees
			pilot.rotate(ROTATION);
		}
		
		return dist;
	}
	
	/*
	 * Returns the index of the maximum value in an array
	 */
	public int max(float[] array)
	{
		int max = 0;
	
		for (int i = 1; i < array.length; i++) {
			
			if (array[i] > array[max] ) {
					max = i;
			}
		}
		
		return max;
	}
	
	/*
	 * Weighs each direction, to prioritise some directions over others.
	 */
	private void weigh(float[] array) {
		//Sets a distance to 0 if its too close, so that direction isn't picked
		for (int i = 0; i < array.length; i++) {
			if (array[i] <= 25) {
				array[i] = 0.0f;
			}
		}
		
		//Decrease the weight of the back 3 directions
		array[3] *= 0.5f;
		array[4] *= 0.5f;
		array[5] *= 0.5f;
		
		//Greatly increase the weight of the front 3 directions
		array[0] *= 2.0f;
		array[1] *= 1.75f;
		array[7] *= 2.0f;
		
		//Slightly increase the weight of left and right
		array[2] *= 1.5f;
		array[6] *= 1.5f;
	}
	
	//The main part of the robot logic
	public void run() {
		//Keep looping
		while (true) {
			//Get the direction around the robot
			float[] dist = spin();
			
			// Weigh each direction
			weigh(dist);
			
			//Picks the furthest direction (which is affected by the weighting)
			int index = max(dist);
			
			// Decide which way to rotate
			if (index <= 4) { // Rotate clockwise
				for (int i = 0; i < index; i++) {
					pilot.rotate(ROTATION);
				}
			} else { // Rotate anti-clockwise
				for (int i = 0; i < (8 - index); i++) {
					pilot.rotate(-ROTATION);
				}
			}
			
			pilot.forward();
			
			/* Changes the untrasonic sensor so it continuously
			 * measures the distance ahead of the robot
			 */
			ultrasonic.continuous();
			
			//Only perform distance checks when the robot is moving
			while (pilot.isMoving()) {
				if (leftBumper.isPressed() || rightBumper.isPressed()) {
					//Backs up the robot if it has hit a wall
					pilot.stop();
					pilot.travel(-800);
				} 
				else if (ultrasonic.getDistance() <= 15 ) {
					//Stops the robot if it's to close to a wall
					pilot.stop();
				}
			}
		}
	}

}