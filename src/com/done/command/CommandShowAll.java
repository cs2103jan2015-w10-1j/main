package com.done.command;

import java.util.List;
import java.util.logging.Level;

import com.done.model.Done;
import com.done.storage.InMemStorage;

public class CommandShowAll extends Command {
	
	private static final String MESSAGE_CREATION = "ShowAll Command Created"; 
	private static final String EMPTY_STRING = "";

	//@author A0115777W
	public CommandShowAll() {
		super(CommandType.SHOWALL, false);
		commandLogger.log(Level.INFO, MESSAGE_CREATION);
	}

	@Override
	public void execute() {
		InMemStorage memory = InMemStorage.getInstance();
		List<Done> tasks = memory.getTasks();
		memory.emptyWorkingTasks();

		for (int i = 0; i < tasks.size(); i++) {
			memory.addIntoWorkingTask(tasks.get(i));
		}
	}

	@Override
	public void undo() {
		//NOT IMPLEMENTED
	}
	
	//@author A0088821X
	@Override
	public String getCommandContent(){
		return EMPTY_STRING;
	}

}
