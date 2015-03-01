package com.done.storage;

import java.util.ArrayList;
import java.util.List;

import com.done.Done;

public class InMemStorage implements DoneStorage {
	
	private List<Done> tasks;
	
	public InMemStorage(){
		this.tasks = new ArrayList<Done>();
	}

	@Override
	public List<Done> load() {
		return null;
	}

	@Override
	public void store(List<Done> tasks) {
		this.tasks = tasks;
	}

}
