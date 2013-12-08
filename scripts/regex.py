#!/usr/bin/env python2
################################################################################
#
# A module containing all of the compiled regular expression objects used
# by the free room to parse the data
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
import re


# Matches the course info, groups: course name, CRN, program code, course code, course section
re_course_info = re.compile(r'(^.*)\s*\-\s*(\d{5})\s*\-\s*([\w]{2,4})\s*(\d{4}[\w]{0,1})\s*\-*\s*(\d{0,3})')

# Matches course term info, groups: school, term, year
re_course_term = re.compile(r'^([A-Z]*)\s([\w/]*)\s(\d{4})')

# Matches the course level info, ie. Post Secondary, Undergraduate, Graduate, etc.
re_course_level = re.compile(r'\s*(Post\sSecondary,\sUndergraduate|Post\sSecondary|Continuing\sEducation|Graduate,\sUndergraduate|Undergraduate|Graduate|Professional)\s*')

# Matches the prof name, groups: first name, last name
#re_prof_name = re.compile(r'([A-Z]\w*|[\w\s\.\'\,\-]*[A-Z]\w*)\s\(|(TBA)')
re_prof_name = re.compile(r'([a-zA-Z\w\s\.\'\,\-]*?)\s\(|(TBA)')

# Matches the campus, group: campus
re_campus = re.compile(r'UOIT[\s]*\-(Off)*[\s]*([A-Za-z\-\s]*)|(Oshawa\sCampus)')

# NOTE THIS REGEX NEEDS TO BE MORE SPECIFIC
# Matches the course type, group: course type
re_course_type = re.compile(r'^\s([A-Za-z]*\s)')

# Matches the capacity info such as capacity, registered, remaining
re_capacity = re.compile(r'^\d{1,3}$')

# Matches the week1 or week2 info, if no week specified room used every week
re_week_info = re.compile(r'(W1|W2|W3|\&nbsp\;)')

# Matches the course start and end time for room, groups: start time,
# start AM/PM, end time, AM/PM end time
re_course_time = re.compile(r'(\d{1,2}\:\d{2})\s(\w{2})\s+\-\s+(\d{1,2}\:\d{2})\s(\w{2})')

# Matches the course day of the week, group: single char representing day of week
# (Monday=M, Tuesday=T, Wednesday=W, Thursday=R, Friday=F)
re_course_day = re.compile(r'([MTWRF]{1})')

# Matches the room info, groups: room number (ie. UA1350)
# There are also rooms such as SQSH4, which has 4 chars
re_room = re.compile(r'\s*?([A-Z]{1,5}\d{1,4})\<\/td\>')

# Matches rooms that do not exist, such as for online courses which can be
# labeled "TBA", "Online", "Virtual ONLINE", etc.
re_room_online = re.compile(r'\s*TBA\s*|\s*Virtual\s*ONLINE\d*|\s*Virtual\s*|\s*Off\sSite\sLocation\s*|\s*Online\s*', re.IGNORECASE)


# Matches the course start and end time, groups: start month, start day,
# starting year, ending month, ending day, ending year
re_course_date = re.compile(r'([A-Z]{1}[a-z]{2,3})\s([0123]{1}\d{1}).*\s(20\d{2})\s\-\s([A-Z]{1}[a-z]{2,3})\s([0123]{1}\d{1}).*\s(20\d{2})')

# Matches the type of class, Lecture, Tutorial, Laboratory, groups: class_type
re_class_type = re.compile(r'(\s*Lecture\s*|\s*Tutorial\s*|\s*Laboratory\s*|\s*Thesis\/Project\s*|\s*Web\sCourse\s*|\s*Work\sTerm\s*|\s*Seminar\s*|\s*Other\s*|\s*Independent\sStudy\s*|\s*Field\sPlacement\s*)')
