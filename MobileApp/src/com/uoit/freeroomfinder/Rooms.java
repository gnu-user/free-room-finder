/**
 * Free Room Finder (FRF)
 * Tired of rooms on campus always being in use? Fear no more the FRF is here.
 *
 * Copyright (C) 2013 Joseph Heron, Jonathan Gillett, and Daniel Smullen
 * All rights reserved.
 *
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.uoit.freeroomfinder;

/**
 * Rooms Stores an encoded group of available room bookings.
 * 
 * @author Joseph Heron
 * @author Jonathan Gillett
 * @author Daniel Smullen
 * 
 */
public class Rooms
{
    /**
     * Store the parameters for a room's availability.
     */
    private long id;
    private String room;
    private long startTime;
    private long endTime;
    private long date;

    /**
     * Create a new room booking
     * 
     * @param room The room's name
     * @param startTime The start time for the booking
     * @param endTime The end time for the booking
     * @param date The date of the booking
     */
    public Rooms(String room, long startTime, long endTime, long date)
    {
        this.room = room;
        this.startTime = startTime;
        this.endTime = endTime;
        this.date = date;
    }

    /**
     * Create a new room booking
     * 
     * @param id The room id
     * @param room The room's name
     * @param startTime The start time for the booking
     * @param endTime The end time for the booking
     * @param date The date of the booking
     */
    public Rooms(long id, String room, long startTime, long endTime, long date)
    {
        this.id = id;
        this.room = room;
        this.startTime = startTime;
        this.endTime = endTime;
        this.date = date;
    }

    /**
     * getRoom Accessor method for the room.
     * 
     * @return Returns the room's name.
     */
    public String getRoom()
    {
        return room;
    }

    /**
     * Mutator method for the room.
     * 
     * @param room The name of the room to set.
     */
    public void setRoom(String room)
    {
        this.room = room;
    }

    /**
     * Accessor method for the availability's start time.
     * 
     * @return Returns the the start time for the availability.
     */
    public long getStartTime()
    {
        return startTime;
    }

    /**
     * Mutator method for the availability's start time.
     * 
     * @param startTime The start time for the availability to set.
     */
    public void setStartTime(long startTime)
    {
        this.startTime = startTime;
    }

    /**
     * Accessor method for the end time of the availability.
     * 
     * @return Returns the end time for the availability.
     */
    public long getEndTime()
    {
        return endTime;
    }

    /**
     * Mutator method for the end time of the availability.
     * 
     * @param endTime The end time of the availability to set.
     */
    public void setEndTime(long endTime)
    {
        this.endTime = endTime;
    }

    /**
     * Accessor method for the date of the availability.
     * 
     * @return Returns the date of the availability in long form.
     */
    public long getDate()
    {
        return date;
    }

    /**
     * Mutator method for the date of the availability.
     * 
     * @param date The date to set, in long form.
     */
    public void setDate(long date)
    {
        this.date = date;
    }

    /**
     * Get the id
     * 
     * @return the id
     */
    public long getId()
    {
        return id;
    }

    /**
     * Set the id
     * 
     * @param id the id to set
     */
    public void setId(long id)
    {
        this.id = id;
    }
}
