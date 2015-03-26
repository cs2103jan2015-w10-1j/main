package com.done.logic;

import java.util.ArrayList;
import java.util.List;

import com.done.result.ExecutionResult;
import com.done.command.Command.CommandType;
import com.done.model.Done;
import com.done.logic.Logic;

public class LogicFacade {
	
	private Logic logic;
	
	public ExecutionResult getExecutionResult(String userCommand){

		CommandType commandType = logic.getCmdType(userCommand);
		String commandContent = logic.getCmdContent(userCommand);
		logic.executeCommand(commandType, commandContent);
		boolean isSuccessful = logic.isSuccessful();
		ExecutionResult tempExecutionResult;
		
		if (isSuccessful){
			tempExecutionResult = new ExecutionResult(commandType, isSuccessful, commandContent);
		} else {
			tempExecutionResult = new ExecutionResult(commandType, isSuccessful);
		}
		return tempExecutionResult;	
	}
	
	public List<Done> getTasks(){
		logic = new Logic();
		return this.logic.getTasksForUI();
	}
}
