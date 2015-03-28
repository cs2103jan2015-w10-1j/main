package com.done.command;

import com.done.storage.InMemStorage;

public class CommandDelete extends Command {
	
	private int deleteindex;
	
	public CommandDelete(int deleteindex){
		super(CommandType.DELETE);
		this.deleteindex = deleteindex;
	}
	
	public int getDeleteIndex(){
		return this.deleteindex;
	}
	
	public void setDeleteIndex(int newindex){
		this.deleteindex = newindex;
	}

	@Override
	public void execute() {
		InMemStorage inMemStorage = InMemStorage.getInstance();
		inMemStorage.delete(deleteindex);
	}

	@Override
	public void undo() {
		// TODO Auto-generated method stub

	}

}
