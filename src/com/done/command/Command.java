package com.done.command;

import java.util.Stack;

public abstract class Command {
	private int id;
	//protected static Stack<Command> allCommands;
	private CommandType type;
	private boolean undoable;

	public enum CommandType {
		ADD, DELETE, LOAD, CLEAR, DISPLAY, EDIT, SEARCH, UNDO, REORDER, MOVE, MARK, REMIND, RECUR, EXIT, INVALID;
	}
	
	public Command(CommandType type, boolean undoable){
		this.type = type;
		this.undoable = undoable;
	}

	public CommandType getCommandType(){
		return this.type;
	}
	
	/*private void setCommandType(CommandType type){
		this.type = type;
	}
	
	public List getContent(){
		return this.content;
	}
	
	public void setContent(List content){
		this.content = content;
	}*/
	
	public int getId(){
		return this.id;
	}
	
	public void setId(int id){
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
