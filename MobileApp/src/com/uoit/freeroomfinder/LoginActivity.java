package com.uoit.freeroomfinder;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnCancelListener;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends Activity {

	private UserLoginTask authTask = null;
	private ProgressDialog dialog;
	
	private static final User test = new User("database", "test123");
	
	private static User user = null;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login_screen);

		//TODO remove
		//Toast.makeText(this, DateTimeUtility.sdf.format(new Date()), Toast.LENGTH_LONG).show();
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
		//TODO open up the submit url
		//https://www.cs-club.ca/reset/
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
			// TODO: attempt authentication against a network service.
			// TODO: login via restful call
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
				LoginActivity.this.setResult(FreeRoom.LOGIN_SUCCESSFUL);
				LoginActivity.this.finish();
			} else {
				//TODO show error
				Toast.makeText(LoginActivity.this, "Invalid Account", Toast.LENGTH_LONG).show();
			}
		}

		@Override
		protected void onCancelled() {
			authTask = null;
			showProgress(false);
		}
	}

}
