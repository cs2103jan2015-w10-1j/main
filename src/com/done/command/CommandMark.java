package com.done.command;

import com.done.model.Done;
import com.done.storage.InMemStorage;

public class CommandMark extends Command {
	
	private Done task;

	public CommandMark(int markIndex) {
		super(CommandType.MARK, true);
		this.task = InMemStorage.getInstance().getTask(markIndex);
	}
	
	public CommandMark(Done task) {
		super(CommandType.MARK, true);
		this.task = task;
	}
	
	@Override
	public void execute() throws Exception {
		InMemStorage inMemStorage = InMemStorage.getInstance();
		inMemStorage.setMarked(task);
	}

	@Override
	public void undo() {
		CommandUnmark command = new CommandUnmark(task);
		try {
			command.execute();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
