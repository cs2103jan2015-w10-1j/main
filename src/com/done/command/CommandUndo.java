package com.done.command;

import com.done.storage.InMemStorage;

public class CommandUndo extends Command{
	
	private Command command;
	
	public CommandUndo() {
		super(CommandType.UNDO, false);
		
		// hand exception here if you throws exception from popFromUndoStack();
		this.command = InMemStorage.getInstance().popFromUndoStack();
		
	}
	
	@Override
	public void execute() {
		// TODO Auto-generated method stub
		command.undo();
	}

	@Override
	public void undo() {
		// TODO Auto-generated method stub
		
	}

}
