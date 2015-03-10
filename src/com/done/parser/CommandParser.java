package com.done.parser;

import java.util.ArrayList;

public class CommandParser {
	
	/* TODO: These commands are for skeletal purpose only
	** DISPLAY and CLEAR command especially will be replaced/removed
	*/
	public enum CommandType{
		ADD, DELETE, CLEAR, DISPLAY, EXIT, INVALID;
	}
	
	public CommandType getCommandType(String userCommand){
		
		String command = CommandUtils.getFirstWord(userCommand);
		
		if(command.equalsIgnoreCase("add")){
			return CommandType.ADD;
		}else if(command.equalsIgnoreCase("delete")){
			return CommandType.DELETE;
		}else if(command.equalsIgnoreCase("display")){
			return CommandType.DISPLAY;
		}else if(command.equalsIgnoreCase("clear")){
			return CommandType.CLEAR;
		}else if(command.equalsIgnoreCase("exit")){
			return CommandType.EXIT;
		}else{
			return CommandType.INVALID;
		}
	}
	
	public ArrayList<String> getCommandContent (String userCommand){
		String currentContent = CommandUtils.removeFirstWord(userCommand);
		ArrayList<String> commandContent = CommandUtils.processContent(currentContent);
		return commandContent;
	}
}
