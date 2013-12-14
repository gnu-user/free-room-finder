package com.uoit.freeroomfinder.preferences;

import android.content.Context;
import android.preference.DialogPreference;
import android.util.AttributeSet;

public class PreferenceDialog extends DialogPreference{
	
	private boolean isSomeoneListening; 
	private OnPreferenceDialogClosedListener dialogClosedListener;

	public PreferenceDialog(Context context, AttributeSet attrs) {
		super(context, attrs);
		isSomeoneListening = false;
	}

	@Override
    protected void onDialogClosed(boolean positiveResult) {
        super.onDialogClosed(positiveResult);
        persistString(String.valueOf(positiveResult));
        
        if(isSomeoneListening)
        {
        	dialogClosedListener.onPreferenceDialogClosed(positiveResult); 
        }
    }
	
	public void setOnPreferenceDialogClosedListener(OnPreferenceDialogClosedListener listener)
	{
		dialogClosedListener = listener;
		isSomeoneListening = true;
	}
	
}
