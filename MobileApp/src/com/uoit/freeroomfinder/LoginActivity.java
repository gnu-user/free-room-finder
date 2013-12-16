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

/**
 * LoginActivity Provides the activity for signing in to the app. Displays a sign-in field.
 * 
 * @author Jonathan Gillett
 * @author Joseph Heron
 * @author Daniel Smullen
 * 
 */
public class LoginActivity extends Activity
{
    /**
     * Determines whether the login fields are open.
     */
    private static boolean open = false;
    /**
     * Provides the task for the user's login process.
     */
    private UserLoginTask authTask = null;
    /**
     * Stores the dialog for the sign in.
     */
    private ProgressDialog dialog;

    /**
     * Success return code for a good login.
     */
    public static final int LOGIN_SUCCESSFUL = 100;

    /**
     * The user who is attempting to login.
     */
    private static User user = null;

    /*
     * (non-Javadoc)
     * 
     * @see android.app.Activity#onCreate(android.os.Bundle)
     */
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_screen);

        // Open the database interface to attempt the sign in.
        DatabaseInterface dbi = new DatabaseInterface(this.getBaseContext());

        if (open || dbi.getUser() != null)
        {
            this.finish();
        }
        else
        {
            open = true;
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see android.app.Activity#onPause()
     */
    @Override
    protected void onPause()
    {
        // Default method implementation. Resets the open database connection state.
        super.onPause();
        open = false;
    }

    /*
     * (non-Javadoc)
     * 
     * @see android.app.Activity#onCreateOptionsMenu(android.view.Menu)
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    /*
     * (non-Javadoc)
     * 
     * @see android.app.Activity#onOptionsItemSelected(android.view.MenuItem)
     */
    @Override
    public boolean onOptionsItemSelected(final MenuItem item)
    {
        // Default method implementation.
        switch (item.getItemId())
        {
        case R.id.action_settings:
            this.startActivity(new Intent(this, SettingsActivity.class));
            return true;

        default:
            return super.onOptionsItemSelected(item);
        }
    }

    /**
     * submit Submits the user name and password field contents to sign in with.
     * 
     * @param v
     *            Requires a handle to a view which initiates the method.
     */
    public void submit(View v)
    {
        // Grab the user name and password input fields.
        EditText username = (EditText) findViewById(R.id.username);
        EditText password = (EditText) findViewById(R.id.password);

        // Ensure the fields aren't blank.
        if (username.getText() != null && password.getText() != null)
        {
            user = new User(username.getText().toString(), password.getText().toString());
            // Validate the user name and password.
            if (user.validUsername() && user.validPassword())
            {
                // Show the progress alert.
                showProgress(true);
                // Do the login.
                authTask = new UserLoginTask();
                authTask.execute((Void) null);
            }
            else
            {
                // Error conditions. Show toasts for each one.
                if (!user.validUsername() && user.validPassword())
                {
                    Toast.makeText(this, R.string.username_error, Toast.LENGTH_LONG).show();
                }
                else if (user.validUsername() && !user.validPassword())
                {
                    Toast.makeText(this, R.string.password_error, Toast.LENGTH_LONG).show();
                }
                else
                {
                    Toast.makeText(this, R.string.username_password_error, Toast.LENGTH_LONG)
                            .show();
                }
                user = null;
            }
        }
    }

    /**
     * reset Opens the password reset web site.
     * 
     * @param v
     *            Requires a handle to the view which initiates the method.
     */
    public void reset(View v)
    {
        // Go to password reset site in the browser.
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(this.getString(R.string.reset_site)));
        this.startActivity(i);
    }

    /**
     * register Opens the registration web site.
     * 
     * @param v
     *            Requires a handle to the view which initiates the method.
     */
    public void register(View v)
    {
        // Go to registration site in the browser.
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(this.getString(R.string.register_site)));
        this.startActivity(i);
    }

    /**
     * showProgress Show or hide the progress dialog.
     * 
     * @param show
     *            Show the dialog if true. Otherwise, don't show it.
     */
    private void showProgress(boolean show)
    {
        if (show)
        {
            dialog = ProgressDialog.show(this, getString(R.string.login_heading),
                    getString(R.string.login_progress_signing_in), true, true,
                    new OnCancelListener()
                    {
                        public void onCancel(DialogInterface dialog)
                        {
                            LoginActivity.this.finish();
                        }
                    });
        }
        else
        {
            dialog.dismiss();
        }
    }

    /**
     * UserLoginTask An asynchronous task for connecting and validating the user login credentials
     * with the external database server.
     * 
     * @author Daniel Smullen
     * @author Jonathan Gillett
     * @author Joseph Heron
     * 
     */
    public class UserLoginTask extends AsyncTask<Void, Void, Boolean>
    {
        /*
         * (non-Javadoc)
         * 
         * @see android.os.AsyncTask#doInBackground(Params[])
         */
        @Override
        protected Boolean doInBackground(Void... params)
        {
            if (user != null)
            {
                /**
                 * Validate the login credentials using REST API request.
                 */
                if (Request.validateCredentials(user.getUsername(), user.getPassword()))
                {
                    /**
                     * Add the user into the internal database if the validation is successful.
                     */
                    DatabaseInterface dbi = new DatabaseInterface(
                            LoginActivity.this.getBaseContext());
                    dbi.insertUser(user);

                    return true;
                }
            }
            return false;
        }

        /*
         * (non-Javadoc)
         * 
         * @see android.os.AsyncTask#onPostExecute(java.lang.Object)
         */
        @Override
        protected void onPostExecute(final Boolean success)
        {
            // Shows a toast when the validation is successful. Shows an error message if it wasn't.
            authTask = null;
            showProgress(false);

            if (success)
            {
                LoginActivity.this.setResult(LoginActivity.LOGIN_SUCCESSFUL);
                LoginActivity.this.finish();
            }
            else
            {
                Toast.makeText(LoginActivity.this, R.string.error_invalid_account,
                        Toast.LENGTH_LONG).show();
            }
        }

        /*
         * (non-Javadoc)
         * 
         * @see android.os.AsyncTask#onCancelled()
         */
        @Override
        protected void onCancelled()
        {
            // Default method implementation. Stops the progress dialog from showing.
            authTask = null;
            showProgress(false);
        }
    }
}
