package Bluetooth;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.UUID;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;

public class BluetoothControl {
	protected static String MAC="B8:27:EB:BF:C1:B0";
	private BluetoothSocket mmSocket;
	private final BluetoothDevice mmDevice;
	protected PrintWriter out;
	private boolean connected;
	public BluetoothControl(String MAC) 
	{
		this.MAC=MAC;
		BluetoothAdapter ba = BluetoothAdapter.getDefaultAdapter();	
		mmDevice = ba.getRemoteDevice(MAC);  		
			
		    BluetoothSocket tmp = null;
	        try {            
	        	UUID uuid = UUID.fromString("550e8400-e29b-41d4-a716-446655440000");
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
	}
}
