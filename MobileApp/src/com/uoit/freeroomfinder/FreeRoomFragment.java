package com.uoit.freeroomfinder;

import android.content.Intent;
import android.support.v4.app.Fragment;

public abstract class FreeRoomFragment extends Fragment{

	
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
