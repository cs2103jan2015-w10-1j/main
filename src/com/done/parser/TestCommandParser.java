package com.done.parser;

import static org.junit.Assert.*;

import org.junit.Test;

import com.done.command.Command;
import com.done.command.Command.CommandType;

public class TestCommandParser {
	
	CommandParser testedParser = new CommandParser();

	@Test
	//Equivalence partition: String content that is positive integer
	public void testGetCommandDeleteValid() {
		Command validCommandDelete = testedParser.parseInstructionToMakeCommand("delete 1");
		assertEquals(validCommandDelete.getCommandType(),CommandType.DELETE);
	}
	
	@Test
	//Equivalence partition: String content that is not a number
	public void testGetCommandDeleteInvalid(){
		Command invalidCommandDelete = testedParser.parseInstructionToMakeCommand("delete aaa");
		assertEquals(invalidCommandDelete.getCommandType(), CommandType.INVALID);
	}

	@Test
	public void testGetCommandType() {
		assertEquals(testedParser.getCommandType("add 1"), CommandType.ADD);
	}

	@Test
	public void testGetCommandContent() {
		assertEquals(testedParser.getCommandContent("add abcde"), "abcde");
	}

}
