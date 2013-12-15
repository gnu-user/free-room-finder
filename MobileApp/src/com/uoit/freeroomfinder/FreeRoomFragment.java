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
