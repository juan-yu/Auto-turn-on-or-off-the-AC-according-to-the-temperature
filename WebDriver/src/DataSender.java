import java.io.DataOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import lejos.pc.comm.NXTCommLogListener;
import lejos.pc.comm.NXTConnector;

public class DataSender {
	public static void main(String[] args) throws InterruptedException {

		NXTConnector conn = new NXTConnector();
		conn.addLogListener(new NXTCommLogListener() {
			public void logEvent(String message) {
				System.out.println("BTSend Log.listener: " + message);
			}

			public void logEvent(Throwable throwable) {
				System.out.println("BTSend Log.listener - stack trace: ");
				throwable.printStackTrace();
			}
		});
		// Connect to any NXT over Bluetooth
		boolean connected = conn.connectTo("btspp://ze-wei-yu");

		if (!connected) {
			System.err.println("Failed to connect to any NXT");
			System.exit(1);
		}

		DataOutputStream dos = new DataOutputStream(conn.getOutputStream());
		//TimeUnit.MINUTES.sleep(150);
		System.out.println("Start");

		while (true) {
			long MSEC_SINCE_EPOCH = System.currentTimeMillis();
			Date instant = new Date(MSEC_SINCE_EPOCH);
			SimpleDateFormat sdf = new SimpleDateFormat("mm");
			String time = sdf.format(instant);
			if (time.equals("55")) {
				try {
					Test scraper = new Test();
					int at = scraper.getAT();
					dos.writeInt(at);
					dos.flush();
					System.out.println("AT:"+at);
				} catch (IOException ioe) {
					System.out.println("IO Exception writing bytes:");
					System.out.println(ioe.getMessage());
					break;
				}
				TimeUnit.SECONDS.sleep(30);
			}
		}
	}
}
