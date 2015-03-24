package com.done.parser;

import java.util.List;

public abstract class Command {
	private int id;
	//protected List content;
	private CommandType type;

	public enum CommandType {
		ADD, DELETE, CLEAR, DISPLAY, EDIT, SEARCH, UNDO, REORDER, MOVE, MARK, REMIND, RECUR, EXIT, INVALID;
	}
	
	public Command(CommandType type){
		this.type = type;
	}

	public CommandType getCommandType(){
		return this.type;
	}
	
	private void setCommandType(CommandType type){
		this.type = type;
	}
	
	/*public List getContent(){
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
	
	public abstract void execute();
	public abstract void undo();
}
