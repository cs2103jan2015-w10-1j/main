package com.done.parser;

import static org.junit.Assert.*;

import org.junit.Test;

import com.done.parser.Command.CommandType;

public class TestCommandParser {
	
	CommandParser testedParser = new CommandParser();

	@Test
	public void testGetCommandDeleteValid() {
		Command validCommandDelete = testedParser.getCommand("delete 1");
		assertEquals(validCommandDelete.getCommandType(),CommandType.DELETE);
	}
	
	@Test
	public void testGetCommandDeleteInvalid(){
		Command invalidCommandDelete = testedParser.getCommand("delete aaa");
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
