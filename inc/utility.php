<?php
/*
 *  Free Room Finder Website
 *
 *
 *  Authors -- Crow's Foot Group
 *  -------------------------------------------------------
 *
 *  Jonathan Gillett
 *  Joseph Heron
 *  Amit Jain
 *  Wesley Unwin
 *  Anthony Jihn
 * 
 *
 *  License
 *  -------------------------------------------------------
 *
 *  Copyright (C) 2012 Crow's Foot Group
 *
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU Affero General Public License as
 *  published by the Free Software Foundation, either version 3 of the
 *  License, or (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU Affero General Public License for more details.
 *
 *  You should have received a copy of the GNU Affero General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

/*
 * Contains an aggregate collection of miscellaneous utility functions, these
 * are functions for things such as calculating the current day of the week give
 * a date
 */

/**
 * A function which determines the current day of the week given a date.
 *
 * @param $date The date you want to determine the day of the week for, formatted as 'Y-m-d'
 * @return string The day of the week as a single character such as M, T, W, R, F
 */
function get_day_of_week($date)
{
	/* Set the timezone */
	date_default_timezone_set('America/Toronto');
	
	$cur_date = DateTime::createFromFormat('Y-m-d', $date);
	
	$day_of_week = '';
	$cur_day_of_week = $cur_date->format('l');

	if (strcasecmp($cur_day_of_week, 'Monday') === 0)
	{
		$day_of_week = 'M'; 
	}
	elseif (strcasecmp($cur_day_of_week, 'Tuesday') === 0)
	{
		$day_of_week = 'T';
	}
	elseif (strcasecmp($cur_day_of_week, 'Wednesday') === 0)
	{
		$day_of_week = 'W';
	}
	elseif (strcasecmp($cur_day_of_week, 'Thursday') === 0)
	{
		$day_of_week = 'R';
	}
	elseif (strcasecmp($cur_day_of_week, 'Friday') === 0)
	{
		$day_of_week = 'F';
	}
	/* Sunday or saturday, N/A */
	else
	{
		$day_of_week = 'S';
	}
	
	return $day_of_week;
}


/**
 * A function which returns the end time given a starting time ane the duration
 * 
 * @param $start_time The start time formatted as H:i:s
 * @param $duration The duration of time
 * 
 * @return The end time formatted as H:i:s
 */
function get_end_time($start_time, $duration)
{
	/* Set the timezone */
	date_default_timezone_set('America/Toronto');
	
	$end_time = DateTime::createFromFormat('H:i:s', $start_time);
	$end_time->add(new DateInterval("PT". $duration ."H"));
	
	return $end_time->format('H:i:s');
}


/**
 * A function which determines the semester and year given the date
 * 
 * @param $date The date you want to determine the semester info for, formatted as 'Y-m-d'
 * @return dictionary containing the semester and year, such as
 * $semester = array ("semester" => "Fall", "year" => "2012")
 * 
 */
function get_semester($date)
{
	$semester = array();
	
	/* Set the timezone */
	date_default_timezone_set('America/Toronto');
	
	$cur_date = DateTime::createFromFormat('Y-m-d', $date);
	$cur_month = $cur_date->format('n');
	
	$semester['year'] = $cur_date->format('Y');
	
	if ($cur_month >= 1 && $cur_month < 5)
	{
		$semester['semester'] = 'Winter';
	}
	elseif ($cur_month >= 5 && $cur_month < 9)
	{
		$semester['semester'] = 'Summer';
	}
	else
	{
		$semester['semester'] = 'Fall';
	}
	
	return $semester;
}

?>