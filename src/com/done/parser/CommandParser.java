package com.done.parser;

import com.done.parser.CommandParser.CommandType;
import java.util.ArrayList;

public class CommandParser {

	/*
	 * TODO: These commands are for skeletal purpose only* DISPLAY and CLEAR
	 * command especially will be replaced/removed
	 */
	public enum CommandType {
		ADD, DELETE, CLEAR, DISPLAY, EDIT, SEARCH, UNDO, REORDER, MOVE, MARK, REMIND, RECUR, EXIT, INVALID;
	}

	public CommandType getCommandType(String userCommand) {

		String command = CommandUtils.getFirstWord(userCommand);

		if (command.equalsIgnoreCase("add")) {
			return CommandType.ADD;
		} else if (command.equalsIgnoreCase("delete")) {
			return CommandType.DELETE;
		} else if (command.equalsIgnoreCase("display")) {
			return CommandType.DISPLAY;
		} else if (command.equalsIgnoreCase("clear")) {
			return CommandType.CLEAR;
		} else if (command.equalsIgnoreCase("edit")) {
			return CommandType.EDIT;
		} else if (command.equalsIgnoreCase("search")) {
			return CommandType.SEARCH;
		} else if (command.equalsIgnoreCase("undo")) {
			return CommandType.UNDO;
		} else if (command.equalsIgnoreCase("reorder")) {
			return CommandType.REORDER;
		} else if (command.equalsIgnoreCase("move")) {
			return CommandType.MOVE;
		} else if (command.equalsIgnoreCase("mark")) {
			return CommandType.MARK;
		} else if (command.equalsIgnoreCase("remind")) {
			return CommandType.REMIND;
		} else if (command.equalsIgnoreCase("recur")) {
			return CommandType.RECUR;
		} else if (command.equalsIgnoreCase("exit")) {
			return CommandType.EXIT;
		} else {
			return CommandType.INVALID;
		}
	}

	public ArrayList<String> getCommandContent(String userCommand) {
		String currentContent = CommandUtils.removeFirstWord(userCommand);
		assert currentContent != null;
		ArrayList<String> commandContent = CommandUtils
				.processContent(currentContent);
		return commandContent;
	}
	
	public String getCommandContentString(String userCommand){
		return CommandUtils.removeFirstWord(userCommand);
	}
}
