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

public class FreeRoom extends FreeRoomFragment {
	
	/**
	 * The fragment argument representing the section number for this
	 * fragment.
	 */
	public static final String ARG_SECTION_NUMBER = "section_number";
	
	private SearchTask searchTask = null;
	
	private SharedPreferences sharedPrefs;
	private Date curDate;
	private Spinner timeSpinner;
	
	public static String datepicked = "";
	
	private static View rootView;
	
	public static ArrayList<Rooms> availableRooms;
	
	public FreeRoom() {
		// Required empty public constructor
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);
		
		rootView = inflater.inflate(R.layout.activity_free_room, container, false);
		
		
		sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this.getActivity().getBaseContext());
		
		curDate = new Date();
		
		//Set up default values
		Spinner campus = (Spinner)rootView.findViewById(R.id.campus);
		campus.setSelection(Integer.valueOf(sharedPrefs.getString("default_campus", "0"))-1);
		
		Spinner duration = (Spinner)rootView.findViewById(R.id.duration);
		duration.setSelection(Integer.valueOf(sharedPrefs.getString("default_duration", "0"))-1);
		
		timeSpinner = (Spinner)rootView.findViewById(R.id.time);
		
		TextView date = (TextView)rootView.findViewById(R.id.date);
		date.setText(MainActivity.datetimeFormater.formatDate(curDate) + " (Today)");
		
		datepicked = MainActivity.datetimeFormater.formatDate(curDate);

		availableRooms = new ArrayList<Rooms>();
		
		date.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				
				Date d = null;
				try {
					d = MainActivity.datetimeFormater.parseDate(datepicked);
					
				} catch (ParseException e) {
				    
					e.printStackTrace();
				}
				
				Calendar c = Calendar.getInstance();
				
				if(d != null)
				{
					c.setTime(d);
				}
	            int yy = c.get(Calendar.YEAR);
	            int mm = c.get(Calendar.MONTH);
	            int dd = c.get(Calendar.DAY_OF_MONTH);				
	            
				DatePickerDialog alert = new DatePickerDialog(rootView.getContext(),
						new DatePickerDialog.OnDateSetListener() {
					
					@Override
					public void onDateSet(DatePicker view, int year, int monthOfYear,
							int dayOfMonth) {

						datepicked = year + "-" + (monthOfYear+1) + "-" + dayOfMonth;
						TextView date = (TextView)rootView.findViewById(R.id.date);
						
						date.setText(datepicked);
						if (datepicked.contains(MainActivity.datetimeFormater.formatDate(curDate))){
							date.setText(datepicked + " (Today)");
						}
					}
				}, yy, mm, dd);
					 
				alert.show();
			}
			
		});
		
		Button search = (Button)rootView.findViewById(R.id.search);
		search.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				Date curDate = new Date();
				
				Spinner timeSpinner = (Spinner)rootView.findViewById(R.id.time);
				Spinner durationSpinner = (Spinner)rootView.findViewById(R.id.duration);
				Spinner campusSpinner = (Spinner)rootView.findViewById(R.id.campus);
				
                try
                {
                	Request req;
                	if (timeSpinner.getSelectedItem().toString().compareTo("Now") == 0){
                		req = new Request(
                                MainActivity.datetimeFormater.formatFullTime(MainActivity.datetimeFormater.formatTime(curDate)), 
                                datepicked, 
                                rootView.getResources().getStringArray(R.array.campus_names)[campusSpinner.getSelectedItemPosition()],
                                Integer.valueOf(durationSpinner.getSelectedItem().toString()));
					}else{
						req = new Request(
	                            MainActivity.datetimeFormater.formatFullTime(timeSpinner.getSelectedItem().toString()), 
	                            datepicked, 
	                            rootView.getResources().getStringArray(R.array.campus_names)[campusSpinner.getSelectedItemPosition()],
	                            Integer.valueOf(durationSpinner.getSelectedItem().toString())); 
					}
                    
                    
                    ((MainActivity) FreeRoom.this.getActivity()).showProgress(true);
                    searchTask = new SearchTask();
                    searchTask.setOnFinshedTaskListener((Results)MainActivity
                    		.switchTabs(MainActivity.RESULTS_TAB));
                    searchTask.execute(req);
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
			}
		});
		
		return rootView;
		
	}
	
	@Override
	public void onResume()
	{
		super.onResume();
		
		//Set up the time spinner to include the current time
		int timeValues = R.array.time_values;
		
		if(!sharedPrefs.getBoolean("army_clock", true))
		{
			timeValues = R.array.army_time_values;
		}
		
		//TODO remove following todo
		//TODO change string defined time stamps to use only hh:mm
		ArrayList<String> spinnerArray = new ArrayList<String>(Arrays.asList(this.getResources().getStringArray(timeValues)));
		spinnerArray.add(0, "Now");
		
		ArrayAdapter<String> sa = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_spinner_item, spinnerArray);
		sa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		timeSpinner.setAdapter(sa);		
		
		
	}
		
	public class SearchTask extends AsyncTask<Request, Void, Boolean> {
		
		private OnFinshedTaskListener listener;
		
		@Override
		protected Boolean doInBackground(Request... params) {		    
		    /* Get the list of available rooms and display the results */
		    availableRooms.clear();
		    availableRooms = params[0].searchRooms();
		    
		    for (Rooms room : availableRooms)
		    {
		        System.out.println(room.getRoom() + ", " + room.getStartTime() + ", " + room.getEndTime());
		    }
			return true;
		}

		@Override
		protected void onPostExecute(final Boolean success) {
			searchTask = null;
			((MainActivity) FreeRoom.this.getActivity()).showProgress(false);

			if (success) {
				if(listener != null)
				{
					listener.onFinishedTaskListener();
				}
			} else {
				//TODO show error if possible otherwise just remove todo
				
			}
		}

		@Override
		protected void onCancelled() {
			searchTask = null;
			((MainActivity) FreeRoom.this.getActivity()).showProgress(false);
		}
		
		public void setOnFinshedTaskListener(OnFinshedTaskListener listener){
			this.listener = listener;
		}		
	}

}

