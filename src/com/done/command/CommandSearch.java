package com.done.command;

import java.util.List;

import com.done.model.Done;
import com.done.storage.InMemStorage;

public class CommandSearch extends Command {

	private String searchString;

	public CommandSearch(String searchString) {
		super(CommandType.SEARCH, false);
		this.searchString = searchString;
	}

	public String getSearchString() {
		return this.searchString;
	}

	public void setSearchString(String searchstring) {
		this.searchString = searchstring;
	}

	@Override
	public void execute() {
		InMemStorage memory = InMemStorage.getInstance();
		List<Done> tasks = memory.getTasks();
		memory.emptyWorkingTasks();

		for (int i = 0; i < tasks.size(); i++) {
			if (tasks.get(i).getTitle().contains(searchString)) {
				memory.addIntoWorkingTask(tasks.get(i));
			}
		}

	}

	@Override
	public void undo() {
		// UNUSED

	}

}
