package com.done;

public class Done {
	private int id;
	private String title;
	private String date;
	private String startTime;
	private String endTime;
	
	// Floating Task constructor
	public Done(String title){
		this.title = title;
	}
	
	// Timed Task constructor
	public Done(String title, String startTime, String endTime){
		this.title = title;
		this.setStartTime(startTime);
		this.setEndTime(endTime);
	}
	
	// Deadline Task constructor
	public Done(String title, String date){
		this.title = title;
		this.setDate(date);
	}
	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @param title the title to set
	 */
	private void setTitle(String title) {
		this.title = title;
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
	 * @return the startTime
	 */
	public String getStartTime() {
		return startTime;
	}

	/**
	 * @param startTime the startTime to set
	 */
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	/**
	 * @return the endTime
	 */
	public String getEndTime() {
		return endTime;
	}

	/**
	 * @param endTime the endTime to set
	 */
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}

}
