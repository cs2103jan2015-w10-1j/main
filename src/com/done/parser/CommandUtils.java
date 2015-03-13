package com.done.parser;

import java.util.ArrayList;

public class CommandUtils {
	
	public static String removeFirstWord(String userCommand) {
        return userCommand.replace(getFirstWord(userCommand), "").trim();
    }
	
	public static String getFirstWord(String userCommand) {
        return userCommand.trim().split("\\s+")[0];
    }

	//At current Stage, only slice the content into parts separated by spaces
	//Might modify in later versions
	public static ArrayList<String> processContent(String content){
		ArrayList<String> processedContent = new ArrayList<String>();
		String[]contentPieces = content.split("\\s+");
		for(String pieceOfContent:contentPieces){
			processedContent.add(pieceOfContent);
		}
		return processedContent;
	}
}
