package com.uoit.freeroomfinder;

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
 * A simple {@link android.support.v4.app.Fragment} subclass. Activities that
 * contain this fragment must implement the
 * {@link Results.OnFragmentInteractionListener} interface to handle interaction
 * events. Use the {@link Results#newInstance} factory method to create an
 * instance of this fragment.
 * 
 */
public class Results extends FreeRoomFragment implements OnFinshedTaskListener {
	
    public static QueryTask loadTask = null;
	private static RadioButton checked = null;
	private static int indexOfChecked = 0;
	private static boolean isNotChecked = true;
	
	private static Button book;
	
	private LayoutInflater inflater;
	private ViewGroup container;
	private TableLayout tl;
	
	private TableRow header;
	
	public Results() {}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		super.onCreateView(inflater, container, savedInstanceState);
		
		View rootView = inflater.inflate(R.layout.fragment_results,
				container, false);
		
		this.inflater = inflater;
		this.container = container;
		
		tl = (TableLayout)rootView.findViewById(R.id.TableLayout1);
		
		book = (Button)rootView.findViewById(R.id.book);
		
		book.setEnabled(false);
		
		book.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				
				//Redundancy check
				if(indexOfChecked >= 0)
				{
					//Add to db
					DatabaseInterface dbi = new DatabaseInterface(Results.this.getActivity().getBaseContext());
					dbi.insertBooking(FreeRoom.availableRooms.get(indexOfChecked));

					
					QueryTask task = new QueryTask();
					task.setOnFinshedTaskListener((RoomsBooked)MainActivity
							.switchTabs(MainActivity.ROOMS_BOOKED_TAB));
					task.execute(Results.this.getActivity().getBaseContext());
					
					//TODO Tell server that, if you want
				}
			}
			
		});
		header = (TableRow)tl.findViewById(R.id.table_header);
		
		
			
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
		for(int i = 0; i < FreeRoom.availableRooms.size(); i++)
		{
			tl.addView(SetupUpTableView(inflater, container, i));
		}

	}

	public TableRow SetupUpTableView(LayoutInflater inflater, ViewGroup container, final int index)
	{
		View newView = inflater.inflate(R.layout.room_item,
				container, false);
		
		TextView room = (TextView)newView.findViewById(R.id.room);
		TextView start = (TextView)newView.findViewById(R.id.stime);
		TextView end = (TextView)newView.findViewById(R.id.etime);
		RadioButton ch = (RadioButton)newView.findViewById(R.id.radio_button);
		
		ch.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				RadioButton rb = (RadioButton)v;

				// A different row has been selected
				if(checked != rb)
				{
					//First time
					if(checked != null)
					{
						checked.setChecked(false);
					}
					checked = rb;
					rb.setChecked(true);
					isNotChecked = false;
				}
				//The same row has been selected
				else if(checked == rb)
				{
					rb.setChecked(isNotChecked);
					checked = rb;
					v = (View) rb;
					isNotChecked = !isNotChecked;
				}
				
				//Prepare the button if a row is select otherwise disable it
				if(isNotChecked)
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
		
		Rooms first = FreeRoom.availableRooms.get(index);
		
		room.setText(first.getRoom());
		start.setText(MainActivity.datetimeFormater.formatTime(new Date(first.getStartTime())));
		end.setText(MainActivity.datetimeFormater.formatTime(new Date(first.getEndTime())));

		TableRow tr = (TableRow)newView.findViewById(R.id.room_row);
		
		tr.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				TableRow d= (TableRow) v;
				// Note the location of the relative layout is hard coded here as the last element in the table row
				// Also, the radio button is hard coded here as the first (and only) element in the relative layout
				((RelativeLayout) d.getChildAt(d.getChildCount()-1)).getChildAt(0).performClick();
			}
			
		});
		
		return tr;
	}
}