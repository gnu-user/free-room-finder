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
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

/**
 * RoomsBooked A simple {@link android.support.v4.app.Fragment} subclass.
 * 
 * @author Daniel Smullen
 * @author Joseph Heron
 * @author Jonathan Gillett
 * 
 */
public class RoomsBooked extends FreeRoomFragment implements OnFinshedTaskListener
{

    /**
     * The query task for loading the booked rooms.
     */
    public static QueryTask loadTask = null;
    /**
     * Handles to the UI elements in the fragment.
     */
    private TableLayout tl;
    private TableRow header;
    private LayoutInflater inflater;
    private ViewGroup container;
    private ActionMode mActionMode = null;

    private TableRow selectedRow = null;
    private Drawable background = null;
    private int rowIndex = 0;

    /**
     * The list of rooms that are booked.
     */
    public static ArrayList<Rooms> results = new ArrayList<Rooms>();

    /**
     * Default constructor. Not implemented.
     */
    public RoomsBooked()
    {
        // Required empty public constructor.
    }

    /*
     * (non-Javadoc)
     * 
     * @see android.support.v4.app.Fragment#onCreate(android.os.Bundle)
     */
    public void onCreate(Bundle savedInstanceState)
    {
        // Default implementation.
        super.onCreate(savedInstanceState);
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

        // Inflate the layout for this fragment.
        View rootView = inflater.inflate(R.layout.rooms_booked, container, false);

        this.inflater = inflater;
        this.container = container;

        tl = (TableLayout) rootView.findViewById(R.id.TableLayout1);
        header = (TableRow) rootView.findViewById(R.id.header_header);

        return rootView;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.uoit.freeroomfinder.OnFinshedTaskListener#onFinishedTaskListener()
     */
    public void onFinishedTaskListener()
    {
        // Refresh the list of the data retrieval is done.
        if (this.getView() != null)
        {
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

        // Load the query on resume.
        loadTask = new QueryTask();
        loadTask.setOnFinshedTaskListener(this);
        loadTask.execute(this.getActivity().getBaseContext());
    }

    /**
     * refreshList Refreshes the list of booking entries in the table.
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
     * SetupUpTableView Sets up the logic for handling what happens inside the table of booking
     * entries.
     * 
     * @param inflater
     *            The specified inflater for the view.
     * 
     * @param container
     *            The ViewGroup container for the view.
     * 
     * @param index
     *            The index of the selected row.
     * 
     * @return Returns the corresponding table row that was selected.
     */
    public TableRow SetupUpTableView(LayoutInflater inflater, ViewGroup container, final int index)
    {
        // Get all the handles to the UI elements inside the view.
        View newView = inflater.inflate(R.layout.room_book_item, container, false);

        TextView room = (TextView) newView.findViewById(R.id.book_room);
        TextView start = (TextView) newView.findViewById(R.id.book_stime);
        TextView end = (TextView) newView.findViewById(R.id.book_etime);
        TextView date = (TextView) newView.findViewById(R.id.book_date);

        // Get the index of the result.
        Rooms first = results.get(index);

        // Get the attributes of this booking.
        room.setText(first.getRoom());
        start.setText(DateTimeUtility.formatTime(new Date(first.getStartTime())));
        end.setText(DateTimeUtility.formatTime(new Date(first.getEndTime())));
        date.setText(DateTimeUtility.formatDate(first.getDate()));

        TableRow tr = (TableRow) newView.findViewById(R.id.tableRow2);

        tr.setOnLongClickListener(new View.OnLongClickListener()
        {

            /*
             * (non-Javadoc)
             * 
             * @see android.view.View.OnLongClickListener#onLongClick(android.view.View)
             */
            public boolean onLongClick(View view)
            {
                // Called when the user long-clicks on a View.
                if (mActionMode != null)
                {
                    return false;
                }

                // Set the row as highlighted.
                selectedRow = (TableRow) view;
                background = selectedRow.getBackground();
                selectedRow.setBackgroundColor(RoomsBooked.this.getResources().getColor(
                        android.R.color.holo_blue_light));
                rowIndex = index;

                // Start the CAB using the ActionMode. Callback defined above.
                mActionMode = getActivity().startActionMode(mActionModeCallback);
                view.setSelected(true);
                return true;
            }
        });
        this.registerForContextMenu(tr);

        return tr;
    }

    /**
     * Used as the callback for the click.
     */
    private ActionMode.Callback mActionModeCallback = new ActionMode.Callback()
    {

        // Called when the action mode is created; startActionMode() was called.
        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu)
        {
            // Inflate a menu resource providing context menu items.
            MenuInflater inflater = mode.getMenuInflater();
            inflater.inflate(R.menu.context_menu, menu);
            return true;
        }

        /*
         * (non-Javadoc)
         * 
         * @see android.view.ActionMode.Callback#onPrepareActionMode(android.view.ActionMode,
         * android.view.Menu)
         */
        @Override
        public boolean onPrepareActionMode(ActionMode mode, Menu menu)
        {
            // Called each time the action mode is shown. Always called after onCreateActionMode,
            // but
            // may be called multiple times if the mode is invalidated.
            return false; // Return false if nothing is done.
        }

        /*
         * (non-Javadoc)
         * 
         * @see android.view.ActionMode.Callback#onActionItemClicked(android.view.ActionMode,
         * android.view.MenuItem)
         */
        @Override
        public boolean onActionItemClicked(ActionMode mode, MenuItem item)
        {
            // Called when the user selects a contextual menu item.
            selectedRow.setBackground(background);
            selectedRow = null;
            switch (item.getItemId())
            {
            case R.id.share:
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);

                // Format this according to the correct output.
                String message = getString(R.string.send_booking_room)
                        + " "
                        + results.get(rowIndex).getRoom()
                        + " "
                        + getString(R.string.send_booking_start)
                        + " "
                        + DateTimeUtility
                                .formatTime(new Date(results.get(rowIndex).getStartTime())) + " "
                        + getString(R.string.send_booking_end) + " "
                        + DateTimeUtility.formatTime(new Date(results.get(rowIndex).getEndTime()))
                        + " " + getString(R.string.send_booking_date) + " "
                        + DateTimeUtility.formatDate(results.get(rowIndex).getDate());
                sendIntent.putExtra(Intent.EXTRA_TEXT, message);
                sendIntent.setType("text/plain");
                startActivity(sendIntent);
                rowIndex = -1;

                mode.finish();
                // Action picked, so close the CAB.
                return true;
            case R.id.delete:
                DatabaseInterface dbi = new DatabaseInterface(getActivity().getBaseContext());
                dbi.deleteBooking(results.get(rowIndex).getId());
                results.remove(rowIndex);
                rowIndex = -1;
                refreshList();

                mode.finish();
                // Action picked, so close the CAB.
                return true;
            default:
                return false;
            }
        }

        /*
         * (non-Javadoc)
         * 
         * @see android.view.ActionMode.Callback#onDestroyActionMode(android.view.ActionMode)
         */
        @Override
        public void onDestroyActionMode(ActionMode mode)
        {
            // Called when the user exits the action mode.
            mActionMode = null;
        }
    };
}
