#!/usr/bin/env python
################################################################################
# 
# A module containing the functions for interfacing with MySQL databases
#
################################################################################
import MySQLdb as mdb

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
        sys.exit(1)

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
        sys.exit(1)

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

#insert the room into the database if it is not already there
def insert_rooms(course_data):
    roomId = get_room_id(course_data[idx_key][alp_key]['room_number'])
    if roomId == 0:
        campus_id = insert_campus(course_data[idx_key]['campus'])
        try:
            cur.execute("""INSERT INTO rooms (name, campusId, room_capacity, 
                        power_outlets, ethernet_ports) VALUES (%s, %d, %d, %b, %b) """ 
                            % ( course_data[idx_key][alp_key]['room_number'],
                                int(campus_id),
                                int(course_data[idx_key]['capacity']),
                                None, None
                              )
            room_id = cur.fetchall()
    return room_id

#Get the room id given the name of the room
def get_room_id(name):
    cur.execute("SELECT roomId FROM rooms WHERE name LIKE %s" % (name))
    return cur.fetchall()

#Insert the campus if it is not already stored
def insert_campus(acr):
    campus_id = get_campus_id(acr)
    if campus_id == 0:
        try:
            cur.execute("INSERT INTO campus (acr, name) VALUES (%d) " 
                        % ( acr, campus_acronyms[acr] )
            campus_id = cur.fetchall()
    return campus_id

#Get the campus id 
def get_campus_id(campus):    
    cur.execute("SELECT campusID FROM campus WHERE acr LIKE %s" % (campus))
    return cur.fetchall()

#Insert the start and finish times if it is not already in the database
def insert_time(start_time, finish_tine):
    start_id = get_time_id(start_time)
    finish_id = get_time_id(finish_time)
    if start_id == 0:
        try:
            cur.execute("INSERT INTO times (time) VALUES (%d) " % ( start_time )
            start_id = cur.fetchall()
    if finish_id == 0:
        try:
            cur.execute("INSERT INTO times (time) VALUES (%d) " % ( finish_time )
            finish_id = cur.fetchall()
    return [start_id, finish_id]

#Get the time id given the time 
def get_time_id(time):
    cur.execute("SELECT timeId FROM times WHERE time LIKE %s" % (time))
    return cur.fetchall()

#Insert the date into the database if it is not already in the database
def insert_date(start_date, finish_date):
    start_id = get_date_id(start_date)
    finish_id = get_date_id(finish_date)
    if start_id == 0:
        try:
            cur.execute("INSERT INTO dates (date) VALUES (%d) " % ( start_date )
            start_id = cur.fetchall()
    if finish_id == 0:
        try:
            cur.execute("INSERT INTO dates (date) VALUES (%d) " % ( finish_date )
            finish_id = cur.fetchall()
    return [start_id, finish_id]

#Get the date id given the date
def get_date_id(time):
    cur.execute("SELECT timeId FROM dates WHERE date LIKE %s" % (date))
    return cur.fetchall()

#Insert the faculty into the database if it is not already in the database
def insert_faculty(code):
    code_id = get_faculty_id(code)
    if code_id == 0:
        try:
            cur.execute("INSERT INTO facutlies (date) VALUES (%d) " % ( code )
            code_id = cur.fetchall()
    return code_id

#Get the faculty id given the faculty code
def get_faculty_id(code):
    cur.execute("SELECT facultyId FROM faculties WHERE code LIKE %s" % (code))
    return cur.fetchall()

#Insert the semesters and years if they are not already in the database
def insert_semesters(year, semester):
    semester_id = get_semesters_id(code) 
    if semester_id == 0:
        try:fashion
            cur.execute("INSERT INTO semesters (year, semester) VALUES (%d, %s) " 
                        % ( year, semester )
            semester_id = cur.fetchall()
    return semester_id

#Get the semester id given the year and semester
def get_semesters_id(year, semester):
    cur.execute("""SELECT semester_id FROM semesters WHERE year = %d AND semester 
                    LIKE %s""" % (semester))
    return cur.fetchall()

#Insert the class type given the acr if it is not already within the database
def insert_class_type(acr):
    class_type_id = get_class_type(acr) 
    if class_type_id == 0:
        try:
            cur.execute("INSERT INTO class_type (acr, semester) VALUES (%s, %s) " % ( acr, class_types[acr] )
            class_type_id = cur.fetchall()
    return class_type_id

#Get the class type given the acr 
def get_class_type(acr):
    cur.execute("SELECT typeId FROM class_type WHERE acr LIKE %s " % (acr))
    return cur.fetchall()

#Insert the professor if it is not already in the database
def insert_professor(professor):
    prof_id = get_prof_id(professor)
    if prof_id == 0:
        try:
            cur.execute("INSERT INTO professors (name) VALUES (%s) " % ( professor ))
            prof_id = cur.fetchall()
    return prof_id

#Get the professor id given the professor name
def get_prof_id(professor):
    cur.execute("SELECT profid FROM professors WHERE name LIKE %s " % (professor))
    return cur.fetchall()

#Insert the course in the database given the database
def insert_course(course_data):
    course_id = get_course_id(course_data[idx_key]['course_name'])
    if course_id == 0:
        faculty_id = insert_faculty(course_data[idx_key]['program_code'])
        try:
            #Need to fix the course data to store the course level
            cur.execute("""INSERT INTO courses (name, course_code, level, facultyId) 
                    VALUES (%s, %s, %s, %d) """ % ( course_data[idx_key]['course_name'], 
                                                    course_data[idx_key]['course_code'],
                                                    course_data[idx_key]['level'],
                                                    faculty_id))
            course_id = cur.fetchall()
    return course_id

#Get course id given the course name
def get_course_id(name):
    cur.execute("SELECT courseId FROM courses WHERE name LIKE %s " % (name))
    return cur.fetchall()

#Insert the offerings into the database along with the rest of tables are filled to
def insert_offerings(course_data):
    course_id = insert_course(course_data)
    type_id = insert_type(course_data[idx_key][alp_key]['class_type'])
    prof_id = insert_professor(course_data[idx_key][alp_key]['teacher_name'])
    room_id = insert_rooms(course_data[idx_key][alp_key]['room_number'])
    start_time = insert_time(course_data[idx_key][alp_key]['start_time'])
    finish_time = insert_time(course_data[idx_key][alp_key]['finish_time'])
    start_date = insert_date(course_data[idx_key][alp_key]['start_date'])
    finish_date = insert_date(course_data[idx_key][alp_key]['finish_date'])

    #Set by default to both weeks
    week_alt = None
    #Need to get the value of the year and semester
    semester_id = None

    if course_data[idx_key]['week1']:
        #Sets to week 1
        week_alt = False

    elif course_data[idx_key]['week2']:
        #Sets to week 2
        week_alt = True

    cur.execute("""INSERT INTO offerings (courseId, crn, section, typeId, 
                registered, day, week_alt, profId, roomId, start_time, end_time,
                start_date, end_date) VALUES (%s, %s, %s, %d) """
                    % ( course_id, 
                        course_data[idx_key]['crn'],
                        course_data[idx_key]['section'],
                        typeId,
                        course_data[idx_key]['registered'],
                        course_data[idx_key]['day'],
                        week_alt,
                        prof_id,
                        room_id,
                        start_time,
                        finish_time,
                        start_date,
                        finish_date,
                        semester_id))


#These need to be declared in a seperate file
class_types =  {'LEC': 'Lecture', 
                'TUT': 'Tutorial', 
                'LAB': 'Laboratory', 
                'THS': 'Thesis/Project', 
                'WEB': 'Web Course',
                'FLD': 'Field Placement'}

campus_acronyms = { 'UON': 'North Oshawa Campus',
                    'UOD': 'Downtown Oshawa Campus',
                    'UOG': 'Campus-Georgian Campus',
                    'TRN': 'Trent at Oshawa Thornton Campus',
                    'WEB': 'Web Course' }