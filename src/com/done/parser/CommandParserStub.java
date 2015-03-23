package com.done.parser;

public class CommandParserStub extends CommandParser {

	public String getCommandContentString(String userCommand) {
		return CommandUtils.removeFirstWord(userCommand);
	}
}
