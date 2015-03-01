package com.done.parser;

public class CommandUtils {
	
	public static String removeFirstWord(String userCommand) {
        return userCommand.replace(getFirstWord(userCommand), "").trim();
    }
	
	public static String getFirstWord(String userCommand) {
        return userCommand.trim().split("\\s+")[0];
    }

}
