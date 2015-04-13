package com.done.command;

import java.util.logging.Level;

import com.done.storage.InMemStorage;

public class CommandUndo extends Command{
	
	private static final String MESSAGE_CREATION = "Undo Command Created"; 
	private static final String EMPTY_STRING = "";
	
	//@author A0115777W
	public CommandUndo() {
		super(CommandType.UNDO, false);
		commandLogger.log(Level.INFO, MESSAGE_CREATION);
	}
	
	//@author A0111830X
	@Override
	public void execute() throws Exception {
		Command command = InMemStorage.getInstance().popFromUndoStack();
		command.undo();
	}
	
	//@author A0111830X-unused
	//redo function not required
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
