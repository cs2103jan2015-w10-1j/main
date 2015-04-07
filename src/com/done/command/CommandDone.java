package com.done.command;

import java.util.logging.Level;

import com.done.model.Done;
import com.done.storage.InMemStorage;

public class CommandDone extends Command {
	
	private Done task;

	//@author A0115777W
	public CommandDone(int markIndex) {
		super(CommandType.DONE, true);
		this.task = InMemStorage.getInstance().getTask(markIndex);
		commandLogger.log(Level.INFO, "Done Command Created");
	}
	
	public CommandDone(Done task) {
		super(CommandType.DONE, true);
		this.task = task;
		commandLogger.log(Level.INFO, "Done Command Created");
	}
	
	@Override
	public void execute() throws Exception {
		InMemStorage inMemStorage = InMemStorage.getInstance();
		inMemStorage.setCompleted(task);
	}

	@Override
	public void undo() {
		InMemStorage inMemStorage = InMemStorage.getInstance();
		inMemStorage.setIncomplete(task);
	}
	
	//@author A0088821X
	@Override
	public String getCommandContent() {
		return task.getTitle();
	}

}
