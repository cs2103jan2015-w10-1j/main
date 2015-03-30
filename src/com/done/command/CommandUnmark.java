package com.done.command;

import com.done.model.Done;
import com.done.storage.InMemStorage;

public class CommandUnmark extends Command {

	private Done task;

	public CommandUnmark(int unmarkIndex) {
		super(CommandType.UNMARK, true);
		this.task = InMemStorage.getInstance().getTask(unmarkIndex);
	}
	
	public CommandUnmark(Done task) {
		super(CommandType.UNMARK, true);
		this.task = task;
	}
	
	@Override
	public void execute() throws Exception {
		InMemStorage inMemStorage = InMemStorage.getInstance();
		inMemStorage.setUnmarked(task);
	}

	@Override
	public void undo() {
		CommandMark command = new CommandMark(task);
		try {
			command.execute();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
