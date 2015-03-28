package com.done.command;

import com.done.storage.InMemStorage;
import com.done.storage.JSONStorage;

public class CommandLoad extends Command {
	
	private String jsonName;

	public CommandLoad(String jsonName) {
		super(CommandType.LOAD, false);
		this.jsonName = jsonName;
	}

	@Override
	public void execute() {
		JSONStorage jsonPref = JSONStorage.getInstance();
		InMemStorage inMemStorage = InMemStorage.getInstance();
		
		jsonPref.setJsonNameToPref(jsonName);
		inMemStorage.loadNew();
		
	}

	@Override
	public void undo() {
		// UNUSED
		
	}

}
