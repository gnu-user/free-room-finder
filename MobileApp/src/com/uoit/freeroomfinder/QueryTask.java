package com.uoit.freeroomfinder;

import android.content.Context;
import android.os.AsyncTask;

//TODO document
public class QueryTask extends AsyncTask<Context, Void, Boolean> {
	
	private OnFinshedTaskListener listener;
	
	@Override
	protected Boolean doInBackground(Context... params) {
		//TODO see if this can fail and catch errors
		DatabaseInterface dbi = new DatabaseInterface(params[0]);
		RoomsBooked.results = dbi.getBooking();
		return true;
		
	}
	
	@Override
	protected void onPostExecute(final Boolean success) {
		
		if(success)
		{
			if(listener != null)
			{
				listener.onFinishedTaskListener();
			}
		}
		else
		{
			//TODO show error
		}
	}

	@Override
	protected void onCancelled() {
	}
	
	public void setOnFinshedTaskListener(OnFinshedTaskListener listener){
		this.listener = listener;
	}
}