Queries
-------

Need to retrieve:
All of the 

Get all campuses:

SELECT
	arc,
	name
FROM 
	campuses;


SELECT
	year, 
	semester
FROM 
	semesters
WHERE
	year = {cur_year} AND
	semester = {cur_semester} OR
	year = {cur_year + 1} AND
	semester = {cur_semester + 1};


