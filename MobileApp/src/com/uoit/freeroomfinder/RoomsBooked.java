package com.uoit.freeroomfinder;

import java.util.ArrayList;
import java.util.Date;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

/**
 * A simple {@link android.support.v4.app.Fragment} subclass.
 * 
 */
public class RoomsBooked extends Fragment {
	
	private static long timeNow = (new Date()).getTime();
	
	
	private static final ArrayList<Rooms> results = new ArrayList<Rooms>();
	static{
		results.add(new Rooms("UA1030", timeNow, timeNow + 10000, timeNow));
		results.add(new Rooms("UA1020", timeNow, timeNow + 10000, timeNow));
		results.add(new Rooms("UA1010", timeNow, timeNow + 10000, timeNow));
	}

	public RoomsBooked() {
		// Required empty public constructor
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		
		View rootView = inflater.inflate(R.layout.rooms_booked, container, false);
		
		TableLayout tl = (TableLayout)rootView.findViewById(R.id.TableLayout1);
		
		//TODO set up to make use of the query to populate the rows
		tl.addView(SetupUpTableView(inflater, container, 0));
		tl.addView(SetupUpTableView(inflater, container, 1));
		tl.addView(SetupUpTableView(inflater, container, 2));
		
		return rootView;
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
		start.setText(DateTimeUtility.stf.format(new Date(first.getStartTime())));
		end.setText(DateTimeUtility.stf.format(new Date(first.getEndTime())));
		date.setText(DateTimeUtility.sdf.format(first.getDate()));

		TableRow tr = (TableRow)newView.findViewById(R.id.tableRow2);
		
		TableLayout tb = new TableLayout(getActivity().getBaseContext());
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
