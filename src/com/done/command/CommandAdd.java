package com.done.command;

import java.util.logging.Level;

import com.done.model.Done;
import com.done.storage.InMemStorage;

public class CommandAdd extends Command {

	private Done task;

	//@author A0115777W
	public CommandAdd(Done task) {
		super(CommandType.ADD, true);
		this.task = task;
		commandLogger.log(Level.INFO, "Add Command Created");
	}

	public Done getTask() {
		return this.task;
	}

	public void setTask(Done task) {
		this.task = task;
	}

	//@author A0111830X
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
