package com.done.command;

import java.util.List;
import java.util.logging.Level;

import org.hamcrest.core.IsInstanceOf;

import com.done.model.Done;
import com.done.model.DoneDeadlineTask;
import com.done.model.Done.TaskType;
import com.done.model.DoneTimedTask;
import com.done.storage.InMemStorage;

public class CommandSearch extends Command {

	private static final String MESSAGE_CREATION = "Search Command Created"; 

	private String searchString;

	//@author A0115777W
	public CommandSearch(String searchString) {
		super(CommandType.SEARCH, false);
		this.searchString = searchString;
		commandLogger.log(Level.INFO, MESSAGE_CREATION);
	}

	public String getSearchString() {
		return this.searchString;
	}

	public void setSearchString(String searchstring) {
		this.searchString = searchstring;
	}

	//@author A0111830X
	@Override
	public void execute() {
		InMemStorage memory = InMemStorage.getInstance();
		List<Done> tasks = memory.getTasks();
		memory.emptyWorkingTasks();

		for (int i = 0; i < tasks.size(); i++) {
			Done task = tasks.get(i);
			if (task.getTitle().contains(searchString)) {
				memory.addIntoWorkingTask(task);
			}
			// check if this is a timedTask
			else if (task.getType().equals(TaskType.TIMED)) {
				// check the event date is equal to searchString
				DoneTimedTask dtt = (DoneTimedTask) task;
				if (dtt.getDateString().equals(searchString)) {
					memory.addIntoWorkingTask(task);
				}
			}

			// check if this is a deadlineTask
			else if (task.getType().equals(TaskType.DEADLINE)) {
				// check the deadline date is equal to searchString
				DoneDeadlineTask ddt = (DoneDeadlineTask) task;
				if (ddt.getDateString().equals(searchString)) {
					memory.addIntoWorkingTask(task);
				}
			}
		}

	}

	//@author A0111830X-unused
	// undo not required in SEARCH
	@Override
	public void undo() {
		// UNUSED

	}

	//@author A0088821X
	@Override
	public String getCommandContent() {
		return searchString;
	}

}
