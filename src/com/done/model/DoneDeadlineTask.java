package com.done.model;

public class DoneDeadlineTask extends Done {
	// Time format to use UNIX epoch style
	private long endTime;

	public DoneDeadlineTask(String title, long endTime) {
		super(title);
		super.setType(TaskType.DEADLINE);
		this.endTime = endTime;
	}

	public DoneDeadlineTask(String title, String tag, long endTime) {
		super(title, tag);
		super.setType(TaskType.DEADLINE);
		this.endTime = endTime;
	}

}
