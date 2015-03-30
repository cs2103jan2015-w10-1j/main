package com.done.command;

import com.done.model.Done;
import com.done.storage.InMemStorage;

public class CommandAdd extends Command {

	private Done task;

	public CommandAdd(Done task) {
		super(CommandType.ADD, true);
		this.task = task;
	}

	public Done getTask() {
		return this.task;
	}

	public void setTask(Done task) {
		this.task = task;
	}

	@Override
	public void execute() throws Exception {
		if (this.task != null) {
			InMemStorage inMemStorage = InMemStorage.getInstance();
			inMemStorage.store(task);
		} else {
			throw new Exception("TaskNullException");
		}

	}

	@Override
	public void undo() {
		CommandDelete command = new CommandDelete(task);
		command.execute();
	}

}
