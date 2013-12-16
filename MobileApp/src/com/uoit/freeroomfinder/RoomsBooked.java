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
import android.widget.Toast;

/**
 * A simple {@link android.support.v4.app.Fragment} subclass.
 * 
 */
public class RoomsBooked extends FreeRoomFragment implements OnFinshedTaskListener{
	
	public static QueryTask loadTask = null;
	private TableLayout tl;
	private TableRow header;
	private LayoutInflater inflater;
	private ViewGroup container;
	private ActionMode mActionMode = null;
	
	private TableRow selectedRow = null;
	private Drawable background = null;

	
	public static ArrayList<Rooms> results = new ArrayList<Rooms>();

	public RoomsBooked() {
		// Required empty public constructor	
	}

	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		
		View rootView = inflater.inflate(R.layout.rooms_booked, container, false);
				
		this.inflater = inflater;
		this.container = container;
		
		tl = (TableLayout)rootView.findViewById(R.id.TableLayout1);
		header = (TableRow)rootView.findViewById(R.id.header_header);
		
		//TODO create menu option for share
		//TODO implement long click for each row (to mark for share or delete)
		
		return rootView;
	}
	
	public void onFinishedTaskListener()
	{
		if(this.getView() != null)
		{
			refreshList();
		}
	}
	
	public void onResume()
	{
		super.onResume();
		
		loadTask = new QueryTask();
		loadTask.setOnFinshedTaskListener(this);
        loadTask.execute(this.getActivity().getBaseContext());
	}
	
	public void refreshList()
	{
		// Delete all children for the table
		
		tl = (TableLayout)this.getView().findViewById(R.id.TableLayout1);
		tl.removeAllViews();

		
		// Add the table header back 
		tl.addView(header);
		
		// Populate the table
		for(int i = 0; i < results.size(); i++)
		{
			tl.addView(SetupUpTableView(inflater, container, i));
		}
	}
	
	public TableRow SetupUpTableView(LayoutInflater inflater, ViewGroup container, int index)
	{
		View newView = inflater.inflate(R.layout.room_book_item,
				container, false);
		
		TextView room = (TextView)newView.findViewById(R.id.book_room);
		TextView start = (TextView)newView.findViewById(R.id.book_stime);
		TextView end = (TextView)newView.findViewById(R.id.book_etime);
		TextView date = (TextView)newView.findViewById(R.id.book_date);
		
		Rooms first = results.get(index);
		
		room.setText(first.getRoom());
		start.setText(DateTimeUtility.formatTime(new Date(first.getStartTime())));
		end.setText(DateTimeUtility.formatTime(new Date(first.getEndTime())));
		date.setText(DateTimeUtility.formatDate(first.getDate()));

		TableRow tr = (TableRow)newView.findViewById(R.id.tableRow2);
		
		//TableLayout tb = new TableLayout(getActivity().getBaseContext());
		tr.setOnLongClickListener(new View.OnLongClickListener() {
		    // Called when the user long-clicks on someView
		    public boolean onLongClick(View view) {
		    	
		        if (mActionMode != null) {
		            return false;
		        }
		
		        // Set the row as highlighted.
		        selectedRow = (TableRow)view;
		        background = selectedRow.getBackground();
		        selectedRow.setBackgroundColor(RoomsBooked.this.getResources()
		        		.getColor(android.R.color.holo_blue_light));
		        
		        // Start the CAB using the ActionMode.Callback defined above
		        mActionMode = getActivity().startActionMode(mActionModeCallback);
		        view.setSelected(true);
		        return true;
		    }
		});
		this.registerForContextMenu(tr);

		return tr;
	}
	
	private ActionMode.Callback mActionModeCallback = new ActionMode.Callback() {

	    // Called when the action mode is created; startActionMode() was called
	    @Override
	    public boolean onCreateActionMode(ActionMode mode, Menu menu) {
	        // Inflate a menu resource providing context menu items
	        MenuInflater inflater = mode.getMenuInflater();
	        inflater.inflate(R.menu.context_menu, menu);
	        return true;
	    }

	    // Called each time the action mode is shown. Always called after onCreateActionMode, but
	    // may be called multiple times if the mode is invalidated.
	    @Override
	    public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
	        return false; // Return false if nothing is done
	    }

	    // Called when the user selects a contextual menu item
	    @Override
	    public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
	    	
	    	selectedRow.setBackground(background);
	    	selectedRow = null;
	        switch (item.getItemId()) {
	            case R.id.share:
	                //shareCurrentItem();
	            	Toast.makeText(RoomsBooked.this.getActivity(), "SHARING IS CARING!", Toast.LENGTH_LONG).show();
	                mode.finish(); // Action picked, so close the CAB
	                return true;
	            case R.id.delete:
	                //shareCurrentItem();
	                mode.finish(); // Action picked, so close the CAB
	                return true;
	            default:
	                return false;
	        }
	    }

	    // Called when the user exits the action mode
	    @Override
	    public void onDestroyActionMode(ActionMode mode) {
	        mActionMode = null;
	    }
	};


}
