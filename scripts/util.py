#!/usr/bin/env python2
################################################################################
# 
# A module containing a collection of useful functions for a variety of tasks
#
################################################################################



################################################################################
# 
# Function which does a reverse-lookup in a dictionary, given the
# value to lookup it returns the key
#
################################################################################
def reverse_lookup(d, v):
    for k in d:
        if d[k] == v:
            return k
    raise ValueError



################################################################################
# 
# Function which converts a tuple with the the date in the format 
# ('Jan', '09', '2012') and returns a string in the format "2012-01-09"
#
################################################################################
def convert_date(date_tup):
    # Map each month to its numeric representation
    months = {'jan': '01', 'feb': '02', 'mar': '03', 'apr': '04', 'may': '05', 
              'jun': '06', 'jul': '07', 'aug': '08', 'sep': '09', 'oct': '10',
              'nov': '11', 'dec': '12'}

    return date_tup[2] + '-' + months[date_tup[0].lower()] + '-' + date_tup[1]



################################################################################
# 
# Function which converts time from the AM/PM tuple format ('2:40', 'pm')
# into military time 14:40:00
#
################################################################################
def convert_time(time):
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


################################################################################
# 
# Function which parses a file of keys and values based on the delimiter sepcifed
# and returns a dicitionary with the corresponding key/value pairs
#
################################################################################
def generate_dictionary(file_name, delim):
    dictionary = {}
    
    print file_name
    # Populate the dictionary
    with open(file_name) as f:
        for line in f:
            (k, v) = line.split(delim)
            dictionary[k] = v.strip()
    
    return dictionary
