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

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import android.app.DatePickerDialog;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;

/**
 * FreeRoom Child class of the FreeRoomFragment, containing methods, attributes, and control logic
 * for storing the main display content in the Free Room Finder.
 * 
 * @author Daniel Smullen
 * @author Joseph Heron
 * @author Jonathan Gillett
 * 
 */
public class FreeRoom extends FreeRoomFragment
{
    /**
     * The fragment argument representing the section number for this fragment.
     */
    public static final String ARG_SECTION_NUMBER = "section_number";

    /**
     * Used to store a SearchTask for searching within this fragment.
     */
    private SearchTask searchTask = null;

    /**
     * Stores shared preferences across the entire app.
     */
    private SharedPreferences sharedPrefs;
    /**
     * Stores the current date and time, as a Date object.
     */
    private Date curDate;
    /**
     * Stores a spinner object for selecting times by the hour.
     */
    private Spinner timeSpinner;

    /**
     * Room search parameters.
     */
    private static String timePicked = "";
    private static String datePicked = "";
    private static String campusPicked = "";
    private static int durationPicked = 0;

    /**
     * The root view for the various fragments in this entire view set.
     */
    private static View rootView;

    /**
     * An ArrayList storing the available rooms from a query.
     */
    public static ArrayList<Rooms> availableRooms;

    /**
     * Default empty public constructor.
     */
    public FreeRoom()
    {
        // Required empty public constructor
    }

    /*
     * (non-Javadoc)
     * 
     * @see android.support.v4.app.Fragment#onCreateView(android.view.LayoutInflater,
     * android.view.ViewGroup, android.os.Bundle)
     */
    /*
     * (non-Javadoc)
     * 
     * @see android.support.v4.app.Fragment#onCreateView(android.view.LayoutInflater,
     * android.view.ViewGroup, android.os.Bundle)
     */
    /*
     * (non-Javadoc)
     * 
     * @see android.support.v4.app.Fragment#onCreateView(android.view.LayoutInflater,
     * android.view.ViewGroup, android.os.Bundle)
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        super.onCreateView(inflater, container, savedInstanceState);

        // Add the root view container.
        rootView = inflater.inflate(R.layout.activity_free_room, container, false);

        // Load the shared preferences.
        sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this.getActivity()
                .getBaseContext());

        // Get the current date and time.
        curDate = new Date();

        // Set up default values for the display elements in the fragment.

        // Default campus selection.
        Spinner campus = (Spinner) rootView.findViewById(R.id.campus);
        campus.setSelection(Integer.valueOf(sharedPrefs.getString("default_campus", "0")) - 1);

        // Default booking duration.
        Spinner duration = (Spinner) rootView.findViewById(R.id.duration);
        duration.setSelection(Integer.valueOf(sharedPrefs.getString("default_duration", "0")) - 1);

        // Default time selection.
        timeSpinner = (Spinner) rootView.findViewById(R.id.time);
        // Set the current date to show as Today.
        TextView date = (TextView) rootView.findViewById(R.id.date);
        date.setText(DateTimeUtility.formatDate(curDate) + " (Today)");

        datePicked = DateTimeUtility.formatDate(curDate);

        // Instantiate the list of available rooms.
        availableRooms = new ArrayList<Rooms>();

        // Logic for when a date is clicked on. This initiates the custom date spinner control.
        date.setOnClickListener(new OnClickListener()
        {
            /*
             * (non-Javadoc)
             * 
             * @see android.view.View.OnClickListener#onClick(android.view.View)
             */
            @Override
            public void onClick(View v)
            {
                // parse the date that's currently in there.
                Date d = null;
                try
                {
                    d = DateTimeUtility.parseDate(datePicked);

                }
                catch (ParseException e)
                {

                    e.printStackTrace();
                }

                Calendar c = Calendar.getInstance();
                // Grab the year, month, and day from the spinners.
                if (d != null)
                {
                    c.setTime(d);
                }
                int yy = c.get(Calendar.YEAR);
                int mm = c.get(Calendar.MONTH);
                int dd = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog alert = new DatePickerDialog(rootView.getContext(),
                /**
                 * Set up the listener for when the date is set from the custom spinner alert
                 * dialog.
                 */
                new DatePickerDialog.OnDateSetListener()
                {

                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth)
                    {

                        // Parse the date - year, month, day.
                        datePicked = year + "-" + (monthOfYear + 1) + "-" + dayOfMonth;
                        TextView date = (TextView) rootView.findViewById(R.id.date);

                        // If the date is today's date, set it to Today.
                        date.setText(datePicked);
                        if (datePicked.contains(DateTimeUtility.formatDate(curDate)))
                        {
                            date.setText(datePicked + " (Today)");
                        }
                    }
                }, yy, mm, dd);

                alert.show();
            }

        });

        // Add the search buttom.
        Button search = (Button) rootView.findViewById(R.id.search);
        search.setOnClickListener(new OnClickListener()
        {

            /*
             * (non-Javadoc)
             * 
             * @see android.view.View.OnClickListener#onClick(android.view.View)
             */
            @Override
            public void onClick(View v)
            {
                // When the search button is pressed, fetch the results.
                Date curDate = new Date();

                Spinner timeSpinner = (Spinner) rootView.findViewById(R.id.time);
                Spinner durationSpinner = (Spinner) rootView.findViewById(R.id.duration);
                Spinner campusSpinner = (Spinner) rootView.findViewById(R.id.campus);

                try
                {
                    // Formulate a query based on the time. If we're using the current time, make
                    // sure we use the actual current time rather than "Now" because that's invalid.
                    if (timeSpinner.getSelectedItem().toString().compareTo("Now") == 0)
                    {
                        timePicked = DateTimeUtility.formatFullTime(DateTimeUtility
                                .formatTime(curDate));
                    }
                    else
                    {
                        timePicked = DateTimeUtility.formatFullTime(timeSpinner.getSelectedItem()
                                .toString());
                    }

                    // Add the campus we've selected to the query.
                    campusPicked = rootView.getResources().getStringArray(R.array.campus_names)[campusSpinner
                            .getSelectedItemPosition()];

                    // Add the duration of the search to the query.
                    durationPicked = Integer.valueOf(durationSpinner.getSelectedItem().toString());

                    // Formulate the search task.
                    ((MainActivity) FreeRoom.this.getActivity()).showProgress(true);
                    searchTask = new SearchTask();
                    searchTask.setOnFinshedTaskListener((Results) MainActivity
                            .switchTabs(MainActivity.RESULTS_TAB));
                    searchTask.execute();
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
        });

        return rootView;

    }

    /*
     * (non-Javadoc)
     * 
     * @see com.uoit.freeroomfinder.FreeRoomFragment#onResume()
     */
    @Override
    public void onResume()
    {
        super.onResume();

        // Set up the time spinner to include the current time.
        int timeValues = R.array.time_values;

        // Set the time to display the correct time format.
        if (!sharedPrefs.getBoolean("army_clock", true))
        {
            timeValues = R.array.army_time_values;
        }

        ArrayList<String> spinnerArray = new ArrayList<String>(Arrays.asList(this.getResources()
                .getStringArray(timeValues)));
        spinnerArray.add(0, "Now");

        ArrayAdapter<String> sa = new ArrayAdapter<String>(this.getActivity(),
                android.R.layout.simple_spinner_item, spinnerArray);
        sa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        timeSpinner.setAdapter(sa);

    }

    /**
     * SearchTask Used to formulate searches within the external data provider.
     * 
     * @author Daniel Smullen
     * @author Joseph Heron
     * @author Jonathan Gillett
     * 
     */
    public class SearchTask extends AsyncTask<Void, Void, Boolean>
    {

        /**
         * Listener for when the task is finished.
         */
        private OnFinshedTaskListener listener;

        /*
         * (non-Javadoc)
         * 
         * @see android.os.AsyncTask#doInBackground(Params[])
         */
        @Override
        protected Boolean doInBackground(Void... params)
        {
            /**
             * Get the list of available rooms and display the results
             */
            availableRooms.clear();
            availableRooms = Request.searchRooms(timePicked, datePicked, campusPicked,
                    durationPicked);

            for (Rooms room : availableRooms)
            {
                System.out.println(room.getRoom() + ", " + room.getStartTime() + ", "
                        + room.getEndTime());
            }
            return true;
        }

        /*
         * (non-Javadoc)
         * 
         * @see android.os.AsyncTask#onPostExecute(java.lang.Object)
         */
        @Override
        protected void onPostExecute(final Boolean success)
        {
            // Default method implementation.
            searchTask = null;
            ((MainActivity) FreeRoom.this.getActivity()).showProgress(false);

            if (success)
            {
                if (listener != null)
                {
                    listener.onFinishedTaskListener();
                }
            }
        }

        /*
         * (non-Javadoc)
         * 
         * @see android.os.AsyncTask#onCancelled()
         */
        @Override
        protected void onCancelled()
        {
            // Default method implementation.
            searchTask = null;
            ((MainActivity) FreeRoom.this.getActivity()).showProgress(false);
        }

        /**
         * setOnFinishedTaskListener Reroutes the task listener to the provided listener.
         * 
         * @param listener
         *            The listener to reroute the task listener to.
         */
        public void setOnFinshedTaskListener(OnFinshedTaskListener listener)
        {
            this.listener = listener;
        }
    }
}
