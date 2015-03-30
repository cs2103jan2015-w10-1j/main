package com.done.model;

public abstract class Done {
	private int id;
	private String title;
	private String tag;
	private TaskType type;
	private boolean completed;
	private boolean marked;

	public static enum TaskType {
		FLOATING, TIMED, DEADLINE;
	}

	// Task without tag constructor
	public Done(String title) {
		this.title = title;
		this.setCompleted(false);
	}

	// Task with tag constructor
	public Done(String title, String tag) {
		this.title = title;
		this.setTag(tag);
		this.setCompleted(false);
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

	public boolean isCompleted() {
		return completed;
	}

	public void setCompleted(boolean completed) {
		this.completed = completed;
	}
	
	public boolean isMarked(){
		return marked;
	}
	
	public void setMarked(boolean marked){
		this.marked = marked;
	}

}
