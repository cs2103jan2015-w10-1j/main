package com.done.command;

import java.util.logging.Level;

public class CommandExit extends Command {
	
	private static final String MESSAGE_CREATION = "Exit Command Created"; 
	private static final int STATUS_NORMAL = 0;
	private static final int STATUS_ABNORMAL = 1;
	private static final String EMPTY_STRING = "";
	
	private boolean isNormal;

	//@author A0115777W
	public CommandExit(boolean isNormal){
		super(CommandType.EXIT,false);
		this.isNormal = isNormal;
		commandLogger.log(Level.INFO, MESSAGE_CREATION);
	}

	@Override
	public void execute() throws Exception {
		if(!isNormal){
			System.exit(STATUS_ABNORMAL);
		}
		System.exit(STATUS_NORMAL);
	}

	@Override
	public void undo() {
		//not implemented
	}
	
	//@author A0088821X
	@Override
	public String getCommandContent(){
		return EMPTY_STRING;
	}

}
