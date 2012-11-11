#!/bin/env python2
################################################################################
# 
# A module containing a collection of dictionaries for the various acronyms used
# for campus names, faculties , and class types
#
################################################################################

# Dictionary mapping the semesters to the appropriate post data
semester = {'winter': '01', 
            'fall': '09', 
            'summer': '05'}

# Dictionary mapping campuses to parts of the url search strings
campuses = { 'ALL': '&sel_camp=dummy&sel_camp=%25',
             'UON': '&sel_camp=UON&sel_camp=%25',
             'UOD': '&sel_camp=UOD&sel_camp=%25',
             'UOG': '&sel_camp=dummy&sel_camp=UOG%25'}

# Dictionary mapping campus acronyms to the full campus name
campus_acronyms = { 'UON': 'North Oshawa Campus',
                    'UOD': 'Downtown Oshawa Campus',
                    'UOG': 'Campus-Georgian Campus',
                    'TRN': 'Trent at Oshawa Thornton Campus',
                    'WEB': 'Web Course' }

# Dictionary mapping class types to acronyms
class_types = { 'LEC': 'Lecture', 
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
faculties = {   'ALSU': 'Academic Learning & Success',
                'ANTH': 'Antropology',
                'APBS': 'Applied Bioscience',
                'BIOL': 'Biology',
                'BUSI': 'Business',
                'CANN': 'Canadian Studies',
                'CHEM': 'Chemistry',
                'CHIN': 'Chinese Studies',
                'COMM': 'Communications',
                'CDEV': 'Community Development',
                'CSCI': 'Computer Science',
                'ECON': 'Economics',
                'EDUC': 'Education',
                'ENGR': 'Engineering',
                'ENGL': 'English',
                'ENVS': 'Environmental Science',
                'FSCI': 'Forensic Science',
                'FREN': 'French',
                'GRMN': 'German',
                'HLSC': 'Health Science',
                'HIST': 'History',
                'INFR': 'Information Technology',
                'LGLS': 'Legal Studies',
                'MITS': 'Masters of IT Security',
                'MTSC': 'Materials Science',
                'MATH': 'Mathematics',
                'MLSC': 'Medical Lab Science',
                'MCSC': 'Modelling & Computational Scie',
                'NUCL': 'Nuclear',
                'NURS': 'Nursing',
                'PHIL': 'Philosophy',
                'PHY': 'Physics',
                'POSC': 'Political Science',
                'PSYC': 'Psychology',
                'PUBP': 'Public Policy',
                'RADI': 'Radiation Science',
                'SCIE': 'Science',
                'SCCO': 'Science Co-op Work Term',
                'SSCI': 'Social Science',
                'SOCI': 'Sociology',
                'STAT': 'Statistics'}