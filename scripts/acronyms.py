#!/bin/env python2
###############################################################################
#
# A module containing a collection of dictionaries for the various acronyms
# used for campus names, faculties , and class types
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

# Dictionary mapping the semesters to the appropriate post data
semester = {'winter': '01',
            'fall': '09',
            'summer': '05'}

# Dictionary mapping campuses to parts of the url search strings
campuses = {'ALL': '&sel_camp=%25',
            'UON': '&sel_camp=UON',
            'UOD': '&sel_camp=UOD',
            'UOG': '&sel_camp=dummy'}

# Dictionary mapping campus acronyms to the full campus name
campus_acronyms = {'UON': 'North Oshawa Campus',
                   'UOD': 'Downtown Oshawa Campus',
                   'UOG': 'Campus-Georgian Campus',
                   'TRN': 'Trent at Oshawa Thornton Campus',
                   'WEB': 'Web Course',
                   'OTH': 'Other Campus'}

# Dictionary mapping class types to acronyms
class_types = {'LEC': 'Lecture',
               'TUT': 'Tutorial',
               'LAB': 'Laboratory',
               'THS': 'Thesis/Project',
               'WEB': 'Web Course',
               'WRK': 'Work Term',
               'SEM': 'Seminar',
               'IND': 'Independent Study',
               'OTH': 'Other',
               'FLD': 'Field Placement'}

# Dictionary mapping all of the faculty acronyms to descriptions
faculties = {'AEDT': 'Adult Education',
             'ALSU': 'Academic Learning & Success',
             'ANTH': 'Anthropology',
             'APBS': 'Applied Bioscience',
             'BIOL': 'Biology',
             'BUSI': 'Business',
             'CANN': 'Canadian Studies',
             'CDEV': 'Community Development',
             'CHEM': 'Chemistry',
             'CHIN': 'Chinese Studies',
             'COMM': 'Communications',
             'CSCI': 'Computer Sciences',
             'CURS': 'Curriculum Studies',
             'ECON': 'Economics',
             'EDUC': 'Education Studies',
             'ENGL': 'English',
             'ENGR': 'Engineering',
             'ENVR': 'Environmental Studies',
             'ENVS': 'Environmental Sciences',
             'FREN': 'French',
             'FSCI': 'Forensic Sciences',
             'GEOG': 'Geography',
             'GRMN': 'German',
             'HIST': 'History',
             'HLSC': 'Health and Life Sciences',
             'IDST': 'Interdisciplinery Studies',
             'INFR': 'Information Technologies',
             'LGLS': 'Legal Studies',
             'MATH': 'Mathematics',
             'MCSC': 'Modelling & Computational Scie',
             'MITS': 'Masters of IT Security',
             'MLSC': 'Medical Laboratory Practises',
             'MTSC': 'Materials Science',
             'NUCL': 'Nuclear Technologies',
             'NURS': 'Nursing',
             'PHIL': 'Philosophy',
             'PHY':  'Physics',
             'POSC': 'Political Sciences',
             'PSYC': 'Psychology',
             'PUBP': 'Public Policy',
             'RADI': 'Radiation Science',
             'SCCO': 'Science Co-op Work Term',
             'SCIE': 'Sciences',
             'SOCI': 'Sociology',
             'SSCI': 'Social Sciences',
             'STAT': 'Statistics',
             'WMST': 'Women Studies'}
