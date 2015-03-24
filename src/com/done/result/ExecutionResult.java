package com.done.result;

import com.done.parser.CommandParser.CommandType;

public class ExecutionResult {
	private CommandType commandType;
	private boolean isSuccessful;
	private String commandContent;
	
	public ExecutionResult(CommandType commandType, boolean isSuccessful,
			String commandContent) {
		assert isSuccessful;
		this.commandType = commandType;
		this.isSuccessful = isSuccessful;
		this.commandContent = commandContent;
	}
	
	public ExecutionResult(CommandType commandType, boolean isSuccessful){
		assert (!isSuccessful);
		this.commandType = commandType;
		this.isSuccessful = isSuccessful;
	}

	public CommandType getCommandType() {
		return commandType;
	}

	public void setCommandType(CommandType commandType) {
		this.commandType = commandType;
	}

	public boolean isSuccessful() {
		return isSuccessful;
	}

	public void setSuccessful(boolean isSuccessful) {
		this.isSuccessful = isSuccessful;
	}

	public String getCommandContent() {
		return commandContent;
	}

	public void setCommandContent(String commandContent) {
		assert isSuccessful;
		this.commandContent = commandContent;
	}
}
