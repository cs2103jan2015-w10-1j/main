package com.done.storage;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Stack;

import com.done.command.Command;
import com.done.model.Done;

public class InMemStorage {

	private static InMemStorage instance = null;

	private static final int ARRAY_POSITION_OFFSET = 1;

	private List<Done> tasks;
	private List<Done> workingTasks;

	private Stack<Command> undoStack;

	private JSONStorage jsonStorage;

	private InMemStorage() {
		jsonStorage = JSONStorage.getInstance();
		this.setTasks(jsonStorage.load());
		this.undoStack = new Stack<Command>();
		workingTasks = tasks;
	}

	public static synchronized InMemStorage getInstance() {
		if (instance == null) {
			instance = new InMemStorage();
		}

		return instance;
	}

	public Done getTask(int id) {
		return getTasks().get(id - ARRAY_POSITION_OFFSET);
	}

	public void loadNew() {
		jsonStorage.setNewJson(true);
		this.setTasks(jsonStorage.load());
		jsonStorage.setNewJson(false);
	}

	public List<Done> load() {
		return getTasks();
	}

	public List<Done> loadSearchResult() {
		return getWorkingTasks();
	}

	public boolean store(Done task) {
		getTasks().add(task);
		updateTaskID();
		if (jsonStorage.store(getTasks()) == true) {
			return true;
		}

		return false;
	}

	public boolean delete(Done task, boolean isDeleteAll) {
		if (isDeleteAll) {
			getTasks().clear();
		} else {
			getTasks().remove(task);
			updateTaskID();
		}
		if (jsonStorage.store(getTasks()) == true) {
			return true;
		}

		return false;
	}

	public void setCompleted(Done task) {
		getTasks().get(task.getId() - ARRAY_POSITION_OFFSET).setCompleted(true);
		jsonStorage.store(getTasks());
	}

	public void setIncompleted(Done task) {
		getTasks().get(task.getId() - ARRAY_POSITION_OFFSET)
				.setCompleted(false);
		jsonStorage.store(getTasks());
	}

	public void pushToUndoStack(Command command) {
		this.undoStack.push(command);
	}

	public Command popFromUndoStack() throws Exception {
		if (undoStack.isEmpty()) {
			throw new Exception("Command Stack is empty!");
		}
		return this.undoStack.pop();
	}

	public List<Done> getTasks() {
		return tasks;
	}

	public void setTasks(List<Done> tasks) {
		this.tasks = tasks;
	}

	public List<Done> getWorkingTasks() {
		return workingTasks;
	}

	public void emptyWorkingTasks() {
		this.workingTasks = new ArrayList<Done>();
	}

	public void addIntoWorkingTask(Done task) {
		this.workingTasks.add(task);
	}

	private void updateTaskID() {
		Iterator<Done> listIterator = getTasks().iterator();
		int i = 1;
		while (listIterator.hasNext()) {
			listIterator.next().setId(i);
			i++;
		}
	}

}
