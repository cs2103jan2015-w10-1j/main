package com.done.command;

import java.util.logging.Level;

public class CommandExit extends Command {
	
	private boolean isNormal;
	private static final int STATUS_NORMAL = 0;
	private static final int STATUS_ABNORMAL = 1;
	
	public CommandExit(boolean isNormal){
		super(CommandType.EXIT,false);
		this.isNormal = isNormal;
		commandLogger.log(Level.INFO, "Exit Command Created");
	}

	@Override
	public void execute() throws Exception {
		if(isNormal){
			System.exit(STATUS_NORMAL);
		}
		System.exit(STATUS_ABNORMAL);
	}

	@Override
	public void undo() {
		//not implemented
	}

}
