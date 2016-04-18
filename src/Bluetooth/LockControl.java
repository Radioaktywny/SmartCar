package Bluetooth;

public class LockControl extends BluetoothControl{
	public LockControl() {
		super(MAC);
		// TODO Auto-generated constructor stub
	}
	public void openCar()
	{
		if(isConnected())
		{
			out.println("OPEN");
		}
	}
	public void closeCar()
	{
		if(isConnected())
		{
			out.println("CLOSE");
		}
	}
	public void autoLocking()
	{
		
	}
	public void childSafetyLock()
	{
		if(isConnected())
		{
			out.println("CHILDSAFETY");
		}
	}

}
