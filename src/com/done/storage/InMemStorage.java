package com.done.storage;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Stack;
import java.util.logging.Level;

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
		StorageLogger.setUpLogger();
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

	//@author A0111830X
	public Done getTask(int id) {
		return getTasks().get(id - ARRAY_POSITION_OFFSET);
	}

	public void loadNew() {
		StorageLogger.getStorageLogger().log(Level.INFO, "Load from new JSON");
		jsonStorage.setNewJson(true);
		this.setTasks(jsonStorage.load());
		jsonStorage.setNewJson(false);
	}

	public List<Done> load() {
		return getTasks();
	}

	public List<Done> loadSearchResult() {
		StorageLogger.getStorageLogger().log(Level.INFO, "Get search result");
		return getWorkingTasks();
	}

	public boolean store(Done task) {
		StorageLogger.getStorageLogger().log(Level.INFO, "Store to memory");
		assert task != null;
		getTasks().add(0, task);
		updateTaskID();
		if (jsonStorage.store(getTasks()) == true) {
			return true;
		}

		return false;
	}

	public boolean delete(Done task, boolean isDeleteAll) {
		StorageLogger.getStorageLogger().log(Level.INFO, "Delete task");
		// isDeleteAll used for clear command
		// if true is a clear
		// else is a normal delete
		if (isDeleteAll) {
			assert task == null;
			getTasks().clear();
		} else {
			assert task != null;
			getTasks().remove(task);
			updateTaskID();
		}
		if (jsonStorage.store(getTasks()) == true) {
			return true;
		}

		return false;
	}

	public boolean edit(Done task, int editIndex) {
		StorageLogger.getStorageLogger().log(Level.INFO, "Edit task");
		assert task != null;

		getTasks().set(editIndex, task);
		updateTaskID();
		if (jsonStorage.store(getTasks()) == true) {
			return true;
		}

		return false;
	}

	// @author A0115777W
	public boolean move(int originIndex, int destinationIndex) {
		StorageLogger.getStorageLogger().log(Level.INFO, "Move task");

		assert (originIndex >= 0 && originIndex < getTasks().size());
		assert (destinationIndex >= 0 && destinationIndex < getTasks().size());
		Done movedTask = getTasks().remove(originIndex);
		getTasks().add(destinationIndex, movedTask);
		updateTaskID();
		if (jsonStorage.store(getTasks()) == true) {
			return true;
		}

		return false;
	}

	public boolean clearDoneTasks() {
		StorageLogger.getStorageLogger()
				.log(Level.INFO, "Clear all Done tasks");

		List<Done> tasks = getTasks();
		int size = tasks.size();
		for (int i = size - 1; i >= 0; i--) {
			if (tasks.get(i).isCompleted()) {
				tasks.remove(i);
			}
		}
		updateTaskID();

		if (jsonStorage.store(getTasks()) == true) {
			return true;
		}

		return false;
	}

	//@author A0111830X
	public void setCompleted(Done task) {
		StorageLogger.getStorageLogger().log(Level.INFO, "Set Task Completed");
		assert task != null;

		getTasks().get(task.getId() - ARRAY_POSITION_OFFSET).setCompleted(true);
		jsonStorage.store(getTasks());
	}

	public void setIncomplete(Done task) {
		StorageLogger.getStorageLogger().log(Level.INFO, "Set Task Incomplete");
		assert task != null;

		getTasks().get(task.getId() - ARRAY_POSITION_OFFSET)
				.setCompleted(false);
		jsonStorage.store(getTasks());
	}

	// Stack used for Undo
	public void pushToUndoStack(Command command) {
		StorageLogger.getStorageLogger().log(Level.INFO,
				"Push command to Undo stack");
		this.undoStack.push(command);
	}

	public Command popFromUndoStack() throws Exception {
		StorageLogger.getStorageLogger().log(Level.INFO,
				"Retreive command from Undo stack");
		if (undoStack.isEmpty()) {
			throw new Exception("Command Stack is empty!");
		}
		return this.undoStack.pop();
	}

	public void emptyWorkingTasks() {
		this.workingTasks = new ArrayList<Done>();
	}

	public void addIntoWorkingTask(Done task) {
		assert task != null;

		this.workingTasks.add(task);
	}

	// For updating ID to be always in increasing order 1 ... n
	private void updateTaskID() {
		Iterator<Done> listIterator = getTasks().iterator();
		int i = 1;
		while (listIterator.hasNext()) {
			listIterator.next().setId(i);
			i++;
		}
	}

	// @author generated
	public List<Done> getTasks() {
		return tasks;
	}

	public void setTasks(List<Done> tasks) {
		assert tasks != null;
		this.tasks = tasks;
	}

	public List<Done> getWorkingTasks() {
		return workingTasks;
	}

}
