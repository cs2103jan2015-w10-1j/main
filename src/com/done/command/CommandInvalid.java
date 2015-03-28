package com.done.command;

public class CommandInvalid extends Command {
	
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
