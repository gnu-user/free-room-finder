package com.uoit.freeroomfinder;

import android.content.Context;
import android.os.AsyncTask;

public class QueryTask extends AsyncTask<Context, Void, Boolean> {
	
	private OnFinshedTaskListener listener;
	
	@Override
	protected Boolean doInBackground(Context... params) {
		DatabaseInterface dbi = new DatabaseInterface(params[0]);
		RoomsBooked.results = dbi.getBooking();
		//publishProgress((Void) null);
		return true;
		
	}
	
	/*@Override
	protected void onProgressUpdate(Void... values)
	{
		refreshList();
	}*/

	@Override
	protected void onPostExecute(final Boolean success) {
		
		if(listener != null)
		{
			listener.onFinishedTaskListener();
		}
	}

	@Override
	protected void onCancelled() {
	}
	
	public void setOnFinshedTaskListener(OnFinshedTaskListener listener){
		this.listener = listener;
	}
}