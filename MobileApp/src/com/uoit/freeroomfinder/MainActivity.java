/**
 * Free Room Finder (FRF)
 * Tired of rooms on campus always being in use? Fear no more the FRF is here.
 *
 * Copyright (C) 2013 Joseph Heron, Jonathan Gillett, and Daniel Smullen
 * All rights reserved.
 *
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.uoit.freeroomfinder;

import java.util.Locale;
import com.bugsense.trace.BugSenseHandler;
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

/**
 * MainActivity The main activity for the application. Shows the main user interface.
 * 
 * @author Joseph Heron
 * @author Jonathan Gillett
 * @author Daniel Smullen
 * 
 */
public class MainActivity extends FragmentActivity implements ActionBar.TabListener
{

    /**
     * The identifier for the Free Rooms tab.
     */
    public static final int FREE_ROOM_TAB = 0;
    
    /**
     * The identifier for the Results tab.
     */
    public static final int RESULTS_TAB = 1;
    
    /**
     * The identifier for the Bookings tab.
     */
    public static final int ROOMS_BOOKED_TAB = 2;

    /**
     * Used to store the shared preferences for all areas of the app.
     */
    public static SharedPreferences sharedPrefs;
    
    /**
     * Used to instantiate the progres dialog for various things.
     */
    private ProgressDialog dialog;

    /**
     * Stores the handle to the action bar.
     */
    public static ActionBar actionBar;

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide fragments for each of the
     * sections. We use a {@link android.support.v4.app.FragmentPagerAdapter} derivative, which will
     * keep every loaded fragment in memory. If this becomes too memory intensive, it may be best to
     * switch to a {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    static SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    static ViewPager mViewPager;

    /*
     * (non-Javadoc)
     * 
     * @see android.support.v4.app.FragmentActivity#onCreate(android.os.Bundle)
     */
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        
        // Start BugSense if enabled
        if (PreferenceManager.getDefaultSharedPreferences(this).getBoolean("enable_bugsense", true))
        {
            BugSenseHandler.initAndStartSession(MainActivity.this, "6e25a944");
        }
        
        setContentView(R.layout.activity_main);
        
        // Grab the shared preferences.
        sharedPrefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());

        // Set up the date time formatter.
        DateTimeUtility.setFormatLocale(sharedPrefs.getBoolean("army_clock", true), this
                .getResources().getConfiguration().locale);

        // Set up the action bar.
        actionBar = getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        // Create the adapter that will return a fragment for each of the three
        // primary tabs of the app.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        // When swiping between different sections, select the corresponding
        // tab. We can also use ActionBar.Tab#select() to do this if we have
        // a reference to the Tab.
        mViewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener()
        {
            @Override
            public void onPageSelected(int position)
            {
                actionBar.setSelectedNavigationItem(position);
            }
        });

        // For each of the sections in the app, add a tab to the action bar.
        for (int i = 0; i < mSectionsPagerAdapter.getCount(); i++)
        {
            // Create a tab with text corresponding to the page title defined by
            // the adapter. Also specify this Activity object, which implements
            // the TabListener interface, as the callback (listener) for when
            // this tab is selected.
            actionBar.addTab(actionBar.newTab().setText(mSectionsPagerAdapter.getPageTitle(i))
                    .setTabListener(this));
        }
    }

    /**
     * switchTabs Switches the current to the specified tab. Also, it creates a new instantiation of
     * the tab to reload interface elements. This is a bit of a hack since the layout is suppose to
     * load the two neighbours when loading itself. However, if a tab wants to switch to another to
     * show results this breaks down. Creating a new instance of the tab and using it will
     * facilitate this.
     * 
     * @param tabIndex The tab index for the selected tab.
     * 
     * @return The new instance of the Fragment at the specified index.
     */
    public static Fragment switchTabs(int tabIndex)
    {
        actionBar.setSelectedNavigationItem(tabIndex);
        return (Fragment) mSectionsPagerAdapter.instantiateItem(mViewPager, tabIndex);
    }

    /*
     * (non-Javadoc)
     * 
     * @see android.app.Activity#onCreateOptionsMenu(android.view.Menu)
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    /*
     * (non-Javadoc)
     * 
     * @see android.app.Activity#onOptionsItemSelected(android.view.MenuItem)
     */
    @Override
    public boolean onOptionsItemSelected(final MenuItem item)
    {
        // Default method implementation.
        switch (item.getItemId())
        {
        case R.id.action_settings:

            this.startActivity(new Intent(this, SettingsActivity.class));
            return true;

        default:
            return super.onOptionsItemSelected(item);
        }
    }

    /**
     * ensureLogin Checks whether the user is logged in, and makes sure they stay logged in.
     */
    public void ensureLogin()
    {
        // This should allow for a more robust login check by using the database interface.
        DatabaseInterface dbi = new DatabaseInterface(this.getBaseContext());

        if (dbi.getUser() == null)
        {
            Intent loginActivity = new Intent(this.getBaseContext(), LoginActivity.class);
            this.startActivityForResult(loginActivity, LoginActivity.LOGIN_SUCCESSFUL);
        }
    }

    /**
     * onLogin The method for handling a login completion.
     * 
     * @param requestCode The expected return code for the request.
     * @param resultCode The result code of the activity.
     */
    public void onLogin(int requestCode, int resultCode)
    {
        if (requestCode == LoginActivity.LOGIN_SUCCESSFUL)
        {
            if (resultCode == Activity.RESULT_CANCELED)
            {
                this.finish();
            }
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see android.app.ActionBar.TabListener#onTabSelected(android.app.ActionBar.Tab,
     * android.app.FragmentTransaction)
     */
    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction)
    {
        // When the given tab is selected, switch to the corresponding page in
        // the ViewPager.
        mViewPager.setCurrentItem(tab.getPosition());
    }

    /*
     * (non-Javadoc)
     * 
     * @see android.app.ActionBar.TabListener#onTabUnselected(android.app.ActionBar.Tab,
     * android.app.FragmentTransaction)
     */
    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction)
    {
        // Not implemented. Provided to satisfy interface.
    }

    /*
     * (non-Javadoc)
     * 
     * @see android.app.ActionBar.TabListener#onTabReselected(android.app.ActionBar.Tab,
     * android.app.FragmentTransaction)
     */
    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction)
    {
        // Not implemented. Provided to satisfy interface.
    }

    /**
     * showProgress Displays the progress dialog.
     * 
     * @param show Shows the progress dialog if true. If false, don't show it.
     */
    public void showProgress(boolean show)
    {
        if (show)
        {
            dialog = ProgressDialog.show(this, getString(R.string.login_heading),
                    getString(R.string.login_progress_signing_in), true, true,
                    new OnCancelListener()
                    {
                        public void onCancel(DialogInterface dialog)
                        {

                        }
                    });
        }
        else
        {
            dialog.dismiss();
        }

    }

    /**
     * SectionsPagerAdapter A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     * 
     * @author Daniel Smullen
     * @author Joseph Heron
     * @author Jonathan Gillett
     * 
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter
    {
        /**
         * Default constructor. Runs the superclass constructor.
         * 
         * @param fm
         *            The FragmentManager to use.
         */
        public SectionsPagerAdapter(FragmentManager fm)
        {
            super(fm);
        }

        /*
         * (non-Javadoc)
         * 
         * @see android.support.v4.app.FragmentPagerAdapter#getItem(int)
         */
        @Override
        public Fragment getItem(int position)
        {
            // getItem is called to instantiate the fragment for the given page.
            // Return a DummySectionFragment (defined as a static inner class
            // below) with the page number as its lone argument.
            Fragment fragment = null;
            Bundle args = new Bundle();
            args.putInt(FreeRoomFragment.ARG_SECTION_NUMBER, position + 1);

            if (position == MainActivity.FREE_ROOM_TAB)
            {
                fragment = new FreeRoom();
            }
            else if (position == MainActivity.RESULTS_TAB)
            {
                fragment = new Results();
            }
            else if (position == MainActivity.ROOMS_BOOKED_TAB)
            {
                fragment = new RoomsBooked();
            }

            fragment.setArguments(args);
            return fragment;
        }

        /*
         * (non-Javadoc)
         * 
         * @see android.support.v4.view.PagerAdapter#getCount()
         */
        @Override
        public int getCount()
        {
            // Show 3 pages only, there are only 3 tabs..
            return 3;
        }

        /*
         * (non-Javadoc)
         * 
         * @see android.support.v4.view.PagerAdapter#getPageTitle(int)
         */
        @Override
        public CharSequence getPageTitle(int position)
        {
            Locale l = Locale.getDefault();
            
            switch (position)
            {
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
