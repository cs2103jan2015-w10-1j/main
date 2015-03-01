package com.done.parser;

import com.done.logic.Utils;

public class CommandParser {
	
	public enum CommandType{
		ADD, CLEAR, EXIT, INVALID;
	}
	
	public CommandType getCommandType(String userCommand){
		
		String command = Utils.getFirstWord(userCommand);
		
		if(command.equalsIgnoreCase("add")){
			return CommandType.ADD;
		}else if(command.equalsIgnoreCase("clear")){
			return CommandType.CLEAR;
		}else if(command.equalsIgnoreCase("exit")){
			return CommandType.EXIT;
		}else{
			return CommandType.INVALID;
		}
	}
}
