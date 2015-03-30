package com.done.command;

public class CommandInvalid extends Command {
	
	private static final String MESSAGE_INVALID = "The command does not work:(";
	
	public CommandInvalid(){
		super(CommandType.INVALID, false);
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
