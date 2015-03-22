package com.done.model;

import com.done.model.Done.TaskType;

public class DoneTimedTask extends Done {

	// Time format to use UNIX epoch style
	private long startTime;
	private long endTime;

	public DoneTimedTask(String title, long startTime, long endTime) {
		super(title);
		super.setType(TaskType.TIMED);
		this.startTime = startTime;
		this.endTime = endTime;
	}

	public DoneTimedTask(String title, String tag, long startTime, long endTime) {
		super(title, tag);
		super.setType(TaskType.TIMED);
		this.startTime = startTime;
		this.endTime = endTime;
	}

}
