package com.done.command;

import java.util.logging.Level;

import com.done.storage.InMemStorage;
import com.done.storage.JsonStorage;

public class CommandLoad extends Command {
	
	private static final String MESSAGE_CREATION = "Load Command Created"; 
	private static final String DIR_TASKS = "tasks//";

	private String jsonName;

	//@author A0115777W
	public CommandLoad(String jsonName) {
		super(CommandType.LOAD, false);
		this.jsonName = jsonName;
		commandLogger.log(Level.INFO, MESSAGE_CREATION);
	}

	//@author A0111830X
	@Override
	public void execute() {
		JsonStorage jsonStorage = JsonStorage.getInstance();
		InMemStorage inMemStorage = InMemStorage.getInstance();

		jsonStorage.setJsonNameToPref(DIR_TASKS + jsonName);
		inMemStorage.loadNew();

	}

	//@author A0111830X-unused
	//undo not required in LOAD
	@Override
	public void undo() {
		// UNUSED
		
	}
	
	//@author A0088821X
	@Override
	public String getCommandContent() {
		return jsonName;
	}

}
