package com.done.logic;

import com.done.parser.CommandParser.CommandType;
import com.done.parser.CommandParserStub;
import com.done.storage.JSONStorage;

public class LogicStub extends Logic {

	private CommandParserStub cmdParser;

	public CommandType getCmdType(String usercommand) {
		return cmdParser.getCommandType(usercommand);
	}

	public String getCmdContent(String usercommand) {
		return cmdParser.getCommandContentString(usercommand);
	}

	public void storeTo(String jsonName) {
		JSONStorage jsonPref = new JSONStorage();
		jsonPref.setJsonNameToPref(jsonName);
	}

}