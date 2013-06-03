<?php
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

/*
 * Displays the rooms that the user has requested. The user from this page will also be able to 
 * 'manage' their current requested rooms by removing them. If a user does remove their room
 * request then the database will be updated with the decreased amount of people expected in a
 * particular room.
 * 
 * 1. If the user has room requests
 * 
 * 		a) Display the room requests
 * 		b) link to room_requests
 * 
 */

?>