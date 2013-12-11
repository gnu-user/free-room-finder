package com.uoit.freeroomfinder;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends Activity {

	private UserLoginTask authTask = null;
	private ProgressDialog dialog;
	

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
		getMenuInflater().inflate(R.menu.login, menu);
		return true;
	}

	public void submit(View v) {
		EditText username = (EditText) findViewById(R.id.username);
		EditText password = (EditText) findViewById(R.id.password);

		if (username.getText() != null && password.getText() != null) {
			
			User user = new User(username.getText().toString(), password
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
			
			return true;
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
				
			}
		}

		@Override
		protected void onCancelled() {
			authTask = null;
			showProgress(false);
		}
	}

}
