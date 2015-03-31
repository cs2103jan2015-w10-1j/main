package com.done.command;

import java.util.List;
import java.util.logging.Level;

import com.done.model.Done;
import com.done.storage.InMemStorage;

public class CommandShowAll extends Command {

	public CommandShowAll() {
		super(CommandType.SHOWALL, false);
	}

	@Override
	public void execute() {
		commandLogger.log(Level.INFO, "Showall Command called");
		InMemStorage memory = InMemStorage.getInstance();
		List<Done> tasks = memory.getTasks();
		memory.emptyWorkingTasks();

		for (int i = 0; i < tasks.size(); i++) {
			memory.addIntoWorkingTask(tasks.get(i));
		}
		commandLogger.log(Level.INFO, "Showall Command successful");
	}

	@Override
	public void undo() {
		//NOT IMPLEMENTED
	}

}
