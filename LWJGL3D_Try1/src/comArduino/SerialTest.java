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
import java.util.Scanner;

import gnu.io.CommPort;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.PrintWriter;

public class SerialTest implements SerialPortEventListener {

SerialPort serialPort;
/** The port we’re normally going to use. */
private static final String PORT_NAMES[] = {"COM4"};
/**
* A BufferedReader which will be fed by a InputStreamReader 
* converting the bytes into characters 
* making the displayed results codepage independent
*/
private BufferedReader input;
/** The output stream to the port */
private static OutputStream output;
/** Milliseconds to block while waiting for port open */
private static final int TIME_OUT = 2000;
/** Default bits per second for COM port. */
private static final int DATA_RATE = 9600;

private boolean cond = false;
private float inpX;
private float inpY;
private float inpZ;

public void initialize() {
	CommPortIdentifier portId = null;
	Enumeration portEnum = CommPortIdentifier.getPortIdentifiers();
	
	//First, Find an instance of serial port as set in PORT_NAMES.
	while (portEnum.hasMoreElements()) {
		CommPortIdentifier currPortId = (CommPortIdentifier) portEnum.nextElement();
		for (String portName : PORT_NAMES) {
			if (currPortId.getName().equals(portName)) {
				portId = currPortId;
				System.out.println("Connexion OK");
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
		serialPort.disableReceiveTimeout();
		serialPort.enableReceiveThreshold(1);
		// set port parameters
		serialPort.setSerialPortParams(DATA_RATE,
				SerialPort.DATABITS_8,
				SerialPort.STOPBITS_1,
		SerialPort.PARITY_NONE);
		
		// open the streams
		input = new BufferedReader(new InputStreamReader(serialPort.getInputStream()));
		output = serialPort.getOutputStream();
		serialPort.addEventListener(this);
		serialPort.notifyOnDataAvailable(true);
	} catch (Exception e) {
		System.out.println("1");
		System.err.println(e.toString());
	}
}

public synchronized void close() {
	if (serialPort != null) {
		serialPort.removeEventListener();
		serialPort.close();
	}
}

/**
 *  write data to the serial port.
 */
public synchronized void writeData(String data) {
	System.out.println("Sent: " + data);
	try {
		output.write(data.getBytes());
	} catch (Exception e) {
		System.out.println("could not write to port");
	}
}

public void serialEvent(SerialPortEvent oEvent) {
	if (oEvent.getEventType() == SerialPortEvent.DATA_AVAILABLE) {
		try {
			String inputLine=null;
			if (input.ready()) {
			inputLine = input.readLine();
			
			String [] chunks = inputLine.split(" , ");
			if(inputLine.charAt(0) == 'X' && inputLine.length()> 3) setInputX(inputLine.substring(2));
			if(inputLine.charAt(0) == 'Y' && inputLine.length()> 3) setInputY(inputLine.substring(2));
			if(inputLine.charAt(0) == 'Z' && inputLine.length()> 3) setInputZ(inputLine.substring(2));
			
			
			cond = true;
			
			System.out.println(inputLine);
			try {
				System.out.println(chunks[0] +  " \t  " + chunks[1] +  " \t  " + chunks[2] +  " \t " );
			} catch (Exception e) {}
			}
		} catch(Exception e) {
			System.err.println(e.toString());
		}
	}
	// Ignore all the other eventTypes, but you should consider the other ones.
}

public boolean getCond() {
	return cond;
}
public void setCond() {
	cond = false;
}

public void setInputX(String x) {
	inpX= Float.parseFloat(x);
}
public float getInputX() {
	return inpX;
}

public void setInputY(String x) {
	inpY= Float.parseFloat(x);
}
public float getInputY() {
	return inpY;
}

public void setInputZ(String x) {
	inpZ= Float.parseFloat(x);
}
public float getInputZ() {
	return inpZ;
}



public SerialTest() throws Exception {
	initialize();
	Thread t=new Thread() {
	public void run() {		
	//the following line will keep this app alive for 1000 seconds,
	//waiting for events to occur and responding to them (printing incoming messages to console).
	try {Thread.sleep(1000000);} catch (InterruptedException ie) {}
	}
	};
	t.start();
	System.out.println(" Started java");
}
}
