package com.done.command;

import com.done.model.Done;
import com.done.storage.InMemStorage;

public class CommandDelete extends Command {

	private Done task;

	public CommandDelete(int deleteIndex) {
		super(CommandType.DELETE, true);
		this.task = InMemStorage.getInstance().getTask(deleteIndex);
	}

	public CommandDelete(Done task) {
		super(CommandType.DELETE, true);
		this.task = task;
	}

	@Override
	public void execute() {
		InMemStorage inMemStorage = InMemStorage.getInstance();
		inMemStorage.delete(task, false);
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
