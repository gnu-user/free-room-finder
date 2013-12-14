package com.uoit.freeroomfinder;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;

public class FreeRoom extends Fragment {
	
	/**
	 * The fragment argument representing the section number for this
	 * fragment.
	 */
	public static final String ARG_SECTION_NUMBER = "section_number";
	
	public static final int LOGIN_SUCCESSFUL = 100;
	
	private UserLoginTask authTask = null;
	private ProgressDialog dialog;
	
	private SharedPreferences sharedPrefs;
	private Date curDate;
	private Spinner timeSpinner;
	
	public static String datepicked = "";
	
	public FreeRoom() {}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);
		
		final View rootView = inflater.inflate(R.layout.activity_free_room,
				container, false);
		
		
		sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this.getActivity().getBaseContext());
		curDate = new Date();
		
		timeSpinner = (Spinner)rootView.findViewById(R.id.time);
		
		//TODO change to "now"
		TextView date = (TextView)rootView.findViewById(R.id.date);
		date.setText(DateTimeUtility.sdf.format(curDate));
		
		datepicked = DateTimeUtility.sdf.format(curDate);

		date.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				
				Date d = null;
				try {
					d = DateTimeUtility.sdf.parse(datepicked);
					
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
					}
				}, yy, mm, dd);
					 
				alert.show();
			}
			
		});
		
		Button search = (Button)rootView.findViewById(R.id.search);
		search.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				Spinner timeSpinner = (Spinner)rootView.findViewById(R.id.time);
				Spinner durationSpinner = (Spinner)rootView.findViewById(R.id.duration);
				Spinner campusSpinner = (Spinner)rootView.findViewById(R.id.campus);
				
				Request req = new Request(timeSpinner.getSelectedItem().toString(),
						Integer.valueOf(durationSpinner.getSelectedItem().toString())
						, datepicked, campusSpinner.getSelectedItemPosition());
				
				//TODO Launch query with dialog and on result go to the results tab activity.
			}
		});
		
		return rootView;
		
	}
	
	@Override
	public void onResume()
	{
		//This should allow for a more robust login check
		DatabaseInterface dbi = new DatabaseInterface(this.getActivity().getBaseContext());
		
		if(dbi.getUser() == null)
		{
			Intent loginActivity = new Intent(this.getActivity().getBaseContext(), LoginActivity.class);
			this.startActivityForResult(loginActivity, LOGIN_SUCCESSFUL);
		}
		
		//Set up the time spinner to include the current time
		int timeValues = R.array.time_values;
		
		if(!sharedPrefs.getBoolean("army_clock", true))
		{
			timeValues = R.array.army_time_values;
		}
		
		//TODO change string defined time stamps to use only hh:mm
		ArrayList<String> spinnerArray = new ArrayList<String>(Arrays.asList(this.getResources().getStringArray(timeValues)));
		spinnerArray.add(0, DateTimeUtility.stf.format(curDate));
		
		ArrayAdapter<String> sa = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_spinner_item, spinnerArray);
		sa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		timeSpinner.setAdapter(sa);		
		
		super.onResume();
	}

	/*@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.free_room, menu);
		return true;
	}*/
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		if(requestCode == LOGIN_SUCCESSFUL)
		{
			if(resultCode != Activity.RESULT_CANCELED)
			{
				DatabaseInterface dbi = new DatabaseInterface(this.getActivity().getBaseContext());
				User user = dbi.getUser();
				
				if(user != null)
				{
					// Successful login
				}
			}
			else
			{
				this.getActivity().finish();
			}
		}
	}
	
	private void showProgress(boolean show) {
		
		if(show)
		{
			dialog = ProgressDialog.show(this.getActivity(),
					getString(R.string.login_heading),
					getString(R.string.login_progress_signing_in), true, true,
					new OnCancelListener() {
	
						public void onCancel(DialogInterface dialog) {
							FreeRoom.this.getActivity().finish();
						}
					});
		}
		else
		{
			dialog.dismiss();
		}

	}
	
	public class UserLoginTask extends AsyncTask<Void, Void, Boolean> {
		@Override
		protected Boolean doInBackground(Void... params) {
			// TODO: attempt authentication against a network service.
			// TODO: login via restful call
			try {
				// Simulate network access.
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				return false;
			}
			
			return true;
		}

		@Override
		protected void onPostExecute(final Boolean success) {
			authTask = null;
			showProgress(false);

			if (success) {
				//FreeRoom.this.getActivity().setResult(FreeRoom.LOGIN_SUCCESSFUL);
				//MainActivity.loggedIn = true;
			} else {
				//TODO show error
				
			}
		}

		@Override
		protected void onCancelled() {
			authTask = null;
			showProgress(false);
		}
	}

}

