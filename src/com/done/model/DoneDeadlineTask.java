package com.done.model;

public class DoneDeadlineTask extends Done {
	// Time format to use UNIX epoch style
	long endTime;
	
	public DoneDeadlineTask(String title, long endTime){
		super(title);
		this.endTime = endTime;
	}

}
