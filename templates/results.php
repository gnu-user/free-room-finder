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
 * This template depends on the follow variables to display the information that a user
 * requested
 * 
 * 	$duration =  2
 *	$start_time = "10:00:00"
 *	$end_time =  "11:00:00"
 *	$date = "2012-12-02"
 *	$campus = "North Oshawa"
 *	$num_people = 4
 *	
 * 
 * 
 * In addition, this template depends on the $available array containing the room, start,
 * and end time that are available for the day.
 * 
 *    $available = array( array("room"       => "UA1350",
 *                            	"starttime"  => "10:10:00",
 *                             	"endtime"    => "11:00:00"
 *                             )
 *                       );
 *                       
 * If The available array does not contain start and end time (user specified the
 * start and end time), then the following are needed.
 * 
 * $start_time = "10:10:00"
 * $end_time = "12:00:00"
 * 
 */

?>
<section id="request_room">
	<div class="page-header">
		<h1>Room Results</h1>
	</div>
	<div class="row">
		<div class="span8">
			<form class="well form-horizontal" action="room_booked.php" method="post" accept-charset="UTF-8">
				<h3>Requested Information</h3>
				<fieldset>
					<div class="control-group">
			      		<label for="select_time" class="control-label">Time</label>
				      	<div class="controls">
				      		<input type="text" placeholder="<?php echo $start_time; ?>" disabled>
				      	</div>
		      		</div>
		      		<div class="control-group">
			      		<label for="select_duration" class="control-label">Duration</label>
				      	<div class="controls">
				      		<div class="input-append">
				      			<input type="text" placeholder="<?php echo $duration; ?>" disabled>
      							<span class="add-on">Hours</span>
				      		</div>
				      	</div>
		      		</div>
		      		<div class="control-group">
			      		<label for="select_date" class="control-label">Date</label>
							<div class="controls">
								<input type="text" placeholder="<?php echo $date; ?>" disabled>
							</div>
		      		</div>
		      		<div class="control-group">
			      		<label for="select_campus" class="control-label">Campus</label>
				      	<div class="controls">
				      		<input type="text" placeholder="<?php echo $campus; ?>" disabled>
				      	</div>
		      		</div>
		      		<div class="control-group">
			      		<label for="select_numberofpeople" class="control-label">Number of People</label>
				      	<div class="controls">
				      		<input type="text" placeholder="<?php echo $num_people; ?>" disabled>
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
                	/* If the available array contains the start and end time then
                	 * display the start and end time for each room stored in the
                	 * array, otherwise just display the start and end time provided
                	 * by the user.
                	 */
                	if (isset($available[0]['starttime']) && isset($available[0]['endtime']))
                	{
		                foreach ($available as $room)
		                {
		                	echo '<tr>';
		                		echo '<td>';
		                			echo '<input type="radio" name="group1">';
		                		echo '</td>';
		                		echo '<td>' . $room['room'] .'</td>';
		                		echo '<td>' . $room['starttime'] . '</td>';
		                		echo '<td>' . $room['endtime'] . '</td>';
		                		echo '<td>0</td>';
		                		echo '<td>0</td>';
		                	echo '</tr>';
		                }
                	}
                	/* Otherwise, display the start and end time provided by the user */
                	else
                	{
                		foreach ($available as $room)
                		{
                			echo '<tr>';
                			echo '<td>';
                			echo '<input type="radio" name="group1">';
                			echo '</td>';
                			echo '<td>' . $room['room'] .'</td>';
                			echo '<td>' . $start_time . '</td>';
                			echo '<td>' . $end_time . '</td>';
                			echo '<td>0</td>';
                			echo '<td>0</td>';
                			echo '</tr>';
                		}
                	}
                ?>
              </tbody>
            </table>
		      		<div class="form-actions">
		            	<button class="btn btn-primary" type="submit" name="book_room" value="Book Room">Book Room</button>
		          		<!-- File and XML schema downloads -->
		          		<a class="btn btn-primary" href="etc/time_slots.xml">Save Rooms</a>
		          		<a class="btn btn-primary" href="etc/time_slots.xsd">Save Schema</a>
          		   	</div>
				</fieldset>
			</form>
		</div>
	</div>
</section>