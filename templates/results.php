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
 * Displays the results, a list of the rooms found that are available during the
 * time/duration specified.
 * 
 * 
 * DEPENDENCIES
 * ------------
 * 
 * This template depends on the $available array containing the room, start, and end 
 * time that are available for the day.
 * 
 *    $available = array( array("room"       => "UA1350",
 *                            	"starttime"  => "10:10:00",
 *                             	"endtime"    => "11:00:00"
 *                             )
 *                       );
 */

?>
<section id="request_room">
	<div class="page-header">
		<h1>Room Results</h1>
	</div>
	<div class="row">
		<div class="span8">
			<form class="well form-horizontal" action="index.php" method="post" accept-charset="UTF-8">
				<h3>Requested Information</h3>
				<fieldset>
					<div class="control-group">
			      		<label for="select_time" class="control-label">Time</label>
				      	<div class="controls">
				      		<input type="text" placeholder="14:10" disabled>
				      	</div>
		      		</div>
		      		<div class="control-group">
			      		<label for="select_duration" class="control-label">Duration</label>
				      	<div class="controls">
				      		<input type="text" placeholder="2 hours" disabled>
				      	</div>
		      		</div>
		      		<div class="control-group">
			      		<label for="select_date" class="control-label">Date</label>
							<div class="controls">
							<input type="text" placeholder="12-02-2012" disabled>
							</div>
		      		</div>
		      		<div class="control-group">
			      		<label for="select_campus" class="control-label">Campus</label>
				      	<div class="controls">
				      		<input type="text" placeholder="North Oshawa" disabled>
				      	</div>
		      		</div>
		      		<div class="control-group">
			      		<label for="select_numberofpeople" class="control-label">Number of People</label>
				      	<div class="controls">
				      		<input type="text" placeholder="4" disabled>
				      	</div>
		      		</div>
          <h3>Rooms Found</h3>
            <table class="table table-striped">
              <thead>
                <tr>
                  <th width=50px>Select</th>	
                  <th width=50px>Room</th>
                  <th width=50px>Start Time</th>
                  <th width=50px>End Time</th>
                  <th width=100px>Estimated <br>Number of People</th>
                  <th width=100px>Average <br>Number of People</th>
                </tr>
              </thead>
              <tbody>
                <!-- Display the rooms found -->
                <?php 
	                foreach ($available as $room)
	                {
	                	echo '<tr>';
	                		echo '<td>';
	                			echo '<input type="radio" name="group1">';
	                		echo '</td>';
	                		echo '<td>' . $room['room'] .'</td>';
	                		echo '<td>' . $room['starttime'] . '</td>';
	                		echo '<td>' . $room['endtime'] . '</td>';
	                		echo '<td>10</td>';
	                		echo '<td>30</td>';
	                	echo '</tr>';
	                }
                ?>
              </tbody>
            </table>
		      		<div class="form-actions">
		            	<button class="btn btn-primary" type="submit" name="book_room" value="Book Room">Book Room</button>
		          </div>
				</fieldset>
			</form>
		</div>
	</div>
</section>