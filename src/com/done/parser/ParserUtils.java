package com.done.parser;

import java.util.ArrayList;


public class ParserUtils {

	public static String removeFirstWord(String userCommand) {
		return userCommand.replace(getFirstWord(userCommand), "").trim();
	}

	public static String getFirstWord(String userCommand) {
		return userCommand.trim().split("\\s+")[0];
	}

	// At current Stage, only slice the content into parts separated by spaces
	// Might modify in later versions
	public static ArrayList<String> processContent(String content) {
		ArrayList<String> processedContent = new ArrayList<String>();
		String[] contentPieces = content.split("\\s+");
		for (String pieceOfContent : contentPieces) {
			processedContent.add(pieceOfContent);
		}
		return processedContent;
	}

	public static Command makeCommand(String commandWord, String commandContent) {
		// Command command;
		if (commandWord.equalsIgnoreCase("add")) {
			return new CommandAdd(commandContent);
		} else if (commandWord.equalsIgnoreCase("delete")) {
			return new CommandDelete(Integer.parseInt(commandContent));
		} else if (commandWord.equalsIgnoreCase("clear")) {
			return new CommandClear();
		} else if (commandWord.equalsIgnoreCase("search")) {
			return new CommandSearch(commandContent);
		} else {
			return new CommandInvalid();
		}
	}

	public static boolean isContentValid(String commandWord,
			String commandContent) {
		if (commandWord.equalsIgnoreCase("add")) {
			return true;
		} else if (commandWord.equalsIgnoreCase("delete")
				|| commandWord.equalsIgnoreCase("mark")) {
			return isPositiveNonZeroInt(commandContent);
		} else {
			return false;
		}
	}

	public static boolean isPositiveNonZeroInt(String content) {
		try {
			int i = Integer.parseInt(content);
			return (i > 0 ? true : false);
		} catch (NumberFormatException nfe) {
			return false;
		}
	}
}
