package com.done.command;

import java.util.logging.Level;

import com.done.storage.InMemStorage;

public class CommandClear extends Command {
	
	public CommandClear(){
		super(CommandType.CLEAR, false);
		commandLogger.log(Level.INFO, "Clear Command Created");
	}

	@Override
	public void execute() {
		InMemStorage.getInstance().delete(null, true);

	}

	@Override
	public void undo() {
		// UNUSED
		//System.out.printf(MESSAGE_ERROR,"undo",this.getCommandType());
	}

}
