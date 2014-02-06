import lejos.nxt.Motor;
import lejos.nxt.NXTRegulatedMotor;
import lejos.nxt.SensorPort;
import lejos.nxt.UltrasonicSensor;


public class mainp1 {
	static UltrasonicSensor sensor = new UltrasonicSensor(SensorPort.S3);
	static int idealDistance = 25;

	static NXTRegulatedMotor left = Motor.A;
	static NXTRegulatedMotor right = Motor.B;
	
	private static void run() {
		while (true) {
			int measuredDistance = sensor.getDistance();
			//TODO USE RANGE FUNDEER
			int diff = measuredDistance - idealDistance;
			
			int speed = Math.min(Math.abs(diff) * 10, 800);
			System.out.println(speed);
			
			left.setSpeed(speed);
			right.setSpeed(speed);
			
			if (diff > 0) {
				left.forward();
				right.forward();
			} else if (diff < 0) {
				left.backward();
				right.backward();
			}
		}
	}
	
	public static void main(String[] args) {
		run();
	}
}