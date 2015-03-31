package com.done.command;

import java.util.List;

import com.done.model.Done;
import com.done.storage.InMemStorage;

public class CommandShowAll extends Command {

	public CommandShowAll() {
		super(CommandType.SHOWALL, false);
	}

	@Override
	public void execute() {
		InMemStorage memory = InMemStorage.getInstance();
		List<Done> tasks = memory.getTasks();
		memory.emptyWorkingTasks();

		for (int i = 0; i < tasks.size(); i++) {
			memory.addIntoWorkingTask(tasks.get(i));
		}

	}

	@Override
	public void undo() {
		//NOT IMPLEMENTED
	}

}
