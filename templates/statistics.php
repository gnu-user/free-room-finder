<?php
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
 ?>
<section id="login">
	<div class="page-header">
		<h1>Statistics</h1>
	</div>
	<div class="row">
		<div class="span6">
		  <h2>Busiest Profs</h2>
	      <table id="tbl_busyprofs" class="table">
	        <thead>
	          <tr>
	            <th>Name</th>
	            <th>Number of Students</th>
	          </tr>
	        </thead>
	        <tbody id="tbody_busyprofs">
	        </tbody>
	      </table>
	    </div>
		<div class="span6">
		  <h2>UOIT Enrollment</h2>
		  <div id="enrollment_all"></div>
		</div>
	</div>
</section>