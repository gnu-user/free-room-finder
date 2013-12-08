package com.uoit.freeroomfinder;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;

public class FreeRoom extends Activity {
	
	public static final int LOGIN_SUCCESSFUL = 100;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_free_room);
		
		DatabaseInterface dbi = new DatabaseInterface(this.getBaseContext());
		
		if(dbi.getUser() == null)
		{
			Intent loginActivity = new Intent(this, LoginActivity.class);
			this.startActivityForResult(loginActivity, LOGIN_SUCCESSFUL);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.free_room, menu);
		return true;
	}
	
	protected void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		if(requestCode == LOGIN_SUCCESSFUL)
		{
			if(resultCode != Activity.RESULT_CANCELED)
			{
				DatabaseInterface dbi = new DatabaseInterface(this.getBaseContext());
				User user = dbi.getUser();
				
				if(user != null)
				{
					// Successful login
				}
			}
			else
			{
				this.finish();
			}
		}
	}
	
	public void search (View v)
	{
		//TODO validate entries
		//TODO start activity which will start the query
	}

}
