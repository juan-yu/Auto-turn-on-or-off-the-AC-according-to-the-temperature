
import java.io.DataInputStream;
import java.io.IOException;

import lejos.nxt.Button;
import lejos.nxt.LCD;
import lejos.nxt.Motor;
import lejos.nxt.SensorPort;
import lejos.nxt.TouchSensor;
import lejos.nxt.comm.BTConnection;
import lejos.nxt.comm.Bluetooth;

public class NXTReceive {

	public static void main(String[] args) {
		String connected = "Connected";
		String waiting = "Waiting...";
		Boolean AcOn = false;
		TouchSensor touch = new TouchSensor(SensorPort.S1);
		Motor.A.setSpeed(30);
		while (!touch.isPressed()) {
			Motor.A.forward();
		}
		Motor.A.stop();
		Motor.A.resetTachoCount();
		LCD.drawString(waiting, 0, 0);
		LCD.refresh();

		BTConnection btc = Bluetooth.waitForConnection();

		LCD.clear();
		LCD.drawString(connected, 0, 0);
		LCD.refresh();

		DataInputStream dis = btc.openDataInputStream();
		while (true) {

			int n = 0;
			try {
				n = dis.readInt();
				LCD.drawInt(n, 2, 0, 1);
				LCD.refresh();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				LCD.drawString("no data", 0, 2);
				btc = Bluetooth.waitForConnection();
				dis = btc.openDataInputStream();
			}
			if (n >= 36 && AcOn == false) {
				Motor.A.setSpeed(10);
				Motor.A.rotateTo(-73);
				Motor.A.rotateTo(-30);
				Motor.A.flt();
				AcOn = true;
				LCD.drawString("on",0,3);
				LCD.refresh();
			}
			if (n < 36 && AcOn == true) {
				Motor.A.setSpeed(10);
				Motor.A.rotateTo(-73);
				Motor.A.rotateTo(-30);
				AcOn = false;
				LCD.drawString("off",0,3);
				LCD.refresh();
			}
		}
	}
}
