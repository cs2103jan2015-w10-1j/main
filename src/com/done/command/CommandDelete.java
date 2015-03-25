package com.done.command;

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
		// TODO Auto-generated method stub

	}

	@Override
	public void undo() {
		// TODO Auto-generated method stub

	}

}
