package com.uoit.freeroomfinder;

import java.util.ArrayList;
import java.util.Date;

import android.os.Bundle;
import android.support.v4.app.Fragment;
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
public class Results extends Fragment {
	
	private static RadioButton checked = null;
	private static boolean isNotChecked = true;
	private static long timeNow = (new Date()).getTime();
	
	private static Button book;
	
	private LayoutInflater inflater;
	private ViewGroup container;
	private TableLayout tl;
	
	
	private static final ArrayList<Rooms> results = new ArrayList<Rooms>();
	static{
		results.add(new Rooms("UA1030", timeNow, timeNow + 10000));
		results.add(new Rooms("UA1020", timeNow, timeNow + 10000));
		results.add(new Rooms("UA1010", timeNow, timeNow + 10000));
	}

	public Results() {}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		super.onCreateView(inflater, container, savedInstanceState);
		
		View rootView = inflater.inflate(R.layout.fragment_results,
				container, false);
		
		this.inflater = inflater;
		this.container = container;
		
		//TODO handle an empty results query
		//TODO set up the request thread to be read for implementation.
		//TODO set up the bookings page and the settings page	
		
		//FrameLayout fl = (FrameLayout)rootView.findViewById(R.id.result_layout);
		
		tl = (TableLayout)rootView.findViewById(R.id.TableLayout1);
		
		book = (Button)rootView.findViewById(R.id.book);
		
		book.setEnabled(false);
		
		//View v = fl.getChildAt(0);
		//TODO change to loop
		tl.addView(SetupUpTableView(inflater, container, 0));
		tl.addView(SetupUpTableView(inflater, container, 1));
		tl.addView(SetupUpTableView(inflater, container, 2));
		
			
		// Inflate the layout for this fragment
		return rootView;
	}
	
	public void onResume()
	{
		refreshList();
		super.onResume();
	}
	
	public void refreshList()
	{
		//TODO clear table
		//TODO add rows back to table
	}

	public TableRow SetupUpTableView(LayoutInflater inflater, ViewGroup container, int index)
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

				if(checked != rb)
				{
					if(checked != null)
					{
						checked.setChecked(false);
					}
					checked = rb;
					rb.setChecked(true);
					isNotChecked = false;
				}
				else if(checked == rb)
				{
					rb.setChecked(isNotChecked);
					checked = rb;
					v = (View) rb;
					isNotChecked = !isNotChecked;
				}
				
				if(isNotChecked)
				{
					book.setEnabled(false);
				}
				else
				{
					book.setEnabled(true);
				}
				
				rb.refreshDrawableState();
			}			
		});
		
		//ch.setChecked(false);

		
		Rooms first = results.get(index);
		
		room.setText(first.getRoom());
		start.setText(DateTimeUtility.stf.format(new Date(first.getStartTime())));
		end.setText(DateTimeUtility.stf.format(new Date(first.getEndTime())));

		TableRow tr = (TableRow)newView.findViewById(R.id.tableRow2);
		
		tr.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				TableRow d= (TableRow) v;
				//TODO set index as the identifier for the row that is clicked.
				
				// Note the location of the relative layout is hard coded here as the last element in the table row
				// Also, the radio button is hard coded here as the first (and only) element in the relative layout
				((RelativeLayout) d.getChildAt(d.getChildCount()-1)).getChildAt(0).performClick();
			}
			
		});
		
		return tr;
	}
	
	
}