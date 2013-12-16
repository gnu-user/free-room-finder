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

import java.util.ArrayList;
import java.util.Date;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

/**
 * Results A simple {@link android.support.v4.app.Fragment} subclass. Activities that contain this
 * fragment must implement the {@link Results.OnFragmentInteractionListener} interface to handle
 * interaction events. Use the {@link Results#newInstance} factory method to create an instance of
 * this fragment.
 * 
 * @author Joseph Heron
 * @author Jonathan Gillett
 * @author Daniel Smullen
 * 
 */
public class Results extends FreeRoomFragment implements OnFinshedTaskListener
{
    /**
     * User interface state parameters.
     */
    public static QueryTask loadTask = null;
    private static RadioButton checked = null;
    private static int indexOfChecked = 0;
    private static boolean isNotChecked = true;

    /**
     * A handle to the book button.
     */
    private static Button book;

    /**
     * Standard view objects for our layout.
     */
    private LayoutInflater inflater;
    private ViewGroup container;
    private TableLayout tl;

    /**
     * A handle to the header.
     */
    private TableRow header;

    /**
     * Used to store the query results.
     */
    public static ArrayList<Rooms> results;

    /**
     * Default constructor.
     */
    public Results()
    {
        // Not implemented
    }

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

        results = FreeRoom.availableRooms;

        View rootView = inflater.inflate(R.layout.fragment_results, container, false);

        this.inflater = inflater;
        this.container = container;

        tl = (TableLayout) rootView.findViewById(R.id.TableLayout1);

        book = (Button) rootView.findViewById(R.id.book);

        book.setEnabled(false);

        book.setOnClickListener(new OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                // Redundancy check
                if (indexOfChecked >= 0 && results.size() > 0)
                {
                    // Add booking to database
                    DatabaseInterface dbi = new DatabaseInterface(Results.this.getActivity()
                            .getBaseContext());
                    dbi.insertBooking(results.get(indexOfChecked));

                    QueryTask task = new QueryTask();
                    task.setOnFinshedTaskListener((RoomsBooked) MainActivity
                            .switchTabs(MainActivity.ROOMS_BOOKED_TAB));
                    task.execute(Results.this.getActivity().getBaseContext());
                }
            }
        });
        header = (TableRow) tl.findViewById(R.id.table_header);

        return rootView;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.uoit.freeroomfinder.OnFinshedTaskListener#onFinishedTaskListener()
     */
    public void onFinishedTaskListener()
    {
        // Once the retrieval of the rooms is done, populate the array.
        if (this.getView() != null)
        {
            results = FreeRoom.availableRooms;
            refreshList();
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.uoit.freeroomfinder.FreeRoomFragment#onResume()
     */
    public void onResume()
    {
        super.onResume();

        // Load the queries.
        loadTask = new QueryTask();
        loadTask.setOnFinshedTaskListener(this);
        loadTask.execute(this.getActivity().getBaseContext());
    }

    /**
     * refreshList Update the list of bookable rooms.
     */
    public void refreshList()
    {
        // Delete all children for the table.
        tl = (TableLayout) this.getView().findViewById(R.id.TableLayout1);
        tl.removeAllViews();

        // Add the table header back.
        tl.addView(header);

        // Populate the table.
        for (int i = 0; i < results.size(); i++)
        {
            tl.addView(SetupUpTableView(inflater, container, i));
        }
    }

    /**
     * SetupUpTableView Set all the default values for the table view and instantiate handles to the
     * interface elements.
     * 
     * @param inflater The inflater for the layout.
     * @param container The ViewGroup container for the view.
     * @param index The index for the table view.
     * 
     * @return Returns a handle to the first table row.
     */
    public TableRow SetupUpTableView(LayoutInflater inflater, ViewGroup container, final int index)
    {
        View newView = inflater.inflate(R.layout.room_item, container, false);

        TextView room = (TextView) newView.findViewById(R.id.room);
        TextView start = (TextView) newView.findViewById(R.id.stime);
        TextView end = (TextView) newView.findViewById(R.id.etime);
        RadioButton ch = (RadioButton) newView.findViewById(R.id.radio_button);

        // Provides the logic for the selectable table rows.
        ch.setOnClickListener(new OnClickListener()
        {
            /*
             * (non-Javadoc)
             * 
             * @see android.view.View.OnClickListener#onClick(android.view.View)
             */
            @Override
            public void onClick(View v)
            {
                RadioButton rb = (RadioButton) v;

                // A different row has been selected.
                if (checked != rb)
                {
                    // Check if it's been checked the first time.
                    if (checked != null)
                    {
                        checked.setChecked(false);
                    }
                    checked = rb;
                    rb.setChecked(true);
                    isNotChecked = false;
                }
                // The same row has been selected.
                else if (checked == rb)
                {
                    rb.setChecked(isNotChecked);
                    checked = rb;
                    v = (View) rb;
                    isNotChecked = !isNotChecked;
                }

                // Prepare the button if a row is selected, otherwise disable it.
                if (isNotChecked)
                {
                    book.setEnabled(false);
                    indexOfChecked = -1;
                }
                else
                {
                    indexOfChecked = index;
                    book.setEnabled(true);
                }

                rb.refreshDrawableState();
            }
        });

        // Grab the results and format the dates according to the values therein.
        Rooms first = results.get(index);

        room.setText(first.getRoom());
        start.setText(DateTimeUtility.formatTime(new Date(first.getStartTime())));
        end.setText(DateTimeUtility.formatTime(new Date(first.getEndTime())));

        TableRow tr = (TableRow) newView.findViewById(R.id.room_row);

        // Provide the logic for selecting a table row.
        tr.setOnClickListener(new OnClickListener()
        {
            /*
             * (non-Javadoc)
             * 
             * @see android.view.View.OnClickListener#onClick(android.view.View)
             */
            @Override
            public void onClick(View v)
            {
                TableRow d = (TableRow) v;
                
                // Note the location of the relative layout is hard coded here as the last element in the table row.
                // Also, the radio button is hard coded here as the first (and only) element in the  relative layout.
                ((RelativeLayout) d.getChildAt(d.getChildCount() - 1)).getChildAt(0).performClick();
            }

        });

        return tr;
    }
}