package com.done.command;

import com.done.storage.InMemStorage;

public class CommandUndo extends Command{
	
	public CommandUndo() {
		super(CommandType.UNDO, false);
	}
	
	@Override
	public void execute() throws Exception {
		Command command = InMemStorage.getInstance().popFromUndoStack();
		command.undo();
	}

	@Override
	public void undo() {
		// UNUSED
		//System.out.printf(MESSAGE_ERROR,"undo",this.getCommandType());
	}

}
