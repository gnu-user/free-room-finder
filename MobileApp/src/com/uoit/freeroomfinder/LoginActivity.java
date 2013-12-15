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

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnCancelListener;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends Activity {

	private static boolean open = false;
	private UserLoginTask authTask = null;
	private ProgressDialog dialog;
	
	public static final int LOGIN_SUCCESSFUL = 100;
	
	private static final User test = new User("database", "test123");
	
	private static User user = null;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login_screen);

		DatabaseInterface dbi = new DatabaseInterface(this.getBaseContext());
		
		if(open || dbi.getUser() != null)
		{
			this.finish();
		}
		else
		{
			
			open = true;
		}
	}
	
	@Override
	protected void onPause()
	{
		super.onPause();
		open = false;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
            
                this.startActivity(new Intent(this, SettingsActivity.class));
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

	public void submit(View v) {
		EditText username = (EditText) findViewById(R.id.username);
		EditText password = (EditText) findViewById(R.id.password);

		if (username.getText() != null && password.getText() != null) {
			
			user = new User(username.getText().toString(), password
					.getText().toString());
			if (user.validUsername() && user.validPassword()) {
				
				showProgress(true);
				authTask = new UserLoginTask();
				authTask.execute((Void) null);
			}
			else
			{
				if(!user.validUsername() && user.validPassword()){
					Toast.makeText(this, R.string.username_error, Toast.LENGTH_LONG).show();
				}
				else if(user.validUsername() && !user.validPassword())
				{
					Toast.makeText(this, R.string.password_error, Toast.LENGTH_LONG).show();
				}
				else
				{
					Toast.makeText(this, R.string.username_password_error, Toast.LENGTH_LONG).show();
				}
				user = null;
			}
		}
	}
	
	public void reset(View v)
	{
		//Go to password reset site
		Intent i = new Intent(Intent.ACTION_VIEW);
		i.setData(Uri.parse(this.getString(R.string.reset_site)));
		this.startActivity(i);
	}

	private void showProgress(boolean show) {
		
		if(show)
		{
			dialog = ProgressDialog.show(this,
					getString(R.string.login_heading),
					getString(R.string.login_progress_signing_in), true, true,
					new OnCancelListener() {
	
						public void onCancel(DialogInterface dialog) {
							LoginActivity.this.finish();
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

			try {
				// Simulate network access.
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				return false;
			}
			
			//TODO replace this primitive test with the rest call
			if(user != null && test != null && test.equals(user))
			{
				
				//Insert the user into the database
				DatabaseInterface dbi = new DatabaseInterface(LoginActivity.this.getBaseContext());
				dbi.insertUser(user);
				
				return true;
			}
			
			
			return false;
		}

		@Override
		protected void onPostExecute(final Boolean success) {
			authTask = null;
			showProgress(false);

			if (success) {
				LoginActivity.this.setResult(LoginActivity.LOGIN_SUCCESSFUL);
				LoginActivity.this.finish();
			} else {
				Toast.makeText(LoginActivity.this, R.string.error_invalid_account, Toast.LENGTH_LONG).show();
			}
		}

		@Override
		protected void onCancelled() {
			authTask = null;
			showProgress(false);
		}
	}

}
