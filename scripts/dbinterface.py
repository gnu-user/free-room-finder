#!/usr/bin/env python
################################################################################
# 
# A module containing the functions for interfacing with MySQL databases
#
################################################################################
import MySQLdb as mdb
from acronyms import *

################################################################################
# 
# Function that connects to the database specified and returns a connection
# object
#
################################################################################
def connect_db(user, passwd, domain, db_name):
    try:
        con = mdb.connect(domain, user, passwd, db_name)
        return con
    except mdb.Error, e:
        print "Error %d: %s" % (e.args[0],e.args[1])
        

#Doesnt create DB since the database is created from a Schema file once.

################################################################################
# 
# Function that determines if a specified table already exists, returns True
# if the table specifed exists
#
################################################################################    
def table_exists(con, table):
    try:
        cur = con.cursor()
        cur.execute("SHOW TABLES")

    except mdb.Error, e:
        print "Error %d: %s" % (e.args[0],e.args[1])
        

    # Check if table exists 
    for row in cur.fetchall():
        if row[0] == table:
            return True
            
    return False

################################################################################
# 
# Function that creates a table for the room number specified
#
################################################################################    
#def insert_faculty(course_data):
    #Use of Insert IGNORE is to allow for duplications to be silently skipped
    #cur.execute("INSERT IGNORE")

#################################################
#Do a test with the data from the db-interface  #
#################################################

#insert the room into the database if it is not already there
def insert_rooms(con, room_number, campus, capacity):
    room_id = get_room_id(con, room_number)
    if room_id == 0:
        campus_id = insert_campus(con, campus)
        try:
            cur = con.cursor()
            cur.execute("""INSERT INTO rooms 
                        (
                            name, 
                            campusId, 
                            room_capacity, 
                            power_outlet, 
                            ethernet_ports
                        ) 
                        VALUES 
                        (
                            '%s', %d, 
                            %d, %d, %d
                        );""" 
                        % ( room_number,
                            int(campus_id),
                            int(capacity),
                            0, 0))
            room_id = cur.lastrowid
        except mdb.Error, e:
            print "Error %d: %s" % (e.args[0],e.args[1])
            
    return room_id

#Get the room id given the name of the room
def get_room_id(con, room_number):
    try:
        cur = con.cursor()
        cur.execute("SELECT roomId FROM rooms WHERE name LIKE ('%s');" % (room_number))
        re_id = cur.fetchone()
        if re_id is None:
            return 0
        else:
            return re_id
    except mdb.Error, e:
        print "Error %d: %s" % (e.args[0],e.args[1])
        

#Insert the campus if it is not already stored
def insert_campus(con, campus):
    campus_id = get_campus_id(con, campus)
    if campus_id == 0:
        try:
            cur = con.cursor()
            cur.execute("INSERT INTO campus (acr, name) VALUES ('%s', '%s');" % ( campus, campus_acronyms[campus] ))
            campus_id = cur.lastrowid
        except mdb.Error, e:
            print "Error %d: %s" % (e.args[0],e.args[1])
    return campus_id

#Get the campus id 
def get_campus_id(con, campus):
    try:
        cur = con.cursor()
        cur.execute("SELECT campusID FROM campus WHERE acr LIKE '%s';" % (campus))
        re_id = cur.fetchone()
        if re_id is None:
            return 0
        else:
            return re_id
    except mdb.Error, e:
        print "Error %d: %s" % (e.args[0],e.args[1])
        

#Insert the start and finish times if it is not already in the database
def insert_time(con, start_time, finish_tine):
    start_id = get_time_id(con, start_time)
    finish_id = get_time_id(con, finish_time)
    if start_id == 0:
        try:
            cur = con.cursor()
            cur.execute("INSERT INTO times (time) VALUES ('%s');" % ( start_time ))
            start_id = cur.lastrowid
        except mdb.Error, e:
            print "Error %d: %s" % (e.args[0],e.args[1])
            
    if finish_id == 0:
        try:
            cur = con.cursor()
            cur.execute("INSERT INTO times (time) VALUES ('%s');" % ( finish_time ))
            finish_id = cur.lastrowid
        except mdb.Error, e:
            print "Error %d: %s" % (e.args[0],e.args[1])
            
    return [start_id, finish_id]

#Get the time id given the time 
def get_time_id(con, time):
    try:
        cur = con.cursor()
        cur.execute("SELECT timeId FROM times WHERE time LIKE '%s';" % (time))
        re_id = cur.fetchone()
        if re_id is None:
            return 0
        else:
            return re_id
    except mdb.Error, e:
        print "Error %d: %s" % (e.args[0],e.args[1])
        

#Insert the date into the database if it is not already in the database
def insert_date(con, start_date, finish_date):
    start_id = get_date_id(con, start_date)
    finish_id = get_date_id(con, finish_date)
    if start_id == 0:
        try:
            cur = con.cursor()
            cur.execute("INSERT INTO dates (date) VALUES ('%s');" % ( start_date ))
            start_id = cur.lastrowid
        except mdb.Error, e:
            print "Error %d: %s" % (e.args[0],e.args[1])
            
    if finish_id == 0:
        try:
            cur = con.cursor()
            cur.execute("INSERT INTO dates (date) VALUES ('%s');" % ( finish_date ))
            finish_id = cur.lastrowid
        except mdb.Error, e:
            print "Error %d: %s" % (e.args[0],e.args[1])
            
    return [start_id, finish_id]

#Get the date id given the date
def get_date_id(con, date):
    try:
        cur = con.cursor()
        cur.execute("SELECT dateId FROM dates WHERE date LIKE '%s';" % (date))
        re_id = cur.fetchone()
        if re_id is None:
            return 0
        else:
            return re_id
    except mdb.Error, e:
        print "Error %d: %s" % (e.args[0],e.args[1])
        

#Insert the faculty into the database if it is not already in the database
def insert_faculty(con, faculty):
    faculty_id = get_faculty_id(con, faculty)
    if faculty_id == 0:
        try:
            cur = con.cursor()
            cur.execute("INSERT INTO faculties (code, name) VALUES ('%s', '%s');" % ( faculty, faculties[faculty] ))
            code_id = cur.lastrowid
        except mdb.Error, e:
            print "Error %d: %s" % (e.args[0],e.args[1])
            
    return faculty_id

#Get the faculty id given the faculty code
def get_faculty_id(con, faculty):
    try:
        cur = con.cursor()
        cur.execute("SELECT facultyId FROM faculties WHERE code LIKE '%s';" % (faculty))
        re_id = cur.fetchone()
        if re_id is None:
            return 0
        else:
            return re_id
    except mdb.Error, e:
        print "Error %d: %s" % (e.args[0],e.args[1])
        

#Insert the semesters and years if they are not already in the database
def insert_semesters(con, year, semester):
    semester_id = 0
    semester_id = get_semesters_id(con, year, semester) 
    #print (semester_id)
    if semester_id == 0:
        try:
            cur = con.cursor()
            cur.execute("INSERT INTO semesters (year, semester) VALUES ('%s', '%s');" % ( year, semester ))
            semester_id = cur.lastrowid
        except mdb.Error, e:
            print "Error %d: %s" % (e.args[0],e.args[1])
            
    return semester_id

#Get the semester id given the year and semester
def get_semesters_id(con, year, semester):
    try:
        cur = con.cursor()
        cur.execute("""SELECT semesterId FROM semesters WHERE year LIKE '%s' AND semester 
                    LIKE '%s';""" % (year, semester))
        re_id = cur.fetchone()
        if re_id is None:
            return 0
        else:
            return re_id
    except mdb.Error, e:
        print "Error %d: %s" % (e.args[0],e.args[1])
        

#Insert the class type given the acr if it is not already within the database
def insert_class_type(con, class_type):
    class_type_id = get_class_type(con, class_type) 
    if class_type_id == 0:
        try:
            cur = con.cursor()
            cur.execute("INSERT INTO class_type (acr, type) VALUES ('%s', '%s') ;" % ( class_type, class_types[class_type] ))
            class_type_id = cur.lastrowid
        except mdb.Error, e:
            print "Error %d: %s" % (e.args[0],e.args[1])
            
    return class_type_id

#Get the class type given the acr 
def get_class_type(con, class_type):
    try:
        cur = con.cursor()
        cur.execute("SELECT typeId FROM class_type WHERE acr LIKE '%s' ;" % (class_type))
        re_id = cur.fetchone()
        if re_id is None:
            return 0
        else:
            return re_id
    except mdb.Error, e:
        print "Error %d: %s" % (e.args[0],e.args[1])
        

#Insert the professor if it is not already in the database
# TODO if we could ever link professors to faculties this may avoid
# the inevitable conflict where two profs have same first & last name
def insert_professor(con, professor):
    prof_id = get_prof_id(con, professor)
    print (prof_id)
    if prof_id == 0:
        try:
            cur = con.cursor()
            cur.execute("""INSERT INTO professors (name) VALUES ('%s');""" % (professor))
            prof_id = cur.lastrowid
            print (prof_id)
        except mdb.Error, e:
            print "Error %d: %s" % (e.args[0],e.args[1])
            
    return prof_id

#Get the professor id given the professor name
def get_prof_id(con, professor):
    try:
        cur = con.cursor()
        cur.execute("""SELECT profid FROM professors WHERE name LIKE '%s';""" % (professor))
        re_id = cur.fetchone()
        if re_id is None:
            return 0
        else:
            return re_id
    except mdb.Error, e:
        print "Error %d: %s" % (e.args[0],e.args[1])
        

#Insert the course in the database given the database
def insert_course(con, name, course_code, level, program_code):
    course_id = get_course_id(con, name)
    if course_id == 0:
        faculty_id = insert_faculty(con, program_code)
        try:
            cur = con.cursor()
            cur.execute("""INSERT INTO courses 
                           (
                                name, 
                                course_code, 
                                level, 
                                facultyId
                            ) 
                            VALUES 
                            (
                                '%s', '%s', 
                                '%s', %d
                            )""" 
                            % ( name, course_code, level, faculty_id))
            course_id = cur.lastrowid
        except mdb.Error, e:
            print "Error %d: %s" % (e.args[0],e.args[1])
            
    return course_id

#Get course id given the course name
def get_course_id(con, name):
    try:
        cur = con.cursor()
        cur.execute("SELECT courseId FROM courses WHERE name LIKE '%s' " % (name))
        re_id = cur.fetchone()
        if re_id is None:
            return 0
        else:
            return re_id
    except mdb.Error, e:
        print "Error %d: %s" % (e.args[0],e.args[1])
        

################################################################################
# Insert the offerings into the database along with the rest of tables are 
# filled to
#
# The offerings argument is a dictionary with the following parameters
# offerings = { 'course_name':      'Introductory Sociology',
#               'crn':              '70483',
#               'program_code':     'SOCI',
#               'course_code':      '1000U',
#               'course_section':   '001',
#               'level':            'Undergraduate',
#               'class_type':       'LEC',
#               'teacher_name':     'Mihai Beligan',
#               'room_number':      'UA1350', 
#               'campus':           'UON', 
#               'capacity':         250,
#               'registered':       236,
#               'start_time':       '08:10:00',
#               'finish_time':      '10:00:00', 
#               'start_date':       '2012-01-09',
#               'finish_date':      '2012-04-13',
#               'day':              'M',
#               'week_alt':         'None/True/False',    
#               'year':             '2012',
#               'semester':         'Fall'
#             }
# 
################################################################################
def insert_offering(con, offerings):

    # Set any offerings that are None as the equivalent NULL type in SQL
    for item in offerings:
        if offerings[item] is None:
            offerings[item] = 'NULL'

    course_id = insert_course(  offerings['con'], 
                                offerings['course_name'],
                                offerings['course_code'],
                                offerings['level'],
                                offerings['program_code'])

    type_id = insert_class_type(offerings['con'], offerings['class_type'])
    
    prof_id = insert_professor(oferings['con'], offerings['teacher_name'])
    
    room_id = insert_rooms( offerings['con'], 
                            offerings['room_number'], 
                            offerings['campus'], 
                            offerings['capacity'])

    time = insert_time( offerings['con'], 
                        offerings['start_time'], 
                        offerings['finish_time'])

    date = insert_date( offerings['con'], 
                        offerings['start_date'], 
                        offerings['finish_date'])

    # Need to get the value of the year and semester
    semester_id = insert_semesters( offerings['con'], 
                                    offerings['year'], 
                                    offerings['semester'])
    
    #print ('course_id', course_id)
    #print ('type_id', type_id)
    #print ('prof', prof_id)
    #print ('room', room_id)
    #print ('time[0]', time[0])
    #print ('time[1]', time[1])
    #print ('date[0]', date[0])
    #print ('date[1]', date[1])
    #print ('semester', semester_id)

    # Sets to week 1 (True)
    if week_alt:
        week_alt = 1
    # Sets to week 2 (False)
    elif not week_alt:
        week_alt = 0

    # TODO FIX HAX put single quotes around values that are not NULL
    if offerings['day'] != 'NULL':
        offerings['day'] = '\'' + offerings['day'] + '\''

    try:
        cur = con.cursor()
        cur.execute("""INSERT INTO offerings 
                    (
                        courseId, 
                        crn, 
                        course_section, 
                        typeId, 
                        registered, 
                        day, 
                        week_alt, 
                        profId, 
                        roomId, 
                        start_time, 
                        end_time,
                        start_date, 
                        end_date, 
                        semesterId
                    ) 
                    VALUES 
                    (
                        %d, '%s', 
                        '%s', %d,
                        %d, %s, 
                        %s, %d, 
                        %d, %d, 
                        %d, %d, 
                        %d, %d
                    )"""
                    % ( offerings['course_id'], 
                        offerings['crn'],
                        offerings['course_section'],
                        type_id,
                        offerings['registered'],
                        offerings['day'],
                        offerings['week_alt'],
                        prof_id,
                        room_id,
                        time[0],
                        time[1],
                        date[0],
                        date[1],
                        semester_id ))
    except mdb.Error, e:
        print "Error %d: %s" % (e.args[0],e.args[1])


con = None
user = ''
passwd = ''
domain = 'localhost'
db_name = ''

con = connect_db(user, passwd, domain, db_name)

course_name = "Introductory Sociology"
course_code = "1000U"
level = "Undergraduate"
program_code = "SOCI"
class_type = "LEC"
teacher_name = "Judith Grant"
room_number = "UA1350" 
campus = "UON"
capacity = 250
start_time = "8:10:00"
finish_time = "10:00:00"
start_date = "2012-01-09"
finish_date = "2012-04-23"
crn = 70483
course_section = "001"
registered = 2
day = "T"
week1 = False
week2 = False
year = '2012'
semester = 'Winter'

insert_professor(con, teacher_name)


#insert_offering(con, course_name, course_code, level, program_code, class_type, 
#                teacher_name, room_number, campus, capacity, start_time,
#                finish_time, start_date, finish_date, crn, course_section, 
#                registered, day, week1, week2, year, semester)

# Commit all changes to the database before closing connection
con.commit()

# Finally, close connection to database
con.close()