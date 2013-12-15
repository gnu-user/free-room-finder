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

import android.content.Context;
import android.os.AsyncTask;

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
			// Call the listener
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