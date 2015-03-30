package com.done.logic;

import java.util.ArrayList;
import java.util.List;

import com.done.parser.CommandParser;
import com.done.result.Result;
import com.done.storage.JSONStorage;
import com.done.command.Command;
import com.done.command.Command.CommandType;
import com.done.model.Done;
import com.done.logic.Logic;
import com.done.observer.Observer;

public class LogicFacade {
	
	private Logic logic;
	private List<Observer> observerList = new ArrayList<Observer>();
	
	public Result getExecutionResult(String userCommand){

		// sorry to comment this but parsing the command should be done in the parser component
		//CommandType commandType = logic.getCmdType(userCommand);
		String commandContent = logic.getCmdContent(userCommand);
		CommandParser parser = new CommandParser();
		Command command = parser.parseInputToMakeCommand(userCommand);
		
		logic.executeCommand(command);
		//ExecutionResult tempExecutionResult = new ExecutionResult(command.getCommandType(), true);
		boolean isSuccessful = logic.isSuccessful();
		Result tempExecutionResult;
		
		if (isSuccessful){
			tempExecutionResult = new Result(command.getCommandType(), isSuccessful, commandContent);
		} else {
			tempExecutionResult = new Result(command.getCommandType(), isSuccessful);
		}
		return tempExecutionResult;	
	}
	
	public List<Done> getTasks(){
		logic = new Logic();
		return this.logic.getTasksForUI();
	}
	
	public List<Done> getSearchResult(){
		logic = new Logic();
		return this.logic.getSearchedTasksForUI();
	}
	
	
	public String getJsonName(){
		JSONStorage jsonStorage = JSONStorage.getInstance();
		String jsonName = jsonStorage.getJsonNameFromPref();
		return jsonName;
	}
	
	public void addUI(Observer o){
		observerList.add(o);
	}
	
	public void notifyUIs(){
		int taskId = 1; //this has to be changed to the task id needed to be reminded - put to avoid compilation error
		for(Observer o: observerList){
			o.updateReminder(taskId);
		}
	}
}
