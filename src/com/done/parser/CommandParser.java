package com.done.parser;

import java.util.logging.Level;
import java.util.logging.Logger;

import com.done.command.Command;
import com.done.command.Command.CommandType;


public class CommandParser {

	private static CommandParser instance = null;
	private static Logger parserLogger = Logger.getLogger("CommandParser");
	
	//@author A0115777W
	private CommandParser() {

	}

	public static CommandParser getInstance() {
		if (instance == null) {
			instance = new CommandParser();
		}
		return instance;
	}

	public Command parseInputToMakeCommand(String userCommand) {

		parserLogger.log(Level.INFO, "Input passed to make Command");
		String command = userCommand.trim();
		String commandWord = ParserUtility.getFirstWord(command);
		String commandContent = ParserUtility.removeFirstWord(command);
		return makeCommand(commandWord, commandContent);

	}
	
	//@author A0115777W
	private Command makeCommand(String commandWord, String commandContent) {
		if (commandWord.equalsIgnoreCase("add")) {
			return ParserUtility.makeAdd(commandContent);
		} else if (commandWord.equalsIgnoreCase("delete")) {
			return ParserUtility.makeDelete(commandContent);
		} else if (commandWord.equalsIgnoreCase("edit")) {
			return ParserUtility.makeEdit(commandContent);
		} else if (commandWord.equalsIgnoreCase("clear")) {
			return ParserUtility.makeClear(commandContent);
		} else if (commandWord.equalsIgnoreCase("move")) {
			return ParserUtility.makeMove(commandContent);
		} else if (commandWord.equalsIgnoreCase("search")) {
			return ParserUtility.makeSearch(commandContent);
		} else if (commandWord.equalsIgnoreCase("showall")) {
			return ParserUtility.makeShowAll();
		} else if (commandWord.equalsIgnoreCase("done")) {
			return ParserUtility.makeDone(commandContent);
		} else if (commandWord.equalsIgnoreCase("cleardone")) {
			return ParserUtility.makeClearDone();
		} else if (commandWord.equalsIgnoreCase("recur")) {
			return ParserUtility.makeRecur(commandContent);
		} else if (commandWord.equalsIgnoreCase("remind")) {
			return ParserUtility.makeRemind(commandContent);
		} else if (commandWord.equalsIgnoreCase("load")) {
			return ParserUtility.makeLoad(commandContent);
		} else if (commandWord.equalsIgnoreCase("undo")) {
			return ParserUtility.makeUndo();
		} else if (commandWord.equalsIgnoreCase("exit")) {
			return ParserUtility.makeExit();
		} else {
			return ParserUtility.makeInvalid();
		}
	}

	public CommandType getCommandType(String userCommand) {

		String command = ParserUtility.getFirstWord(userCommand);

		if (command.equalsIgnoreCase("add")) {
			return CommandType.ADD;
		} else if (command.equalsIgnoreCase("delete")) {
			return CommandType.DELETE;
		} else if (command.equalsIgnoreCase("clear")) {
			return CommandType.CLEAR;
		} else if (command.equalsIgnoreCase("edit")) {
			return CommandType.EDIT;
		} else if (command.equalsIgnoreCase("load")) {
			return CommandType.LOAD;
		} else if (command.equalsIgnoreCase("search")) {
			return CommandType.SEARCH;
		} else if (command.equalsIgnoreCase("showall")) {
			return CommandType.SHOWALL;
		} else if (command.equalsIgnoreCase("undo")) {
			return CommandType.UNDO;
		} else if (command.equalsIgnoreCase("move")) {
			return CommandType.MOVE;
		} else if (command.equalsIgnoreCase("done")) {
			return CommandType.DONE;
		} else if (command.equalsIgnoreCase("cleardone")) {
			return CommandType.CLEARDONE;
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

	public String getCommandContent(String userCommand) {
		String currentContent = ParserUtility.removeFirstWord(userCommand);
		return currentContent;
	}
	
}
