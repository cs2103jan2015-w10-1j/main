package com.done.logic;

import java.util.List;

import com.done.command.Command;
import com.done.command.Command.CommandType;
import com.done.model.Done;
import com.done.parser.CommandParser;
import com.done.storage.InMemStorage;

public class Logic {
	
	private CommandParser cmdParser;
	private InMemStorage inMemStorage;
	private boolean isSuccessful;
	
	//@author A0115635J
	public Logic(){
		this.inMemStorage = InMemStorage.getInstance();
		this.cmdParser = CommandParser.getInstance();
	}
 	
	public void executeCommand(Command command){
		try{
			command.execute();
			isSuccessful = true;
			if(command.isUndoable()){
				inMemStorage.pushToUndoStack(command);
			}
		}
		catch(Exception e){
			isSuccessful = false;
		}
		
	}
	
	
	public CommandType getCmdType(String userCommand){
		CommandType command = cmdParser.getCommandType(userCommand);
		return command;
	}
	
	public String getCmdContent(String userCommand){
		return cmdParser.getCommandContent(userCommand);
	}
	
	public boolean isSuccessful(){
		return isSuccessful;
	}

	/**
	 * @return the tasks
	 */
	public List<Done> getTasksForUI() {
		return inMemStorage.load();
	}
	
	public List<Done> getSearchedTasksForUI() {
		return inMemStorage.loadSearchResult();
	}
	
 }