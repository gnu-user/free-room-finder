package com.uoit.freeroomfinder;


public class Request {
	
	private String time;
	private int duration;
	private String date;
	private int campus;
	
	//Pass the date and time as formated strings
	public Request(String time, int duration, String date, int campus)
	{
		this.time = time;
		this.duration = duration;
		this.date = date;
		this.campus = campus;
	}

	/**
	 * @return the time
	 */
	public String getTime() {
		return time;
	}

	/**
	 * @param time the time to set
	 */
	public void setTime(String time) {
		this.time = time;
	}

	/**
	 * @return the duration
	 */
	public int getDuration() {
		return duration;
	}

	/**
	 * @param duration the duration to set
	 */
	public void setDuration(int duration) {
		this.duration = duration;
	}

	/**
	 * @return the date
	 */
	public String getDate() {
		return date;
	}

	/**
	 * @param date the date to set
	 */
	public void setDate(String date) {
		this.date = date;
	}

	/**
	 * @return the campus
	 */
	public int getCampus() {
		return campus;
	}

	/**
	 * @param campus the campus to set
	 */
	public void setCampus(int campus) {
		this.campus = campus;
	}

}
