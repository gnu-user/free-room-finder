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




          <h3>Rooms Found </h3>
            <table class="table table-striped">
              <thead>
                <tr>
                  <th width=50px>Select</th>	
                  <th width=50px>Room</th>
                  <th width=50px>Time</th>
                  <th width=100px>Estimated <br>Number of People</th>
                  <th width=100px>Average <br>Number of People</th>
                </tr>
              </thead>
              <tbody>
                <tr>
	                  <td>
						<input type="radio" name="group1"> 
	                  </td>
	                  <td>UA1120</td>
	                  <td>
				      		14:10
	                  </td>
	                  <td>
				      		10
	                  </td>
	                  <td>30</td>
                </tr>
                <tr>
	                  <td>
						<input type="radio" name="group1"> 
	                  </td>
	                  <td>UA2220</td>
	                  <td>
				      		14:10
	                  </td>
	                  <td>
				      		20
	                  </td>
	                  <td>30</td>
                </tr>
                <tr>
	                  <td>
						<input type="radio" name="group1" value="choco"> 
	                  </td>
	                  <td>J102</td>
	                  <td>
				      		15:10
	                  </td>
	                  <td>
				      		30
	                  </td>
	                  <td>30</td>
                </tr>      
                <tr>
	                  <td>
						<input type="radio" name="group1" value="choco"> 
	                  </td>
	                  <td>UA3120</td>
	                  <td>
				      		15:10
	                  </td>
	                  <td>
				      		10
	                  </td>
	                  <td>30</td>
                </tr>
              </tbody>
            </table>
		      		<div class="form-actions">
		            	<button class="btn btn-primary" type="submit" name="book_room" value="Submit Vote">Submit/Book</button>
		          </div>
				</fieldset>
			</form>
		</div>
	</div>
</section>