package com.done.command;

public class CommandSearch extends Command {
	
	private String searchString;

	public CommandSearch(String searchString){
		super(CommandType.SEARCH, false);
		this.searchString = searchString;
	}
	
	public String getSearchString(){
		return this.searchString;
	}
	
	public void setSearchString(String searchstring){
		this.searchString = searchstring;
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
