package com.uoit.freeroomfinder;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateTimeUtility {

	private static final String ARMY_TIME = "kk:mm";
	private static final String NORMAL_TIME = "hh:mm aa";
	private static final String DATE = "yyyy-MM-dd";
	
	private SimpleDateFormat stf;
	private SimpleDateFormat sdf;
	
	private Locale locale;
	private boolean notUseArmyClock;
	
	public DateTimeUtility(boolean notUseArmyClock, Locale locale)
	{
		if(notUseArmyClock)
		{
			stf = new SimpleDateFormat(NORMAL_TIME, locale);
		}
		else
		{
			stf = new SimpleDateFormat(ARMY_TIME, locale);
		}
		sdf = new SimpleDateFormat(DATE, locale);
		
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
				stf = new SimpleDateFormat(NORMAL_TIME, locale);
			}
			else
			{
				stf = new SimpleDateFormat(ARMY_TIME, locale);
			}
		}
	}
	
	public String formatDate(Date date)
	{
		return sdf.format(date);
	}
	
	public String formatDate(long date)
	{
		return sdf.format(date);
	}
	
	public Date parseDate(String date) throws ParseException
	{
		return sdf.parse(date);
	}
	
	public Date parseTime(String time) throws ParseException
	{
	    return stf.parse(time);
	}
	
	public String formatTime(Date date)
	{
		return stf.format(date);
	}
	
}
