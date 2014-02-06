package Part2;

import lejos.nxt.LightSensor;
import lejos.nxt.Motor;
import lejos.nxt.SensorPort;
import lejos.robotics.Color;
import lejos.robotics.navigation.DifferentialPilot;


public class LineFollowerRun {
	private DifferentialPilot pilot;
	private LightSensor left;
	private LightSensor right;
	private boolean quit;
	
	LineFollowerRun() {
		pilot = new DifferentialPilot(300, 1720, Motor.A, Motor.B, false);
		
		left = new LightSensor(SensorPort.S1);
		right = new LightSensor(SensorPort.S2);
		
		left.setFloodlight(Color.WHITE);
		right.setFloodlight(Color.WHITE);
		
		quit = false;
	}
	
	public void run() {
		while (!quit) {
			boolean leftLight = left.getLightValue() < 25;
			boolean rightLight = right.getLightValue() < 25;
			
			if (leftLight && rightLight) {
				pilot.stop();
			} else if (leftLight) {
				pilot.steer(100);
			} else if (rightLight) {
				pilot.steer(-100);
			} else {
				pilot.forward();
			}
		}
	}
}
