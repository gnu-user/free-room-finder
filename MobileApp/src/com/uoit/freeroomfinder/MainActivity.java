package com.uoit.freeroomfinder;

import java.util.Locale;

import android.app.ActionBar;
import android.app.Activity;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends FragmentActivity implements
		ActionBar.TabListener {

	public static final int FREE_ROOM_TAB = 0;
	public static final int RESULTS_TAB = 1;
	public static final int ROOMS_BOOKED_TAB = 2;
	
	public static DateTimeUtility datetimeFormater;
	public static SharedPreferences sharedPrefs;
	private ProgressDialog dialog;

	public static ActionBar actionBar;
	
	/**
	 * The {@link android.support.v4.view.PagerAdapter} that will provide
	 * fragments for each of the sections. We use a
	 * {@link android.support.v4.app.FragmentPagerAdapter} derivative, which
	 * will keep every loaded fragment in memory. If this becomes too memory
	 * intensive, it may be best to switch to a
	 * {@link android.support.v4.app.FragmentStatePagerAdapter}.
	 */
	static SectionsPagerAdapter mSectionsPagerAdapter;

	/**
	 * The {@link ViewPager} that will host the section contents.
	 */
	static ViewPager mViewPager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		sharedPrefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
		
		//Set up the date time formatter
		datetimeFormater = new DateTimeUtility(sharedPrefs.getBoolean("army_clock", true),
				this.getResources().getConfiguration().locale);
		
		
		// Set up the action bar.
		actionBar = getActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

		// Create the adapter that will return a fragment for each of the three
		// primary sectiodatetimeFormaterns of the app.
		mSectionsPagerAdapter = new SectionsPagerAdapter(
				getSupportFragmentManager());

		// Set up the ViewPager with the sections adapter.
		mViewPager = (ViewPager) findViewById(R.id.pager);
		mViewPager.setAdapter(mSectionsPagerAdapter);

		// When swiping between different sections, select the corresponding
		// tab. We can also use ActionBar.Tab#select() to do this if we have
		// a reference to the Tab.
		mViewPager
				.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
					@Override
					public void onPageSelected(int position) {
						actionBar.setSelectedNavigationItem(position);
					}
				});

		// For each of the sections in the app, add a tab to the action bar.
		for (int i = 0; i < mSectionsPagerAdapter.getCount(); i++) {
			// Create a tab with text corresponding to the page title defined by
			// the adapter. Also specify this Activity object, which implements
			// the TabListener interface, as the callback (listener) for when
			// this tab is selected.
			actionBar.addTab(actionBar.newTab()
					.setText(mSectionsPagerAdapter.getPageTitle(i))
					.setTabListener(this));
		}
		
	}
	
	/**
	 * Switches the current to the specified tab. Also, it creates a
	 * new instantiation of the tab to reload ui elements. This is a
	 * bit of a hack since the layout is suppose to load the two
	 * neighbours when loading itself. However if a tab wants to
	 * switch to another to show results this breaks down. So
	 * creating a new instance of the tab and using it will
	 * facilitate this.
	 * @param tabIndex The tab index
	 * @return The new instance of the Fragment at the specified
	 * index.
	 */
	public static Fragment switchTabs(int tabIndex)
	{
		actionBar.setSelectedNavigationItem(tabIndex);
		
		return (Fragment) mSectionsPagerAdapter.instantiateItem(mViewPager, tabIndex);		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
    @Override		
    public boolean onOptionsItemSelected(final MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
            
                this.startActivity(new Intent(this, SettingsActivity.class));
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
    
    /**
     * Ensure user is logged in.
     */
    public void ensureLogin()
    {
		//This should allow for a more robust login check
		DatabaseInterface dbi = new DatabaseInterface(this.getBaseContext());
		
		if(dbi.getUser() == null)
		{
			Intent loginActivity = new Intent(this.getBaseContext(), LoginActivity.class);
			this.startActivityForResult(loginActivity, LoginActivity.LOGIN_SUCCESSFUL);
		}
    }
    
    /**
     * The method for handling a login completion.
     * @param requestCode The expected return code
     * @param resultCode The result code of the activity.
     */
    public void onLogin(int requestCode, int resultCode)
    {
		if(requestCode == LoginActivity.LOGIN_SUCCESSFUL)
		{
			if(resultCode == Activity.RESULT_CANCELED)
			{
				this.finish();
			}
		}
    }

	@Override
	public void onTabSelected(ActionBar.Tab tab,
			FragmentTransaction fragmentTransaction) {
		// When the given tab is selected, switch to the corresponding page in
		// the ViewPager.
		mViewPager.setCurrentItem(tab.getPosition());
	}

	@Override
	public void onTabUnselected(ActionBar.Tab tab,
			FragmentTransaction fragmentTransaction) {
	}

	@Override
	public void onTabReselected(ActionBar.Tab tab,
			FragmentTransaction fragmentTransaction) {
	}
	
	public void showProgress(boolean show) {
		
		if(show)
		{
			dialog = ProgressDialog.show(this,
					getString(R.string.login_heading),
					getString(R.string.login_progress_signing_in), true, true,
					new OnCancelListener() {
	
						public void onCancel(DialogInterface dialog) {
							
						}
					});
		}
		else
		{
			dialog.dismiss();
		}

	}

	/**
	 * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
	 * one of the sections/tabs/pages.
	 */
	public class SectionsPagerAdapter extends FragmentPagerAdapter {

		public SectionsPagerAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int position) {
			// getItem is called to instantiate the fragment for the given page.
			// Return a DummySectionFragment (defined as a static inner class
			// below) with the page number as its lone argument.
			Fragment fragment = null;
			Bundle args = new Bundle();
			args.putInt(FreeRoomFragment.ARG_SECTION_NUMBER, position + 1);
			
			if(position == MainActivity.FREE_ROOM_TAB)
			{
				fragment = new FreeRoom();
			}
			else if(position == MainActivity.RESULTS_TAB)
			{
				fragment = new Results();
			}
			else if(position == MainActivity.ROOMS_BOOKED_TAB)
			{
				fragment = new RoomsBooked();
			}
			
			fragment.setArguments(args);
			
			return fragment;
		}

		@Override
		public int getCount() {
			// Show 3 total pages.
			return 3;
		}

		@Override
		public CharSequence getPageTitle(int position) {
			Locale l = Locale.getDefault();
			switch (position) {
			case 0:
				return getString(R.string.title_section1).toUpperCase(l);
			case 1:
				return getString(R.string.title_section2).toUpperCase(l);
			case 2:
				return getString(R.string.title_section3).toUpperCase(l);
			}
			return null;
		}
	}
}
