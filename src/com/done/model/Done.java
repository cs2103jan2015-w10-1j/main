package com.done.model;

public abstract class Done {
	private int id;
	private String title;
	private String tag;
	private TaskType type;

	public static enum TaskType {
		FLOATING, TIMED, DEADLINE;
	}

	// Task without tag constructor
	public Done(String title) {
		this.title = title;
	}

	// Task with tag constructor
	public Done(String title, String tag) {
		this.title = title;
		this.setTag(tag);
	}

	public String getTitle() {
		return title;
	}

	private void setTitle(String title) {
		this.title = title;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	public TaskType getType() {
		return type;
	}

	public void setType(TaskType type) {
		this.type = type;
	}

}
