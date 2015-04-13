package com.done.command;

import java.util.logging.Level;

import com.done.model.Done;
import com.done.storage.InMemStorage;

public class CommandDelete extends Command {

	private static final String MESSAGE_CREATION = "Delete Command Created"; 

	private int deleteIndex;
	private Done task;

	//@author A0115777W
	public CommandDelete(int deleteIndex) throws Exception {
		super(CommandType.DELETE, true);
		assert deleteIndex > 0;
		if (deleteIndex > InMemStorage.getInstance().getTasks().size()) {
			throw new Exception("Too large Destination Index Value");
		}
		this.deleteIndex = deleteIndex;
		commandLogger.log(Level.INFO, MESSAGE_CREATION);
	}

	public CommandDelete(Done task) {
		super(CommandType.DELETE, true);
		this.task = task;
		commandLogger.log(Level.INFO, MESSAGE_CREATION);
	}

	//@author A0111830X
	@Override
	public void execute() {
		InMemStorage memory = InMemStorage.getInstance();
		if (task == null) {
			this.task = memory.getTask(deleteIndex);
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
	
	//@author A0088821X
	@Override
	public String getCommandContent(){
		return task.getTitle();
	}

}
