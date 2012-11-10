#! /usr/bin/env python

import MySQLdb as mdb
import sys
import datetime
import re
import math
from datetime import datetime

con = None
open = "08:00:00"
close = "22:00:00"
EVERY = "ALL"

#input
#       start time  (in hours and minutes)
#       duration    (in hours and minutes)
#       date        (Just the day) #This might extend to give more to determine which week
#       campus      (UON, UOD, UOG, ALL)
#       term        (winter, spring, summer, fall) #This is currently not implemented

#Splits the given string time in format 'hh:mm:ss' and array in of the hour, minute, second
def time_pars(time):
    if time == None:        #If the time is null throw null execption
        return
    return re.split(':', time)      #returns ['hh', 'mm', 'ss']

#Calculates the difference between 2 given sets of time
def gapfinder(start, finish):
    FMT = '%H:%M:%S'        #Format the values
    tdelta = []             #Stores the difference in the times
    i = 0
    while i < len(start):   #it is start(n+1) - finish(n)
        tdelta.append(datetime.strptime(start[i], FMT) - datetime.strptime(finish[i], FMT))
        i+=1
    return tdelta       #The length of the gap between the times

#Determines if their is a negative at the front of the given string
def neg(time):
    m = re.search('^-', time)
    if m is not None:
        if m.group(0) == "-":
            return True     #The Time is negativeu
        else:
            return False;
    return 

#Calculate if the given time slot is available on a day in a class
def time_finder(start, finish, required_time):
    a = 0
    FMT = '%H:%M:%S'
    diff = [0 ,0]
    while a < len(start):
        #Calculate the difference between the start and finish times
        diff = ([datetime.strptime(required_time[0], FMT) - datetime.strptime(finish[a], FMT),
        datetime.strptime(start[a], FMT) - datetime.strptime(required_time[1], FMT)])
        
        #If these differences are negative then this time is not available 
        if neg(str(diff[1])) or neg(str(diff[0])):
            a+=1
        else:
            return [required_time[0], start[a]]    #The time is available
	return None
    
try:
	
    database = 'winter2012'             #The database that will be used, GIVEN    
    req_duration = [1,00]               #The minimum duration for the gab, GIVEN
    day = 'T'                           #The day that they are looking for, GIVEN
    required_time = None#["14:00:00"]        #The start time, GIVEN
    if required_time == None:
        find_gaps = True                #Finding time slots
    else:
        find_gaps = False               #Finding a gap over the given time
        #Calculate the finish time 
        final_time = time_pars(required_time[0])
        #Add the minutes
        final_time[1] = (int(final_time[1])+req_duration[1])
        #Add the hours and the carried minutesk
        final_time[0] = str(int((int(final_time[0])+req_duration[0])+math.floor(final_time[1]/60)))
        #Calculate the minutes
        final_time[1] = str(final_time[1]%60)
        if len(final_time[1]) < 2:
            final_time[1] = "0" + final_time[1]
        
        required_time.append(final_time[0]+":"+final_time[1]+":"+final_time[2])
    #print required_time[1]
    #if campus = ALL (EVERY) then all campuses are returned
    campus = "UON"                      #The campus of the rooms, GIVEN
        
    k=0
    avail = []
    room = []
    
    #connect to the database
    con = mdb.connect('localhost', 'jon', 'abc123', database);
    cur = con.cursor()

    #returns the table name
    cur.execute("SHOW TABLES")
    tables = cur.fetchall()    
    
    #Store all the names of the tables
    for table in tables[:]:
        room.append(str(tables[k][0]))
        k+=1
    
    k=0
    
    #Search for all the tables, rooms, in the database
    while k < len(room):
        cur.execute("SELECT class_type FROM "+ room[k] +" ORDER BY FIELD(class_type, 'LEC', 'TUT', 'THS', 'LAB', 'WEB', 'FLD')")
        class_types = cur.fetchall()
        #print class_types
        #if class_types[0][0] == "LEC" or class_types[0][0] == "TUT":
            #print room[k], " usable" 
        #Checks if the requested campus is all campuses
        if campus != EVERY:
            while k < len(room):
                #Checks if the current current room's campus is the requested one
                cur.execute("SELECT campus FROM " + room[k])
                camp = cur.fetchall()
                if campus != camp[0][0]:
                    k+=1        #Not the right campus
                else:
                    break       #The right campus
        
        #Gets all the different start times ordered by the start times from the given campus or campuses
        if campus != EVERY:
            cur.execute("SELECT start_time FROM "+ room[k] +" WHERE day='"+day+"' AND campus ='"+ campus +"' ORDER BY start_time")
        else:
            cur.execute("SELECT start_time FROM "+ room[k] +" WHERE day='"+day+"' ORDER BY start_time")
        
        start_times = cur.fetchall()

        start = []
        i=0
        
        #stores the start times
        for start_time in start_times[:]:
            if i<1 or str(start_time[0]) != start[len(start)-1]:
                start.append(str(start_time[0]))
            i+=1
            
        #gets all the different finish times ordered by the start times from the given campus or campuses
        if campus != EVERY:
            cur.execute("SELECT finish_time FROM "+ room[k] +" WHERE day='"+day+"' AND campus ='"+ campus +"' ORDER BY start_time")
        else:
            cur.execute("SELECT start_time FROM "+ room[k] +" WHERE day='"+day+"' ORDER BY start_time")
        finish_times = cur.fetchall()

        finish = []
        i =0
        #stores the finish times
        for finish_time in finish_times[:]:
            if i<1 or str(finish_time[0]) != finish[len(finish)-1]:
                finish.append(str(finish_time[0]))
            i+=1

        #Adds the limits to list of times to account for the gabs
        #Adds the time 08:00:00 to the start of the finish times list to 
        #account for UOIT opening
        #Adds the time 22:00:00 (10 pm) to the end of the start times list to
        #account for UOIT closing
        start.append(close)
        finish.insert(0, open)
        
        if find_gaps:
            #Finds all the gabs in the schedule
            gaps = gapfinder(start, finish)
            i = 0
            #Finds gabs that are longer than or equal to the requested time
            while i < len(gaps):
                hour = int(time_pars(str(gaps[i]))[0])   #calculates the hours
                minute = int(time_pars(str(gaps[i]))[1]) #calculates the minutes
                if hour == req_duration[0] and minute >= req_duration[1] or hour > req_duration[0]:
                    #Stores the room, duration of the gap, the start time of the
                    #gap and the end time of the gap in order
                    avail.append([room[k], gaps[i], finish[i], start[i]])
                i+=1
        else:
            #Finds the amount of time that the time slot is available for and 
            #does not store it if it is not available 
            found = time_finder(start, finish, required_time)
            if found != None:
                avail.append([room[k], found])
        k+=1

    i = 0
    
    #Displays the results of the search
    if find_gaps:
        while i < len(avail):
            print avail[i][0]
            print "GAP %s" %avail[i][1]
            print "Start %s" %avail[i][2]
            print "End %s" %avail[i][3]
            i+=1
    else:
        while i < len(avail):
            print "Room %s" %avail[i][0]
            print "Available %s" %avail[i][1]
            i+=1
            

except mdb.Error, e:

    print "Error %d: %s" % (e.args[0],e.args[1])
    sys.exit(1)

finally:
    if con:
        con.close()
