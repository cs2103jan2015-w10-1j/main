package com.done.command;

import java.util.logging.Level;

import com.done.model.Done;
import com.done.storage.InMemStorage;

public class CommandDone extends Command {
	
	private static final String MESSAGE_CREATION = "Done Command Created"; 

	private Done task;

	//@author A0115777W
	public CommandDone(int doneIndex) throws Exception {
		super(CommandType.DONE, true);
		assert doneIndex > 0;
		if (doneIndex > InMemStorage.getInstance().getTasks().size()) {
			throw new Exception("Too large Destination Index Value");
		}
		this.task = InMemStorage.getInstance().getTask(doneIndex);
		commandLogger.log(Level.INFO, MESSAGE_CREATION);
	}
	
	public CommandDone(Done task) {
		super(CommandType.DONE, true);
		this.task = task;
		commandLogger.log(Level.INFO, MESSAGE_CREATION);
	}
	
	@Override
	public void execute() throws Exception {
		InMemStorage memory = InMemStorage.getInstance();
		memory.setCompleted(task);
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
