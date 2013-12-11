package com.uoit.freeroomfinder;

public class Times {
	
	private String formated;
	private long time;
	
	public Times(String formated, long time)
	{
		this.formated = formated;
		this.time = time;
	}

	/**
	 * @return the formated
	 */
	public String getFormated() {
		return formated;
	}

	/**
	 * @param formated the formated to set
	 */
	public void setFormated(String formated) {
		this.formated = formated;
		this.setTime(this.parse(formated));
	}

	private long parse(String formated) {
		// TODO Auto-generated method stub
		return 0;
	}

	/**
	 * @return the time
	 */
	public long getTime() {
		return time;
	}

	/**
	 * @param time the time to set
	 */
	public void setTime(long time) {
		this.time = time;
		this.setFormated(this.format(time));
	}

	private String format(long time) {
		
		
		return null;
	}
	
	

}
