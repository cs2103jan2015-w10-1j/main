package com.done.command;

import java.util.logging.Level;

public class CommandInvalid extends Command {
	
	private static final String MESSAGE_CREATION = "Invalid Command Created"; 
	private static final String EMPTY_STRING = "";
	
	//@author A0115777W
	public CommandInvalid(){
		super(CommandType.INVALID, false);
		commandLogger.log(Level.INFO, MESSAGE_CREATION);

	}

	@Override
	public void execute() {
		// TODO Auto-generated method stub

	}

	@Override
	public void undo() {
		// TODO Auto-generated method stub

	}
	
	//@author A0088821X
	@Override
	public String getCommandContent(){
		return EMPTY_STRING;
	}

}
