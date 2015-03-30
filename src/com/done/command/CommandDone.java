package com.done.command;

import com.done.model.Done;
import com.done.storage.InMemStorage;

public class CommandDone extends Command {
	
	private Done task;

	public CommandDone(int markIndex) {
		super(CommandType.DONE, true);
		this.task = InMemStorage.getInstance().getTask(markIndex);
	}
	
	public CommandDone(Done task) {
		super(CommandType.DONE, true);
		this.task = task;
	}
	
	@Override
	public void execute() throws Exception {
		InMemStorage inMemStorage = InMemStorage.getInstance();
		inMemStorage.setCompleted(task);
	}

	@Override
	public void undo() {
		InMemStorage inMemStorage = InMemStorage.getInstance();
		inMemStorage.setIncompleted(task);
	}
}
