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
 */


/*
 * The room request/search form, used to search for an available room on campus
 * 
 * 
 * DEPENDENCIES
 * ------------
 * 
 * This template depends on the array $campus being set with the list of available campuses
 * 
 * $campus = array(	'UOIT North Campus',
 *					'UOIT Downtown Campus',
 *					...
 *					);
 * 
 */

?>
<section id="request_room">
	<div class="page-header">
		<h1>Request Room</h1>
	</div>
	<div class="row">
		<div class="span8">
			<form class="well form-horizontal" action="room_results.php" method="post" accept-charset="UTF-8">
				<fieldset>
					<div class="control-group">
			      		<label for="select_time" class="control-label">Time</label>
				      	<div class="controls">
				      		<select id="select_time" name="select_time" class="input-xlarge">
				      			<option></option>
				      			<?php 
		      						echo '<option>' . $candidates['President'][0] . ' (Candidate)' . '</option>';
		      						echo '<option>' . $incumbents['President'][0] . ' (Incumbent)' . '</option>';
				      			?>
				      		</select>
				      	</div>
		      		</div>
		      		<div class="control-group">
			      		<label for="select_duration" class="control-label">Duration</label>
				      	<div class="controls">
				      		<select id="select_duration" name="select_duration" class="input-xlarge">
				      			<option></option>
				      			<?php 
		      						echo '<option>' . $candidates['Vice President'][0] . ' (Candidate)' . '</option>';
		      						echo '<option>' . $incumbents['Vice President'][0] . ' (Incumbent)' . '</option>';
				      			?>
				      		</select>
				      	</div>
		      		</div>
		      		<div class="control-group">
			      		<label for="select_date" class="control-label">Date</label>
							<div class="controls">
							<class id="dp3" data-date-format="yyyy-mm-dd">
							<!--  Set the current date -->
							<?php
								date_default_timezone_set('America/Toronto');
								$cur_date = date('Y-m-d');
								echo '<input id="select_date" name="select_date"class="input-datelarge" type="text" value="'. $cur_date .'">';
 							?>
							</div>
		      		</div>
		      		<div class="control-group">
			      		<label for="select_campus" class="control-label">Campus</label>
				      	<div class="controls">
				      		<select id="select_campus" name="select_campus" class="input-xlarge">
				      			<option></option>
				      			<?php 
		      						echo '<option>' . $candidates['Treasurer'][0] . ' (Candidate)' . '</option>';
		      						echo '<option>' . $incumbents['Treasurer'][0] . ' (Incumbent)' . '</option>';
				      			?>
				      		</select>
				      	</div>
		      		</div>
		      		<div class="control-group">
			      		<label for="select_num_people" class="control-label">Number of People</label>
				      	<div class="controls">
				      		<select id="select_num_people" name="select_num_people" class="input-xlarge">
				      			<option></option>
				      			<?php 
		      						echo '<option>' . $candidates['Treasurer'][0] . ' (Candidate)' . '</option>';
		      						echo '<option>' . $incumbents['Treasurer'][0] . ' (Incumbent)' . '</option>';
				      			?>
				      		</select>
				      	</div>
		      		</div>

		      		<div class="form-actions">
		            	<button class="btn btn-primary" type="submit" name="search_room" value="Search">Search</button>
		          </div>
				</fieldset>
			</form>
		</div>
	</div>
</section>