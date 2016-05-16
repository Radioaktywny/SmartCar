package Bluetooth;

import java.io.IOException;
import java.util.Date;

public class LockControl extends BluetoothControl{
	public LockControl() {
		super(MAC);
		// TODO Auto-generated constructor stub
	}
	public void openCar() throws IOException
	{
		if(isConnected())
		{
			out.println("OPEN");
//			if(bReader.ready())
//			if(!bReader.readLine().contains("OPENED"));
//			throw new IOException("ERROR");
		}
	}
	public void closeCar() throws IOException
	{
		if(isConnected())
		{
			out.println("CLOSE");
//			if(bReader.ready())
//			if(!bReader.readLine().contains("CLOSED"));
//			throw new IOException("ERROR");
		}
	}
	public void autoLocking(boolean state) throws IOException
	{
		if(isConnected())
		{
			if(state)
			out.println("LOCKING_TRUE");			
			else
			out.println("LOCKING_FALSE");
//			if(bReader.ready())
//			if(!bReader.readLine().equals("LOCKING_ACCEPT"));
//			throw new IOException("ERROR");
		}
	}
	public void childSafetyLock()
	{
		if(isConnected())
		{
			out.println("CHILDSAFETY");
		}
	}
	public String getInfo()
	{
		out.println("czas");
		try {
			return bReader.readLine();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "brak :(";

		}
	}

}
