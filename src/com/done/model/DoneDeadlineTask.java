package com.done.model;

import java.text.SimpleDateFormat;

public class DoneDeadlineTask extends Done {
	// Time format to use UNIX epoch style
	private long endTime;

	public DoneDeadlineTask(String title, long endTime) {
		super(title);
		super.setType(TaskType.DEADLINE);
		this.setEndTime(endTime);
	}

	public DoneDeadlineTask(String title, String tag, long endTime) {
		super(title, tag);
		super.setType(TaskType.DEADLINE);
		this.setEndTime(endTime);
	}

	public void setEndTime(long endTime) {
		this.endTime = endTime;
	}
	
	public String getEndTime(){
		SimpleDateFormat sdf = new SimpleDateFormat("d MMM yyyy HH:mm");
		return sdf.format(endTime);
	}

}
