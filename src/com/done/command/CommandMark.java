package com.done.command;

import com.done.model.Done;
import com.done.storage.InMemStorage;

public class CommandMark extends Command {
	
	private Done task;

	public CommandMark(int markIndex) {
		super(CommandType.MARK, true);
		this.task = InMemStorage.getInstance().getTask(markIndex);
	}
	
	@Override
	public void execute() throws Exception {
		InMemStorage inMemStorage = InMemStorage.getInstance();
		inMemStorage.setMarked(task);
	}

	@Override
	public void undo() {
		// TODO Auto-generated method stub

	}

}
