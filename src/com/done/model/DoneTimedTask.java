package com.done.model;

import java.text.SimpleDateFormat;

import com.done.task.TaskReminder;

public class DoneTimedTask extends Done {

	// Time format to use UNIX epoch style
	private long startTime;
	private long endTime;

	public DoneTimedTask(String title, long startTime, long endTime) {
		super(title);
		super.setType(TaskType.TIMED);
		this.setStartTime(startTime);
		this.setEndTime(endTime);
		//new TaskReminder(this);
	}

	public DoneTimedTask(String title, String tag, long startTime, long endTime) {
		super(title, tag);
		super.setType(TaskType.TIMED);
		this.setStartTime(startTime);
		this.setEndTime(endTime);
	}

	//@author A0111830X
	public String getStartTime() {
		// SimpleDateFormat sdf = new SimpleDateFormat("d MMM yyyy HH:mm");
		SimpleDateFormat sdf = new SimpleDateFormat("d MMM HH:mm");
		return sdf.format(startTime);
	}
	
	// This method returns you a ddMMyyyy for search purpose
	public String getDateString(){
		SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyyy");
		return sdf.format(startTime);
		
	}

	//@author A0111830X
	public long getStartTimeLong() {
		return startTime;
	}

	//@author generated
	public void setStartTime(long startTime) {
		this.startTime = startTime;
	}

	//@author A0111830X
	public String getEndTime() {
		SimpleDateFormat sdf = new SimpleDateFormat("d MMM HH:mm");
		return sdf.format(endTime);
	}

	public long getEndTimeLong() {
		return endTime;
	}

	//@author generated
	public void setEndTime(long endTime) {
		this.endTime = endTime;
	}

}
