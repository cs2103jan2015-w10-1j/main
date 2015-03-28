package com.done.command;

import com.done.storage.InMemStorage;
import com.done.storage.JSONStorage;

public class CommandLoad extends Command {
	
	private String jsonName;

	public CommandLoad(String jsonName) {
		super(CommandType.LOAD);
		this.jsonName = jsonName;
	}

	@Override
	public void execute() {
		JSONStorage jsonPref = JSONStorage.getInstance();
		InMemStorage inMemStorage = InMemStorage.getInstance();
		
		jsonPref.setNewJson(true);
		jsonPref.setJsonNameToPref(jsonName);
		jsonPref.setNewJson(false);
		inMemStorage.loadNew();
		
	}

	@Override
	public void undo() {
		// TODO Auto-generated method stub
		
	}

}
