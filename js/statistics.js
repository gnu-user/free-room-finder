/*
 *  Free Room Finder Website
 *
 *  Copyright (C) 2013 Jonathan Gillett and Joseph Heron
 *  All rights reserved.
 *
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU Affero General Public License as
 *  published by the Free Software Foundation, either version 3 of the
 *  License, or (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU Affero General Public License for more details.
 *
 *  You should have received a copy of the GNU Affero General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

/* The root URL for the RESTful services */
var rootURL = "http://free-room.dom/api";


$(document).ready(function () {
	/* Display the statistics when page is loaded */
	if (window.location.pathname.match(/statistics\.php/))
	{
		busyProfs();
		totalEnrollment();
	}
});

/* Get the list of the top 10 busiest professors and populate the table */
function busyProfs() {
	console.log('busyprofs');
	$.ajax({
		type: 'GET',
		url: rootURL + '/busyprofs/10',
		dataType: "json", // data type of response
		success: displayTable
	});
}

/* Get the total enrollment for all students and plot the chart */
function totalEnrollment() {
	console.log('busyprofs');
	$.ajax({
		type: 'GET',
		url: rootURL + '/totalregistered',
		dataType: "json", // data type of response
		success: plotEnrollmentAll
	});
}


/* Function which populates the busiest profs table with the top 10
 * busiest professors
 */
function displayTable(data) {
	console.log('displayTable');
	// JAX-RS serializes an empty list as null, and a 'collection of one' as an object (not an 'array of one')
	var list = data == null ? [] : (data.busyProfs instanceof Array ? data.busyProfs : [data.busyProfs]);

	$.each(list, function(index, prof) {
		$('#tbody_busyprofs').append('<tr><td>' + prof.professor + '</td><td>' + prof.student_num + '</td></tr>');
	});
}

/* Function which plots the student enrollment for all students */
function plotEnrollmentAll(data) {
	console.log('plotEnrollmentAll');
	var chart;
    var options = {
        chart: {
            renderTo: 'enrollment_all',
            type: 'area'
        },
        title: {
            text: 'UOIT Enrollment'
        },
        xAxis: {
            labels: {
                formatter: function() {
                    return this.value; // clean, unformatted number for year
                }
            }
        },
        yAxis: {
            title: {
                text: 'Number of Students'
            },
            labels: {
                formatter: function() {
                    return this.value / 1000 +'k';
                }
            }
        },
        tooltip: {
            formatter: function() {
                return this.series.name +' enrolled <b>'+
                    Highcharts.numberFormat(this.y, 0) +'</b><br/>students in '+ this.x;
            }
        },
        plotOptions: {
            area: {
                pointStart: 2005,
                marker: {
                    enabled: false,
                    symbol: 'circle',
                    radius: 2,
                    states: {
                        hover: {
                            enabled: true
                        }
                    }
                }
            }
        },
        series: [{
            name: 'UOIT',
            data: []
        }]
    };
    
    /* Get the enrollment data from the REST api using AJAX and add it to the series */
	console.log('enrollmentAllGetData');
	// JAX-RS serializes an empty list as null, and a 'collection of one' as an object (not an 'array of one')
	var list = data == null ? [] : (data.totalRegistered instanceof Array ? data.totalRegistered : [data.totalRegistered]);
	var arr_registered = [];
	var approx_reg = 0;
	
	$.each(list, function(index, enrollment) {
		/* Approximate the number of students attending as total / 5 (assume avg. course load of 5) */
		approx_reg = parseInt(enrollment.registered) / 5;
		
		/* Add the approx number of registered students to the plotting data */
		options.series[0].data.push(approx_reg);
	});
	
	chart = new Highcharts.Chart(options);
}