package com.done.model;

import com.done.model.Done.TaskType;

public class DoneFloatingTask extends Done {

	public DoneFloatingTask(String title) {
		super(title);
		super.setType(TaskType.FLOATING);
	}

	public DoneFloatingTask(String title, String tag) {
		super(title, tag);
		super.setType(TaskType.FLOATING);
	}
}
