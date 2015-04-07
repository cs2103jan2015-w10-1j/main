package com.done.command;

import java.util.logging.Level;

import com.done.storage.InMemStorage;

public class CommandClearDone extends Command {
	
	private static final String EMPTY_STRING = "";

	//@author A0115777W
	public CommandClearDone(){
		super(CommandType.CLEARDONE,false);
		commandLogger.log(Level.INFO, "ClearDone Command Created");
	}

	@Override
	public void execute() throws Exception {
		commandLogger.log(Level.INFO, "Cleardone Command Execution called");
		InMemStorage.getInstance().clearDoneTasks();
		commandLogger.log(Level.INFO, "cleardone Command Execution succeeded");
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
