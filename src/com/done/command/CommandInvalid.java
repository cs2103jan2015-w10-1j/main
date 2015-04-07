package com.done.command;

import java.util.logging.Level;

public class CommandInvalid extends Command {
	
	private static final String MESSAGE_INVALID = "The command does not work:(";
	
	//@author A0115777W
	public CommandInvalid(){
		super(CommandType.INVALID, false);
		commandLogger.log(Level.INFO, "Invalid Command Created");

	}

	@Override
	public void execute() {
		// TODO Auto-generated method stub

	}

	@Override
	public void undo() {
		// TODO Auto-generated method stub

	}

}
