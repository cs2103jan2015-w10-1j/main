package com.done.logic;

import java.util.ArrayList;
import java.util.List;

import com.done.parser.CommandParser;
import com.done.result.ExecutionResult;
import com.done.storage.JsonStorage;
import com.done.command.Command;
import com.done.command.Command.CommandType;
import com.done.model.Done;
import com.done.logic.Logic;
import com.done.observer.Observer;

public class LogicFacade {
	
	private Logic logic = new Logic();
	//private List<Observer> observerList = new ArrayList<Observer>();
	
	public ExecutionResult getExecutionResult(String userCommand){

		CommandParser parser = CommandParser.getInstance();
		Command command = parser.parseInputToMakeCommand(userCommand);
		
		
		logic.executeCommand(command);
		boolean isSuccessful = logic.isSuccessful();
		ExecutionResult tempExecutionResult;
		
		if (isSuccessful){
			String commandContent = command.getCommandContent();
			tempExecutionResult = new ExecutionResult(command.getCommandType(), isSuccessful, commandContent);
		} else {
			tempExecutionResult = new ExecutionResult(command.getCommandType(), isSuccessful);
		}
		return tempExecutionResult;	
	}
	
	/*public String getReminder(int taskId){
		return null;
	}*/
	
	public List<Done> getTasks(){
		logic = new Logic();
		return this.logic.getTasksForUI();
	}
	
	public List<Done> getSearchResult(){
		logic = new Logic();
		return this.logic.getSearchedTasksForUI();
	}
	
	
	public String getJsonName(){
		JsonStorage jsonStorage = JsonStorage.getInstance();
		String jsonName = jsonStorage.getJsonNameFromPref();
		return jsonName;
	}
	
	/*public void addUI(Observer o){
		observerList.add(o);
	}
	
	public void notifyUIs(){
	}*/
}
