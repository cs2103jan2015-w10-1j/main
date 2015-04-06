package com.done.command;

import java.util.logging.Logger;

public abstract class Command {
	private int id;
	private CommandType type;
	private boolean undoable;
	protected static Logger commandLogger = Logger.getLogger("Command");

	//String for reporting error from behaviour that is not supposed to be present.
	//Could be used for logging.
	protected static final String MESSAGE_ERROR = "Error happened when attempting to execute %1$s method in %2$s command\n";

	public enum CommandType {
		ADD, DELETE, LOAD, CLEAR, EDIT, SEARCH, SHOWALL, UNDO, MOVE, DONE, REMIND, RECUR, EXIT, INVALID;
	}

	public Command(CommandType type, boolean undoable) {
		this.type = type;
		this.undoable = undoable;
	}

	// Only getter of CommandType is given as CommandType is not set to be
	// mutable
	public CommandType getCommandType() {
		return this.type;
	}
	
	protected void setCommandType(CommandType type){
		this.type = type;
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public abstract void execute() throws Exception;

	public abstract void undo();

	public boolean isUndoable() {
		return undoable;
	}

	public void setUndoable(boolean undoable) {
		this.undoable = undoable;
	}
}
