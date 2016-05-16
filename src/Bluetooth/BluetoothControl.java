package Bluetooth;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.UUID;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;

public class BluetoothControl {
	protected static String MAC="B8:27:EB:8D:1A:37";
	private BluetoothSocket mmSocket;
	private final BluetoothDevice mmDevice;
	protected PrintWriter out;
	public static boolean connected;
	public BluetoothControl(String MAC) 
	{
		//this.MAC=MAC;
		BluetoothAdapter ba = BluetoothAdapter.getDefaultAdapter();	
		mmDevice = ba.getRemoteDevice(MAC);  		
			
		    BluetoothSocket tmp = null;
	        try {            
	        	String uid = "00001101-0000-1000-8000-00805F9B34FB";

	        	UUID uuid = UUID.fromString(uid);
	            tmp = mmDevice.createRfcommSocketToServiceRecord(uuid);
	        } catch (Exception e) {e.printStackTrace(); }
	        mmSocket = tmp;
	    
		// TODO Auto-generated constructor stub
	}
	

	public boolean isConnected()
	{
		return connected;
	}
	public BluetoothSocket connect() throws IOException
	{
		BluetoothAdapter.getDefaultAdapter();
        mmSocket.connect();
        connected=true;
        out=new PrintWriter(mmSocket.getOutputStream(),true);
        return mmSocket;		
	}
	public void disconnect() throws IOException
	{
		mmSocket.close();
		connected=false;
	}
}
