package com.done.model;

public abstract class Done {
	private int id;
	private String title;
	private String tag;
	
	// Task without tag constructor
	public Done(String title){
		this.title = title;
	}
	
	// Task with tag constructor
	public Done(String title, String tag){
		this.title = title;
		this.setTag(tag);
	}
	
	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @param title the title to set
	 */
	private void setTitle(String title) {
		this.title = title;
	}

	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * @return the tag
	 */
	public String getTag() {
		return tag;
	}

	/**
	 * @param tag the tag to set
	 */
	public void setTag(String tag) {
		this.tag = tag;
	}

}
