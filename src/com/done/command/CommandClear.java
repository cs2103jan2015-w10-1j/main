package com.done.command;

import java.util.logging.Level;

import com.done.storage.InMemStorage;

public class CommandClear extends Command {
	
	private static final String EMPTY_STRING = "";
	
	//@author A0115777W
	public CommandClear(){
		super(CommandType.CLEAR, false);
		commandLogger.log(Level.INFO, "Clear Command Created");
	}

	//@author A0111830X
	@Override
	public void execute() {
		InMemStorage.getInstance().delete(null, true);

	}
	
	//@author A0111830X-unused
	//undo not required in CLEAR
	@Override
	public void undo() {
		// UNUSED
		//System.out.printf(MESSAGE_ERROR,"undo",this.getCommandType());
	}
	//@author A0088821X
	@Override
	public String getCommandContent(){
		return EMPTY_STRING;
	}
}
