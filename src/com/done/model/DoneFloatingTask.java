package com.done.model;

public class DoneFloatingTask extends Done {

	public DoneFloatingTask(String title) {
		super(title);
		super.setType(Type.FLOATING);
	}
	
	public DoneFloatingTask(String title, String tag) {
		super(title, tag);
		super.setType(Type.FLOATING);
	}
}
