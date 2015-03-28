package com.done.storage;

import java.util.Iterator;
import java.util.List;

import com.done.model.Done;

public class InMemStorage {

	private static InMemStorage instance = null;
	
	private static final int ARRAY_DELETE_OFFSET = 1;

	private List<Done> tasks;
	private JSONStorage jsonStorage;

	private InMemStorage() {
		jsonStorage = JSONStorage.getInstance();
		this.tasks = jsonStorage.load();
	}

	public static synchronized InMemStorage getInstance() {
		if (instance == null) {
			instance = new InMemStorage();
		}

		return instance;
	}

	public void loadNew() {
		jsonStorage.setNewJson(true);
		this.tasks = jsonStorage.load();
		jsonStorage.setNewJson(false);
	}

	public List<Done> load() {
		return tasks;
	}

	public boolean store(Done task) {
		tasks.add(task);
		updateTaskID();
		if (jsonStorage.store(tasks) == true) {
			return true;
		}

		return false;
	}
	
	public boolean delete(int index, boolean isDeleteAll){
		if(isDeleteAll){
			tasks.clear();
		}else{
			tasks.remove(index - ARRAY_DELETE_OFFSET);
			updateTaskID();
		}
		if (jsonStorage.store(tasks) == true) {
			return true;
		}

		return false;
	}

	private void updateTaskID() {
		Iterator<Done> listIterator = tasks.iterator();
		int i = 1;
		while (listIterator.hasNext()) {
			listIterator.next().setId(i);
			i++;
		}
	}

}
