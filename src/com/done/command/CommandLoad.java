package com.done.command;

import java.util.logging.Level;

import com.done.storage.InMemStorage;
import com.done.storage.JSONStorage;

public class CommandLoad extends Command {
	
	private String jsonName;

	//@author A0115777W
	public CommandLoad(String jsonName) {
		super(CommandType.LOAD, false);
		this.jsonName = jsonName;
		commandLogger.log(Level.INFO, "Load Command Created");
	}

	//@author A0111830X
	@Override
	public void execute() {
		JSONStorage jsonPref = JSONStorage.getInstance();
		InMemStorage inMemStorage = InMemStorage.getInstance();
		
		jsonPref.setJsonNameToPref(jsonName);
		inMemStorage.loadNew();
		
	}

	//@author A0111830X-unused
	//undo not required in LOAD
	@Override
	public void undo() {
		// UNUSED
		
	}

}
