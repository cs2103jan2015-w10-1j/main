package com.done.command;

import java.util.logging.Level;

import com.done.storage.InMemStorage;

public class CommandClearDone extends Command {
	
	private static final String EMPTY_STRING = "";
	private static final String MESSAGE_CREATION = "ClearDone Command Created"; 

	//@author A0115777W
	public CommandClearDone(){
		super(CommandType.CLEARDONE,false);
		commandLogger.log(Level.INFO, MESSAGE_CREATION);
	}

	@Override
	public void execute() throws Exception {
		InMemStorage.getInstance().clearDoneTasks();
	}

	//undo not required in CLEARDONE
	@Override
	public void undo() throws Exception {
		// TODO Auto-generated method stub

	}
	
	//@author A0088821X
	@Override
	public String getCommandContent(){
		return EMPTY_STRING;
	}

}
