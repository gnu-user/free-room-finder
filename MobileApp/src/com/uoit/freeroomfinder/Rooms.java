package com.uoit.freeroomfinder;

public class Rooms {

	private String room;
	private long startTime;
	private long endTime;
	private long date;
	
	/**
	 * Create a new room booking
	 * @param room The room's name
	 * @param startTime The start time for the booking
	 * @param endTime The end time for the booking
	 */
	public Rooms(String room, long startTime, long endTime)
	{
		this.room = room;
		this.startTime = startTime;
		this.endTime = endTime;
	}
	
	/**
	 * Create a new room booking
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
	 * Get the room
	 * @return the room
	 */
	public String getRoom() {
		return room;
	}

	/**
	 * Set the rooms
	 * @param room the room to set
	 */
	public void setRoom(String room) {
		this.room = room;
	}

	/**
	 * Get the start time
	 * @return the startTime
	 */
	public long getStartTime() {
		return startTime;
	}

	/**
	 * Set the start time
	 * @param startTime the startTime to set
	 */
	public void setStartTime(long startTime) {
		this.startTime = startTime;
	}

	/**
	 * Get the end time
	 * @return the endTime
	 */
	public long getEndTime() {
		return endTime;
	}

	/**
	 * Set the end time
	 * @param endTime the endTime to set
	 */
	public void setEndTime(long endTime) {
		this.endTime = endTime;
	}

	/**
	 * Get the date
	 * @return the date
	 */
	public long getDate() {
		return date;
	}

	/**
	 * Set the date
	 * @param date the date to set
	 */
	public void setDate(long date) {
		this.date = date;
	}	
}
