package com.done;

public class Utils {
	
	public String removeFirstWord(String userCommand) {
        return userCommand.replace(getFirstWord(userCommand), "").trim();
    }
	
	public String getFirstWord(String userCommand) {
        return userCommand.trim().split("\\s+")[0];
    }

}
