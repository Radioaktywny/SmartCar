package Bluetooth;

import java.util.ArrayList;

public class SensorsControl extends BluetoothControl {
	private int avFuel;
	private ArrayList<Integer> lastErrors = new ArrayList<Integer>();

	public SensorsControl() {
		super(MAC);
		// TODO Auto-generated constructor stub

	}

	public int getFuelUsage() {
		return 0;
	}

	public int getOilState() {
		return 0;
	}

	public int[] getErrors() {
		return null;
	}

	public int getAvFuel() {
		return avFuel;
	}

	public ArrayList<Integer> getLastErrors() {
		return lastErrors;
	}
	
}
