package com.done;

public class Done {
	private int id;
	private String title;
	private String date;
	private String startTime;
	private String endTime;
	
	// Floating Task constructor
	public Done(int id, String title){
		this.id = id;
		this.title = title;
	}
	
	// Timed Task constructor
	public Done(int id, String title, String startTime, String endTime){
		this.id = id;
		this.title = title;
		this.startTime = startTime;
		this.endTime = endTime;
	}
	
	// Deadline Task constructor
	public Done(int id, String title, String date){
		this.id = id;
		this.title = title;
		this.date = date;
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

}
