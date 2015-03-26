package com.done.storage;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.done.model.Done;

public class InMemStorage implements DoneStorage {

	private static InMemStorage instance = null;

	private List<Done> tasks;
	private DoneStorage jsonStorage;

	private InMemStorage() {
		this.tasks = new ArrayList<Done>();
		jsonStorage = new JSONStorage();
	}

	public static synchronized InMemStorage getInstance() {
		if (instance == null) {
			instance = new InMemStorage();
		}

		return instance;
	}

	@Override
	public List<Done> load() {
		return jsonStorage.load();
	}

	@Override
	public boolean store(List<Done> tasks) {
		this.tasks = tasks;
		updateTaskID();
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
