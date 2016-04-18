package com.example.smartcar;

import java.io.IOException;
import java.io.PrintWriter;

import Bluetooth.BluetoothControl;
import Bluetooth.LockControl;
import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.Activity;
import android.app.FragmentTransaction;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class SwipActivity extends FragmentActivity implements
ActionBar.TabListener{

	/**
	 * The {@link android.support.v4.view.PagerAdapter} that will provide
	 * fragments for each of the sections. We use a {@link FragmentPagerAdapter}
	 * derivative, which will keep every loaded fragment in memory. If this
	 * becomes too memory intensive, it may be best to switch to a
	 * {@link android.support.v13.app.FragmentStatePagerAdapter}.
	 */
	
	/**
	 * The {@link ViewPager} that will host the section contents.
	 */
	private PrintWriter out;
	private ActionBar actionBar;
	private TabsPagerAdapter mAdapter;
	private ViewPager viewPager;
	private String[] tabs = { "Main", "States" };
		LockControl b;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.swip_main);

		// Create the adapter that will return a fragment for each of the three
		// primary sections of the activity.
		

		// Set up the ViewPager with the sections adapter.
		viewPager = (ViewPager) findViewById(R.id.pager);
		actionBar = getActionBar();
		mAdapter = new TabsPagerAdapter(getSupportFragmentManager());

		viewPager.setAdapter(mAdapter);
		actionBar.setHomeButtonEnabled(false);
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);	
		BluetoothAdapter ba = BluetoothAdapter.getDefaultAdapter();
		Log.d("SwipActivity.onCreate()","Twój adres urzadzenia: "+ba.getAddress());
		
		if(!ba.isEnabled()){
			Intent i=new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
			startActivityForResult(i, 1);
		}
		// Adding Tabs
		for (String tab_name : tabs) {
			actionBar.addTab(actionBar.newTab().setText(tab_name)
					.setTabListener(this));
		}

		/**
		 * on swiping the viewpager make respective tab selected
		 * */
		viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

			public void onPageSelected(int position) {
				// on changing the page
				// make respected tab selected
				actionBar.setSelectedNavigationItem(position);
			}

			public void onPageScrolled(int arg0, float arg1, int arg2) {
			}

			public void onPageScrollStateChanged(int arg0) {
			}
		});
	}

	public void onTabReselected(Tab tab, FragmentTransaction ft) {
	}

	public void onTabSelected(Tab tab, FragmentTransaction ft) {
		// on tab selected
		// show respected fragment view
		viewPager.setCurrentItem(tab.getPosition());
	}

	public void onTabUnselected(Tab tab, FragmentTransaction ft) {
	}
	public void polacz(View view)
	{
		b=new LockControl();
		b.openCar();
		b.closeCar();
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	protected void onActivityResult(int requestCode,int resultCode,Intent i){
		if(resultCode==Activity.RESULT_OK){
			Log.d("SwipActivity.onresult","Mamy zgodê!");
			BluetoothAdapter ba = BluetoothAdapter.getDefaultAdapter();					
		}
	}

}
