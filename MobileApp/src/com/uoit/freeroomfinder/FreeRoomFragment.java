package com.uoit.freeroomfinder;

import android.content.Intent;
import android.support.v4.app.Fragment;

/**
 * General fragment class to check if the user has logged in.
 * If not the login activity will be launched for a successful
 * login.
 */
public abstract class FreeRoomFragment extends Fragment{

	/**
	 * The fragment argument representing the section number for this
	 * fragment.
	 */
	public static final String ARG_SECTION_NUMBER = "section_number";
	
	@Override
	public void onResume()
	{
		super.onResume();
		((MainActivity)this.getActivity()).ensureLogin();
	}
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		((MainActivity)this.getActivity()).onLogin(requestCode, resultCode);
	}	
}
