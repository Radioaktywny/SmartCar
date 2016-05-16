package com.example.smartcar;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

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
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

public class SwipActivity extends FragmentActivity implements ActionBar.TabListener {

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
		Log.d("SwipActivity.onCreate()", "Twój adres urzadzenia: " + ba.getAddress());

		if (!ba.isEnabled()) {
			Intent i = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
			startActivityForResult(i, 1);
		}
		// Adding Tabs
		for (String tab_name : tabs) {
			actionBar.addTab(actionBar.newTab().setText(tab_name).setTabListener(this));
		}

		/**
		 * on swiping the viewpager make respective tab selected
		 */
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

	public void polacz(View view) {
		final ToggleButton bt = (ToggleButton) findViewById(R.id.connectBT);
		bt.setChecked(!bt.isChecked());
		if (b == null)
			b = new LockControl();

		if (!bt.isChecked()) {
			Thread t = new Thread((new Runnable() {
				@Override
				public void run() {
					// while (true)
					try {
						if (!b.isConnected()) {
							b.connect();
							BluetoothControl.connected = true;
							Log.d("INFO", "polacz() : polaczyl sie ");
						}
						// break;
					} catch (IOException es) {
						BluetoothControl.connected = false;
						es.printStackTrace();
						showToast("nie udalo sie po³¹czyæ z samochodem");
						runOnUiThread(new Runnable() {
							@Override
							public void run() {
								bt.setChecked(false);
							}
						});
					}
				}
				// TODO Auto-generated method stub
			}));
			t.start();
		} else {
			try {
				Log.d("INFO", "polacz() : ROZLACZYLEM");
				b.disconnect();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	private void showToast(final String informacja) {
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				android.widget.Toast info = android.widget.Toast.makeText(SwipActivity.this, informacja,
						Toast.LENGTH_SHORT);
				info.setGravity(Gravity.CENTER, 0, 0);
				info.show();
			}
		});
	}

	public void openClose(View view) {
		final ToggleButton bt = (ToggleButton) findViewById(R.id.openClose);
		bt.setChecked(!bt.isChecked());
		if (b.isConnected()) {
			final TextView tv = (TextView) findViewById(R.id.doorStatus);
			if (!bt.isSelected()) {
				new Thread(new Runnable() {
					@Override
					public void run() {
						try {
							b.openCar();
							runOnUiThread(new Runnable() {
								@Override
								public void run() {
									bt.setChecked(true);
									tv.setText("Drzwi otwarte");
								}
							});
						} catch (IOException e) {
							showToast(
									"nast¹pi³ b³¹d przy otwieraniu drzwi, sprawdŸ swoje po³¹czenie z samochodem i spróbuj ponownie.");
							e.printStackTrace();
						}

					}
				}).start();

			} else {
				new Thread(new Runnable() {
					@Override
					public void run() {
						try {
							b.closeCar();
							runOnUiThread(new Runnable() {
								@Override
								public void run() {
									bt.setChecked(false);
									tv.setText("Drzwi zamkniête");
								}
							});
						} catch (IOException e) {
							showToast(
									"nast¹pi³ b³¹d przy otwieraniu drzwi, sprawdŸ swoje po³¹czenie z samochodem i spróbuj ponownie.");
							e.printStackTrace();
						}

					}
				}).start();
			}
		} else {
			showToast("Najpierw po³¹cz siê z samochodem, by móc sterowaæ zamkami");
		}

	}

	public void autolocking(View view) {
		final ToggleButton bt = (ToggleButton) findViewById(R.id.autoLocking);
		bt.setChecked(!bt.isChecked());
		if (b == null)
			b = new LockControl();
		if (b.isConnected()) {
			if (!bt.isChecked()) {
					try {
						b.autoLocking(true);
						runOnUiThread(new Runnable() {
							@Override
							public void run() {
								bt.setChecked(true);								
							}
						});
					} catch (IOException e) {
						// TODO Auto-generated catch block						
						e.printStackTrace();
						showToast("Nie udalo siê zmieniæ autoblokowania zamków");
					}
			} else {
					try {
						b.autoLocking(false);						
						runOnUiThread(new Runnable() {
							@Override
							public void run() {
								bt.setChecked(false);
							}
						});
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						showToast("Nie udalo siê zmieniæ autoblokowania zamków");
					}
			}
		} else {
			showToast("Najpierw po³¹cz siê z samochodem, by móc sterowaæ zamkami");
		}

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

	protected void onActivityResult(int requestCode, int resultCode, Intent i) {
		if (resultCode == Activity.RESULT_OK) {
			Log.d("SwipActivity.onresult", "Mamy zgodê!");
			BluetoothAdapter ba = BluetoothAdapter.getDefaultAdapter();
		}
	}

}
