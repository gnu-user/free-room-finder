/*
 *  Free Room Finder Website
 *
 *
 *  Authors -- Crow's Foot Group
 *  -------------------------------------------------------
 *
 *  Jonathan Gillett
 *  Joseph Heron
 *  Amit Jain
 *  Wesley Unwin
 *  Anthony Jihn
 * 
 *
 *  License
 *  -------------------------------------------------------
 *
 *  Copyright (C) 2012 Crow's Foot Group
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
 *
 */

/* The root URL for the RESTful services */
var rootURL = "http://free-room.dom/api";


$(document).ready(function () {
	/* Display the statistics when page is loaded */
	
	busyProfs();
	plotEnrollmentAll();
	
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
function plotEnrollmentAll() {
	console.log('plotEnrollmentAll');
	var chart;
    chart = new Highcharts.Chart({
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
                pointStart: 2003,
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
            data: [10, 143, 369, 640, 1005, 1436, 2063, 3057, 4618, 6112]
        }]
    });
}