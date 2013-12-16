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
 * A date time utility helper class to greatly simplify all of the date and 
 * time parsing and conversions needed.
 */
public abstract class DateTimeUtility
{
    private static final String ARMY_TIME = "kk:mm";
    private static final String NORMAL_TIME = "h:mm aa";
    private static final String FULL_TIME = "kk:mm:ss";
    private static final String DATE = "yyyy-MM-dd";

    private static SimpleDateFormat stf;
    private static SimpleDateFormat stfFull;
    private static SimpleDateFormat sdf;

    private static Locale locale;
    private static boolean notUseArmyClock;

    /**
     * Set the preferred time format and the current locale
     * @param notArmyClock Whether to use 12 hour or 24 hour format
     * @param curLocale The current locale of the time.
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
     * Change time format
     * @param notArmyClock Whether to use 12 hour clock or not
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
     * Format the date
     * @param date The date
     * @return The formatted date
     */
    public static String formatDate(Date date)
    {
        return sdf.format(date);
    }

    /**
     * Format the date
     * @param date The date
     * @return The formatted date
     */
    public static String formatDate(long date)
    {
        return sdf.format(date);
    }

    /**
     * Format the time
     * @param date The date
     * @return The formatted time
     */
    public static String formatTime(Date date)
    {
        return stf.format(date);
    }

    /**
     * Format the time
     * @param date The formatted date
     * @return The formatted time
     */
    public static String formatFullTime(String time) throws ParseException
    {
        return stfFull.format(parseTime(time));
    }

    /**
     * Parse the date
     * @param date The formatted date
     * @return The date
     * @throws ParseException Parsing exception from the given string
     */
    public static Date parseDate(String date) throws ParseException
    {
        return sdf.parse(date);
    }

    /**
     * Parse the time
     * @param time The formatted time
     * @return The time
     * @throws ParseException Parsing exception from the given string
     */
    public static Date parseTime(String time) throws ParseException
    {
        return stf.parse(time);
    }
    
    /**
     * Parse the time for the FULL_TIME format
     * @param time The formatted time
     * @return The time
     * @throws ParseException Parsing exception from the given string
     */
    public static Date parseFullTime(String time) throws ParseException
    {
        return stfFull.parse(time);
    }
}
