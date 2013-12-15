package com.uoit.freeroomfinder;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

//TODO document class
public class DateTimeUtility
{
    private static final String ARMY_TIME = "kk:mm";
    private static final String NORMAL_TIME = "h:mm aa";
    private static final String FULL_TIME = "kk:mm:ss";
    private static final String DATE = "yyyy-MM-dd";

    private SimpleDateFormat stf;
    private SimpleDateFormat stfFull;
    private SimpleDateFormat sdf;

    private Locale locale;
    private boolean notUseArmyClock;

    /**
     * Create a DateTimeUtility
     * @param notUseArmyClock Whether to use 12 hour or 24 hour format
     * @param locale The local of the time.
     */
    public DateTimeUtility(boolean notUseArmyClock, Locale locale)
    {
        if (notUseArmyClock)
        {
            stf = new SimpleDateFormat(NORMAL_TIME, locale);
        }
        else
        {
            stf = new SimpleDateFormat(ARMY_TIME, locale);
        }
        stfFull = new SimpleDateFormat(FULL_TIME, locale);
        sdf = new SimpleDateFormat(DATE, locale);

        this.locale = locale;
        this.notUseArmyClock = notUseArmyClock;
    }

    /**
     * Change time format
     * @param notUseArmyClock Whether to use 12 hour clock or not
     */
    public void setArmyClock(boolean notUseArmyClock)
    {
        if (this.notUseArmyClock != notUseArmyClock)
        {
            this.notUseArmyClock = notUseArmyClock;

            if (notUseArmyClock)
            {
                stf = new SimpleDateFormat(NORMAL_TIME, locale);
            }
            else
            {
                stf = new SimpleDateFormat(ARMY_TIME, locale);
            }
        }
    }

    /**
     * Format the date
     * @param date The date
     * @return The formatted date
     */
    public String formatDate(Date date)
    {
        return sdf.format(date);
    }

    /**
     * Format the date
     * @param date The date
     * @return The formatted date
     */
    public String formatDate(long date)
    {
        return sdf.format(date);
    }

    /**
     * Format the time
     * @param date The date
     * @return The formatted time
     */
    public String formatTime(Date date)
    {
        return stf.format(date);
    }

    /**
     * Format the time
     * @param date The formatted date
     * @return The formatted time
     */
    public String formatFullTime(String time) throws ParseException
    {
        return stfFull.format(parseTime(time));
    }

    /**
     * Parse the date
     * @param date The formatted date
     * @return The date
     * @throws ParseException Parsing exception from the given string
     */
    public Date parseDate(String date) throws ParseException
    {
        return sdf.parse(date);
    }

    /**
     * Parse the time
     * @param time The formatted time
     * @return The time
     * @throws ParseException Parsing exception from the given string
     */
    public Date parseTime(String time) throws ParseException
    {
        return stf.parse(time);
    }
}
