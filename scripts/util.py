#!/usr/bin/env python2
################################################################################
#
# A module containing a collection of useful functions for a variety of tasks
#
# Copyright (C) 2013, Jonathan Gillett, Joseph Heron, and Daniel Smullen
# All rights reserved.
#
#
# This program is free software: you can redistribute it and/or modify
# it under the terms of the GNU General Public License as published by
# the Free Software Foundation, either version 3 of the License, or
# (at your option) any later version.
#
# This program is distributed in the hope that it will be useful,
# but WITHOUT ANY WARRANTY; without even the implied warranty of
# MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
# GNU General Public License for more details.
#
# You should have received a copy of the GNU General Public License
# along with this program.  If not, see <http://www.gnu.org/licenses/>.
#
###############################################################################


def reverse_lookup(d, v):
    """A function which does a reverse-lookup in a dictionary, given the
    value to lookup it returns the key.
    """
    for k in d:
        if d[k] == v:
            return k
    raise ValueError


def convert_date(date_tup):
    """A function which converts a tuple with the the date in the format
    ('Jan', '09', '2012') and returns a string in the format "2012-01-09"
    """
    # Map each month to its numeric representation
    months = {'jan': '01', 'feb': '02', 'mar': '03', 'apr': '04', 'may': '05',
              'jun': '06', 'jul': '07', 'aug': '08', 'sep': '09', 'oct': '10',
              'nov': '11', 'dec': '12'}

    return date_tup[2] + '-' + months[date_tup[0].lower()] + '-' + date_tup[1]


def convert_time(time):
    """A function which converts time from the AM/PM tuple format ('2:40', 'pm')
    into military time 14:40:00.
    """
    # Split the time up into hours and minutes
    (hour, minute) = time[0].split(':')
    military_time = ""

    # I hate doing this, there must be a library FFS
    if hour == '12' and time[1].lower() == 'pm':
        military_time = hour + ':' + minute + ':' + '00'
    elif hour == '12' and minute == '00' and time[1].lower() == 'am':
        military_time = '24' + ':' + '00' + ':' + '00'
    elif hour == '12' and time[1].lower() == 'am':
        military_time = '24' + ':' + minute + ':' + '00'
    elif time[1].lower() == 'pm':
        # Add 12 hours to the time
        hour = str(int(hour) + 12)  # Why can't python do simple addition of int and string like ruby
        military_time = hour + ':' + minute + ':' + '00'
    elif time[1].lower() == 'am':
        # Add a leading 0 for single digit time
        if len(hour) == 1:
            hour = '0' + hour
        military_time = hour + ':' + minute + ':' + '00'

    return military_time


def generate_dictionary(file_name, delim):
    """A function which parses a file of keys and values based on the delimiter
    sepcifed and returns a dicitionary with the corresponding key/value pairs.
    """
    dictionary = {}

    print file_name
    # Populate the dictionary
    with open(file_name) as f:
        for line in f:
            (k, v) = line.split(delim)
            dictionary[k] = v.strip()

    return dictionary
