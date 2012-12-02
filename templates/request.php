<?php
/*
 *  UOIT/DC Computer Science Club Elections Website
 *  Copyright (C) 2012 UOIT/DC Computer Science Club
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
 * The final election voting form, displays a form with the list of candidates and
 * incumbents for each position.
 * 
 * 
 * DEPENDENCIES
 * ------------
 * 
 * This template depends on the arrays $candidates and incumbents containing the positions
 * and the nominee for each position
 * 
 * An array mapping the positions to the incumbent
 * $candidates = array(	'President'         => '',
 *						'Vice President'    => '',
 *						'Coordinator'       => '',
 *						'Treasurer'         => ''
 *					   );
 *	
 * An array mapping the positions to the incumbent
 * $incumbents = array( 'President'         => '',
 *						'Vice President'    => '',
 *						'Coordinator'       => '',
 *						'Treasurer'         => ''
 *						);
 */

?>
<section id="request_room">
	<div class="page-header">
		<h1>Request Room</h1>
	</div>
	<div class="row">
		<div class="span8">
			<form class="well form-horizontal" action="index.php" method="post" accept-charset="UTF-8">
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
							<class id="dp3" data-date="12-02-2012" data-date-format="dd-mm-yyyy">
							<input id="dp1" class="input-datelarge" type="text" value="12-02-2012">
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
			      		<label for="select_numberofpeople" class="control-label">Number of People</label>
				      	<div class="controls">
				      		<select id="select_numberofpeople" name="select_numberofpeople" class="input-xlarge">
				      			<option></option>
				      			<?php 
		      						echo '<option>' . $candidates['Treasurer'][0] . ' (Candidate)' . '</option>';
		      						echo '<option>' . $incumbents['Treasurer'][0] . ' (Incumbent)' . '</option>';
				      			?>
				      		</select>
				      	</div>
		      		</div>

		      		<div class="form-actions">
		            	<button class="btn btn-primary" type="submit" name="election_vote" value="Submit Vote">Search</button>
		          </div>
				</fieldset>
			</form>
		</div>
	</div>
</section>