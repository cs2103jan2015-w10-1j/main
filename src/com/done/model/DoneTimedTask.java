package com.done.model;

public class DoneTimedTask extends Done {

	// Time format to use UNIX epoch style
	private long startTime;
	private long endTime;

	public DoneTimedTask(String title, long startTime, long endTime) {
		super(title);
		super.setType(TaskType.TIMED);
		this.setStartTime(startTime);
		this.setEndTime(endTime);
	}

	public DoneTimedTask(String title, String tag, long startTime, long endTime) {
		super(title, tag);
		super.setType(TaskType.TIMED);
		this.setStartTime(startTime);
		this.setEndTime(endTime);
	}

	public long getStartTime() {
		return startTime;
	}

	public void setStartTime(long startTime) {
		this.startTime = startTime;
	}

	public long getEndTime() {
		return endTime;
	}

	public void setEndTime(long endTime) {
		this.endTime = endTime;
	}

}
