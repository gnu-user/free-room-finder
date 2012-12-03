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
	alert(window.location.toString());
	/* Display the statistics when page is loaded */
	busyProfs();
});

/* Get the list of the busiest professors and populate the table */
function busyProfs() {
	console.log('busyprofs');
	$.ajax({
		type: 'GET',
		url: rootURL + '/busyprofs',
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