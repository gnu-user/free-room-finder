package com.uoit.freeroomfinder;

import java.util.ArrayList;
import java.util.Date;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

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
		start.setText(MainActivity.datetimeFormater.formatTime(new Date(first.getStartTime())));
		end.setText(MainActivity.datetimeFormater.formatTime(new Date(first.getEndTime())));
		date.setText(MainActivity.datetimeFormater.formatDate(first.getDate()));

		TableRow tr = (TableRow)newView.findViewById(R.id.tableRow2);
		
		//TableLayout tb = new TableLayout(getActivity().getBaseContext());
		tr.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				
				//TODO set index as the identifier for the row.
				
				//Toast.makeText(RoomsBooked.this.getActivity().getBaseContext(), "HERE", Toast.LENGTH_LONG).show();
				//TableRow d= (TableRow) v;
				
				// Note the location of the relative layout is hard coded here as the last element in the table row
				// Also, the radio button is hard coded here as the first (and only) element in the relative layout
				//((RelativeLayout) d.getChildAt(d.getChildCount()-1)).getChildAt(0).performClick();
			}
		});
		
		return tr;
	}
	
    
	


}
