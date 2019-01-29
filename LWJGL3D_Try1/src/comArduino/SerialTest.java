package comArduino;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import gnu.io.CommPortIdentifier;
import gnu.io.SerialPort;
import gnu.io.SerialPortEvent;
import gnu.io.SerialPortEventListener;
import java.util.Enumeration;
import gnu.io.CommPort;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.PrintWriter;

public class SerialTest implements SerialPortEventListener {
	
	SerialPort serialPort;
	/** The port we’re normally going to use. */
	private static final String PORT_NAMES[] = {"COM6"};
	/**
	* A BufferedReader which will be fed by a InputStreamReader 
	* converting the bytes into characters 
	* making the displayed results codepage independent
	*/
	private BufferedReader input;
	/** The output stream to the port */
	private OutputStream output;
	/** Milliseconds to block while waiting for port open */
	private static final int TIME_OUT = 2000;
	/** Default bits per second for COM port. */
	private static final int DATA_RATE = 9600;
	
	public void initialize() {
		CommPortIdentifier portId = null;
		Enumeration portEnum = CommPortIdentifier.getPortIdentifiers();
		
		//First, Find an instance of serial port as set in PORT_NAMES.
		while (portEnum.hasMoreElements()) {
			CommPortIdentifier currPortId = (CommPortIdentifier) portEnum.nextElement();
			for (String portName : PORT_NAMES) {
				if (currPortId.getName().equals(portName)) {
					portId = currPortId;
					break;
				}
			}
		}
		if (portId == null) {
			System.out.println(" Could not find COM port. ");
			return;
		}
		
		try {
			// open serial port, and use class name for the appName
			serialPort = (SerialPort) portId.open(this.getClass().getName(),TIME_OUT);
			// set port parameters
			serialPort.setSerialPortParams(DATA_RATE,
					SerialPort.DATABITS_8,
					SerialPort.STOPBITS_1,
			SerialPort.PARITY_NONE);
			
			String data="yessss";
			// open the streams
			input = new BufferedReader(new InputStreamReader(serialPort.getInputStream()));
			output = serialPort.getOutputStream();
			//(new Thread(new SerialWriter(output))).start();
			
			output.write(data.getBytes());
			output.flush();
			Thread.sleep(1000);
			serialPort.addEventListener(this);
			serialPort.notifyOnDataAvailable(true);
		} catch (Exception e) {
			System.out.println("1");
			System.err.println(e.toString());
		}
	}
	
	/*public static class SerialWriter implements Runnable {
		OutputStream out;
	
		public SerialWriter(OutputStream out) {
			this.out = out;
		}
	
		public void run() {
			try {
				int c = 0;
				while ((c = System.in.read()) > -1) {
					this.out.write(c);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}*/
	
	/*public static void writedata(int v1) {
	    try {
	      output.write(v1);
	      output.flush();
	      System.out.println("ok");
	    } catch (IOException e) {
	      // TODO Auto-generated catch block
	      e.printStackTrace();
	    }
	  }*/
	
	public synchronized void close() {
		if (serialPort != null) {
			serialPort.removeEventListener();
			serialPort.close();
		}
	}
	
	public synchronized void serialEvent(SerialPortEvent oEvent) {
		if (oEvent.getEventType() == SerialPortEvent.DATA_AVAILABLE) {
			try {
				String inputLine=null;
				if (input.ready()) {
				inputLine = input.readLine();
				
				String [] chunks = inputLine.split(" , ");
				
				System.out.println(inputLine);
				try {
					System.out.println(chunks[0] +  " \t  " + chunks[1] +  " \t  " + chunks[2] +  " \t " );
					} catch (Exception e) {}
				}
				
			} catch (Exception e) {
				System.out.println("2");
				System.err.println(e.toString());
			}
		}
		// Ignore all the other eventTypes, but you should consider the other ones.
	}

	public static void main(String[] args) throws Exception {
		SerialTest main = new SerialTest();
		main.initialize();
		Thread t=new Thread() {
		public void run() {
			//writedata(12);
			
		//the following line will keep this app alive for 1000 seconds,
		//waiting for events to occur and responding to them (printing incoming messages to console).
		try {Thread.sleep(1000000);} catch (InterruptedException ie) {}
		}
		};
		t.start();
		System.out.println(" Started ");
	}

}

