package com.done.logic;

import java.util.ArrayList;
import java.util.List;

import com.done.result.ExecutionResult;
import com.done.parser.CommandParser.CommandType;
import com.done.model.Done;

public class LogicFacade {
	
	private Logic logic;
	
	public ExecutionResult getExecutionResult(String userCommand){
		ExecutionResult tempExecutionResult = new ExecutionResult(CommandType.ADD, true, "Task 1 added.");
		return tempExecutionResult;	
	}
	
	public List<Done> getTasks(){
		this.logic.getTasksForUI();
	}
}
