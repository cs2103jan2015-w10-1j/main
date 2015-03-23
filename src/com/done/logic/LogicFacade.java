package com.done.logic;

import com.done.result.ExecutionResult;
import com.done.parser.CommandParser.CommandType;

public class LogicFacade {
	public ExecutionResult getExecutionResult(String userCommand){
		ExecutionResult tempExecutionResult = new ExecutionResult(CommandType.ADD, true, "Task 1 added.");
		return tempExecutionResult;	
	}
}
