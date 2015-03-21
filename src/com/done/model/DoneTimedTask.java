package com.done.model;

public class DoneTimedTask extends Done {
	
	// Time format to use UNIX epoch style
	private long startTime;
	private long endTime;
	
	
	public DoneTimedTask(String title, long startTime, long endTime){
		super(title);
		this.startTime = startTime;
		this.endTime = endTime;
		super.setType(Type.TIMED);
	}
	
	public DoneTimedTask(String title, String tag, long startTime, long endTime){
		super(title, tag);
		this.startTime = startTime;
		this.endTime = endTime;
		super.setType(Type.TIMED);
	}

}
