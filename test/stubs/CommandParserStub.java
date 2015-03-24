package stubs;

import java.util.ArrayList;

import com.done.parser.ParserUtils;

public class CommandParserStub {

	/*
	 * TODO: These commands are for skeletal purpose only* DISPLAY and CLEAR
	 * command especially will be replaced/removed
	 */
	public enum CommandTypeStub {
		ADD, DELETE, CLEAR, DISPLAY, EDIT, SEARCH, UNDO, REORDER, MOVE, MARK, REMIND, RECUR, EXIT, INVALID, LOAD;
	}

	public CommandTypeStub getCommandType(String userCommand) {

		String command = ParserUtils.getFirstWord(userCommand);

		if (command.equalsIgnoreCase("add")) {
			return CommandTypeStub.ADD;
		} else if (command.equalsIgnoreCase("delete")) {
			return CommandTypeStub.DELETE;
		} else if (command.equalsIgnoreCase("display")) {
			return CommandTypeStub.DISPLAY;
		} else if (command.equalsIgnoreCase("clear")) {
			return CommandTypeStub.CLEAR;
		} else if (command.equalsIgnoreCase("edit")) {
			return CommandTypeStub.EDIT;
		} else if (command.equalsIgnoreCase("search")) {
			return CommandTypeStub.SEARCH;
		} else if (command.equalsIgnoreCase("undo")) {
			return CommandTypeStub.UNDO;
		} else if (command.equalsIgnoreCase("reorder")) {
			return CommandTypeStub.REORDER;
		} else if (command.equalsIgnoreCase("move")) {
			return CommandTypeStub.MOVE;
		} else if (command.equalsIgnoreCase("mark")) {
			return CommandTypeStub.MARK;
		} else if (command.equalsIgnoreCase("remind")) {
			return CommandTypeStub.REMIND;
		} else if (command.equalsIgnoreCase("recur")) {
			return CommandTypeStub.RECUR;
		} else if (command.equalsIgnoreCase("exit")) {
			return CommandTypeStub.EXIT;
		} else if (command.equalsIgnoreCase("load")) {
			return CommandTypeStub.LOAD;
		} else {
			return CommandTypeStub.INVALID;
		}
	}

	public ArrayList<String> getCommandContent(String userCommand) {
		String currentContent = ParserUtils.removeFirstWord(userCommand);
		assert currentContent != null;
		ArrayList<String> commandContent = ParserUtils
				.processContent(currentContent);
		return commandContent;
	}

	public String getCommandContentString(String userCommand) {
		return ParserUtils.removeFirstWord(userCommand);
	}
}
