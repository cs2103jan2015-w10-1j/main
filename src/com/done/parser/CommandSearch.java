package com.done.parser;

public class CommandSearch extends Command {
	
	private String searchstring;

	public CommandSearch(String searchstring){
		super(CommandType.SEARCH);
		this.searchstring = searchstring;
	}
	
	public String getSearchString(){
		return this.searchstring;
	}
	
	public void setSearchString(String searchstring){
		this.searchstring = searchstring;
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
