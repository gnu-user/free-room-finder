package com.uoit.freeroomfinder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

public class FreeRoom extends Fragment {
	
	/**
	 * The fragment argument representing the section number for this
	 * fragment.
	 */
	public static final String ARG_SECTION_NUMBER = "section_number";
	
	public static final int LOGIN_SUCCESSFUL = 100;
	
	private UserLoginTask authTask = null;
	private ProgressDialog dialog;
	
	public FreeRoom() {}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);
		
		View rootView = inflater.inflate(R.layout.activity_free_room,
				container, false);
		
		DatabaseInterface dbi = new DatabaseInterface(this.getActivity().getBaseContext());
		
		if(dbi.getUser() == null)
		{
			Intent loginActivity = new Intent(this.getActivity().getBaseContext(), LoginActivity.class);
			this.startActivityForResult(loginActivity, LOGIN_SUCCESSFUL);
		}
		else if (!MainActivity.loggedIn)
		{
			//TODO possibly move away from have to login to only have to login to search for rooms.
			showProgress(true);
			authTask = new UserLoginTask();
			authTask.execute((Void) null);
		}
		
		
		//TODO put this stuff in the other part once the login is actually set up
		//Set up the time spinner to include the current time
		Spinner timeSpinner = (Spinner)rootView.findViewById(R.id.time);
		ArrayList<String> spinnerArray = new ArrayList<String>(Arrays.asList(this.getResources().getStringArray(R.array.time_values)));
		spinnerArray.add(0, DateTimeUtility.stf.format(new Date()));
		
		ArrayAdapter<String> sa = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_spinner_item, spinnerArray);
		sa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		timeSpinner.setAdapter(sa);
		
		EditText date = (EditText)rootView.findViewById(R.id.date);
		date.setText(DateTimeUtility.sdf.format(new Date()));
		
		
		//TODO decided if a button is really needed
		Button search = (Button)rootView.findViewById(R.id.search);
		search.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				//TODO Launch query with dialog and on result go to the results tab activity.
			}
		});

		return rootView;
	}

	/*@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.free_room, menu);
		return true;
	}*/
	
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
				MainActivity.loggedIn = true;
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
