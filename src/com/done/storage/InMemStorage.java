package com.done.storage;

import java.util.ArrayList;
import java.util.List;

import com.done.model.Done;

public class InMemStorage implements DoneStorage {
	
	private List<Done> tasks;
	private DoneStorage JSONStorage;
	
	public InMemStorage(){
		this.tasks = new ArrayList<Done>();
		JSONStorage = new JSONStorage();
	}

	@Override
	public List<Done> load() {
		return JSONStorage.load();
	}

	@Override
	public boolean store(List<Done> tasks) {
		this.tasks = tasks;
		if(JSONStorage.store(tasks)==true){
			return true;
		}
		
		return false;
	}

}
