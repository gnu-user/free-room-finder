package com.uoit.freeroomfinder;

import java.text.SimpleDateFormat;
import java.util.Locale;

public class DateTimeUtility {

	//public static Array<>
	
	//TODO add local
	public static SimpleDateFormat stf = new SimpleDateFormat("hh:mm aa");
	//public static SimpleDateFormat stf = new SimpleDateFormat("kk:mm:ss");
	
//	public static SimpleDateFormat stf = new SimpleDateFormat("kk:mm");
	public static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	
	private Locale locale;
	private boolean notUseArmyClock;
	
	public DateTimeUtility(boolean notUseArmyClock, Locale locale)
	{
		if(notUseArmyClock)
		{
			stf = new SimpleDateFormat("hh:mm aa", locale);
		}
		else
		{
			stf = new SimpleDateFormat("kk:mm", locale);
		}
		sdf = new SimpleDateFormat("yyyy-MM-dd", locale);
		
		this.locale = locale;
		this.notUseArmyClock = notUseArmyClock;
	}
	
	public void setArmyClock(boolean notUseArmyClock)
	{
		if(this.notUseArmyClock != notUseArmyClock)
		{
			this.notUseArmyClock = notUseArmyClock;
			
			if(notUseArmyClock)
			{
				stf = new SimpleDateFormat("hh:mm aa", locale);
			}
			else
			{
				stf = new SimpleDateFormat("kk:mm", locale);
			}
		}
	}
	
}
