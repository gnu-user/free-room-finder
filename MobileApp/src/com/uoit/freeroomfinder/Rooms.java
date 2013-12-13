package com.uoit.freeroomfinder;

public class Rooms {

	private String room;
	private long startTime;
	private long endTime;
	private long date;
	
	public Rooms(String room, long startTime, long endTime)
	{
		this.room = room;
		this.startTime = startTime;
		this.endTime = endTime;
	}
	
	public Rooms(String room, long startTime, long endTime, long date)
	{
		this.room = room;
		this.startTime = startTime;
		this.endTime = endTime;
		this.date = date;
	}

	/**
	 * @return the room
	 */
	public String getRoom() {
		return room;
	}

	/**
	 * @param room the room to set
	 */
	public void setRoom(String room) {
		this.room = room;
	}

	/**
	 * @return the startTime
	 */
	public long getStartTime() {
		return startTime;
	}

	/**
	 * @param startTime the startTime to set
	 */
	public void setStartTime(long startTime) {
		this.startTime = startTime;
	}

	/**
	 * @return the endTime
	 */
	public long getEndTime() {
		return endTime;
	}

	/**
	 * @param endTime the endTime to set
	 */
	public void setEndTime(long endTime) {
		this.endTime = endTime;
	}

	/**
	 * @return the date
	 */
	public long getDate() {
		return date;
	}

	/**
	 * @param date the date to set
	 */
	public void setDate(long date) {
		this.date = date;
	}
	
}
