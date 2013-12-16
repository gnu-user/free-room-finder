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
package com.uoit.freeroomfinder.preferences;

import android.content.Context;
import android.preference.DialogPreference;
import android.util.AttributeSet;

/**
 * PreferenceDialog A class used to provide methods for the preferences dialogs in the user
 * interface.
 * 
 * @author Joseph Heron
 * @author Daniel Smullen
 * @author Jonathan Gillett
 * 
 */
public class PreferenceDialog extends DialogPreference
{

    /**
     * Used to determine the listener and whether a listener is bound.
     */
    private boolean isSomeoneListening;
    private OnPreferenceDialogClosedListener dialogClosedListener;

    /**
     * Default constructor.
     * 
     * @param context
     *            The context for the activity.
     * 
     * @param attrs
     *            The attribute set for the preference dialog.
     */
    public PreferenceDialog(Context context, AttributeSet attrs)
    {
        // Unbinds from a listener. Calls the superclass.
        super(context, attrs);
        isSomeoneListening = false;
    }

    /*
     * (non-Javadoc)
     * 
     * @see android.preference.DialogPreference#onDialogClosed(boolean)
     */
    @Override
    protected void onDialogClosed(boolean positiveResult)
    {
        // Default implementation. If bound to a listener, invokes the handler.
        super.onDialogClosed(positiveResult);
        persistString(String.valueOf(positiveResult));

        if (isSomeoneListening)
        {
            dialogClosedListener.onPreferenceDialogClosed(positiveResult);
        }
    }

    /**
     * setOnPreferenceDialogClosedListener Binds the class to a listener.
     * 
     * @param listener
     *            The listener to bind to.
     */
    public void setOnPreferenceDialogClosedListener(OnPreferenceDialogClosedListener listener)
    {
        dialogClosedListener = listener;
        isSomeoneListening = true;
    }
}
