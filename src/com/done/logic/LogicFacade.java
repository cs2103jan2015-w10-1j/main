package com.done.logic;

import java.util.ArrayList;
import java.util.List;

import com.done.result.ExecutionResult;
import com.done.parser.CommandParser.CommandType;
import com.done.model.Done;
import com.done.logic;

public class LogicFacade {
	
	private Logic logic;
	
	public ExecutionResult getExecutionResult(String userCommand){

		private CommandType commandType = logic.getCmdType(userCommand);
		private String commandContent = logic.getCmdContent(userCommand);
		private boolean isSuccessful = logic.isSuccessful();
		
		if (isSuccessful){
			ExecutionResult tempExecutionResult = new ExecutionResult(commandType, isSuccessful, commandContent);
		} else {
			ExecutionResult tempExecutionResult = new ExecutionResult(commandType, isSuccessful);
		}
		return tempExecutionResult;	
	}
	
	public List<Done> getTasks(){
		logic = new Logic();
		this.logic.getTasksForUI();
	}
}
