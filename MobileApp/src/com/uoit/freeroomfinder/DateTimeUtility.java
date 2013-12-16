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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * DateTimeUtility A utility helper class to greatly simplify all of the date and time parsing and
 * conversions.
 * 
 * @author Joseph Heron
 * @author Daniel Smullen
 * @author Jonathan Gillett
 */
public abstract class DateTimeUtility
{
    /**
     * Stores the format for the 24 hour clock.
     */
    private static final String ARMY_TIME = "kk:mm";
    /**
     * Stores the format for the 12 hour clock.
     */
    private static final String NORMAL_TIME = "h:mm aa";
    /**
     * Stores the format for the full 24 hour clock.
     */
    private static final String FULL_TIME = "kk:mm:ss";
    /**
     * Stores the format for dates.
     */
    private static final String DATE = "yyyy-MM-dd";

    /**
     * Used to store simple date formats.
     */
    private static SimpleDateFormat stf;
    private static SimpleDateFormat stfFull;
    private static SimpleDateFormat sdf;

    /**
     * Used to store the current locale (used in date and time formats).
     */
    private static Locale locale;
    /**
     * Used to store whether or not to use the 24 hour clock.
     */
    private static boolean notUseArmyClock;

    /**
     * setFormatLocale Set the preferred time format and the current locale
     * 
     * @param notArmyClock
     *            Determines whether to use 12 hour or 24 hour format.
     * @param curLocale
     *            The current locale for the time.
     */
    public static void setFormatLocale(boolean notArmyClock, Locale curLocale)
    {
        if (notArmyClock)
        {
            stf = new SimpleDateFormat(NORMAL_TIME, curLocale);
        }
        else
        {
            stf = new SimpleDateFormat(ARMY_TIME, curLocale);
        }
        stfFull = new SimpleDateFormat(FULL_TIME, curLocale);
        sdf = new SimpleDateFormat(DATE, curLocale);

        locale = curLocale;
        notUseArmyClock = notArmyClock;
    }

    /**
     * setArmyClock Change the time format to 24 hour.
     * 
     * @param notArmyClock
     *            Whether to use 12 hour clock or not.
     */
    public static void setArmyClock(boolean notArmyClock)
    {
        if (notUseArmyClock != notArmyClock)
        {
            notUseArmyClock = notArmyClock;

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
     * formatDate Format the date to the correct format. Accepts Date objects.
     * 
     * @param date
     *            The date, in Date type.
     * 
     * @return The formatted date.
     */
    public static String formatDate(Date date)
    {
        return sdf.format(date);
    }

    /**
     * formatDate Format the date to the correct format. Accepts long format.
     * 
     * @param date
     *            The date, in long type.
     * 
     * @return The formatted date.
     */
    public static String formatDate(long date)
    {
        return sdf.format(date);
    }

    /**
     * formatTime Format the time to the correct format. Uses time values extracted from Date
     * objects.
     * 
     * @param date
     *            The time, in Date type.
     * 
     * @return The formatted time.
     */
    public static String formatTime(Date date)
    {
        return stf.format(date);
    }

    /**
     * formatFullTime Format the time, based on a string representation.
     * 
     * @param time
     *            The time to format, as a string.
     * 
     * @return The formatted time.
     */
    public static String formatFullTime(String time) throws ParseException
    {
        return stfFull.format(parseTime(time));
    }

    /**
     * parseDate Parse the date, based on a string representation.
     * 
     * @param date
     *            Accepts a formatted string representing a date.
     * 
     * @return The date, as a Date object.
     * 
     * @throws ParseException
     *             Parsing exception from the given string. If the string does not conform to the
     *             correct format, this will be thrown.
     */
    public static Date parseDate(String date) throws ParseException
    {
        return sdf.parse(date);
    }

    /**
     * parseTime Parse the time, based on a string representation.
     * 
     * @param time
     *            Accepts a formatted string representing a time.
     * 
     * @return The time, as an encoded Date object.
     * 
     * @throws ParseException
     *             Parsing exception from the given string. If the string does not conform to the
     *             correct format, this will be thrown.
     */
    public static Date parseTime(String time) throws ParseException
    {
        return stf.parse(time);
    }

    /**
     * parseFullTime Parse the time for the FULL_TIME format.
     * 
     * @param time
     *            Accepts a formatted string representing the full time, down to the second.
     * 
     * @return The time, as an encoded Date object.
     * 
     * @throws ParseException
     *             Parsing exception from the given string. If the string does not conform to the
     *             correct format, this will be thrown.
     */
    public static Date parseFullTime(String time) throws ParseException
    {
        return stfFull.parse(time);
    }
}
