#!/bin/bash

YEARS=("2005" "2006" "2007" "2008" "2009" "2010" "2011" "2012" "2013")
SEMESTERS=("fall" "winter")

for YEAR in "${YEARS[@]}"
do
    for SEMESTER in "${SEMESTERS[@]}"
    do
        python2 db-generate-room.py ${SEMESTER} ${YEAR} &> logs/${SEMESTER}_${YEAR}.log
    done
done