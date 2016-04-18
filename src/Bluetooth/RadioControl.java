package Bluetooth;

public class RadioControl extends BluetoothControl{
	private boolean status;
	private int volume;
	public RadioControl() {
		super(MAC);
		// TODO Auto-generated constructor stub
	}
	public void volumeControl()
	{
		if(isConnected())
		{
			out.println("VOLUMEUP");
		}
	}
	public void playMusic()
	{
		if(isConnected())
		{
			out.println("PLAYMUSIC");
		}
	}
	public void callingMode()
	{
		if(isConnected())
		{
			out.println("CALLING");
		}
	}
	public boolean isStatus() {
		return status;
	}
	public int getVolume() {
		return volume;
	}
}
