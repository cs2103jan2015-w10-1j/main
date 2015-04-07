package com.done.command;

import java.util.logging.Level;

import com.done.model.Done;
import com.done.storage.InMemStorage;

public class CommandDelete extends Command {

	private int deleteIndex;
	private Done task;

	public CommandDelete(int deleteIndex) {
		super(CommandType.DELETE, true);
		this.deleteIndex = deleteIndex;
		commandLogger.log(Level.INFO, "Delete Command Created");
	}

	public CommandDelete(Done task) {
		super(CommandType.DELETE, true);
		this.task = task;
		commandLogger.log(Level.INFO, "Delete Command Created");
	}

	//@author A0111830X
	@Override
	public void execute() {
		InMemStorage memory = InMemStorage.getInstance();
		if (task == null) {
			this.task = InMemStorage.getInstance().getTask(deleteIndex);
			memory.delete(task, false);
		} else {
			memory.delete(task, false);
		}
	}

	@Override
	public void undo() {
		CommandAdd command = new CommandAdd(task);

		try {
			command.execute();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
